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
    private final Predictor predictor; //预测器，对当前状态进行启发式估值
    private final Set<State> explored = new HashSet<>();
    private final Set<State> expanded = new HashSet<>();
    Deque<Node> resPath = new ArrayDeque<>(); // 返回的路径
    Problem local_problem;

    /**
     * 构造函数
     * @param predictor 具体的预测器（不在位将牌，曼哈顿距离等）
     */
    public IdAStar(Predictor predictor) {
        super();
        this.predictor = predictor;
    }

    @Override
    public Deque<Node> search(Problem problem) {
        local_problem = problem;
        // 先判断问题是否可解，无解时直接返回解路径为null
        if (!local_problem.solvable()) {
            System.out.println("No Solution!");
            return null;
        }

        // 起始节点root
        Node root = local_problem.root(predictor);
        // 最大搜索深度
        int maxDepth = root.getHeuristic();
        while (!dfs(root, null, maxDepth)) { //深搜失败，最大深度增加
            maxDepth++;
        }
        return resPath;
    }

    boolean dfs(Node node, Node parentNode, int depth) {
        if (node.getPathCost() >= depth) // 剪枝
            return false;
        if (local_problem.goal(node.getState())) {
            // 如果到达目标状态，回溯得到路径
            resPath = generatePath(node);
            return true;
        }

        //添加扩展结点
        expanded.add(node.getState());

        // 对node的子节点迭代
        for (Node child : local_problem.childNodes(node, predictor)) {
            explored.add(child.getState());
            // 确保不会回到上一步
            if (parentNode != null) {
                if (child.equals(parentNode))
                    continue;
            }
            // 比最大深度小则继续迭代
            if (child.evaluation() < depth && dfs(child, node, depth)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int nodesExpanded() {
        return expanded.size();
    }

    @Override
    public int nodesGenerated() {
        return explored.size();
    }
}
