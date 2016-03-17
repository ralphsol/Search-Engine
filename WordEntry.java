import java.util.*;
import java.lang.*;
import java.io.*;

public class WordEntry {
    
    public AvlTree v = new AvlTree();
    //public linkedList<Position> list = new linkedList<Position>();
    public String str;
    
    public WordEntry() {
        
    }
    
    public WordEntry(String word) {
        str = word;
        //linkedList<Position> v = new linkedList<Position>();
    }
    
    public String getWord() {
        return str;
    }
    
    public void addPosition(Position position) {
        v.insert(position);
        //list.insert(position);
    }
    
    /*public void addPositions(linkedList<Position> positions) {
        Node<Position> tmp = positions.head;
        if(tmp == null) {
            return ;
        }
        while(tmp.getNext() != null) {
            addPosition(tmp.getElement());
            //list.insert(tmp.getElement());
            tmp = tmp.getNext();
        }
        //list.insert(tmp.getElement());
        addPosition(tmp.getElement());
    }*/
    
    public void addPositions(AvlTree positions) {
        ArrayList<AvlNode> tmpNode = positions.inorder();
        for(int i = 0 ; i < tmpNode.size() ; i++) {
            addPosition(tmpNode.get(i).key);
        }
    }
    
    public linkedList<Position> getAllPositionsForThisWord() {
        ArrayList<AvlNode> tmpNode = v.inorder();
        linkedList<Position> res = new linkedList<Position>();
        for(int i = 0 ; i < tmpNode.size() ; i++) {
            res.insert(tmpNode.get(i).key);
        }
        return res;
    }
    
    public String toString() {
        String printStr = "[";
        printStr += "word:'" + str + "' "; 
        printStr += "; list: ";
        printStr += v.inorder();    
        printStr += "]";
        return printStr;
    }
    
    public static void main (String[] args)
	{
	    WordEntry mytest = new WordEntry("voila");
	    PageEntry t1 = new PageEntry("webpages/stackmagazine");
	    PageEntry t2 = new PageEntry("webpages/stackoverflow");
	    Position p1 = new Position(t1, 59);
	    Position p2 = new Position(t1, 23);
	    Position p3 = new Position(t2, 37);
	    mytest.addPosition(p1);
	    mytest.addPosition(p2);
	    mytest.addPosition(p3);
	    
	    WordEntry mytest2 = new WordEntry("harshil");
	    //mytest2.addPositions(mytest.v);
	    System.out.println(mytest.toString());
	}
}