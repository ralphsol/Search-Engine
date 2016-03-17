import java.util.*;
import java.lang.*;
import java.io.*;

public class Set {
    
    public Node top;
    public int size;
    
    public int getSize() {
        return size;
    }
    
    public class Node {
        SearchResult element;
        Node next;
    }
    
    public Set() {
        top = null;
        size = 0;
    }
    
    public boolean IsEmpty() {
        return (size == 0);
    }
    
    public boolean IsMember(SearchResult o) {
        Node temp = top;
        while(temp != null){
            if (temp.element.getPageEntry().getName().equals(o.getPageEntry().getName())){
                return true;
            }
            temp = temp.next;
        }
        return false;
    }
    
    public void Insert(SearchResult o) {
        if(!IsMember(o)) {
            Node temp = top;
            top = new Node();
            top.element = o;
            top.next = temp;
            size++;
        }
    }
    
    public void Delete(SearchResult o) {
        if(IsMember(o)) {
            Node temp = top;
            if (temp.element.getPageEntry().getName().equals(o.getPageEntry().getName())) {
                top = top.next;
            }
            else {
                while(!temp.next.element.getPageEntry().getName().equals(o.getPageEntry().getName())) {
                    temp = temp.next;
                }
                temp.next = temp.next.next;
            }
            size--;
        }
    }
    
    public Set Intersection(Set a) {
        Set.Node temp2 = a.top;
        Node temp1 = top;
        Set res = new Set();
        while(temp1 != null) {
            temp2 = a.top;
            while(temp2 != null) {
                if (temp1.element.getPageEntry().getName().equals(temp2.element.getPageEntry().getName())) {
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
    
    public Set Union(Set a) {
        Set res = new Set();
        Node temp1 = top;
        Set.Node temp2 = a.top;
        Set f = Intersection(a);
        Node temp3 = f.top;
        while(temp1 != null){
            Node temp = res.top;
            res.top = new Node();
            res.top.element = temp1.element;
            res.top.next = temp;
            temp1 = temp1.next;
        }
        //System.out.println(res.toString());
        while(temp2 != null){
            Node temp = res.top;
            res.top = new Node();
            res.top.element = temp2.element;
            res.top.next = temp;
            temp2 = temp2.next;
        }
        //System.out.println(res.toString());
        while(temp3 != null){
            res.Delete(temp3.element);
            temp3 = temp3.next;
        }
        //System.out.println(res.toString());
        return res;
    }
    
    public String toString() {
        String str = "[ ";
        Node temp = top;
        while(temp != null) {
            str += (temp.element.getPageEntry().getName()) + "; ";
            temp = temp.next;
        }
        str += "]";
        return str;
    }
    
    public static void main (String[] args)
	{
	    Set mytest = new Set();
	    Set mytest2 = new Set();
	    
	    PageEntry p1 = new PageEntry("barney");
	    SearchResult s1 = new SearchResult(p1, 23);
	    SearchResult s2 = new SearchResult(p1, 45);
	    
	    //mytest.Insert(s1);
	    mytest2.Insert(s2);
	    mytest = mytest.Union(mytest2);
	    
	    System.out.println(mytest.toString());
	}
}