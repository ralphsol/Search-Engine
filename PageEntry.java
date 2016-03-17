import java.util.*;
import java.lang.*;
import java.io.*;

public class PageEntry {
    
    PageIndex pagex = new PageIndex();
    String name;
    public String fileStr;
    
    public String removePunc(String word) {
        Myset<Character> punc = new Myset<Character>();
        punc.Insert('{');
        punc.Insert('}');
        punc.Insert('[');
        punc.Insert(']');
        punc.Insert('<');
        punc.Insert('>');
        punc.Insert('=');
        punc.Insert('(');
        punc.Insert(')');
        punc.Insert('.');
        punc.Insert(',');
        punc.Insert(';');
        //punc.Insert(''');
        punc.Insert('"');
        punc.Insert('?');
        punc.Insert('#');
        punc.Insert('!');
        punc.Insert('-');
        punc.Insert(':');
        
        int i = 0;
        while(i < word.length()) {
            //System.out.println(word);
            if(punc.IsMember(word.charAt(i))) {
                word = word.substring(0, i) + word.substring(i + 1);
                i = 0;
            }
            i += 1;
        }
        
        return word;
    }
    
    public PageEntry(String pageName) {
        try {
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
            fileStr = "";
            this.name = pageName;
            FileInputStream fstream = new FileInputStream(pageName);
            Scanner s = new Scanner(fstream);
            int i = 0;
            while(s.hasNext()) {
                String tempStr = s.next();
                tempStr = removePunc(tempStr);
                tempStr = tempStr.toLowerCase();
                //System.out.println("-" + tempStr + "-");
                boolean inConc = false;
                Myset<String>.Node nodeConc = connector.top;
                while(nodeConc != null){
                    if (nodeConc.element.equals(tempStr)){
                        inConc = true;
                    }
                    nodeConc = nodeConc.next;
                }
                if(!inConc && tempStr.length() > 0) {
                    Position tmp = new Position(this, i);
                    if(tempStr.equals("stacks")) {
                        tempStr = "stack";
                    }
                    else if(tempStr.equals("structures")) {
                        tempStr = "structure";
                    }
                    else if(tempStr.equals("applications")) {
                        tempStr = "application";
                    }
                    fileStr += tempStr + " ";
                    pagex.addPositionForWord(tempStr, tmp);
                }
                i += 1;
            }
        } catch(FileNotFoundException e) {
            //System.out.println("File not found");
        }
    }
    
    public boolean containsString(String str) {
        String[] tokens = fileStr.split(" ");
        for(int i = 0 ; i < tokens.length ; i++) {
            for(int j = i ; j < tokens.length ; j++) {
                String tempStr = "";
                for(int k = i ; k <= j ; k++) {
                    tempStr += tokens[k] + " ";
                }
                //System.out.println(tempStr);
                if(tempStr.equals(str + " ")) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public PageIndex getPageIndex() {
        return pagex;
    }
    
    public String getName() {
        return name;
    }
    
    float getRelevanceOfPhrase(String curStr) {
        if(containsString(curStr)) {
                String[] tokens = fileStr.split(" ");
                for(int i = 0 ; i < tokens.length ; i++) {
                    for(int j = i ; j < tokens.length ; j++) {
                        float res = (float) 0;
                        String tempStr = "";
                        for(int k = i ; k <= j ; k++) {
                            res = res + (1 / (float) ((k+1)*(k+1)));
                            tempStr += tokens[k] + " ";
                        }
                        if(tempStr.equals(curStr + " ")) {
                            return res;
                        }
                    }
                }
        }
        return (float) 0;
    }
    
    float getRelevanceOfPage(String str[], int doTheseWordsRepresentAPhrase) {
        //phrase
        if(doTheseWordsRepresentAPhrase == 0) {
            String curStr = "";
            for(int i = 0 ; i < str.length ; i++) {
                curStr += str[i] + " ";
            }
            curStr = curStr.substring(0, curStr.length() - 1);
            return getRelevanceOfPhrase(curStr);
            
        }
        //or
        else if(doTheseWordsRepresentAPhrase == 1) {
            float res = (float)0;
            for(int i = 0 ; i < str.length ; i++) {
                res += getRelevanceOfPhrase(str[i]);
            }
            return res;
        }
        //and
        else {
            float res = (float)0;
            for(int i = 0 ; i < str.length ; i++) {
                if(Float.compare(getRelevanceOfPhrase(str[i]), 0) == 0) {
                    return (float) 0;
                }
                res += getRelevanceOfPhrase(str[i]);
            }
            return res;
        }
        /*else {
            Vector<String> Vand = new Vector<String>();
            String curStr = "";
            for(int i = 0 ; i < str.length ; i++) {
                if(str[i].toLowerCase().equals("and")) {
                    Vand.add(curStr);
                    curStr = "";
                }
                else {
                    curStr = curStr + str[i] + " ";
                }
            }
            Vand.add(curStr);
            float res = 0;
            for(int i = 0 ; i < Vand.size() ; i++) {
                //System.out.println(Vand.get(i));
                Vector<String> Vor = new Vector<String>();
                String[] tokens = Vand.get(i).split(" ");
                String curStr2 = "";
                for(int j = 0 ; j < tokens.length ; j++) {
                    //System.out.println(tokens[j]);
                    if(tokens[j].toLowerCase().equals("or")) {
                        curStr2 = curStr2.substring(0, curStr2.length() - 1);
                        Vor.add(curStr2);
                        //System.out.println(curStr2);
                        curStr2 = "";
                    }
                    else {
                        curStr2 = curStr2 + tokens[j] + " ";
                    }
                }
                curStr2 = curStr2.substring(0, curStr2.length() - 1);
                Vor.add(curStr2);
                //System.out.println(curStr2);
                float value = (float) 0;
                for(int j = 0 ; j < Vor.size() ; j++) {
                    //System.out.println("'" + Vor.get(j) + "'" + " " + getRelevanceOfPhrase(Vor.get(j)));
                    value = value + getRelevanceOfPhrase(Vor.get(j));
                }
                //System.out.println(value);
                if(value == (float) 0) {
                    return (float) 0;
                }
                else {
                    res = res + value;
                }
            }
            return res;
        }*/
    }
    
    public String toString() {
        String str = "[";
        str += "Page name'" + getName().substring(9) + "'";
        str += getPageIndex().toString();
        str += "]";
        return str;
    }
    
    public static void main (String[] args)
	{
	    PageEntry mytest = new PageEntry("webpages/sample");
	    //System.out.println(mytest.toString());
	    
	    //System.out.println(mytest.fileStr);
	    String[] s1 = {"quick", "brown", "and", "fox", "or", "retro"};
	    System.out.println(mytest.getRelevanceOfPage(s1, 1));
	    
	    /*System.out.println(mytest.getRelevanceOfPage({"jumps"}, true));
	    System.out.println(mytest.getRelevanceOfPage({"quick", "brown"}, true));
	    System.out.println(mytest.getRelevanceOfPage({"quick", "brown", "fox", "jumps"}, true));
	    System.out.println(mytest.getRelevanceOfPage({"brown"}, true));
	    /*System.out.println(mytest.removePunc("j-a,m.e:s"));
	    System.out.println(mytest.removePunc("wo---rd"));
	    System.out.println(mytest.removePunc("-"));
	    System.out.println(mytest.removePunc("bond"));
	    System.out.println(mytest.removePunc(""));*/
	}
	
}