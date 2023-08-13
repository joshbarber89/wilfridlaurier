# Josh Barber
import os

allEdges = []
numberOfVertices = 0

txtPath = os.path.dirname(__file__)
with open(os.path.join(txtPath, "input2.txt")) as f:
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


def DFS_1(G):
    G.visited = G.d = G.f = [0] * G.n
    S = []
    for u in range(G.n):
        if G.visited[u] == 0:
            DFS_Visit_1(G, u, S)
    print("\nStack: {}\n".format(GetLetters(S)))
    return S

def DFS_Visit_1(G, u, S):
    G.visited[u] = 1
    print("Discover DFS_1 {}".format(GetLetter(u)))
    for e in G.adj[u]:
        v = e[0]
        if G.visited[v] == 0:
            print("\tTree Edge: {} from {}".format(GetLetter(v), GetLetter(u)))
            DFS_Visit_1(G, v, S)
        elif G.visited[v] == 1:
            print("\tBack Edge: {} from {}".format(GetLetter(v), GetLetter(u)))
        elif G.visited[v] == 2:
            if G.d[u] < G.d[v] and G.f[v] < G.f[u]:
                print("\tForward Edge: {} from {}".format(GetLetter(v), GetLetter(u)))
            else:
                print("\tCross Edge: {} from {}".format(GetLetter(v), GetLetter(u)))
    G.visited[u] = 2
    print("Finsihed DFS_1 {}".format(GetLetter(u)))
    S.append(u)

def DFS_2(G, S):
    G.visited = G.d = G.f = [0] * G.n
    sccList = [ ]
    while len(S) > 0:
        u = S.pop()
        if G.visited[u] == 0:
            scc = [ ]
            DFS_Visit_2(G, u, scc)
            sccList.append(GetLetters(scc))
    return sccList

def DFS_Visit_2(G, u, scc):
    G.visited[u] = 1
    print("Discover DFS_2 {}".format(GetLetter(u)))
    scc.append(u)
    for e in G.adj[u]:
        v = e[0]
        if G.visited[v] == 0:
            print("\tTree Edge: {} from {}".format(GetLetter(v), GetLetter(u)))
            DFS_Visit_2(G, v, scc)
        elif G.visited[v] == 1:
            print("\tBack Edge: {} from {}".format(GetLetter(v), GetLetter(u)))
        elif G.visited[v] == 2:
            if G.d[u] < G.d[v] and G.f[v] < G.f[u]:
                print("\tForward Edge: {} from {}".format(GetLetter(v), GetLetter(u)))
            else:
                print("\tCross Edge: {} from {}".format(GetLetter(v), GetLetter(u)))
    G.visited[u] = 2
    print("Finished DFS_2 {}".format(GetLetter(u)))

def Transpose(G):
    Gt = Graph(G.n, True)
    for u in range(G.n):
        for e in G.adj[u]:
            v = e[0]
            Gt.AddEdge(v, u)
    return Gt

def Strongly_Connected_Components(G):
    S = DFS_1(G)
    Gt = Transpose(G)
    sccList = DFS_2(Gt, S)
    return sccList

print("Lexicographically increasing order:\n")
newEdges = MapLetters(allEdges, 'ASC')

G = Graph(numberOfVertices, True)
G.AddEdgeList(newEdges)
G.Print()

sccList = Strongly_Connected_Components(G)

print("\nsccList: {}\n".format(sccList))

print("\nLexicographically descreasing order: \n")
newEdges = MapLetters(allEdges, 'DESC')

G = Graph(numberOfVertices, True)
G.AddEdgeList(newEdges)
G.Print()

sccList = Strongly_Connected_Components(G)

print("\nsccList: {}\n".format(sccList))