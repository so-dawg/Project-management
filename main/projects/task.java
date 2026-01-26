 
 class Node {
    int data;
    Node next;

    Node(int data) {
        this.data = data;
    }
}

class MyLinkedList {
    Node head;

    void add(int Data) {
        if (head == null) {
            head = new Node(Data);
            return;
        }
        Node cur = head;
        while (cur.next != null)
            cur = cur.next;
        cur.next = new Node(Data);
    }
    void traversal(Node head){
        Node cur = head;
        if (cur == null) {
            return;
        }
        else if (cur != null){
            while(cur != null){
                System.out.println(cur.data + "->");
                cur = cur.next;
            }
        }
        
    }
}
// this use for testing link list of task
public class task{
    public static void main(String[] args) {
        
    }
}