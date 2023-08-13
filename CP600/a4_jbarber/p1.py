# Josh Barber
import os

numberArrays = []
queries = {
    1: [],
    2: [],
    3: []
}

txtPath = os.path.dirname(__file__)
for filename in range(1, 4):
    with open(os.path.join(txtPath, "input1" + str(filename) + ".txt")) as f:
        for line in f:
            tempArray = []
            for number in line.rstrip().split(' '):
                if number != '':
                    tempArray.append(int(number))
            if (len(tempArray) > 2):
                numberArrays.append(tempArray)
            elif (len(tempArray) == 2):
                queries[filename].append(tempArray)
    f.close()



for filename in range(1, 4):
    A = numberArrays[filename - 1]
    k = A[len(A) - 1]
    for query in range(0, len(queries)):

        a = queries[filename][query][0] - 1
        b = queries[filename][query][1] - 1

        B = []

        for i in range(0, k):
            B.append(0)

        for j in range(0, len(A)):
            B[A[j] - 1] = 1

        for q in range(1, k):
            B[q] = B[q] + B[q-1]

        q = a - 1
        if q < 0:
            q = 0
        howManyNumbers = B[b] - B[q]

        print("There are " + str(howManyNumbers) + " numbers between indices `a` (" + str(a + 1)+ ") and `b` (" + str(b + 1) + ")" )
    print("\n")

