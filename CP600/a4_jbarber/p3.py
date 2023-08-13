# Josh Barber
import os
import math

numberArrays = []

txtPath = os.path.dirname(__file__)
for filename in range(1, 4):
    with open(os.path.join(txtPath, "input3" + str(filename) + ".txt")) as f:
        for line in f:
            tempArray = []
            for number in line.rstrip().split(' '):
                if number != '':
                    tempArray.append(int(number))
            if (len(tempArray) > 2):
                numberArrays.append(tempArray)
    f.close()



def CountingSort(A, k):
    # Create B array
    B = [0] * (len(A))
    # Create and initialize C array
    C = [0] * 10
    for x in range(0, 10): C[x] = 0

    # Compute the count of each element in A
    for j in range(0, len(A)):
        d = int((A[j]/10**k) % 10)
        C[d] = C[d] + 1

    # Compute C[x] == count of A[j] ≤ x
    for i in range(1, 10):
        C[i] = C[i] + C[i-1]

    # Place each A[j] in sorted order in B starting from the end
    for j in range(len(A)-1, -1, -1):
        d = int((A[j]/10**k) % 10)
        C[d] = C[d] - 1
        B[C[d]] = A[j]

    return B

# test the algorithm
def RadixSort(A, d):
    r = int(math.floor(math.log(d, 10) + 1))
    for i in range(0, r):
        A = CountingSort(A, i)
    return A

for i in range(len(numberArrays)):
    A = numberArrays[i]
    d = max(A)
    print('Before Sort:')
    print(A)
    print('After Sort:')
    print(RadixSort(A, d))
    print("\n")