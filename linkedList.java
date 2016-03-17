import java.util.*;
import java.lang.*;
import java.io.*;

public class linkedList<T> {
    
    public Node<T> head;
    public int size;
    
    public linkedList() {
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
    public void insert(T val) {
        Node<T> nptr = new Node<T>(val, null);
        size++;
        if(head == null) {
            head = nptr;
        }
        else {
            nptr.setNext(head);
            head = nptr;
        }
    }
    
    public void delete(T val) {
        Node<T> prevNode = null;
        Node<T> currNode = head;
        
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
        Node<T> ptr = head;
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
        Node<T> ptr = head;
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
	    linkedList<Integer> ll = new linkedList<Integer>();
	    ll.insert(23);
	    ll.insert(49);
	    ll.insert(7);
	    ll.insert(21);
	    ll.delete(23);
	    ll.delete(21);
	    //ll.delete(7);
	    //ll.delete(49);
	    //ll.display();
	    //ll.toString();
	    System.out.println(ll.toString());
	}
    
}