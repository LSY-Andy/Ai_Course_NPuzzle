package stud.g05.queue;

import core.problem.State;
import core.solver.queue.Frontier;
import core.solver.queue.Node;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class ZobristFrontier extends PriorityQueue<Node> implements Frontier {
    private final Comparator<Node> evaluator;
    private final HashMap<Integer, Node> hashMap = new HashMap<>(); //һ���������Ӧһ������״̬

    public ZobristFrontier(Comparator<Node> evaluator) {
        super(evaluator);
        this.evaluator = evaluator;
    }

    /**
     * ȡ��Frontier�еĵ�һ��Ԫ�غ͹�ϣ���и�Ԫ�ض�Ӧ�������
     */
    @Override
    public Node poll(){
        Node node = super.poll();
        if (node != null) {
            hashMap.remove(((node.getState()).hashCode()));
        }
        return node;
    }

    /**
     * Frontier���Ƿ��н��node
     */
    @Override
    public boolean contains(Node node) {
        return getNode(node.getState()) != null;
    }

    /***
     * ��Frontier�в�����node��
     * �������Ľ�㲻��frontier�У���ֱ�Ӳ���
     * ���Frontier���Ѿ�������ͬ״̬��������㣬������������֮�в��õġ�
     * @param node Ҫ����Ľ��
     * @return true: �Ѳ���/replaced; false: discarded
     */
    @Override
    public boolean offer(Node node) {
        Node oldNode = getNode(node.getState());
        if(oldNode == null){ //frontier��δ�ҵ���node״̬��ͬ�Ľڵ�
            //����fֵ����ʱ��nodeӦ���ڵ�λ��
            super.offer(node);
            hashMap.put((node.getState()).hashCode(), node);
            return true;
        } else{ //node���ظ����ʵĽڵ�
            return discardOrReplace(oldNode, node);
        }
    }

    private Node getNode(State state){return hashMap.get(state.hashCode());}

    /**
     * @param oldNode ��node״̬��ͬ�ľɽ��
     * @param node ��㣬��״̬Ҫô�Ѿ�������Explored�У�Ҫô�Ѿ�������Frontier��
     * @return true: replaced; false: discarded
     */
    private boolean discardOrReplace(Node oldNode, Node node) {
        // ����ɽ��Ĺ�ֵ���µĴ󣬼������ɵĽ�����
        if (evaluator.compare(oldNode, node) > 0) {
            // ���½ڵ��滻�ɽڵ�
            replace(oldNode, node);
            return true;
        }
        return false;   //discard���ӵ��½��
    }
    /**
     * ���½ڵ��滻��������ͬ״̬�ľɽڵ� oldNode
     *
     * @param oldNode ���滻�Ľ��
     * @param newNode �½��
     */
    private void replace(Node oldNode, Node newNode) {
        hashMap.put((oldNode.getState()).hashCode(), newNode);
        super.offer(newNode);
    }
}
