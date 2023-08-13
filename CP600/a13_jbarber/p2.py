# Josh Barber
count = 0

def Process(A,k,S):
    global count
    count = count + 1
    if count == 1:
        print("\nN = {}".format(len(A)))
        print("\nA Solution:")
        for x in range(0, len(A)):
            for y in range(0, len(A)):
                print(" %3d" % A[x][y], end="")
            print()
        print()

def IsSolution(A, k, S):
    return k >= len(A)


def ConstructCandidates(A, row, col):
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
    if IsSolution(A, k, S):
        Process(A, k, S)
    else:
        for i in range(len(A)):
            if(ConstructCandidates(A, i, k)):
                A[i][k] = 1
                Backtrack(A, k + 1, S)
                if Finished():
                    return
                A[i][k] = 0

def Finished():
    global count
    return count > 0

n = 8
A = [[0 for _ in range(n)] for _ in range(n)]
k = 0
S = []

Backtrack(A,k,S)
