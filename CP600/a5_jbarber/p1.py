# Josh Barber
import os

findValue = 67

numberList = list([])

txtPath = os.path.dirname(__file__)
with open(os.path.join(txtPath, "input.txt")) as f:
    for line in f:
        for number in line.rstrip().split(' '):
            if number != '':
                numberList.append(int(number))
f.close()

print("\nOriginal Array:")
print(numberList)
print("")

class Node:
    def __init__(self, key, value):
        self.left = None
        self.right = None
        self.key = key
        self.value = value
        self.size = 1

def BST_Size(x):
    if x is None:
        return 0
    return x.size

def BST_Insert(node, key, value):
    if node is None:
        return Node(key, value)
    if key < node.key:
        node.left = BST_Insert(node.left, key, value)
    elif node.key < key:
        node.right = BST_Insert(node.right, key, value)
    else:
        node.value = value
    node.size = 1 + BST_Size(node.left) + BST_Size(node.right)
    return node


def BST_Ceil(x, k):
    if x is None:
        return
    if k == x.key:
        return x
    if k > x.key:
        print('Look right')
        return BST_Ceil(x.right, k)
    print('Look left')
    t = BST_Ceil(x.left, k)
    if t is not None:
        return t
    return x

print("Building Binary Tree...")
node = None
for x in range(0, len(numberList)):
    node = BST_Insert(node, numberList[x], numberList[x]) # I know key value pairing, but in this case might as well make them the same

print("Build complete")
print("\nLets track the search for " + str(findValue) + " through the binary tree:\n")
searchNode = BST_Ceil(node, findValue)
print("")
print("Found Node with value: " + str(searchNode.value) + "\n")