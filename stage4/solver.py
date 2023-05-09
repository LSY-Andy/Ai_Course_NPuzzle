import random, operator,  time, math
import numpy as np
import generator, utility

class Node:
	def __init__(self, parent, grid, h_score, dist, greedy, uniform):
		self.parent = parent
		self.grid = grid
		self.h_score = h_score
		self.dist = dist
		if greedy:
			self.total = h_score
		elif uniform:
			self.total = dist
		else:
			self.total = h_score + dist

class Queue:
	def __init__(self, first):
		self.lst = [first]
		self.biggest = first.total

	def insert(self, data):
		if (data.total >= self.biggest or len(self.lst) == 0):
			self.biggest = data.total
			self.lst.append(data)
		else:
			for i in range(len(self.lst)):
				if (self.lst[i].total >= data.total):
					self.lst.insert(i, data)
					break
	
	def pop_first(self):
		ret = self.lst[0]
		self.lst.pop(0)
		return ret

def	get_listed_coords(n):
	target = generator.gen_solution(n)
	x_crd = np.zeros((n**2))
	y_crd = np.zeros((n**2))
	for i in range(n):
		for j in range(n):
			x_crd[int(target[j][i])] = i
			y_crd[int(target[j][i])] = j
	return (x_crd, y_crd)

def	manhattan_dist(grid, n, x_tar, y_tar):
	dist = 0
	for i in range(n):
		for j in range(n):
			nbr = int(grid[j][i])
			if nbr != 0:
				dist += int(abs(j - y_tar[nbr])) + int(abs(i - x_tar[nbr]))
	return dist

def	euclidian_dist(grid, n, x_tar, y_tar):
	dist = 0
	for i in range(n):
		for j in range(n):
			nbr = int(grid[j][i])
			if nbr != 0:
				dist += round(math.sqrt(int((j - y_tar[nbr])**2) + int((i - x_tar[nbr])**2)))
	return dist

def toof(grid, n, x_tar, y_tar):
	dist = 0
	for i in range(n):
		for j in range(n):
			nbr = int(grid[j][i])
			if (nbr != 0):
				if (x_tar[nbr] != i or y_tar[nbr] != j):
					dist += 1
	return dist

def gen_stats(solved, parent, cpl_size, cpl_time, verbose):
	steps = [solved]
	if (verbose):
		print("")
	print("Solved!\nNumber of moves:", parent.dist + 1, "\nComplexity in size:", cpl_size, "\nComplexity in time:", cpl_time)
	while parent.parent != None:
		steps.insert(0, parent.grid)
		parent = parent.parent
	return steps

def solve(grid, n, h_fnc, greedy, uniform, verbose):
	solution = generator.gen_solution(n)
	if np.array_equal(grid, solution):
		return None
	h_dict = {"Manhattan":manhattan_dist, "Euclidian":euclidian_dist, "Tiles out-of-place":toof}
	x_tar, y_tar = get_listed_coords(n)
	open_list = Queue(Node(None, grid, h_dict[h_fnc](grid, n, x_tar, y_tar), 0, greedy, uniform))
	closed_list = {}
	cpl_size, cpl_time = 0, 1
	if verbose:
		print(f"Solving with {h_fnc} heuristic function using", end='')
		if greedy or uniform:
			print(f" {'greedy' if greedy else 'uniform-cost' if uniform else 'normal'}", end='')
		print(" A* algorithm")
	while (len(open_list.lst) != 0):
		if (len(open_list.lst) > cpl_size):
			cpl_size = len(open_list.lst)
		parent = open_list.pop_first()
		if (verbose):
			print(f"Open list: {len(open_list.lst):6d}\tClosed list: {len(closed_list):6d}", end="\r")
		for move in utility.get_moves(parent.grid):
			if (np.array_equal(move, solution)):
				steps = gen_stats(move, parent, cpl_size, cpl_time, verbose)
				return steps
			if (not np.array2string(np.concatenate(move)) in closed_list):
				child = Node(parent, move, h_dict[h_fnc](move, n, x_tar, y_tar), parent.dist + 1, greedy, uniform)
				open_list.insert(child)
				cpl_time += 1
				del child
		closed_list[np.array2string(np.concatenate(parent.grid))] = parent