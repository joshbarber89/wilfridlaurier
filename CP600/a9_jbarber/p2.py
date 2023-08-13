# Josh Barber
import heapq
import os
import re


######################################
###
###  Choose your test case, options 1 to 3
###
###
###
######################################
testCase = 3
######################################
###
###
######################################

dictionary = {}
Symbols = []
codes = {}
OriginalMessage = ''

class node:
    def __init__(self, freq, symbol="", left=None, right=None):
        self.freq = freq
        self.symbol = symbol
        self.left = left
        self.right = right

        self.code = ''

    def __lt__(self, other):
        return self.freq < other.freq

txtPath = os.path.dirname(__file__)
with open(os.path.join(txtPath, "input2-"+str(testCase)+".txt")) as f:
    for line in f:
        for word in line.rstrip():
            for letter in word:
                OriginalMessage += letter
                if letter not in dictionary:
                    dictionary[letter] = 1
                else:
                    dictionary[letter] += 1
    for key, value in dictionary.items():
        Symbols.append(node(value, key))

def storeCodes(node, val=''):

    newVal = val + str(node.code)

    if(node.left):
        storeCodes(node.left, newVal)
    if(node.right):
        storeCodes(node.right, newVal)

    if(not node.left and not node.right):
        codes[node.symbol] = newVal

def printNodes(node, val=''):

    newVal = val + str(node.code)

    if(node.left):
        printNodes(node.left, newVal)
    if(node.right):
        printNodes(node.right, newVal)

    if(not node.left and not node.right):
        print("{} -> {}".format(node.symbol, newVal))

def Huffman(Symbols):
    symbols = [(s.freq, s) for s in Symbols]
    heapq.heapify(symbols)

    while len(symbols) > 1:
        fl, left = heapq.heappop(symbols)
        fr, right = heapq.heappop(symbols)

        left.code = 0
        right.code = 1

        z = node(fl + fr, "", left, right)

        heapq.heappush(symbols, (fl + fr, z))

    return symbols[0][1]

def PrefixEncode(message, prefix_tree):

    storeCodes(prefix_tree)

    encodedMessage = ''

    for l in message:
        encodedMessage += codes[l]

    return encodedMessage

def PrefixDecode(message, prefix_tree):
    decodedMessage = ""
    curr = prefix_tree
    n = len(message)
    for i in range(n):
        if message[i] == '0':
            curr = curr.left
        else:
            curr = curr.right

        if curr.left is None and curr.right is None:
            decodedMessage += curr.symbol
            curr = prefix_tree

    return decodedMessage



prefix_tree = Huffman(Symbols)

print("\nSymbols and their codes:")
printNodes(prefix_tree)

message = OriginalMessage

encodedMessage = PrefixEncode(message, prefix_tree)
print("\nEncoded Message:")
print(encodedMessage)

decodedMessage = PrefixDecode(encodedMessage, prefix_tree)
print("\nDecoded Message:")
print(decodedMessage)
print()
