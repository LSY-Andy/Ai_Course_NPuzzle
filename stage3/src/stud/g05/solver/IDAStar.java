package stud.g05.solver;

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
    //private final HashMap<Integer, Integer> closeStack;

    public IDAStar(Predictor predictor) {
        this.predictor = predictor;
        openStack = new Stack<Node>();
        //closeStack = new HashMap<Integer, Integer>();
    }

    @Override
    public Deque<Node> search(Problem problem) {
        if (!problem.solvable()){
            return null;
        }
        //��ȡ���ڵ�
        openStack.clear();
        //closeStack.clear();
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
//                System.out.println("cost="+node.evaluation()+" pathCost="+node.getPathCost());
//                node.getState().draw();

                //���²ü�ֵΪδ��̽���ڵ�����С������ֵ
                if (problem.goal(node.getState())) {
                    System.out.println("cutoff="+cutoff+" expanded="+expanded);
                    return generatePath(node);
                }
                //��С�ڵ��ڲü�ֵʱ�������������
                for (Node child : problem.childNodes(node, predictor)) {
                    //��֦����ֹ�ڵ�̽���ص����ڵ�
                    if (child.evaluation() <= cutoff) {
                        explored++;
                        if (node.getParent() == null || !node.getParent().equals(child)) {

                            openStack.push(child);

                        }
                    } else {
                        //��¼���ڵ�ǰcutoff����Сֵ
                        newCutoff = (newCutoff > cutoff) ? (Math.min(child.evaluation(), newCutoff)) : child.evaluation();
                        //System.out.println("cutoff="+cutoff+" newcutoff="+newCutoff+" child.f="+child.evaluation());
                    }
                }
            }
            //���²ü�ֵ
            cutoff = newCutoff;
            //System.out.println("cutoff="+cutoff+" expanded="+expanded);
            //closeStack.clear();
            //System.out.println("cutoff: " + cutoff);
            //System.out.println("expanded node: " +  expanded);
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