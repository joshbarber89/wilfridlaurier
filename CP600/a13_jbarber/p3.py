# Josh Barber
import os
import time

######################################
###
###  Choose your test case, options 1 to 3
###
###  First case 8 queens, Second case 10 queens, Third case 12 queens
###
######################################
testCase = 1
######################################
###
###
######################################

startTime = time.time()
numberOfQueens = 0

txtPath = os.path.dirname(__file__)
with open(os.path.join(txtPath, "input"+str(testCase)+".txt")) as f:
    for line in f:
        for p in line.rstrip().split(' '):
            numberOfQueens = int(p)

count = 0
recursiveCount = 0

def PrintMatrix(X):
    for x in range(0, len(X)):
        for y in range(0, len(X)):
            print(" %3d" % X[x][y], end="")
        print()

def Process(A,k,S):
    global count

    if count == 0:
        print("\nN = {}".format(len(A)))

    if len(S) > count:
        print("\nSolution {}:".format(count + 1))
        PrintMatrix(S[count])
        count = count + 1
        return

def IsSolution(A, k, S):
    if k >= len(A):
        queenCount = 0
        B = [[0 for _ in range(n)] for _ in range(n)]
        for i in range(len(A)):
            for j in range(len(A)):
                if A[i][j] == 1:
                    B[i][j] = 1
                    queenCount += 1
                else:
                    B[i][j] = 0
        if queenCount == len(A):
            S.append(B)
        return True
    return False


def ConstructCandidates(A, row, col, S):
    for i in range(col):
        if A[row][i] == 1:
            return False

    for i in range(row, -1, -1):
        for j in range(col, -1, -1):
            if A[i][j] == 1 and abs(row - i) == abs(col - j):
                return False

    for i in range(row, len(A), 1):
        for j in range(col, -1, -1):
            if A[i][j] == 1 and abs(row - i) == abs(col - j):
                return False
    return True

def Backtrack(A, k, S):
    global recursiveCount
    if IsSolution(A, k, S):
        Process(A, k, S)
    else:
        for i in range(len(A)):
            if(ConstructCandidates(A, i, k, S)):
                A[i][k] = 1
                Backtrack(A, k + 1, S)
                recursiveCount += 1
                if Finished():
                    return
                A[i][k] = 0

def Finished():
    return False

n = numberOfQueens
A = [[0 for _ in range(n)] for _ in range(n)]
k = 0
S = []

Backtrack(A,k,S)
endTime = time.time() - startTime
print('\nThere are {} solutions to N Queens Problem when N = {}'.format(len(S), n))
print('\nThere were also {} recursive calls to the `Backtrack` function'.format(recursiveCount))
print('\nIt took %.2f seconds of execution time\n' % (endTime))