# Josh Barber
import os
import math

allEdges = []
letterMap = []
numberOfVertices = 0
startingLetter = ''

txtPath = os.path.dirname(__file__)
with open(os.path.join(txtPath, "input1.txt")) as f:
    for line in f:
        for edge in line.rstrip().split(' '):
            if edge != '' and len(edge.split(',')) > 1:
                allEdges.append(edge.split(','))
            elif edge != '' and edge.isnumeric():
                numberOfVertices = int(edge)
            else:
                startingLetter = edge

f.close()

print('\nEdges input:')
print(allEdges)
print('\n')

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
        if (len(edge) == 3):
            newEdge.append(int(edge[2]))
        newAllEdges.append(newEdge)
    return newAllEdges

def GetLetterIndex(l):
    for i in range(len(letterMap)):
        if (l == letterMap[i]):
            return i

def GetLetter(i):
    if (i != None):
        return letterMap[i]
    return ''

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


newEdges = MapLetters(allEdges, 'ASC')

G = Graph(numberOfVertices, True)
G.AddEdgeListWeighted(newEdges)
G.Print()



def BellmanFord(G, startingVertex):

    print("\nStarting vertex {}\n".format(GetLetter(startingVertex)))

    # Initialize starting distance and arrays
    G.d = [math.inf] * G.n
    G.pi = [None] * G.n

    G.d[startingVertex] = 0

    # Relax all edges
    for i in range(1,G.n-1):
        for u in range(G.n):
            connections = ''
            for v, wt in G.adj[u]:
                connections += GetLetter(u) + '-(' + str(wt) + ')->' + GetLetter(v) + ', '
            print("Adjacent connections from {}: {}".format(GetLetter(u), connections))
            for v, wt in G.adj[u]:
                if G.d[u] + wt < G.d[v]:
                    print("Found shorter path to {} from {} with weight {}, previous weight {}".format(GetLetter(v), GetLetter(startingVertex), G.d[u] + wt, G.d[v]))
                    G.d[v] = G.d[u] + wt
                    G.pi[v] = u

    # Make sure there are no negative cycles
    for u in range(G.n):
        for v, wt in G.adj[u]:
            if G.d[v] > G.d[u] + wt:
                print("Graph contains negative weight cycle")
                return

    print("\n")
    for u in range(G.n):
        print("Shortest Weight to {} from {}: {} π: {}".format(GetLetter(u), GetLetter(startingVertex), G.d[u], GetLetter(G.pi[u])))
    print("\n")

BellmanFord(G, GetLetterIndex(startingLetter))
