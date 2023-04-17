package stud.g05.runner;

import core.problem.Problem;
import core.runner.EngineFeeder;
import core.solver.algorithm.heuristic.HeuristicType;
import core.solver.algorithm.heuristic.Predictor;
import core.solver.queue.EvaluationType;
import core.solver.queue.Frontier;
import core.solver.queue.Node;
import stud.g05.queue.PqFrontier;
import stud.g05.queue.ZobristFrontier;
import stud.queue.ListFrontier;

import java.util.ArrayList;
//Fix Me
public class PuzzleFeeder extends EngineFeeder {
    @Override
    public ArrayList<Problem> getProblems(ArrayList<String> problemLines) {
        return null;
    }

    @Override
    public Frontier getFrontier(EvaluationType type) {
//        return new ListFrontier(Node.evaluator(type)); // 常规用法
//        return new PqFrontier(Node.evaluator(type)); // stage1: 存储结构为优先队列
        return new ZobristFrontier(Node.evaluator(type)); // stage2: 存储为Zobrist哈希
    }

    @Override
    public Predictor getPredictor(HeuristicType type) {
        return null;
    }
}
