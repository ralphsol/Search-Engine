import java.util.*;
import java.lang.*;
import java.io.*;

public class InvertedPageIndex {
    
    public MyHashTable table = new MyHashTable();
    Myset<PageEntry> allPage = new Myset<PageEntry>();
    
    public void addPage(PageEntry p) {
        allPage.Insert(p);
        Node<WordEntry> temp = p.getPageIndex().getWordEntries().head;
        if(temp == null) {
            return ;
        }
        while(temp.getNext() != null) {
            table.addPositionsForWord(temp.getElement());
            temp = temp.getNext();
        }
        table.addPositionsForWord(temp.getElement());
    }
    
    public Myset<PageEntry> getPagesWhichContainWord(String str) {
        return table.getPagesWithWord(str);
    }
    
    public Set getPagesWhichContainWord2(String str) {
        return table.getPagesWithWord2(str);
    }
    
    public String remCon(String str[]) {
        Myset<String> connector = new Myset<String>();
        connector.Insert("a");
        connector.Insert("an");
        connector.Insert("the");
        connector.Insert("they");
        connector.Insert("these");
        connector.Insert("this");
        connector.Insert("for");
        connector.Insert("is");
        connector.Insert("are");
        connector.Insert("was");
        connector.Insert("of");
        connector.Insert("or");
        connector.Insert("and");
        connector.Insert("does");
        connector.Insert("will");
        connector.Insert("whose");
        String res = "";
        for(int i = 0 ; i < str.length ; i++) {
            if(!connector.IsMember(str[i])) {
                res += str[i] + " ";
            } 
        }
        return res;
    }
    
    public Myset<PageEntry> getPagesWhichContainPhrase(String str[]) {
        Myset<PageEntry> res = new Myset<PageEntry>();
        String tempStr = remCon(str);
        String[] tokens = tempStr.split(" ");
        for(int i = 0 ; i < str.length ; i++) {
            for(int j = i ; j < str.length ; j++) {
                String curStr = "";
                for(int k = i ; k <= j ; k++) {
                    curStr += tokens[k] + " ";
                }
                curStr = curStr.substring(0, curStr.length() - 1);
                
                //System.out.println("'" + curStr + "'");
                Myset<PageEntry>.Node tempNode = allPage.top;
                while(tempNode != null) {
                    if(tempNode.element.containsString(curStr)) {
                        //System.out.println(tempNode.element.getName());
                        res.Insert(tempNode.element);
                    }
                    tempNode = tempNode.next;
                }
                
            }
        }
        return res;
    }
    
    public String toString() {
        String str = "";
        str += table.toString();
        str += "";
        return str;
    }
    
    public static void main (String[] args){
        String str[] = {"whose", "will", "to", "succeed"};
        InvertedPageIndex test = new InvertedPageIndex();
	    PageEntry t1 = new PageEntry("webpages/stack_datastructure_wiki");
	    PageEntry t2 = new PageEntry("webpages/stackoverflow");
	    PageEntry t3 = new PageEntry("webpages/stack_cprogramming");
	    
	    test.addPage(t1);
	    test.addPage(t2);
	    test.addPage(t3);
	    
	    String[] myStr = {"bitches", "stack", "top"};
        Myset<PageEntry>.Node output = test.getPagesWhichContainPhrase(myStr).top;
        while(output != null) {
            System.out.println(output.element.getName().substring(9));
            output = output.next;
        }
        
        /*Myset<PageEntry> tempSet = test.getPagesWhichContainWord("stack");
        Myset<PageEntry>.Node temp = tempSet.top;
        while(temp != null) {
            System.out.println(temp.element.getName().substring(9));
            temp = temp.next;
        }*/
        
        //System.out.println(test.toString());
        //System.out.println(test.table.getRelev("to", t1));
        //System.out.println(test.table.wordIndexinPage("to", t1));

        /*Myset<PageEntry> tempSet = new Myset<PageEntry>();
        tempSet.Insert(t2);
        tempSet.Insert(t3);
        tempSet.Insert(t1);
        Myset<PageEntry>.Node tempNode = test.table.sortRel("function", tempSet).top;
        while(tempNode != null) {
            System.out.println(tempNode.element.getName());
            tempNode = tempNode.next;
        }
        
        System.out.println(test.table.getRelev("function", t3));
        System.out.println(test.table.getRelev("function", t2));
        System.out.println(test.table.getRelev("function", t1));*/
    }

}