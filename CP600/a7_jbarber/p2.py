# Josh Barber
import os
import math

allEdges = []
letterMap = []
numberOfVertices = 0

txtPath = os.path.dirname(__file__)
with open(os.path.join(txtPath, "input2.txt")) as f:
    for line in f:
        for edge in line.rstrip().split(' '):
            if edge != '' and len(edge.split(',')) > 1:
                allEdges.append(edge.split(','))
            elif edge != '' and edge.isnumeric():
                numberOfVertices = int(edge)

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

def FloydWarshall(G):
    # Create Matrix and Alternative Matrix
    M1 = [ [ math.inf for x in range(G.n) ] for y in range(G.n) ]
    M2 = [ [ math.inf for x in range(G.n) ] for y in range(G.n) ]

    # Add weights to M1
    for u in range(G.n):
        for v, wt in G.adj[u]:
            M1[u][v] = wt

    # The weight to itself is 0, update Matrix to reflect that
    for u in range(G.n):
        M1[u][u] = 0

    for k in range(G.n):
        for i in range(G.n):
            for j in range(G.n):
                # Get minimum path and input into alternative Matrix
                M2[i][j] = min(M1[i][j], M1[i][k] + M1[k][j])

        # Alternate Matrix
        M1, M2 = M2, M1
        # Print Step
        PrintMatrix(M1)

    return M1

def PrintMatrix(M):
    # Looks dirty but prints clean
    print(" ", end="")
    for k in range(len(M)):
        print(" %3c" % GetLetter(k), end="")
    print("")
    for i in range(len(M)):
        print(GetLetter(i), end="")
        for j in range(len(M)):
            if M[i][j] == math.inf:
                print(" ---", end="")
            else:
                print(" %3d" % M[i][j], end="")
        print("")
    print("\n")

M = FloydWarshall(G)
