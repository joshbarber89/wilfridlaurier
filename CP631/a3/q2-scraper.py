import concurrent.futures
from urllib import request
from urllib.error import HTTPError
import sys
from bs4 import BeautifulSoup as bs
import json

# The base url that is going to be scraped. Leaving the end of the url open so it can be concated later
base = "https://datacrunch.9c9media.ca/statsapi/sports/hockey/leagues/nhl/sortablePlayerSeasonStats/skater?brand=tsn&type=json&seasonType=regularSeason&season="

# Grab page data and JSON parse it
def retrievePage(year):
    try:
        doc = request.urlopen(base + year)
    except HTTPError as e:
        return
    return json.loads(str(bs(doc.read(), "html.parser")))


sys.setrecursionlimit(10000)

# For each year of NHL stats, lets scrape.
for year in ["2018", "2019", "2020", "2021"]:

    with concurrent.futures.ThreadPoolExecutor(max_workers=20) as executor:
        # grab page of JSON data
        stats = executor.submit(retrievePage, year)
        print('Retrieve NHL stats for year {}'.format(year))
        pages = []
        # if page grab is complete append to pages array
        if (concurrent.futures.as_completed(stats)):
            try:
                pages.append((year, stats.result()))
            except:
                pages.append((year, None))
    # write out the stat page grabed for iterated year
    with open('stats_page_{}.json'.format(year), 'w') as file_:
        file_.writelines("\n".join(json.dumps((year, page_i[1])) for page_i in pages))
