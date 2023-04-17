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
    private final Predictor predictor; //Ԥ�������Ե�ǰ״̬��������ʽ��ֵ
    private final Set<State> explored = new HashSet<>();
    private final Set<State> expanded = new HashSet<>();
    Deque<Node> resPath = new ArrayDeque<>(); // ���ص�·��
    Problem local_problem;

    /**
     * ���캯��
     * @param predictor �����Ԥ����������λ���ƣ������پ���ȣ�
     */
    public IdAStar(Predictor predictor) {
        super();
        this.predictor = predictor;
    }

    @Override
    public Deque<Node> search(Problem problem) {
        local_problem = problem;
        // ���ж������Ƿ�ɽ⣬�޽�ʱֱ�ӷ��ؽ�·��Ϊnull
        if (!local_problem.solvable()) {
            System.out.println("No Solution!");
            return null;
        }

        // ��ʼ�ڵ�root
        Node root = local_problem.root(predictor);
        // ����������
        int maxDepth = root.getHeuristic();
        while (!dfs(root, null, maxDepth)) { //����ʧ�ܣ�����������
            maxDepth++;
        }
        return resPath;
    }

    boolean dfs(Node node, Node parentNode, int depth) {
        if (node.getPathCost() >= depth) // ��֦
            return false;
        if (local_problem.goal(node.getState())) {
            // �������Ŀ��״̬�����ݵõ�·��
            resPath = generatePath(node);
            return true;
        }

        //�����չ���
        expanded.add(node.getState());

        // ��node���ӽڵ����
        for (Node child : local_problem.childNodes(node, predictor)) {
            explored.add(child.getState());
            // ȷ������ص���һ��
            if (parentNode != null) {
                if (child.equals(parentNode))
                    continue;
            }
            // ��������С���������
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
