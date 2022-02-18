package Lab3;
/*
* 1. 对于Item的理解(之前都是使用的int 或者其他)
* 2. items = (Item[]) new Object[100];
* 3. arraycopy的函数如何操作
* */

//数组链表
public class AList <Item>{
    private Item[] items;
    private int size;

    /** creates an empty list*/
    public AList(){
        items = (Item[]) new Object[100];
        size = 0;
    }

    private void resize(int capacity){
        Item[] a  = (Item[]) new Object[capacity];
        System.arraycopy(items,0,a,0,size);
        items = a;
    }

    public void addLast(Item x){
        if(size == items.length){
            resize(size + 1);
        }

        items[size] = x;
        size++;
    }

    public Item getLast(){
        return items[size - 1];
    }

    public Item get(int i){
        return items[i];
    }

    public int size(){
        return size;
    }

    public Item removeLast(){
        Item x = getLast();
        items[size - 1] = null;
        size = size - 1;
        return x;
    }
}
