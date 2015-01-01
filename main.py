"""
	Project: A*
	We'll see how it goes from there!
	Probably the only file used in the project.


	0: clear square, can go here
	1: wall, can't go here
	2: start
	3: end
"""
import sys
import time
import random

# 
# Before allowing user to give input,
# use some default hardcoded values
# 

STARTBLOCK = [1,1,2]
ENDBLOCK = [4,4,3] 
WIDTH = 10
HEIGHT = 10
"""
	Main function, nothing special
"""
def main():
	#No args for now
	start = time.time()
	graph = getGraph(WIDTH,HEIGHT)
	printMap(graph)
	print neighbors([2,3], graph)
	print time.time() - start
"""
	Helper function to print the map correctly
	or in other words human readable form
"""
def printMap(graph):
	line = "1234567890"
	for y in graph:
		# print y
		if(y[0] % WIDTH == 0): 
			print(line + "        " + str(y[1]))
			line = ""
		if(y[2] == 0): line += "_"
		if(y[2] == 1): line += "\033[91m" + "#" +"\033[0m"
		if(y[2] == 2): line += "\033[92m" + "O" + "\033[0m"
		if(y[2] == 3): line += "\033[92m" + "X" + "\033[0m"

"""
	Generates a graph with given width and height
	@h: Height of the graph
	@w: Width of the graph
"""
def getGraph(w,h):
	nodes = []
	for y in range(h+1):
		for x in range(w):
			if(x == 0 or x == w-1 or y == 0 or y == h-1):
				nodes.append([x,y, 1])
			elif(STARTBLOCK[0] == x and STARTBLOCK[1] == y):
				nodes.append(STARTBLOCK)
			elif(ENDBLOCK[0] == x+1 and ENDBLOCK[1] == y+1):
				nodes.append(ENDBLOCK)
			else:
				if(random.random() > 0.8):
					nodes.append([x,y,1])
				else:
					nodes.append([x,y,0])
	return nodes

"""
	Get neighbors of a node.
	In our algo, we can move only up/down/left/right
	@node: the node we want to find the neighbors for
"""
def neighbors(node, graph):
	#				right 	down	left		up
	directions = [[1,0], [0,1], [-1, 0], [0, -1]]
	nodes = []
	for dir in directions:
		x,y = node
		c,v = dir
		n = [x+c, y+v]
		#see if we have the node
		for x in range(4):
			curr = n[:]
			curr.append(x)
			print (str(curr) + str(curr in graph))
			if curr in graph:
				nodes.append(curr)
				break
	return nodes


if __name__ == "__main__":
    main()