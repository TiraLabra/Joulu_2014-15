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
#our graph
"""
	Main function, nothing special
"""
def main():
	#No args for now
	print time.time()
	graph = getGraph(20,10)
	# print graph
	print neighbors(graph[0], graph)

"""
	Generates a graph with given width and height
	@h: Height of the graph
	@w: Width of the graph
"""
def getGraph(h,w):
	nodes = []
	for x in range(w):
		for y in range(h):
			nodes.append([x,y, 0])
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
	return neighbors;


if __name__ == "__main__":
    main()