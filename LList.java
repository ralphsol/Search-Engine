import java.util.*;
import java.lang.*;
import java.io.*;

public class LList {
    
    public Node<WordEntry> head;
    public int size;
    
    public LList() {
        head = null;
        size = 0;
    }
    
    public boolean isEmpty() {
        return (head == null);
    }
    
    public int getSize() {
        return size;
    }
    
    //Inserts at beginning
    public void insert(WordEntry val) {
        Node<WordEntry> nptr = new Node<WordEntry>(val, null);
        size++;
        if(head == null) {
            head = nptr;
        }
        else {
            nptr.setNext(head);
            head = nptr;
        }
    }
    
    public void delete(WordEntry val) {
        Node<WordEntry> prevNode = null;
        Node<WordEntry> currNode = head;
        
        if(head.getElement() == val) {
            head = head.getNext();
            size--;
            return ;
        }
        
        while(currNode != null && currNode.getElement() != val) {
            prevNode = currNode;
            currNode = currNode.getNext();
        }
        
        if(currNode != null) {
            prevNode.setNext(currNode.getNext());
            size--;
        }
    }
    
    public void display() {
        if(head == null) {
            System.out.println("Empty");
            return ;
        }
        if(head.getNext() == null) {
            System.out.println(head.getElement());
            return ;
        }
        Node<WordEntry> ptr = head;
        while(ptr.getNext() != null) {
            System.out.println(ptr.getElement());
            ptr = ptr.getNext();
        }
        System.out.println(ptr.getElement());
    }
    
    public String toString() {
        String str = "[";
        if(head == null) {
            str += "Empty";
            str += "]";
            return str;
        }
        if(head.getNext() == null) {
            str += "head:";
            str += head.getElement();
            str += "]";
            return str;
        }
        Node<WordEntry> ptr = head;
        str += "head: ";
        while(ptr.getNext() != null) {
            str += ptr.getElement() + "->";
            //System.out.println(str);
            ptr = ptr.getNext();
        }
        str += ptr.getElement();
        str += "]";
        return str;
    }
    
    public static void main (String[] args)
	{
	    LList ll = new LList();
	    WordEntry mytest = new WordEntry("voila");
	    PageEntry t1 = new PageEntry("stackmagazine");
	    PageEntry t2 = new PageEntry("stackoverflow");
	    Position p1 = new Position(t1, 59);
	    Position p2 = new Position(t1, 23);
	    Position p3 = new Position(t2, 37);
	    mytest.addPosition(p1);
	    mytest.addPosition(p2);
	    mytest.addPosition(p3);
	    
	    WordEntry mytest2 = new WordEntry("harshil");
	    mytest2.addPositions(mytest.v);
	    ll.insert(mytest);
	    ll.insert(mytest2);
	    /*ll.insert(23);
	    ll.insert(49);
	    ll.insert(7);
	    ll.insert("harshil");
	    ll.delete(23);*/
	    //ll.delete(21);
	    //ll.delete(7);
	    //ll.delete(49);
	    //ll.display();
	    //ll.toString();
	    System.out.println(ll.toString());
	}
    
}