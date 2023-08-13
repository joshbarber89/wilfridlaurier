# Josh Barber
import os

numberArrays = []

txtPath = os.path.dirname(__file__)
with open(os.path.join(txtPath, "input.txt")) as f:
    for line in f:
        tempArray = []
        for number in line.rstrip().split(' '):
            tempArray.append(int(number))
        numberArrays.append(tempArray)
f.close()

print('\nArrays input:')
print(numberArrays)


def MergeSort(A, lo, hi):
    if len(A) <= 1:
        return A

    if lo < hi:
        mid = (lo + hi)//2
        MergeSort(A, lo, mid)
        MergeSort(A, mid + 1, hi)
        Merge(A, mid)

def Merge(A, mid):

    L = A[:mid]
    R = A[mid:]

    i, j, k = 0, 0, 0

    while i < len(L) and j < len(R):
        if L[i] < R[j]:
            A[k] = L[i]
            i += 1
        else:
            A[k] = R[j]
            j += 1
        k += 1

    while i < len(L):
        A[k] = L[i]
        i += 1
        k += 1

    while j < len(R):
        A[k]= R[j]
        j += 1
        k += 1

for numberArray in numberArrays:
    MergeSort(numberArray, 0, len(numberArray))

print('\nArrays sorted:')
print(numberArrays)