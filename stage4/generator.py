import numpy as np
import random
import utility

def	gen_puzzle(n, moves):
	state = gen_solution(n)
	prev = state
	for i in range(moves):
		moves = utility.get_moves(state)
		for i in range(len(moves)):
			if (np.array_equal(moves[i], prev)):
				moves.pop(i)
				break
		prev = state
		state = moves[random.randint(0, len(moves) - 1)]
	return state

def	cycle(sx, sy):
	if (sx == 1 and sy == 0):
		return (0, 1)
	elif (sx == 0 and sy == 1):
		return (-1, 0)
	elif (sx == -1 and sy == 0):
		return (0, -1)
	else:
		return (1, 0)

def	gen_solution(n):
	sol = np.zeros((n, n))
	i = 1
	x, y = [0, 0]
	sx, sy = [1, 0]
	while i != n**2:
		if (utility.is_valid_or_empty(sol, x + sx, y + sy) == 0):
			sx, sy = cycle(sx, sy)
		sol[y][x] = i
		i += 1
		x += sx
		y += sy
	return sol
