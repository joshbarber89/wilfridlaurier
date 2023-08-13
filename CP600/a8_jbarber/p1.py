# Josh Barber
import os


######################################
###
###  Choose your test case, options 1 to 3
###
###  First case, Tree is a MST. Second case, Tree doesn't belong to G. Third case, Tree isn't an MST
###
######################################
testCase = 1
######################################
###
###
######################################

allEdgesGraph = []
allEdgesTree = []

letterMap = []
numberOfVerticesGraph = 0
numberOfVerticesTree = 0

txtPath = os.path.dirname(__file__)
with open(os.path.join(txtPath, "input1-"+str(testCase)+".txt")) as f:
    mode = 'Graph'
    for line in f:
        for edge in line.rstrip().split(' '):
            if edge == 'Graph':
                mode = 'Graph'
            if edge == 'Tree':
                mode = 'Tree'
            if edge != '' and len(edge.split(',')) > 1:
                if (mode == 'Graph'):
                    allEdgesGraph.append(edge.split(','))
                else:
                    allEdgesTree.append(edge.split(','))
            elif edge != '' and edge.isnumeric():
                if (mode == 'Graph'):
                    numberOfVerticesGraph = int(edge)
                else:
                    numberOfVerticesTree = int(edge)

f.close()

print('\nEdges Graph input:')
print(allEdgesGraph)
print('Edges Tree input:')
print(allEdgesTree)
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
        self.verticies = []
        self.weight = 0
        self.directed = directed
        self.adj = [[] for i in range(n)]   # n adjacency lists each empty

    def AddVertex(self):
        self.adj.append([])
        self.n = self.n + 1

    def AddEdge(self, u, v, wt=1):
        try:
            self.edges.append([u,v,wt])
            self.weight += int(wt)
            if GetLetter(u) not in self.verticies:
                self.verticies.append(GetLetter(u))
            if GetLetter(v) not in self.verticies:
                self.verticies.append(GetLetter(v))
            self.adj[u].append([v,wt])
            if not self.directed: self.adj[v].append([u,wt])
        except:
            print("More verticies than specified\n")
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

    def PrintEdges(self):
        for e in self.edges:
            print("%c -- %c == %d" % (GetLetter(e[0]), GetLetter(e[1]), e[2]))

    def GetVerticies(self):
        self.verticies.sort()
        return self.verticies
    def GetWeight(self):
        return self.weight

class Tree:
    def __init__(self, n, edges):
        self.edges = edges
        self.n = n
        self.tree = {}
        self.weight = 0
        self.vertices = []
        for i in edges:
            v1, v2, weight = i
            if v1 not in self.vertices:
                self.vertices.append(v1)
            if v2 not in self.vertices:
                self.vertices.append(v2)

            self.weight += int(weight)
            if v1 in self.tree:
                self.tree[v1].append([v2,weight])
            else:
                self.tree[v1] = [[v2,weight]]

            if v2 in self.tree:
                self.tree[v2].append([v1,weight])
            else:
                self.tree[v2] = [[v1,weight]]
    def GetVertices(self):
        self.vertices.sort()
        return self.vertices

    def GetTree(self):
        return self.tree

    def GetWeight(self):
        return self.weight

    def PrintTree(self):
        print(self.tree)


class DisjointSets:
    def __init__(self, n):
        self.parent = [ i for i in range(n) ]
        self.rank   = [ 0 for i in range(n) ]

    def FindSet(self, x):
        if x != self.parent[x]:
            self.parent[x] = self.FindSet(self.parent[x])
        return self.parent[x]

    def Union(self, x, y):
        px = self.FindSet(x)
        py = self.FindSet(y)
        if px == py: return
        if self.rank[px] > self.rank[py]:
            self.parent[py] = px
        else:
            self.parent[px] = py
            if (self.rank[px] == self.rank[py]):
                self.rank[py] = self.rank[py] + 1

def Kruskul(G):
    DS = DisjointSets(G.n)
    MST = Graph(G.n, False)

    E = G.GetInitialEdgeList()

    E.sort(key=lambda e: e[2])

    e = 0
    i = 0
    while e < G.n - 1:
        u, v, w = E[i]
        i += 1
        if DS.FindSet(u) != DS.FindSet(v):
            e += 1
            MST.AddEdge(u, v, w)
            DS.Union(u, v)
    return MST

def IsMST(G: Graph, T: Tree):
    print("The Graph:")
    G.Print()
    print("The Tree:")
    T.PrintTree()
    treeWeight = T.GetWeight()
    treeVertices = T.GetVertices()
    graphVertices = G.GetVerticies()
    if (treeVertices != graphVertices):
        print("The Tree doesn't belong to this Graph\n")
        return
    MST = Kruskul(G)
    print("\nThe MST using Kruskul:")
    MST.PrintEdges()
    weight = MST.GetWeight()
    print ("\nThe best weight of a MST using Kruskal on G is", weight)
    if (weight == treeWeight):
        print("\nThis Custom Tree is also a MST with a weight of", treeWeight)
    else:
        print("The Custom Tree is not a MST with a weight of", treeWeight)
    print("\n( Must note that there could be multiple MST's for the same Graph. As long as the weight of the tree is the true minimum, it is a MST )\n")

newEdges = MapLetters(allEdgesGraph, 'ASC')

Tree = Tree(numberOfVerticesTree, allEdgesTree)
G = Graph(numberOfVerticesGraph, False)
G.AddEdgeListWeighted(newEdges)

IsMST(G, Tree)