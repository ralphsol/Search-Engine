import java.util.*;
import java.lang.*;
import java.io.*;

public class Myset<T> {
    
    public Node top;
    public int size;
    
    public int getSize() {
        return size;
    }
    
    public class Node {
        T element;
        Node next;
    }
    
    public Myset() {
        top = null;
        size = 0;
    }
    
    public boolean IsEmpty() {
        return (size == 0);
    }
    
    public boolean IsMember(T o) {
        Node temp = top;
        while(temp != null){
            if (temp.element == o){
                return true;
            }
            temp = temp.next;
        }
        return false;
    }
    
    public void Insert(T o) {
        if(!IsMember(o)) {
            Node temp = top;
            top = new Node();
            top.element = o;
            top.next = temp;
            size++;
        }
    }
    
    public void Delete(T o) {
        if(IsMember(o)) {
            Node temp = top;
            if (temp.element == o) {
                top = top.next;
            }
            else {
                while(temp.next.element != o) {
                    temp = temp.next;
                }
                temp.next = temp.next.next;
            }
            size--;
        }
        else {
            throw new IllegalArgumentException(o + " is not a member of the set");
        }
    }
    
    public Myset<T> Intersection(Myset<T> a) {
        Node temp2 = a.top;
        Node temp1 = top;
        Myset<T> res = new Myset<T>();
        while(temp1 != null) {
            temp2 = a.top;
            while(temp2 != null) {
                if (temp1.element == temp2.element) {
                    Node temp = res.top;
                    res.top = new Node();
                    res.top.element = temp1.element;
                    res.top.next = temp;    
                }
                temp2 = temp2.next;
            }
            temp1 = temp1.next;    
        }
        return res;
    }
    
    public Myset<T> Union(Myset<T> a) {
        Myset<T> res = new Myset<T>();
        Node temp1 = top;
        Node temp2 = a.top;
        Myset<T> f = Intersection(a);
        Node temp3 = f.top;
        while(temp1 != null){
            Node temp = res.top;
            res.top = new Node();
            res.top.element = temp1.element;
            res.top.next = temp;
            temp1 = temp1.next;
        }
        while(temp2 != null){
            Node temp = res.top;
            res.top = new Node();
            res.top.element = temp2.element;
            res.top.next = temp;
            temp2 = temp2.next;
        }
        while(temp3 != null){
            res.Delete(temp3.element);
            temp3 = temp3.next;
        }
        return res;
    }
    
    public void printSet() {
        Node temp = top;
        while(temp != null) {
            System.out.println(temp.element);
            temp = temp.next;
        }
    }
    
    public String toString() {
        String str = "[ ";
        Node temp = top;
        while(temp != null) {
            str += (temp.element) + "; ";
            temp = temp.next;
        }
        str += "]";
        return str;
    }
    
    public static void main (String[] args)
	{
	    Myset<Integer> s = new Myset<Integer>();
        Myset<Integer> t = new Myset<Integer>();
        //s.Insert(20);
        //s.Insert(30);
        //s.Insert(40);
        //s.Insert(50);
        //s.Insert(60);
        //s.Delete(30);
        
        t.Insert(30);
        t.Insert(10);
        t.Insert(40);
        
        Myset<Integer> c = s.Union(t);
        System.out.println(s.toString());
        System.out.println(t.toString());
        System.out.println(c.toString());
	}
}