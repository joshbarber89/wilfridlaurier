# Josh Barber
import os

######################################
###
###  Choose your test case, options 1 to 3
###
###  First case graph from assignment 6, Second case 4 clique example graph, Third case 5 clique example graph
###
######################################
testCase = 1
######################################
###
###
######################################



allEdges = []
numberOfVertices = 0

txtPath = os.path.dirname(__file__)
with open(os.path.join(txtPath, "input"+str(testCase)+".txt")) as f:
    for line in f:
        for edge in line.rstrip().split(' '):
            if edge != '' and len(edge.split(',')) > 1:
                allEdges.append(edge.split(','))
            elif edge != '':
                numberOfVertices = int(edge)

f.close()

print('\nEdges input:')
print(allEdges)
print('\n')

letterMap = []

def areEqual(arr1, arr2):
    if (len(arr1) != len(arr2)):
        return False
    arr1.sort()
    arr2.sort()
    for i in range(len(arr1)):
        if (arr1[i] != arr2[i]):
            return False
    return True


def MapLetters(allEdges, order = 'ASC'):
    for i in range(len(allEdges)):
        edge = allEdges[i]
        for j in range(2):
            letterInMap = False
            for k in range(len(letterMap)):
                if letterMap[k] == edge[j]:
                    letterInMap = True
            if (not letterInMap):
                letterMap.append(edge[j])

    if (order == 'ASC'):
        letterMap.sort()
    else:
        letterMap.sort(reverse=True)

    newAllEdges = []
    for i in range(len(allEdges)):
        edge = allEdges[i]
        newEdge = []
        for j in range(2):
            letter = edge[j]
            for k in range(len(letterMap)):
                if letterMap[k] == letter:
                    newEdge.append(k)
        newAllEdges.append(newEdge)
    return newAllEdges

def GetLetterIndex(l):
    for i in range(len(letterMap)):
        if (l == letterMap[i]):
            return i

def GetLetter(i):
    return letterMap[i]

def GetLetters(array):
    letterArray = []
    for i in range(len(array)):
        letterArray.append(GetLetter(array[i]))

    return letterArray

class Graph:
    def __init__(self, n, directed):
        self.n = n
        self.directed = directed
        self.adj = [[] for i in range(n)]   # n adjacency lists each empty

    def AddVertex(self):
        self.adj.append([])
        self.n = self.n + 1

    def AddEdge(self, u, v, wt=1):
        self.adj[u].append([v,wt])
        if not self.directed: self.adj[v].append([u,wt])

    def AddEdgeList(self, elist):
        for e in elist:
            self.AddEdge(e[0], e[1])

    def AddEdgeListWeighted(self, elist):
        for e in elist:
            self.AddEdge(e[0], e[1], e[2])

    def Print(self):
        for u in range(G.n):
            print ("v({}): ".format(GetLetter(u)))
            for e in G.adj[u]:
                print(" <{},{},{}>".format( GetLetter(u), GetLetter(e[0]),e[1]))
        print("\n")

def CliqueCheck(connections, adjConnections):
    cliqueArray = []
    for u in connections:
        for v in adjConnections:
            if u == v:
                cliqueArray.append(u)
    return cliqueArray


def MaxCliques(G: Graph):

    returnVertices = []
    maxClique = 0

    for u in range(G.n):
        connections = []
        connections.append(u)
        for e in G.adj[u]:
            connections.append(e[0])
        connections.sort()
        count = 0
        for v in connections:
            if v != u:
                adjConnections = []
                adjConnections.append(v)

                for adj in G.adj[v]:
                    adjConnections.append(adj[0])

                count += 1
                cliqueArray = CliqueCheck(connections, adjConnections)

                if (count == len(cliqueArray) and maxClique < len(cliqueArray)):
                    maxClique = len(cliqueArray)
                    returnVertices = cliqueArray

    return returnVertices


newEdges = MapLetters(allEdges, 'ASC')

G = Graph(numberOfVertices, False)
G.AddEdgeList(newEdges)
G.Print()

vertices = MaxCliques(G)

print("The maximum clique in this graph is {}\n".format(len(vertices)))
print("The connected vertices involved in the maximum clique are:")
print(GetLetters(vertices))
print()
