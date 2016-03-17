import java.util.*;
import java.lang.*;
import java.io.*;

public class MySort {
    
    public static ArrayList<SearchResult> sortThisList(Set listOfSortableEntries) {
        ArrayList<SearchResult> res = new ArrayList<SearchResult>();
        Set.Node tmpNode = listOfSortableEntries.top;
        while(tmpNode != null) {
            res.add(tmpNode.element);
            tmpNode = tmpNode.next;
        }
        Collections.sort(res);
        return res;
    }
    
    public static void main(String args[]){  
  
        Myset<SearchResult> al = new Myset<SearchResult>();  
        PageEntry p1 = new PageEntry("joey");
        PageEntry p2 = new PageEntry("chandler");
        PageEntry p3 = new PageEntry("phoebe");
        
        al.Insert(new SearchResult(p1 ,23));  
        al.Insert(new SearchResult(p2 ,27));  
        al.Insert(new SearchResult(p3 ,21));  
        
        /*ArrayList<SearchResult> tmp = sortThisList(al);
        for(int i = 0 ; i < tmp.size() ; i++) {
            System.out.println(tmp.get(i).getPageEntry().getName());
        }*/
    }    
}