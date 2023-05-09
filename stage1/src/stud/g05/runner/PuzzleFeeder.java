package stud.g05.runner;

import core.problem.Problem;
import core.runner.EngineFeeder;
import core.solver.algorithm.heuristic.HeuristicType;
import core.solver.algorithm.heuristic.Predictor;
import core.solver.queue.EvaluationType;
import core.solver.queue.Frontier;
import core.solver.queue.Node;
import stud.g05.problem.npuzzle.NPuzzleProblem;
import stud.g05.problem.npuzzle.PuzzleBoard;
import stud.g05.queue.PqFrontier;
import stud.g05.queue.Zobrist;

import java.util.ArrayList;
//Fix Me
public class PuzzleFeeder extends EngineFeeder {
    @Override
    public ArrayList<Problem> getProblems(ArrayList<String> problemLines) {

        ArrayList<Problem> problems = new ArrayList<>();
        for (String problemLine : problemLines) {
            NPuzzleProblem problem = createNPuzzle(problemLine);
            problems.add(problem);
        }
        return problems;
    }


    //构造一个Puzzle
    private NPuzzleProblem createNPuzzle(String problemLine) {
        String[] strings = problemLine.split(" ");
        int size = Integer.parseInt(strings[0]); //输入的第一个数表示size
        byte[] initial_state = new byte[size * size];
        byte[] goal = new byte[size * size];
        for(int i = 1;i <= size * size;i++){
            initial_state[i - 1] = (byte) Integer.parseInt(strings[i]);
            goal[i - 1] = (byte)Integer.parseInt(strings[i + size * size]);
        }
        return new NPuzzleProblem(new PuzzleBoard(size, initial_state, true),new PuzzleBoard(size, goal, false),size);
    }

    @Override
    public Frontier getFrontier(EvaluationType type) {
        return new PqFrontier(Node.evaluator(type)); // stage1: 存储结构为优先队列
    }

    @Override
    public Predictor getPredictor(HeuristicType type) {
            return PuzzleBoard.predictor(type);
    }
}
