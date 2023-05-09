package core.solver.queue;

/**
 * Frontier�ӿ�
 */
import core.problem.State;

import java.util.*;

/**
 *  AbstractQueue�����࣬
 *  ���ŵ�Ԫ�ص�����Ϊ core.solver.Node
 */
public abstract class Frontier extends AbstractQueue<Node> {

    public Frontier(Comparator<Node> evaluator) {
        this.evaluator = evaluator;
    }

    public Comparator<Node> getEvaluator() {
        return evaluator;
    }

    // �ڵ����ȼ��Ƚ�������Node���ж�����������ͬ�ıȽ�����Dijkstra, Greedy Best-First, and Best-First��
    // ��ͬ��ѡ���Ӧ��ͬ���㷨��
    protected final Comparator<Node> evaluator;

    /**
     * ��ȡ Frontier �У�״̬Ϊ s �Ľڵ�
     * @param s  ״̬
     * @return   ���ڣ�  ��Ӧ��״̬Ϊ s �Ľڵ㣻
     *           �����ڣ�null
     */
    protected abstract Node getNode(State s);

    /**
     * ���Frontier���Ѿ�������node״̬��ͬ�Ľ�㣬
     * ������������֮�䲻�õ���һ����
     * @param node ���
     * @return ����ɹ�����true
     */
    public final boolean discardOrReplace(Node node){
        if (node == null)
            throw new NullPointerException();

        //���node�Ƿ������frontier��; null: not revisited
        Node oldNode = getNode(node.getState());
        //���oldNodeΪnull����ǰ���node��״̬����Frontier�У���ô�϶���explored���У�
        // ����Ϊh������consistent�ģ�����discard
        //���oldNode��Ϊnull������oldNode�Ѿ���Frontier�У����ҾɵĹ�ֵ���µĴ󣬼������ɵĽ�����
        if (oldNode != null && evaluator.compare(oldNode, node) > 0){
            //�����½ڵ��滻�ɽڵ�
            replace(oldNode, node);
        }
        return true;
    }

    /**
     * �ýڵ� e �滻��������ͬ״̬�ľɽڵ� oldNode
     *
     * @param oldNode ���滻�Ľ��
     * @param newNode �½��
     */
    public void replace(Node oldNode, Node newNode) {
        this.remove(oldNode);
        this.add(newNode);
    }

    public boolean contains(Node node) {
        return false;
    }

    //�ú����ж�frontier���Ƿ���ڸ�child��״̬��ͬ�Ľ��
    public abstract boolean compare(Node node);
}
