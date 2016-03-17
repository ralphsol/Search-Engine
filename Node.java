//Node of a linked list
import java.util.*;
import java.lang.*;
import java.io.*;

public class Node<T> {
    
    private T element;
    private Node<T> next;
    
    public Node() {
        this(null, null);
    }
    
    public Node(T e, Node<T> n) {
        element = e;
        next = n;
    }
    
    public T getElement() {
        return element;
    }
    
    public Node<T> getNext() {
        return next;
    }
    
    public void setElement(T newElem) {
        element = newElem;
    }
    
    public void setNext(Node<T> newNext) {
        next = newNext;
    }
    
    public String toString() {
        String str = "[value:" + element + "]";
        return str;
    }
    
    public static void main (String[] args)
	{
	    Node<Integer> mytest = new Node<Integer>();
	    mytest.setElement(59);
	    System.out.println(mytest.toString());
	}
}