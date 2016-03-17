import java.util.*;
import java.lang.*;
import java.io.*;

public class Position {
    PageEntry p;
    int WordIndex;
    
    public Position(PageEntry p, int WordIndex) {
        this.p = p;
        this.WordIndex = WordIndex;
    }
    
    public PageEntry getPageEntry() {
        return p;
    }
    
    public int getWordIndex() {
        return WordIndex;
    }
    
    public String toString() {
        String str = "[";
        str += "Page:'" + p.getName();
        str += "' WordIndex:" + WordIndex;
        str += "]";
        return str;
    }
    
    public static void main (String[] args)
	{
	    PageEntry t1 = new PageEntry("stackmagazine");
	    Position mytest = new Position(t1, 59);
	    System.out.println(mytest.toString());
	}
}