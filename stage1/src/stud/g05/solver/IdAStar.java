package stud.g05.solver;

import core.problem.Problem;
import core.problem.State;
import core.solver.algorithm.heuristic.Predictor;
import core.solver.algorithm.searcher.AbstractSearcher;
import core.solver.queue.Frontier;
import core.solver.queue.Node;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

public class IdAStar extends AbstractSearcher {
    Predictor predictor;
    public IdAStar(Predictor predictor) {
        //FixMe
        this.predictor = predictor;
    }

    @Override
    public Deque<Node> search(Problem problem) {
        //FixMe
        return null;
    }

    @Override
    public int nodesExpanded() {
        //FixMe
        return 0;
    }

    @Override
    public int nodesGenerated() {
        //FixMe
        return 0;
    }
}
