package stud.queue;


import core.problem.State;
import core.solver.queue.Frontier;
import core.solver.queue.Node;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;


        //evaluator决定了优先取出Frontier中的哪个元素。
public class MyFrontier extends Frontier {

    /**
     * 优先队列，节点根据评估值排序
     */
    /*Open 表数据结构采用 PriorityQueue 和 HashMap 共同实现。
     其中优先队列 PriorityQueue 实现数据的有序存取。
      HashMap 映射用来实现取最小元素
     */
    PriorityQueue<Node> frontier = new PriorityQueue(new Comparator<Node>(){
        public int compare(Node a, Node b){
            //return evaluator.compare(a, b);
            if (a.evaluation() != b.evaluation()) {
                return a.evaluation() - b.evaluation();
            } else {
                return a.getHeuristic() - b.getHeuristic();
            }
        }
    });
    //增加哈希Map集合，降低查找时间
    HashMap<Integer, Node> hashMap=new HashMap<Integer, Node>();

    /**
     *
     * @param comparator
     */
    public MyFrontier(Comparator<Node> comparator) {
        super(comparator);
    }

    /**
     * 获取 Frontier 中，状态为 s 的节点
     *
     * @param s 状态
     * @return 存在：  相应的状态为 s 的节点；
     * 不存在：null
     */
    @Override
    protected Node getNode(State s) {
        return hashMap.get(s.hashCode());
    }

    /**
     * 用节点 e 替换掉具有相同状态的旧节点 oldNode
     *
     * @param oldNode
     * @param e
     */
    @Override
    public void replace(Node oldNode, Node e) {
        hashMap.put(oldNode.getState().hashCode(), e);
        //frontier.remove(oldNode);
        frontier.offer(e);
    }

    /**
     * Returns an iterator over the elements contained in this collection.
     *
     * @return an iterator over the elements contained in this collection
     */
    @Override
    public Iterator<Node> iterator() {
        return frontier.iterator();
    }

    @Override
    public int size() {
        return frontier.size();
    }

    @Override

    //对于存取，用的是优先队列里自己带的offer函数实现的存取，对于哈希表map用的put的方法存
    public boolean offer(Node node) {
        hashMap.put(node.getState().hashCode(), node);
        frontier.offer(node);
        return true;
    }

    @Override
    //取出Frontier中的第一个元素
    public Node poll(){
        try {
            Node node = frontier.poll();
            hashMap.remove(node.getState().hashCode());
            return node;
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    //输入流中读取一个字符
    public Node peek() {
        return frontier.peek();
    }

    /**
     * 判断是否含有该节点
     * @param node
     * @return
     */
    @Override
    //该函数判断frontier中是否存在跟child的状态相同的结点
    public boolean compare(Node node) {
        return hashMap.get(node.getState().hashCode()) != null;
    }
}
