import numpy as np
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation
import tkinter as tk

# 定义初始状态和目标状态
initial_state = np.array([[5, 0, 8], [4, 2, 1], [7, 3, 6]])
goal_state = np.array([[1, 2, 3], [4, 5, 6], [7, 8, 0]])

# 定义解路径
state1 = [[5, 2, 8], [4, 0, 1], [7, 3, 6]]
state2 = [[5, 2, 8], [4, 1, 0], [7, 3, 6]]
state3 = [[5, 2, 0], [4, 1, 8], [7, 3, 6]]
state4 = [[5, 0, 2], [4, 1, 8], [7, 3, 6]]
state5 = [[5, 1, 2], [4, 0, 8], [7, 3, 6]]
state6 = [[5, 1, 2], [4, 3, 8], [7, 0, 6]]
state7 = [[5, 1, 2], [4, 3, 8], [7, 6, 0]]
state8 = [[5, 1, 2], [4, 3, 0], [7, 6, 8]]
state9 = [[5, 1, 2], [4, 0, 3], [7, 6, 8]]
state10 = [[5, 1, 2], [4, 6, 3], [7, 0, 8]]
state11 = [[5, 1, 2], [4, 6, 3], [0, 7, 8]]
state12 = [[5, 1, 2], [0, 6, 3], [4, 7, 8]]
state13 = [[0, 1, 2], [5, 6, 3], [4, 7, 8]]
state14 = [[1, 0, 2], [5, 6, 3], [4, 7, 8]]
state15 = [[1, 2, 0], [5, 6, 3], [4, 7, 8]]
state16 = [[1, 2, 3], [5, 6, 0], [4, 7, 8]]
state17 = [[1, 2, 3], [5, 0, 6], [4, 7, 8]]
state18 = [[1, 2, 3], [0, 5, 6], [4, 7, 8]]
state19 = [[1, 2, 3], [4, 5, 6], [0, 7, 8]]
state20 = [[1, 2, 3], [4, 5, 6], [7, 0, 8]]
state21 = [[1, 2, 3], [4, 5, 6], [7, 8, 0]]

# Define solution path
solution = [state1, state2, state3, state4, state5, state6, state7, state8, state9, state10, state11, state12, state13, state14, state15, state16, state17, state18, state19, state20, state21]
solution = np.array(solution)


def animate_solution(initial_state, goal_state, solution):
    fig, ax = plt.subplots()
    im = ax.imshow(initial_state, cmap=plt.cm.Blues)
    ax.set_title("Initial state")
    for i in range(initial_state.shape[0]):
        for j in range(initial_state.shape[1]):
            ax.text(j, i, initial_state[i, j], ha="center", va="center", color="white",fontsize=20)
    ax.set_axis_off()
    frame_store = [im]

    def update(frame):
        ax.clear()
        ax.set_title(f"Step {frame+1}")
        for i in range(solution[frame].shape[0]):
            for j in range(solution[frame].shape[1]):
                value = solution[frame][i, j]
                if value != 0:
                    ax.text(j, i, value, ha="center", va="center", color="white", fontsize=20)
        ims = ax.imshow(solution[frame], cmap=plt.cm.Blues)
        print(f"The {frame} step that N-Puzzle project solved")
        frame_store.append(ims)
        ax.set_axis_off()
        return frame_store

    ani = FuncAnimation(fig, update, frames=len(solution), blit=False, repeat=False, interval=400)
    #一切困难都是因为没有禁用:blit!

    plt.show()

animate_solution(initial_state, goal_state, solution)


