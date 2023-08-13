# Josh Barber
import os
import math
from itertools import permutations

######################################
###
###  Choose your test case, options 1 to 3
###
###  First case, Tree is a MST. Second case, Tree doesn't belong to G. Third case, Tree isn't an MST
###
######################################
testCase = 4
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


def TravellingSalesmanProblem(G, r):

    vertex = []
    for i in range(G.n):
        if i != letterMap[r]:
            vertex.append(i)


    minPath = math.inf
    allPermutations = permutations(vertex)
    verticies = G.allVertices

    TSPPath = []
    lastIndex = 0
    for perm in allPermutations:

        currentWeight = 0
        v = verticies[letterMap[r]]


        for i in perm:

            u = verticies[i]

            x1 = int(v[1])
            y1 = int(v[2])

            x2 = int(u[1])
            y2 = int(u[2])

            v = u

            d = math.sqrt(pow((x2 - x1),2) + pow((y2 - y1),2))

            currentWeight += d


        if currentWeight < minPath:
            minPath = currentWeight
            TSPPath = []
            TSPPath.append(r)
            for num in perm:
                for letter in letterMap:
                    if num == letterMap[letter]:
                        lastIndex = num
                        TSPPath.append(letter)
    TSPPath.append(r)
    root = verticies[letterMap[r]]
    last = verticies[lastIndex]
    minPath += math.sqrt(pow((int(last[1]) - int(root[1])),2) + pow((int(last[2]) - int(root[2])),2))
    return TSPPath, minPath

class Graph:
    def __init__(self, n, allVertices):
        self.n = n
        self.allVertices = allVertices

G = Graph(numberOfVerticesGraph, allVertices)

TSPOptimal = TravellingSalesmanProblem(G, 'a')
print()
print('Shortest Path:')
print(TSPOptimal[0])
print('Shortest Weight:')
print(TSPOptimal[1])
