import findspark
findspark.init()
import pyspark
from operator import add
import json
import matplotlib.pyplot as plt

#Python 3.6 was used.
conf = pyspark.SparkConf().setAppName("NHLStats")

sc = pyspark.SparkContext(conf=conf)

# Grab all local stats pages with a number int them
number_range="[0-9]"
stats_page_json = sc.textFile("./*page*"+number_range+"*.json")

# RDD data structure loads all NHL stats for all years
stats_page = stats_page_json.map(json.loads)

# RDD structure with all season stat values flatmapped to have just players, no years as keys
stats_page_for_all_seasons = stats_page.flatMap(lambda x: x[1])

#RDD structure that groups stats for each player per season
base_combined_player_stats = stats_page_for_all_seasons.groupBy(lambda x: x['playerId']).mapValues(list)

# Convert string values from JSON into ints and grab important statistics and map it out
def getStatsCombined(val):
    id = 0
    name = ""
    gp = 0
    totalpoints = 0
    avg = 0
    goals = 0
    assists = 0
    plusMinus = 0
    blockedShots = 0
    hits = 0
    shots = 0
    pim = 0
    shotPercentage = 0
    teamId = ''
    if val:
        for v in val:
            id = v['playerId']
            stats = v['stats']
            teamId = stats['competitor-seo-identifier']
            totalpoints += int(stats['points'])
            gp += int(stats['gamesPlayed'])
            name = stats['firstName'] + " " + stats['lastName']
            goals += int(stats['goals'])
            assists += int(stats['assists'])
            plusMinus += int(stats['plusMinus'])
            blockedShots += int(stats['blockedShots'])
            hits += int(stats['hits'])
            shots += int(stats['shotsOnGoal'])
            pim += int(stats['penaltyInMinutes'])
        avg = totalpoints / gp
        shotPercentage = shots / goals if goals > 0 else 0
    return {
        "id": id, "team": teamId, "name": name, "gamesPlayed" : gp, "points": totalpoints,
        "averagePointsPerGame": avg, "goals" : goals, "assists": assists,
        "plusMinus": plusMinus, "blockedShots": blockedShots, "hits": hits,
        "shots": shots, "shotPercentage": shotPercentage, "PIM": pim
    }

combined_player_season_stats = base_combined_player_stats.map(lambda x: x[1]).map(getStatsCombined)

#With the mapping, its easy now to see who the top performers are over all NHL seasons.
top_performers_over_seasons = combined_player_season_stats.filter(lambda x: x['gamesPlayed'] > 50).sortBy(lambda x: x['averagePointsPerGame'], False)

top_ten = top_performers_over_seasons.map(lambda x: x['name']).take(10)

print(top_ten)

# Map stats to specific teams from the players
def teamStats(val):
    goals = 0
    if val:
        goals += val['goals']

    return goals

goals_per_team = combined_player_season_stats.keyBy(lambda a: a['team']).mapValues(teamStats).reduceByKey(add).collect()

#Plot the goals per team
years = stats_page.keys().collect()
years = [int(x) for x in years]

teams = list()
values = list()
for k,x in goals_per_team:
    teams.append(k[:3])
    values.append(x)
teams.sort()

ylabel = "Goals over " + str(min(years)) + " to " + str(max(years))

plt.bar(teams, values)
plt.ylabel(ylabel)
plt.xlabel("Teams")
plt.show()

#Combined statistics per season for all players
# mapped out the important stats
def seasonCombinedStats(val):
    goals = 0
    ppgoals = 0
    pims = 0
    if val:
        for v in val:
            stats = v['stats']
            goals += int(stats['goals'])
            ppgoals += int(stats['ppGoals'])
            pims += int(stats['penaltyInMinutes'])
    return {'goals':goals, 'pim': pims, 'ppgoals': ppgoals}

season_combined_stats = stats_page.mapValues(seasonCombinedStats)

#Plot the graph of combined season statistics
years = list(season_combined_stats.map(lambda x: x[0]).collect())
goals = list(season_combined_stats.map(lambda x: x[1]['goals']).collect())
pim = list(season_combined_stats.map(lambda x: x[1]['pim']).collect())
ppgoals = list(season_combined_stats.map(lambda x: x[1]['ppgoals']).collect())


plt.plot(years,goals,'.b--', years, pim, '.g:', years, ppgoals, '.r-')
plt.legend(('Goals', 'PIM', 'PP Goals'),
           loc='upper center', shadow=True)
plt.show()


