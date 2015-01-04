"""
	Project: A*
	We'll see how it goes from there!
	Probably the only file used in the project.


	0: clear square, can go here
	1: wall, can't go here
	2: start
	3: end

	Currently only prints the map and neighbours of 
	chosen block.
	Also how long it took to get neighbours and print map in millis.
"""
import sys
import time
import random
import Queue
# 
# Before allowing user to give input,
# use some default hardcoded values
# 

STARTBLOCK = 1,1
ENDBLOCK = 4,4
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
	# This does not work yet:
	# print mapTravel([1,1], graph)
	print neighbors(STARTBLOCK, graph)
	print time.time() - start
"""
	Helper function to print the map correctly
	or in other words human readable form
"""
def printMap(graph):
	line = "1234567890"
	def asd(line, row):
		print line, "        ", row

	for x,y,type_ in graph:
		if x % WIDTH == 0: 
			asd(line, y)
			line = ''
		if (x,y) == STARTBLOCK: line += "\033[92m" + "O" + "\033[0m"
		elif (x,y) == ENDBLOCK: line += "\033[92m" + "X" + "\033[0m"
		elif type_ == 0: line += "_"
		elif type_ == 1: line += "\033[91m" + "#" +"\033[0m"

	if line:
		asd(line, y+1)

"""
	Generates a graph with given width and height
	@h: Height of the graph
	@w: Width of the graph
"""
def getGraph(w,h):
	return [
	(x, y, 1 if random.random() > 0.8 or x in (0,h-1) or y in (0,w-1) else 0)
	for y in range(w) for x in range(h)
	]

"""
	Get neighbors of a node.
	In our algo, we can move only up/down/left/right
	@node: the node we want to find the neighbors for
"""
def neighbors(node, graph):
	#			   right  down   left	  up
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
			if tuple(curr) in graph:
				nodes.append(curr)
				break
	return nodes

"""
	Travel the whole map using a queue
"""
def mapTravel(start, graph):
	frontier = Queue.Queue()
	# Start block:
	frontier.put(start);
	visited = {}
	visited[tuple(start)] = True

	#Loop as long as we have something to go thru
	while not frontier.empty():
		current = frontier.get()
		for next in neighbors(current, graph):
			if next not in tuple(visited):
				frontier.put(next)
				visited[tuple(next)] = True

if __name__ == "__main__":
    main()