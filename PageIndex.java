import java.util.*;
import java.lang.*;
import java.io.*;

public class PageIndex {
    linkedList<WordEntry> list = new linkedList<WordEntry>(); 
    
    public boolean isMember(String str1) {
        Node<WordEntry> tmp = list.head;
        if(tmp == null) {
            return false;
        }
        while(tmp.getNext() != null) {
            if(tmp.getElement().str.equals(str1)) {
                return true;
            }
            tmp = tmp.getNext();
        }
        if(tmp.getElement().str.equals(str1)) {
            return true;
        }
        return false;
    }
    
    public void addPositionForWord(String str1, Position p) {
        if(isMember(str1)) {
            Node<WordEntry> tmp = list.head;
            while(tmp.getNext() != null) {
                if(tmp.getElement().str.equals(str1)) {
                    tmp.getElement().addPosition(p);
                }
                tmp = tmp.getNext();
            }
            if(tmp.getElement().str.equals(str1)) {
                tmp.getElement().addPosition(p);
            }    
        }
        else {
            WordEntry tmp2 = new WordEntry(str1);
            tmp2.addPosition(p);
            list.insert(tmp2);
        }
    }
    
    public linkedList<WordEntry> getWordEntries() {
        return list;
    }
    
    /*public String toString() {
        String str = list.toString();
        return str;
    }*/

    public String toString() {
        String printStr = "{";
        //printStr += "word:'" + str + "' "; 
        //printStr += "; list: ";
        
        if(list.head == null) {
            printStr += "Empty";
            printStr += "}";
            return printStr;
        }
        if(list.head.getNext() == null) {
            printStr += list.head.getElement().toString();
            printStr += "}";
            return printStr;
        }
        Node<WordEntry> ptr = list.head;
        while(ptr.getNext() != null) {
            printStr += ptr.getElement().toString() + "\n";
            ptr = ptr.getNext();
        }
        printStr += ptr.getElement().toString();
            
        printStr += "}";
        return printStr;
    
    }
    
    public static void main (String[] args)
	{
	    PageIndex mytest = new PageIndex();
	    PageEntry t1 = new PageEntry("webpages/stackmagazine");
	    PageEntry t2 = new PageEntry("webpages/stackoverflow");
	    Position p1 = new Position(t1, 59);
	    Position p2 = new Position(t1, 23);
	    Position p3 = new Position(t2, 37);
	    
	    mytest.addPositionForWord("joey", p1);
	    mytest.addPositionForWord("chandler", p2);
	    mytest.addPositionForWord("chandler", p3);
	    System.out.println(mytest.toString());
	}
	
}