import java.util.*;
import java.lang.*;
import java.io.*;
import java.*;


// chaining by linked list
public class MyHashTable {
    
    // size of hashtable
    private int N;
    private LList[] list;
    
    // gives warning due to creation of generic array
    public MyHashTable() {
        N = 1023;
        list = new LList[N];
        for(int i = 0 ; i < N ; i++) {
            LList tmp = new LList();
            list[i] = tmp;
        }
    }
    
    private int getHashIndex(String str) {
        int sum = 0;
        for(int i = 0 ; i < str.length() ; i++) {
            sum = sum + (int) str.charAt(i);
        }
        return sum % N;
    }
    
    public void addPositionsForWord(WordEntry w) {
        int index = getHashIndex(w.getWord());
        if(list[index].isEmpty()) {
            list[index].insert(w);
            return ;
        }
        Node<WordEntry> scanList = list[index].head;
        if(scanList.getElement().getWord().equals(w.getWord())) {
            scanList.getElement().addPositions(w.v);
            return ;
        }
        while(scanList.getNext() != null) {
            scanList = scanList.getNext();
            if(scanList.getElement().getWord().equals(w.getWord())) {
                scanList.getElement().addPositions(w.v);
                return ;
            }    
        }
        list[index].insert(w);
    }
    
    public double getRelev(String str, PageEntry p) {
        int index = getHashIndex(str);
        double res = 0.0;
        if(list[index].isEmpty()) {
            return res;
        }
        Node<WordEntry> scanList = list[index].head;
        if(scanList.getElement().getWord().equals(str)) {
            
            Node<Position> temp_node = scanList.getElement().getAllPositionsForThisWord().head;
            while(temp_node != null) {
                if(temp_node.getElement().getPageEntry() == p) {
                    res += 1/(temp_node.getElement().getWordIndex() + 1.0);
                }    
                temp_node = temp_node.getNext();
            }
            
            return res;
        }
        while(scanList.getNext() != null) {
            scanList = scanList.getNext();
            if(scanList.getElement().getWord().equals(str)) {
                
                Node<Position> temp_node = scanList.getElement().getAllPositionsForThisWord().head;
                while(temp_node != null) {
                    if(temp_node.getElement().getPageEntry() == p) {
                        res += 1/(temp_node.getElement().getWordIndex() + 1.0);
                    }
                    temp_node = temp_node.getNext();
                }
                
                return res;
            }
        }
        return res;
    }
    
    public Myset<PageEntry> sortRel(String str, Myset<PageEntry> tempSet) {
        Myset<PageEntry> res = new Myset<PageEntry>();
        
        while(!tempSet.IsEmpty()) {
            double min = 10000.0;
            Myset<PageEntry>.Node temp_node = tempSet.top;
            PageEntry tracker = new PageEntry("");
            while(temp_node != null) {
                if(getRelev(str, temp_node.element) < min) {
                    min = getRelev(str, temp_node.element);
                    tracker = temp_node.element;
                }
                temp_node = temp_node.next;
            }
            
            res.Insert(tracker);
            tempSet.Delete(tracker);
        }
        
        return res;
    }
    
    public Myset<PageEntry> getPagesWithWord(String str) {
        
        Myset<PageEntry> tempSet = new Myset<PageEntry>();
        
        int index = getHashIndex(str);
        if(list[index].isEmpty()) {
            return tempSet;
        }
        Node<WordEntry> scanList = list[index].head;
        if(scanList.getElement().getWord().equals(str)) {
            
            Node<Position> temp_node = scanList.getElement().getAllPositionsForThisWord().head;
            tempSet.Insert(temp_node.getElement().getPageEntry());
            while(temp_node.getNext() != null) {
                temp_node = temp_node.getNext();
                tempSet.Insert(temp_node.getElement().getPageEntry());
            }
            
            tempSet = sortRel(str, tempSet);
            return tempSet;
        }
        while(scanList.getNext() != null) {
            scanList = scanList.getNext();
            if(scanList.getElement().getWord().equals(str)) {
            
                Node<Position> temp_node = scanList.getElement().getAllPositionsForThisWord().head;
                tempSet.Insert(temp_node.getElement().getPageEntry());
                while(temp_node.getNext() != null) {
                    temp_node = temp_node.getNext();
                    tempSet.Insert(temp_node.getElement().getPageEntry());
                }
                
                tempSet = sortRel(str, tempSet);
                return tempSet;
            }    
        }
        
        return tempSet;
    }
    
    
    /*public Myset<SearchResult> getPagesWithWord2(String str) {
        
        Myset<SearchResult> tempSet = new Myset<SearchResult>();
        
        int index = getHashIndex(str);
        if(list[index].isEmpty()) {
            return tempSet;
        }
        Node<WordEntry> scanList = list[index].head;
        if(scanList.getElement().getWord().equals(str)) {
            
            Node<Position> temp_node = scanList.getElement().getAllPositionsForThisWord().head;
            PageEntry tmpPE = temp_node.getElement().getPageEntry(); 
            tempSet.Insert(new SearchResult(tmpPE, (float)getRelev(str, tmpPE)));
            while(temp_node.getNext() != null) {
                temp_node = temp_node.getNext();
                tmpPE = temp_node.getElement().getPageEntry(); 
                tempSet.Insert(new SearchResult(tmpPE, (float)getRelev(str, tmpPE)));
            }
            
            return tempSet;
        }
        while(scanList.getNext() != null) {
            scanList = scanList.getNext();
            if(scanList.getElement().getWord().equals(str)) {
            
                Node<Position> temp_node = scanList.getElement().getAllPositionsForThisWord().head;
                PageEntry tmpPE = temp_node.getElement().getPageEntry(); 
                tempSet.Insert(new SearchResult(tmpPE, (float)getRelev(str, tmpPE)));
                while(temp_node.getNext() != null) {
                    temp_node = temp_node.getNext();
                    tmpPE = temp_node.getElement().getPageEntry(); 
                    tempSet.Insert(new SearchResult(tmpPE, (float)getRelev(str, tmpPE)));
                }
                
                return tempSet;
            }    
        }
        
        return tempSet;
    }*/
    
    
    public Set getPagesWithWord2(String str) {
        
        Set tempSet = new Set();
        
        int index = getHashIndex(str);
        if(list[index].isEmpty()) {
            return tempSet;
        }
        Node<WordEntry> scanList = list[index].head;
        if(scanList.getElement().getWord().equals(str)) {
            
            Node<Position> temp_node = scanList.getElement().getAllPositionsForThisWord().head;
            PageEntry tmpPE = temp_node.getElement().getPageEntry(); 
            tempSet.Insert(new SearchResult(tmpPE, (float)getRelev(str, tmpPE)));
            while(temp_node.getNext() != null) {
                temp_node = temp_node.getNext();
                tmpPE = temp_node.getElement().getPageEntry(); 
                tempSet.Insert(new SearchResult(tmpPE, (float)getRelev(str, tmpPE)));
            }
            
            return tempSet;
        }
        while(scanList.getNext() != null) {
            scanList = scanList.getNext();
            if(scanList.getElement().getWord().equals(str)) {
            
                Node<Position> temp_node = scanList.getElement().getAllPositionsForThisWord().head;
                PageEntry tmpPE = temp_node.getElement().getPageEntry(); 
                tempSet.Insert(new SearchResult(tmpPE, (float)getRelev(str, tmpPE)));
                while(temp_node.getNext() != null) {
                    temp_node = temp_node.getNext();
                    tmpPE = temp_node.getElement().getPageEntry(); 
                    tempSet.Insert(new SearchResult(tmpPE, (float)getRelev(str, tmpPE)));
                }
                
                return tempSet;
            }    
        }
        
        return tempSet;
    }
    
    public String wordIndexinPage(String str, PageEntry p) {
        int index = getHashIndex(str);
        String res = "";
        if(list[index].isEmpty()) {
            return res;
        }
        Node<WordEntry> scanList = list[index].head;
        if(scanList.getElement().getWord().equals(str)) {
            
            Node<Position> temp_node = scanList.getElement().getAllPositionsForThisWord().head;
            while(temp_node != null) {
                if(temp_node.getElement().getPageEntry() == p) {
                    res += temp_node.getElement().getWordIndex() + " ";
                }    
                temp_node = temp_node.getNext();
            }
            
            return res;
        }
        while(scanList.getNext() != null) {
            scanList = scanList.getNext();
            if(scanList.getElement().getWord().equals(str)) {
                
                Node<Position> temp_node = scanList.getElement().getAllPositionsForThisWord().head;
                while(temp_node != null) {
                    if(temp_node.getElement().getPageEntry() == p) {
                        res += temp_node.getElement().getWordIndex() + " ";
                    }
                    temp_node = temp_node.getNext();
                }
                
                return res;
            }
        }
        return res;
    }
    
    public String toString() {
        String str = "";
        for(int i = 0 ; i < N ; i++) {
            if(list[i].isEmpty()) {
                continue;
            }
            Node<WordEntry> scanList = list[i].head;
            /*if(scanList.getElement() == null) {
                str = "EMPTY]";
                break;
            }*/
            str += "i = " + i + " : ";
            str += scanList.getElement().toString();
            while(scanList.getNext() != null) {
                scanList = scanList.getNext();
                str += scanList.getElement().toString();
            }
            str += "\n";
        }
        return str;
    }
        
    public static void main (String[] args){
        MyHashTable test = new MyHashTable();
        
        WordEntry mytest = new WordEntry("voila");
        WordEntry mytest2 = new WordEntry("harshil");
	    
	    PageEntry t1 = new PageEntry("stack_cprogramming");
	    PageEntry t2 = new PageEntry("stackoverflow");
	    Position p1 = new Position(t1, 59);
	    Position p2 = new Position(t1, 23);
	    Position p3 = new Position(t2, 37);
	    
	    mytest.addPosition(p1);
	    mytest2.addPosition(p2);
	    mytest.addPosition(p3);
    
        test.addPositionsForWord(mytest);
        test.addPositionsForWord(mytest2);
        
        System.out.println(test.toString());
        
        /*Myset<PageEntry> tempSet = test.getPagesWithWord("voila");
        Myset<PageEntry>.Node temp = tempSet.top;
        while(temp != null) {
            System.out.println(temp.element.getName());
            temp = temp.next;
        }*/
        
        /*Myset<PageEntry> tempSet = test.getPagesWithWord("harshil");
        Myset<PageEntry>.Node temp = tempSet.top;
        while(temp != null) {
            System.out.println(temp.element.getName());
            temp = temp.next;
        }*/
        
        
    }
    
}