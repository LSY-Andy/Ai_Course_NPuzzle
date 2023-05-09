package stud.g05.queue;

import core.solver.queue.Frontier;
import core.solver.queue.Node;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;

public class PqFrontier extends PriorityQueue<Node> implements Frontier {
    private final Comparator<Node> comparator = Node::compareTo;
    PriorityQueue<Node> nodes = new PriorityQueue<>(comparator);
    private final HashMap<Integer, Node> hashMap = new HashMap<>();

    // �ڵ����ȼ��Ƚ�������Node���ж�����������ͬ�ıȽ����� Dijkstra,Greedy Best-First,Best-First)
    // evaluator����������ȡ��Frontier�е��ĸ�Ԫ�ء�
    protected final Comparator<Node> evaluator;
    public PqFrontier(Comparator<Node> evaluator) {
        this.evaluator = evaluator;
    }

    /**
     * ȡ��Frontier�еĵ�һ��Ԫ��
     */
    public Node poll(){
        Node first = nodes.poll();
        hashMap.remove(first.getState().hashCode());
        return first;
    }

    /**
     * ���Frontier
     */
    public void clear(){
        nodes.clear();
        hashMap.clear();
    };

    /**
     * Frontier��Ԫ�صĸ���
     */
    public int size(){
        return nodes.size();
    }

    /**
     * Frontier�Ƿ�Ϊ��
     *
     */
    public boolean isEmpty(){
        return nodes.isEmpty();
    }

    /**
     * Frontier���Ƿ��н��node
     */
    @Override
    public boolean contains(Node node) {
        return hashMap.containsKey(node.getState().hashCode());
    }

    /**
     * �����node���뵽���ȶ�����
     * @param node Ҫ�������ȶ��еĽ��
     */
    @Override
    public boolean offer(Node node) {
        return this.add(node);
    }
    public boolean add(Node node) {
        nodes.add(node);
        hashMap.put(node.getState().hashCode(), node);
        return true;
    }

    /**
     * ���ȶ�����ʽ��frontier�ڵ㣨��Ҫ���ʵģ�������
     */
    public Iterator<Node> iterator(){
        return nodes.iterator();
    }

    /**
     * ����Frontier�еĵ�һ��Ԫ��
     */
    public Node peek(){
        return nodes.peek();
    }

    /**
     * ���½ڵ��滻��������ͬ״̬�ľɽڵ� oldNode
     * @param oldNode ���滻�Ľ��
     * @param newNode �½��
     */
    public void replace(Node oldNode, Node newNode){
        hashMap.put(oldNode.getState().hashCode(), newNode);
        nodes.add(newNode);
    }
}