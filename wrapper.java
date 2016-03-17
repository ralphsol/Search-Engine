import java.util.*;
import java.lang.*;
import java.io.*;

public class wrapper {
    
    public static void main (String[] args)
	{
        InvertedPageIndex mytest = new InvertedPageIndex();
        PageEntry t1 = new PageEntry("stackoverflow");
        PageEntry t2 = new PageEntry("stack_cprogramming");
        mytest.addPage(t1);
        System.out.println(mytest.toString());
	}
}