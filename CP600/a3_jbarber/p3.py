# Josh Barber
import os

k = 0
numberArray = []

txtPath = os.path.dirname(__file__)
with open(os.path.join(txtPath, "inputp3.txt")) as f:
    for line in f:
        tempArray = []
        for number in line.rstrip().split(' '):
            if number != '':
                tempArray.append(int(number))
        if (len(tempArray) > 1):
            numberArray = tempArray
        else:
            k = int(tempArray[0])
f.close()

print('\nFull Array input:')
print(numberArray)
print('\nThe k index value:')
print ('k='+str(k)+'\n')

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

# This will be the main array with infinite stream
heapArray = []

# To pretty output
stringSuffix = 'th'
if k == 1:
    stringSuffix = 'st'
elif k == 2:
    stringSuffix = 'nd'
elif k == 3:
    stringSuffix = 'rd'


# Add function, add new value to heap then sort it
def add(number):
    heapArray.append(number)
    HeapSort(heapArray)
    if (len(heapArray) > k):
        heapArray.pop()


# Get function, get kth Element
def get(k):
    if k - 1 >= 0:
        return heapArray[k - 1]
    else:
        return heapArray[0]

#initialize k sized array first
i = 0
for _ in range(0, k):
    print('Enter element ' + str(i + 1) + ': ' + str(numberArray[i]))
    add(numberArray[i])
    i += 1


# Grab kth elements in infinite dataset
for j in range(i, len(numberArray) + 1):
    kthElement = get(k)
    print('K-th "' + str(k) + stringSuffix + '" largest element after reading element ' + str(j) + ' is: ' + str(kthElement))
    if j < len(numberArray):
        add(numberArray[j])