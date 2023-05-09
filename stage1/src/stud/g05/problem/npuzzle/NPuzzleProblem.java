package stud.g05.problem.npuzzle;

import core.problem.Action;
import core.problem.Problem;
import core.problem.State;
import core.solver.queue.Node;

import java.util.Deque;


public class NPuzzleProblem extends Problem {

    public NPuzzleProblem(State initialState, State goal, int size) {
        super(initialState, goal, size);
    }

    private int getReverseCount(byte[] state) {
        int res = 0;
        for (int i = 0; i < size * size - 1; i++) {
            for (int j = i + 1; j < size * size; j++) {
                if (state[j] == 0) continue;
                if (state[j] < state[i]) res++;
            }
        }
        return res;
    }

    public State getInitialState() {
        return initialState;
    }

    @Override
    public boolean solvable() {  //�ж��Ƿ�ɽ�
        byte[] state = ((PuzzleBoard)getInitialState()).getStates();
        int count = getReverseCount(state);
        if(size % 2 == 1) { //sizeΪ����,�����Ϊż�����н�
            return (count % 2 == 0);
        } else { //sizeΪż��
            if((size - ((PuzzleBoard)getInitialState()).getRow()) % 2 == 1) {  //�ո���������
                return (count % 2 == 0);
            } else { //�ո���ż����
                return (count % 2 == 1);
            }
        }
    }

    @Override
    public int stepCost(State state, Action action) {
        return 1;
    }

    @Override
    public boolean applicable(State state, Action action) {
        int[] offsets = PuzzleDirection.offset(((PuzzleMove)action).getDirection());
        int row = ((PuzzleBoard)state).getRow() + offsets[0];
        int col = ((PuzzleBoard)state).getCol() + offsets[1];
        return row >= 0 && row < size && col >= 0 && col < size;
    }

    public void showSolution(Deque<Node> path) {
        if (path == null){
            System.out.println("No Solution.");
            return;
        }
        for(Node node : path)
        {
            if(node.getAction() != null)
                node.getAction().draw();
            node.getState().draw();
        }
    }
}

