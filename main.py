"""
	Project: A*
	We'll see how it goes from there!
	Probably the only file used in the project.


	0: clear square, can go here
	1: wall, can't go here
	2: start
	3: end

	OUTPUT:
	
1419685786.78
12345        0
#####        1
#O__#        2
#_X_#        3
#___#        4
#####        5
"""
import sys
import time

# 
# Before allowing user to give input,
# use some default hardcoded values
# 

STARTBLOCK = [1,1,2]
ENDBLOCK = [5,5,3] 
WIDTH = 25
HEIGHT = 25
"""
	Main function, nothing special
"""
def main():
	#No args for now
	print time.time()
	graph = getGraph(WIDTH,HEIGHT)
	printMap(graph)

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
		if(y[2] == 1): line += "#"
		if(y[2] == 2): line += "O"
		if(y[2] == 3): line += "X"

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
				nodes.append([x,y,0])
	return nodes

"""
	Get neighbors of a node.
	In our algo, we can move only up/down/left/right
	@node: the node we want to find the neighbors for
"""
def neighbors(node, graph):
	directions = [[1,0,0], [0,1,0], [-1, 0,0], [0, -1,0]]
	neighbors = []
	for dir in directions:
		neighbor = [node[0] + dir[0], node[1] + dir[1]]
		#see if we have the node
		if neighbor in graph:
			neighbors.append(neighbor)
	return neighbors


if __name__ == "__main__":
    main()