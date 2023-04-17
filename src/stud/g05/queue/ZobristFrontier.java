package stud.g05.queue;

import core.problem.State;
import core.solver.queue.Frontier;
import core.solver.queue.Node;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class ZobristFrontier extends PriorityQueue<Node> implements Frontier {
    private final Comparator<Node> evaluator;
    private final HashMap<Integer, Node> hashMap = new HashMap<>(); //一个随机数对应一种期盼状态

    public ZobristFrontier(Comparator<Node> evaluator) {
        super(evaluator);
        this.evaluator = evaluator;
    }

    /**
     * 取出Frontier中的第一个元素和哈希表中该元素对应的随机数
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
     * Frontier中是否含有结点node
     */
    @Override
    public boolean contains(Node node) {
        return getNode(node.getState()) != null;
    }

    /***
     * 在Frontier中插入结点node。
     * 如果插入的结点不在frontier中，则直接插入
     * 如果Frontier中已经存在相同状态的其他结点，则舍弃掉二者之中不好的。
     * @param node 要插入的结点
     * @return true: 已插入/replaced; false: discarded
     */
    @Override
    public boolean offer(Node node) {
        Node oldNode = getNode(node.getState());
        if(oldNode == null){ //frontier中未找到与node状态相同的节点
            //按照f值排序时，node应该在的位置
            super.offer(node);
            hashMap.put((node.getState()).hashCode(), node);
            return true;
        } else{ //node是重复访问的节点
            return discardOrReplace(oldNode, node);
        }
    }

    private Node getNode(State state){return hashMap.get(state.hashCode());}

    /**
     * @param oldNode 与node状态相同的旧结点
     * @param node 结点，其状态要么已经出现在Explored中，要么已经出现在Frontier中
     * @return true: replaced; false: discarded
     */
    private boolean discardOrReplace(Node oldNode, Node node) {
        // 如果旧结点的估值比新的大，即新生成的结点更好
        if (evaluator.compare(oldNode, node) > 0) {
            // 用新节点替换旧节点
            replace(oldNode, node);
            return true;
        }
        return false;   //discard，扔掉新结点
    }
    /**
     * 用新节点替换掉具有相同状态的旧节点 oldNode
     *
     * @param oldNode 被替换的结点
     * @param newNode 新结点
     */
    private void replace(Node oldNode, Node newNode) {
        hashMap.put((oldNode.getState()).hashCode(), newNode);
        super.offer(newNode);
    }
}
