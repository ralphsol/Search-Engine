import java.util.*;
import java.lang.*;
import java.io.*;

public class SearchEngine{

    InvertedPageIndex engine;

	public SearchEngine() {
		this.engine = new InvertedPageIndex();
	}

	public void performAction(String actionMessage) {
		try {
		    String x, y;
		    String val = "";
            FileInputStream fstream = new FileInputStream ("actions.txt");
            Scanner s = new Scanner ( fstream );
            while ( s. hasNextLine ()){
                val = s.nextLine();
                String[] tokens = val.split(" ");
                if(tokens[0].equals("addPage")) {
                    x = tokens[1]; 
                    x = "webpages/" + x;
                    PageEntry t1 = new PageEntry(x);
                    engine.addPage(t1);
                }
                else if(tokens[0].equals("queryFindPagesWhichContainWord")) {
                    x = tokens[1];
                    String res = "queryFindPagesWhichContainWord " + x + " : ";
                    x = x.toLowerCase();
                    Myset<PageEntry> tempSet = engine.getPagesWhichContainWord(x);
                    Myset<PageEntry>.Node temp = tempSet.top;
                    if(temp == null) {
                        res += "No webpage contains word " + x;
                    }
                    while(temp != null) {
                        res += (temp.element.getName().substring(9)) + "; ";
                        temp = temp.next;
                    }
                    System.out.println(res);
                    System.out.println();
                }
                else if(tokens[0].equals("queryFindPositionsOfWordInAPage")) {
                    x = tokens[1];
                    y = tokens[2];
                    y = "webpages/" + y;
                    x = x.toLowerCase();
                    Myset<PageEntry> tempSet = engine.getPagesWhichContainWord(x);
                    PageEntry yPage = new PageEntry(y);
                    boolean inLoop = false;
                    Myset<PageEntry>.Node temp_node = tempSet.top;
                    while(temp_node != null) {
                        if(temp_node.element.getName().equals(y)) {
                            yPage = temp_node.element;
                            inLoop = true;
                        }
                        temp_node = temp_node.next;
                    }
                    /*if(tempSet.IsMember(yPage)) {
                        System.out.println("queryFindPositionsOfWordInAPage " + x + " " + y + " : " + engine.table.wordIndexinPage(x, yPage));
                    }*/
                    if(inLoop) {
                        System.out.println("queryFindPositionsOfWordInAPage " + x + " " + y.substring(9) + " : " + engine.table.wordIndexinPage(x, yPage));
                    }
                    else {
                        //System.out.println(yPage.toString());
                        System.out.println("Webpage " + y.substring(9) + " does not contain word " + x);
                    }
                    System.out.println();
                }
                else if(tokens[0].equals("queryFindPagesWhichContainAllWords")) {
                    Set tempSet = new Set();
                    String[] tmpStr = new String[tokens.length - 1];
                    for(int i = 0 ; i < tokens.length - 1 ; i++) {
                        tmpStr[i] = tokens[i + 1];
                    }
                    Map<Float, String> tempMap = new HashMap<Float, String>();
                    String str1 = "";
                    for(int i = 1 ; i < tokens.length ; i++) {
                        str1 += tokens[i] + " ";
                        tempSet = tempSet.Union(engine.table.getPagesWithWord2(tokens[i]));
                    }
                    for(int i = 1 ; i < tokens.length ; i++) {
                        str1 += tokens[i] + " ";
                        tempSet = tempSet.Intersection(engine.table.getPagesWithWord2(tokens[i]));
                    }
                    
                    Set.Node scanNode = tempSet.top;
                    MySort test = new MySort();
                    ArrayList<SearchResult> curr = test.sortThisList(tempSet);
                    String res = "";
                    String res2 = "";
                    if(scanNode != null) {
                        for(int k = 0 ; k < curr.size() ; k++) {
                            tempMap.put(curr.get(k).getPageEntry().getRelevanceOfPage(tmpStr, 2), curr.get(k).getPageEntry().getName().substring(9));
                        }
                        Map<Float, String> newMap = new TreeMap<Float, String>(tempMap);
                        for(Map.Entry<Float, String> searEnt : newMap.entrySet()) {
                            if(searEnt.getKey() > (float)0) {
                                res2 = searEnt.getValue() + "; " + res2;
                                //res2 = searEnt.getValue() + " " + searEnt.getKey() + "; " + res2;
                            }
                        }
                        System.out.println("queryFindPagesWhichContainAllWords " + str1 + ": " + res2);
                    }
                    else {
                        System.out.println("queryFindPagesWhichContainAllWords " + str1 + ": No such page exsists");
                    }
                    System.out.println();
                }
                /*else if(tokens[0].equals("queryFindPagesWhichContainAllWords")) {
                    Myset<PageEntry> tempSet = new Myset<PageEntry>();
                    for(int i = 1 ; i < tokens.length ; i++) {
                        tempSet = tempSet.Union(engine.getPagesWhichContainWord(tokens[i]));
                    }
                    
                    String str1 = "";
                    for(int i = 1 ; i < tokens.length ; i++) {
                        str1 += tokens[i] + " ";
                        tempSet = tempSet.Intersection(engine.getPagesWhichContainWord(tokens[i]));
                    }
                    
                    Myset<PageEntry>.Node scanNode = tempSet.top;
                    String res = "";
                    if(scanNode != null) {
                        while(scanNode.next != null) {
                            res = res + scanNode.element.getName().substring(9) + "; ";
                            scanNode = scanNode.next;
                        }
                        System.out.println("queryFindPagesWhichContainAllWords " + str1 + ": " + res.substring(0, res.length() - 1));
                    }
                    else {
                        System.out.println("queryFindPagesWhichContainAllWords " + str1 + ": No such page exsists");
                    }
                    System.out.println();
                    
                }*/
                else if(tokens[0].equals("queryFindPagesWhichContainAnyOfTheseWords")) {
                    Set tempSet = new Set();
                    Map<Float, String> tempMap = new HashMap<Float, String>();
                    String str1 = "";
                    for(int i = 1 ; i < tokens.length ; i++) {
                        str1 += tokens[i] + " ";
                        tempSet = tempSet.Union(engine.table.getPagesWithWord2(tokens[i]));
                    }
                    
                    Set.Node scanNode = tempSet.top;
                    MySort test = new MySort();
                    ArrayList<SearchResult> curr = test.sortThisList(tempSet);
                    String res = "";
                    String res2 = "";
                    if(scanNode != null) {
                        for(int k = 0 ; k < curr.size() ; k++) {
                            tempMap.put(curr.get(k).getPageEntry().getRelevanceOfPage(tokens, 1), curr.get(k).getPageEntry().getName().substring(9));
                        }
                        Map<Float, String> newMap = new TreeMap<Float, String>(tempMap);
                        for(Map.Entry<Float, String> searEnt : newMap.entrySet()) {
                            if(searEnt.getKey() > (float) 0) {
                                res2 = searEnt.getValue() + "; " + res2;
                                //res2 = searEnt.getValue() + " " + searEnt.getKey() + "; " + res2;
                            }
                        }
                        System.out.println("queryFindPagesWhichContainAnyOfTheseWords " + str1 + ": " + res2);
                    }
                    else {
                        System.out.println("queryFindPagesWhichContainAnyOfTheseWords " + str1 + ": No such page exsists");
                    }
                    System.out.println();
                }
                else if(tokens[0].equals("queryFindPagesWhichContainPhrase")) {
                    Set tempSet = new Set();
                    String[] tmpStr = new String[tokens.length - 1];
                    for(int i = 0 ; i < tokens.length - 1 ; i++) {
                        tmpStr[i] = tokens[i + 1];
                    }
                    Map<Float, String> tempMap = new HashMap<Float, String>();
                    String str1 = "";
                    for(int i = 1 ; i < tokens.length ; i++) {
                        str1 += tokens[i] + " ";
                        tempSet = tempSet.Union(engine.table.getPagesWithWord2(tokens[i]));
                    }
                    
                    Set.Node scanNode = tempSet.top;
                    MySort test = new MySort();
                    ArrayList<SearchResult> curr = test.sortThisList(tempSet);
                    String res = "";
                    String res2 = "";
                    if(scanNode != null) {
                        for(int k = 0 ; k < curr.size() ; k++) {
                            tempMap.put(curr.get(k).getPageEntry().getRelevanceOfPage(tmpStr, 0), curr.get(k).getPageEntry().getName().substring(9));
                        }
                        Map<Float, String> newMap = new TreeMap<Float, String>(tempMap);
                        for(Map.Entry<Float, String> searEnt : newMap.entrySet()) {
                            if(searEnt.getKey() > (float) 0) {
                                res2 = searEnt.getValue() + "; " + res2;
                                //res2 = searEnt.getValue() + " " + searEnt.getKey() + "; " + res2;
                            }
                        }
                        System.out.println("queryFindPagesWhichContainPhrase " + str1 + ": " + res2);
                    }
                    else {
                        System.out.println("queryFindPagesWhichContainPhrase " + str1 + ": No such page exsists");
                    }
                    System.out.println();
                }
                /*else if(tokens[0].equals("queryFindPagesWhichContainPhrase")) {
                    String[] currStr = new String[tokens.length - 1];
                    String str1 = "";
                    for(int i = 0 ; i < tokens.length - 1 ; i++) {
                        str1 += tokens[i + 1] + " ";
                        currStr[i] = tokens[i + 1];
                    }
                    Myset<PageEntry> tempSet = engine.getPagesWhichContainPhrase(currStr);
                    
                    Myset<PageEntry>.Node scanNode = tempSet.top;
                    String res = "";
                    if(scanNode != null) { 
                        while(scanNode.next != null) {
                            res = res + scanNode.element.getName().substring(9) + "; ";
                            scanNode = scanNode.next;
                        }
                        System.out.println("queryFindPagesWhichContainPhrase " + str1 + ": " + res.substring(0, res.length() - 1));
                    }
                    else {
                        System.out.println("queryFindPagesWhichContainPhrase " + str1 + ": No such page exsists");
                    }
                    System.out.println();
                }*/
            }
            
        } catch ( FileNotFoundException e) {
            System . out . println (" File not found ");
        }
	}
	
}
