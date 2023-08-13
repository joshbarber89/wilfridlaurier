# Josh Barber
import os

numberArrays = []

txtPath = os.path.dirname(__file__)
with open(os.path.join(txtPath, "input.txt")) as f:
    for line in f:
        tempArray = []
        for number in line.rstrip().split(' '):
            if number != '':
                tempArray.append(int(number))
        if (len(tempArray) > 1):
            numberArrays.append(tempArray)
f.close()

print('\nArrays input:')
print(numberArrays)


def QuickSort(A, lo, hi):
    if lo < hi:
    	mid = Partition(A, lo, hi)
    	QuickSort(A, lo, mid-1)
    	QuickSort(A, mid + 1, hi)

def Partition (A, lo, hi):
    x = A[hi]
    i = lo - 1
    for j in range(lo, hi):
        if A[j] <= x:
            i = i + 1
            A[i], A[j] = A[j], A[i]

    A[i+1], A[hi] = A[hi], A[i+1]
    return i + 1

for numberArray in numberArrays:
    QuickSort(numberArray, 0, len(numberArray) - 1)

print('\nArrays sorted:')
print(numberArrays)