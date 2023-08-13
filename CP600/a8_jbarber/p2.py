# Josh Barber
import os



######################################
###
###  Choose your test case, options 1 to 3
###
###  First case, Find proper MST. Second case, too many verticies. Error. Third case, edges pointing to themselves
###
######################################
testCase = 1
######################################
###
###
######################################




allEdges = []
letterMap = []
numberOfVertices = 0

txtPath = os.path.dirname(__file__)
with open(os.path.join(txtPath, "input2-"+str(testCase)+".txt")) as f:
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


class Graph:
    def __init__(self, n, directed):
        self.initialEdgeList = None
        self.edges = []
        self.n = n
        self.directed = directed
        self.adj = [[] for i in range(n)]   # n adjacency lists each empty

    def AddVertex(self):
        self.adj.append([])
        self.n = self.n + 1

    def AddEdge(self, u, v, wt=1):
        try:
            if u == v:
                raise
            else:
                self.edges.append([u,v,wt])
                self.adj[u].append([v,wt])
                if not self.directed: self.adj[v].append([u,wt])
        except:
            print("More verticies than specified. Or edges are pointed to themselves\n")
            exit()

    def AddEdgeList(self, elist):
        self.initialEdgeList = elist
        for e in elist:
            self.AddEdge(e[0], e[1])

    def AddEdgeListWeighted(self, elist):
        self.initialEdgeList = elist
        for e in elist:
            self.AddEdge(e[0], e[1], e[2])

    def GetInitialEdgeList(self):
        return self.initialEdgeList

    def GetEdges(self):
        return self.edges

    def Print(self):
        for u in range(G.n):
            print ("v({}): ".format(GetLetter(u)))
            for e in G.adj[u]:
                print(" <{},{},{}>".format( GetLetter(u), GetLetter(e[0]),e[1]))
        print("\n")

    def PrintMST(self):
        minCost = 0
        print("MST Edges and weights:")
        for e in self.edges:
            minCost += e[2]
            print("%c -- %c == %d" % (GetLetter(e[0]), GetLetter(e[1]), e[2]))
        print("Min cost for Spanning Tree:", minCost)
        print("")


class DisjointSets:
    def __init__(self, n):   # create a DSet with n elements (0..n-1) each in its own set
        self.parent = [ i for i in range(n) ] # parent of each node is pointing to itself
        self.rank   = [ 0 for i in range(n) ] # rank of each node is 0

    def FindSet(self, x):
        if x != self.parent[x]:
            self.parent[x] = self.FindSet(self.parent[x])
        return self.parent[x]

    def Union(self, x, y):
        px = self.FindSet(x)
        py = self.FindSet(y)

        if px == py: return
        print("Parent of {} is {}".format(GetLetter(x), GetLetter(px)))
        print("Parent of {} is {}".format(GetLetter(y), GetLetter(py)))
        if self.rank[px] > self.rank[py]:
            self.parent[py] = px
        else:
            self.parent[px] = py
            if (self.rank[px] == self.rank[py]):
                print("Increase Parent count of {} by 1".format(GetLetter(py)))
                self.rank[py] = self.rank[py] + 1
        print("\nDisjoint sets:")
        for i in range(len(self.parent)):
            vertex = GetLetter(i)
            p = GetLetter(self.parent[i])
            print("Parent of {} is {}".format(vertex, p))
        print("")

def Kruskul(G):
    ds = DisjointSets(G.n)
    MST = Graph(G.n, False)

    E = G.GetInitialEdgeList()

    print("Sort the edges by its weight, as the shortest weight by theory is always in the MST. So start from there.\n")
    E.sort(key=lambda e: e[2])

    e = 0
    i = 0
    while e < G.n - 1:
        u, v, w = E[i]
        i += 1
        if ds.FindSet(u) != ds.FindSet(v):
            print("Add Edge {} -- {}".format(GetLetter(u),GetLetter(v)))
            e += 1
            MST.AddEdge(u, v, w)
            ds.Union(u, v)
    return MST

newEdges = MapLetters(allEdges, 'ASC')

G = Graph(numberOfVertices, False)
G.AddEdgeListWeighted(newEdges)

MST = Kruskul(G)
MST.PrintMST()