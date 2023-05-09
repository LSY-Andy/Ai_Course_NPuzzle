package stud.queue;


import core.problem.State;
import core.solver.queue.Frontier;
import core.solver.queue.Node;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;


        //evaluator����������ȡ��Frontier�е��ĸ�Ԫ�ء�
public class MyFrontier extends Frontier {

    /**
     * ���ȶ��У��ڵ��������ֵ����
     */
    /*Open �����ݽṹ���� PriorityQueue �� HashMap ��ͬʵ�֡�
     �������ȶ��� PriorityQueue ʵ�����ݵ������ȡ��
      HashMap ӳ������ʵ��ȡ��СԪ��
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
    //���ӹ�ϣMap���ϣ����Ͳ���ʱ��
    HashMap<Integer, Node> hashMap=new HashMap<Integer, Node>();

    /**
     *
     * @param comparator
     */
    public MyFrontier(Comparator<Node> comparator) {
        super(comparator);
    }

    /**
     * ��ȡ Frontier �У�״̬Ϊ s �Ľڵ�
     *
     * @param s ״̬
     * @return ���ڣ�  ��Ӧ��״̬Ϊ s �Ľڵ㣻
     * �����ڣ�null
     */
    @Override
    protected Node getNode(State s) {
        return hashMap.get(s.hashCode());
    }

    /**
     * �ýڵ� e �滻��������ͬ״̬�ľɽڵ� oldNode
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

    //���ڴ�ȡ���õ������ȶ������Լ�����offer����ʵ�ֵĴ�ȡ�����ڹ�ϣ��map�õ�put�ķ�����
    public boolean offer(Node node) {
        hashMap.put(node.getState().hashCode(), node);
        frontier.offer(node);
        return true;
    }

    @Override
    //ȡ��Frontier�еĵ�һ��Ԫ��
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
    //�������ж�ȡһ���ַ�
    public Node peek() {
        return frontier.peek();
    }

    /**
     * �ж��Ƿ��иýڵ�
     * @param node
     * @return
     */
    @Override
    //�ú����ж�frontier���Ƿ���ڸ�child��״̬��ͬ�Ľ��
    public boolean compare(Node node) {
        return hashMap.get(node.getState().hashCode()) != null;
    }
}
