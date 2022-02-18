package Lab3;
//链表
public class SLList <Item>{
    private class IntNode{
        public Item item;
        public IntNode next;

        public IntNode(Item i,IntNode n){
            item = i;
            next = n;
        }
    }

    private IntNode sentinel;
    private int size;

    public SLList(){
        sentinel = new IntNode(null,null);
        size = 0;
    }

    public void addFirst(Item x){
        sentinel.next = new IntNode(x,sentinel.next);
        size++;
    }

    public Item getFirst(){
        return sentinel.next.item;
    }

    public void addLast(Item x){
        size++;

        IntNode p = sentinel;

        while(p.next != null){
            p = p.next;
        }

        p.next = new IntNode(x,null);
    }

    public int size(){
        return size;
    }

    public static void main(String[] args) {
        SLList L = new SLList();
        L.addLast(20);
        System.out.println(L.size());
    }
}
