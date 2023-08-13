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

def left(i):
    if (i == 0):
        return 1
    else:
        return 2*i
def right(i):
    if (i == 0):
        return 2
    else:
        return 2*i + 1

def Min_Heapify(A, i, n):
    l = left(i)
    r = right(i)
    smallest = i
    if l <= n and A[i] > A[l]:
        smallest = l
    if r <= n and A[smallest] > A[r]:
        smallest = r
    if smallest != i:
        A[i], A[smallest] = A[smallest], A[i]
        Min_Heapify(A, smallest, n)

def Build_Min_Heap(A):
    n = len(A) - 1
    for i in range(n//2, -1, -1):
        Min_Heapify(A, i, n)

def HeapSort(A):
    n = len(A)-1
    Build_Min_Heap(A)
    for i in range(len(A)-1, 0, -1):
        A[i], A[0] = A[0], A[i]
        n = n - 1
        Min_Heapify(A, 0, n)

for numberArray in numberArrays:
    HeapSort(numberArray)

print('\nArrays sorted:')
print(numberArrays)