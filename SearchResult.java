import java.util.*;
import java.lang.*;
import java.io.*;

public class SearchResult implements Comparable<SearchResult>{
    
    private PageEntry p;
    private float r;
    
    public SearchResult(PageEntry p, float r) {
        this.p = p;
        this.r = r;
    }
    public PageEntry getPageEntry() {
        return p;
    }
    public float getRelevance() {
        return r;
    }
    public int compareTo(SearchResult otherObject) {
        if (this.getRelevance() == otherObject.getRelevance()) {
            return 0;
        }
        else if (this.getRelevance() > otherObject.getRelevance()) {
            return 1;
        }
        else {
            return -1;
        }
    }
    public static void main(String args[]){  
  
        Vector<SearchResult> al = new Vector<SearchResult>();  
        PageEntry p1 = new PageEntry("joey");
        PageEntry p2 = new PageEntry("chandler");
        PageEntry p3 = new PageEntry("phoebe");
        
        al.add(new SearchResult(p1 ,23));  
        al.add(new SearchResult(p2 ,27));  
        al.add(new SearchResult(p3 ,21));  
        
        Collections.sort(al);  
        
        for(int i = 0 ; i < al.size() ; i++) { 
            System.out.println(al.get(i).getPageEntry().getName());
        }        
    }    
}