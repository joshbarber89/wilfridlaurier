# Josh Barber
import os
import heapq
import math

######################################
###
###  Choose your test case, options 1 to 3
###
###  First case, Tree is a MST. Second case, Tree doesn't belong to G. Third case, Tree isn't an MST
###
######################################
testCase = 5
######################################
###
###
######################################

allVertices = []

numberOfVerticesGraph = 0

letterMap = {
    'a': 0,
    'b': 1,
    'c': 2,
    'd': 3,
    'e': 4,
    'f': 5,
    'g': 6,
    'h': 7,
    'i': 8,
    'j': 9,
    'k': 10,
    'l': 11,
    'm': 12,
    'n': 13,
    'o': 14,
    'p': 15,
    'q': 16,
    'r': 17,
    's': 18,
    't': 19,
    'u': 20,
    'v': 21,
    'w': 22,
    'x': 23,
    'y': 24,
    'z': 25
}

txtPath = os.path.dirname(__file__)
with open(os.path.join(txtPath, "input"+str(testCase)+".txt")) as f:
    for line in f:
        for edge in line.rstrip().split(' '):
            if edge != '' and len(edge.split(',')) > 1:
                allVertices.append(edge.split(','))
            elif edge != '' and edge.isnumeric():
                numberOfVerticesGraph = int(edge)

f.close()

print('\nEdges Graph input:')
print(allVertices)

def Visit(u, visited, G, PQ):
    verticies = G.GetVerticies()[u]
    for (v, wt, x1, y1) in verticies['children']:
        if visited[letterMap[v]] == 0:
            heapq.heappush(PQ, (wt, u, v, verticies['coords'][0] , verticies['coords'][1], x1, y1))

    visited[letterMap[u]] = 1

def Prim(G, r):
    PQ = []
    MST = Graph(G.n, [])
    visited = [0] * numberOfVerticesGraph

    Visit(r, visited, G, PQ)

    while len(PQ) > 0:
        (weight, pu, u, x1, y1, x2, y2) = heapq.heappop(PQ)
        if visited[letterMap[u]] == 0:
            Visit(u, visited, G, PQ)
            MST.AddEdge(pu, u, weight, x1, y1, x2, y2)
    return MST

def PreorderTraversal(MST, r, TSPweight=[], parent = '', path = []):
    if r not in path:
        path.append(r)

    verticies = MST.GetVerticies()
    if r in verticies:
        vertice = verticies[r]
        for child in vertice['children']:
            TSPweight.append(child[1])
            PreorderTraversal(MST, child[0], TSPweight, r, path)
    return path, TSPweight

class Graph:
    def __init__(self, n, allVertices):
        self.n = n
        self.vertices = {}
        self.allEdges = []
        self.CalcEdgeWeights(allVertices)
        self.SetEdgeList()

    def CalcEdgeWeights(self, allVertices):
        for vertice1 in allVertices:
            v = vertice1[0]
            x1 = int(vertice1[1])
            y1 = int(vertice1[2])
            for vertice2 in allVertices:
                u = vertice2[0]
                if v == u: continue
                x2 = int(vertice2[1])
                y2 = int(vertice2[2])
                d = math.sqrt(pow((x2 - x1),2) + pow((y2 - y1),2))
                if v in self.vertices:
                    self.vertices[v]['children'].append((u, d, x2, y2))
                else:
                    self.vertices[v] = {'children': [], 'coords': (x1,y1)}
                    self.vertices[v]['children'].append((u, d, x2, y2))

    def SetEdgeList(self):
        for u in self.vertices:
            for v in self.vertices[u]['children']:
                self.allEdges.append([u, v[0], v[1]])

    def GetEdgeList(self):
        return self.allEdges

    def PrintEdges(self):
        for e in self.allEdges:
            print("%c -- %c == %d" % (e[0], e[1], e[2]))

    def AddEdge(self, u, v, w, x1 ,y1, x2, y2):
        if u in self.vertices:
            self.vertices[u]['children'].append((v,w, x2, y2))
        else:
            self.vertices[u] = {'children':[], 'coords': (x1,y1)}
            self.vertices[u]['children'].append((v,w, x2, y2))
        self.allEdges.append([u,v,w])

    def GetVerticies(self):
        return self.vertices
root = 'a'
G = Graph(numberOfVerticesGraph, allVertices)
MST = Prim(G, root)
print()
print("MST Edges:")
MST.PrintEdges()
print()
print("TSP approx path:")
TSPApprox = PreorderTraversal(MST, root)
TSPApprox[0].append(root)
print(TSPApprox[0])
print("Weight:")

fullSum = 0
first = TSPApprox[0][0]
last = TSPApprox[0][len(TSPApprox[0]) - 2]
for av in allVertices:
    if av[0] == first:
        first = av
    elif av[0] == last:
        last = av

fullSum += math.sqrt(pow((int(first[1]) - int(last[1])),2) + pow((int(first[2]) - int(last[2])),2))

for v in TSPApprox[1]:
    fullSum += v
print(fullSum)
print()