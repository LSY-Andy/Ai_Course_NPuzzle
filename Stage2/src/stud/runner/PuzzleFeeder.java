package stud.runner;

import core.problem.Problem;
import core.runner.EngineFeeder;
import core.solver.algorithm.heuristic.HeuristicType;
import core.solver.algorithm.heuristic.Predictor;
import stud.problem.npuzzle.PuzzleBoard;
import stud.problem.npuzzle.PuzzleProblem;

import java.util.ArrayList;



public class PuzzleFeeder extends EngineFeeder {

    @Override
    public ArrayList<Problem> getProblems(ArrayList<String> problemLines) {

        ArrayList<Problem> problems = new ArrayList<>();
        for (String problemLine : problemLines) {
            PuzzleProblem problem = createNPuzzle(problemLine);
            problems.add(problem);
        }
        return problems;
    }


    //����һ��Puzzle
    private PuzzleProblem createNPuzzle(String problemLine) {
        String[] strings = problemLine.split(" ");
        int size = Integer.parseInt(strings[0]); //����ĵ�һ������ʾsize
        byte[] initial_state = new byte[size * size];
        byte[] goal = new byte[size * size];
        for(int i = 1;i <= size * size;i++){
            initial_state[i - 1] = (byte) Integer.parseInt(strings[i]);
            goal[i - 1] = (byte)Integer.parseInt(strings[i + size * size]);
        }
        return new PuzzleProblem(new PuzzleBoard(size, initial_state, true),new PuzzleBoard(size, goal, false),size);
    }

    @Override
    public Predictor getPredictor(HeuristicType type) {
        return PuzzleBoard.predictor(type);
    }
}
