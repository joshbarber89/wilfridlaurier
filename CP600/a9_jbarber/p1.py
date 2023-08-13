# Josh Barber
import os


######################################
###
###  Choose your test case, options 1 to 3
###
###  First case, 65.23. Second case, 8.62. Third case, 87.89
###
######################################
testCase = 1
######################################
###
###
######################################

value = 0

denominations = [0.25, 0.1, 0.05, 0.01]

txtPath = os.path.dirname(__file__)
with open(os.path.join(txtPath, "input1-"+str(testCase)+".txt")) as f:
    for line in f:
        for number in line.rstrip().split(' '):
            value = float(number)



def GreedyAlgorithm(A):
    n = len(denominations)
    S = []
    i = 0
    while(i < n):
        # Find denominations
        while (A >= denominations[i]):
            S.append(denominations[i])
            A = round(A - denominations[i], 2)
        i += 1
    return S

def PrintResults(S):
    count = {}
    for i in range(len(denominations)):
        count[str(denominations[i])+"¢"] = 0
        for j in range(len(S)):
            if denominations[i] == S[j]:
                count[str(denominations[i])+"¢"] += 1
    print("\nThe change for the value:")
    print(count)
    print("")

print("\nThe Denominations:")
print(denominations)
print("\nThe value: {}".format(value))

Selection = GreedyAlgorithm(value)
PrintResults(Selection)
