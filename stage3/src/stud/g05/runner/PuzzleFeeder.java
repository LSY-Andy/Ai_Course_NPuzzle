package stud.g05.runner;

import core.problem.Problem;
import core.runner.EngineFeeder;
import core.solver.algorithm.heuristic.HeuristicType;
import core.solver.algorithm.heuristic.Predictor;
import stud.problem.npuzzle.PuzzleProblem;
import stud.problem.npuzzle.PuzzleBoard;

import java.util.ArrayList;

public class PuzzleFeeder extends EngineFeeder {
    @Override
    public ArrayList<Problem> getProblems(ArrayList<String> problemLines) {

        /* ����������� */
        ArrayList<Problem> problems = new ArrayList<>();
        int lineNo = 0;
        while (lineNo < problemLines.size()){
            //��������ʵ��
            PuzzleProblem problem = getPathFinding(problemLines.get(lineNo));
            //��ӵ������б�
            problems.add(problem);
            lineNo++;
        } //�����������

        return problems;
    }

    @Override
    public Predictor getPredictor(HeuristicType type) {
        return PuzzleBoard.predictor(type);
    }

    private PuzzleProblem getPathFinding(String problemLine) {
        String[] cells = problemLine.split(" ");
        int size = Integer.parseInt(cells[0]);
        //�����ʼ״̬
        int[][] initiall = new int[size][size];
        int[][] goall = new int[size][size];
        for(int i=0;i<size*size;++i){
            initiall[i/size][i%size] = Integer.parseInt(cells[i+1]);
            goall[i/size][i%size] = Integer.parseInt(cells[i+size*size+1]);
        }
        PuzzleBoard initialState = new PuzzleBoard(initiall,size);
        PuzzleBoard goal = new PuzzleBoard(goall,size);
//        Position  goal = new Position(initiall,size);
//        Position initialState = new Position(goall,size);

        //����Ѱ·�����ʵ��
        return new PuzzleProblem(initialState, goal, size);
    }

}
