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

    // 节点优先级比较器，在Node类中定义了三个不同的比较器（ Dijkstra,Greedy Best-First,Best-First)
    // evaluator决定了优先取出Frontier中的哪个元素。
    protected final Comparator<Node> evaluator;
    public PqFrontier(Comparator<Node> evaluator) {
        this.evaluator = evaluator;
    }

    /**
     * 取出Frontier中的第一个元素
     */
    public Node poll(){
        Node first = nodes.poll();
        hashMap.remove(first.getState().hashCode());
        return first;
    }

    /**
     * 清空Frontier
     */
    public void clear(){
        nodes.clear();
        hashMap.clear();
    };

    /**
     * Frontier中元素的个数
     */
    public int size(){
        return nodes.size();
    }

    /**
     * Frontier是否为空
     *
     */
    public boolean isEmpty(){
        return nodes.isEmpty();
    }

    /**
     * Frontier中是否含有结点node
     */
    @Override
    public boolean contains(Node node) {
        return hashMap.containsKey(node.getState().hashCode());
    }

    /**
     * 将结点node插入到优先队列中
     * @param node 要插入优先队列的结点
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
     * 优先队列形式的frontier节点（将要访问的）迭代器
     */
    public Iterator<Node> iterator(){
        return nodes.iterator();
    }

    /**
     * 返回Frontier中的第一个元素
     */
    public Node peek(){
        return nodes.peek();
    }

    /**
     * 用新节点替换掉具有相同状态的旧节点 oldNode
     * @param oldNode 被替换的结点
     * @param newNode 新结点
     */
    public void replace(Node oldNode, Node newNode){
        hashMap.put(oldNode.getState().hashCode(), newNode);
        nodes.add(newNode);
    }
}