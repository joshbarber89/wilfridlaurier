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


def height(node):
    if node is None:
        return 0
    return max(height(node.left), height(node.right)) + 1


def isBalanced(node):
    if node is None:
        return True

    lh = height(node.left)
    rh = height(node.right)

    if (abs(lh - rh) <= 1
        and isBalanced(node.left) is True
        and isBalanced(node.right) is True):
        return True
    return False

print("Building Binary Tree...")
node = None
for x in range(0, len(numberList)):
    node = BST_Insert(node, numberList[x], numberList[x])
print("Build complete\n")

print("Is the binary Tree balanced:")
print(isBalanced(node))