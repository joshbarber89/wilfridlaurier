# Josh Barber
import os


######################################
###
###  Choose your test case, options 1 to 3
###
###  First case, Second case, Third case
###
######################################
testCase = 1
######################################
###
###
######################################

palindrome = ""

txtPath = os.path.dirname(__file__)
with open(os.path.join(txtPath, "input"+str(testCase)+".txt")) as f:
    for line in f:
        for p in line.rstrip().split(' '):
            palindrome = p

def PrintMatrix(M, string):
    print("\nMatrix:")
    print(" ", end="")
    for k in range(len(M)):
        print(" %3c" % string[k], end="")
    print("")
    for i in range(len(M)):
        print(string[i], end="")
        for j in range(len(M)):
                print(" %3d" % M[i][j], end="")
        print("")
    print("\n")

def Palindrome(string):
        palindrome = [0, 1]
        matrix = [[0]*len(string) for _ in range(len(string))]

        for i in range(len(string)):
            matrix[i][i] = 1

        # filling the matrix table
        for i in range(len(string) - 1, -1, -1):
			# j starts from the i location : to only work on the upper side of the diagonal
            for j in range(i + 1, len(string)):
                if string[i] == string[j]:  # if the chars mathces
                    # if len sliced string is just one letter if the characters are equal, we can say they are palindome matrix[i][j] = 1
                    # if the sliced string is longer than 1, then we should check if the inner string is also palindrome (check matrix[i + 1][j - 1] is 1)
                    if j - i == 1 or matrix[i + 1][j - 1] == 1:
                        matrix[i][j] = 1
                        # we also need to keep track of the maximum palindrome sequence
                        if palindrome[1] - palindrome[0] < j - i + 1:
                            palindrome = [i, j + 1]
        PrintMatrix(matrix, string)
        return string[palindrome[0] : palindrome[1]]

p = Palindrome(palindrome)
print("Input string:")
print(palindrome)
print()
print("Longest subsequence palindrome:")
print(p)
print()