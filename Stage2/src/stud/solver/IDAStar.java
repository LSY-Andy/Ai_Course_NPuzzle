package stud.solver;

import core.problem.Problem;
import core.solver.algorithm.Searcher;
import core.solver.algorithm.heuristic.Predictor;
import core.solver.queue.Node;
import java.util.Deque;
import java.util.Stack;

/**
 * ���������A*�㷨����Ҫͬѧ���Լ���д���
 */
public class IDAStar implements Searcher {

    private final Predictor predictor;

    //�ü���ֵ
    private int cutoff;
    //��һ�ֵ����Ĳü���ֵ
    private int newCutoff;
    //���������
    private int maxIteratorDepth = 256;
    //ͳ����չ�����
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
        //��ȡ���ڵ�
        openStack.clear();
        Node root = problem.root(predictor);
        cutoff = root.evaluation();

        while (cutoff < maxIteratorDepth) {
            openStack.push(root);
            newCutoff = cutoff;
            //��ջδ��ʱ������ִ�д��ü�ֵ�������������
            expanded = 0;
            while (!openStack.empty()) {
                expanded++;
                Node node = openStack.pop();

                //���²ü�ֵΪδ��̽���ڵ�����С������ֵ
                if (problem.goal(node.getState())) {
                    return generatePath(node);
                }
                //��С�ڵ��ڲü�ֵʱ�������������
                for (Node child : problem.childNodes(node, predictor)) {
                    //��֦����ֹ�ڵ�̽���ص����ڵ�
                    if (child.evaluation() <= cutoff) {
                        if (node.getParent() == null || !node.getParent().equals(child)) {
                            openStack.push(child);
                            explored++;
                        }
                    } else {
                        //��¼���ڵ�ǰcutoff����Сֵ
                        newCutoff = (newCutoff > cutoff) ? (Math.min(child.evaluation(), newCutoff)) : child.evaluation();
                    }
                }
            }
            //���²ü�ֵ
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
