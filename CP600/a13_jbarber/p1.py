# Josh Barber

count = 0

def Process(A,k,S):
    global count
    count = count + 1
    print("\nN = {}".format(len(A)))
    print("\nThe Array:")
    print(A)
    print("\nFirst pass:")
    for x in range(0, len(A)):
        for y in range(0, len(A)):
            if A[x] == y:
                print(" %3d" % 1, end="")
            else:
                print(" %3d" % 0, end="")
        print()
    print()

def IsSolution(A, k, S):
    return k > len(A) - 1

def ConstructCandidates(A, k, S):
    Result = []
    for i in range(1, len(A)):
        hasThreat = False
        for j in range(1, k):
            if i == A[i] or abs(k - j) == abs(i-A[j]):
                hasThreat = True
        if not hasThreat:
            Result.append(i)

    return Result

def Backtrack(A, k, S):
    if IsSolution(A, k, S):
        Process(A, k, S)
    else:
        k = k + 1
        L = ConstructCandidates(A, k, S)
        if k < len(A):
            for c in L:
                A[k] = c
                Backtrack(A, k, S)
                if Finished():
                    return
        else:
            Backtrack(A, k, S)

def Finished():
    global count
    return count > 0

n = 4
A = [0 for _ in range(n)]
k = 0
S = []

Backtrack(A,k,S)
