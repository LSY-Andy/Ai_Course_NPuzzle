package stud.solver;

import core.problem.Problem;
import core.solver.algorithm.Searcher;
import core.solver.algorithm.heuristic.Predictor;
import core.solver.queue.Node;
import java.util.Deque;
import java.util.Stack;

/**
 * 迭代加深的A*算法，需要同学们自己编写完成
 */
public class IDAStar implements Searcher {

    private final Predictor predictor;

    //裁剪阈值
    private int cutoff;
    //下一轮迭代的裁剪阈值
    private int newCutoff;
    //最大迭代深度
    private int maxIteratorDepth = 256;
    //统计扩展结点数
    private int expanded = 0;
    private int explored = 0;

    private final Stack<Node> openStack;

    public IDAStar(Predictor predictor) {
        this.predictor = predictor;
        openStack = new Stack<Node>();;
    }

    @Override
    public Deque<Node> search(Problem problem) {
        if (!problem.solvable()){
            return null;
        }
        //获取根节点
        openStack.clear();
        Node root = problem.root(predictor);
        cutoff = root.evaluation();

        while (cutoff < maxIteratorDepth) {
            openStack.push(root);
            newCutoff = cutoff;
            //当栈未空时继续，执行带裁剪值的深度优先搜索
            expanded = 0;
            while (!openStack.empty()) {
                expanded++;
                Node node = openStack.pop();

                //更新裁剪值为未被探索节点中最小的评估值
                if (problem.goal(node.getState())) {
                    return generatePath(node);
                }
                //当小于等于裁剪值时，继续向深处搜索
                for (Node child : problem.childNodes(node, predictor)) {
                    //剪枝，防止节点探索回到父节点
                    if (child.evaluation() <= cutoff) {
                        if (node.getParent() == null || !node.getParent().equals(child)) {
                            openStack.push(child);
                            explored++;
                        }
                    } else {
                        //记录大于当前cutoff的最小值
                        newCutoff = (newCutoff > cutoff) ? (Math.min(child.evaluation(), newCutoff)) : child.evaluation();
                    }
                }
            }
            //更新裁剪值
            cutoff = newCutoff;
        }
        return null;
    }

    @Override
    public int nodesExpanded() {
        return expanded;
    }

    @Override
    public int nodesGenerated() {
        return explored;
    }
}
