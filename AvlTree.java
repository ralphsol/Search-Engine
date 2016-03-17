import java.util.*;

public class AvlTree {
    public AvlNode root; 

    public void insert(Position k) {
        AvlNode n = new AvlNode(k);
        insertAVL(this.root,n);
    }
 
    public void insertAVL(AvlNode p, AvlNode q) {
    if(p==null) {
        this.root=q;
    } else {
        if(q.key.getWordIndex()<p.key.getWordIndex()) {
            if(p.left==null) {
                p.left = q;
                q.parent = p;
                recursiveBalance(p);
            } else {
                insertAVL(p.left,q);
            }
        
        } else if(q.key.getWordIndex()>p.key.getWordIndex()) {
            if(p.right==null) {
                p.right = q;
                q.parent = p;
                recursiveBalance(p);
            } else {
                insertAVL(p.right,q);
            }
        } else {
    
        }
    }
}
 
    public void recursiveBalance(AvlNode cur) {
        
        setBalance(cur);
        int balance = cur.balance;
        if(balance==-2) {
            if(height(cur.left.left)>=height(cur.left.right)) {
                cur = rotateRight(cur);
            } else {
                cur = doubleRotateLeftRight(cur);
            }
        } else if(balance==2) {
            if(height(cur.right.right)>=height(cur.right.left)) {
                cur = rotateLeft(cur);
            } else {
                cur = doubleRotateRightLeft(cur);
            }
        }
        if(cur.parent!=null) {
            recursiveBalance(cur.parent);
        } else {
            this.root = cur;
        }
    }

    public void remove(Position k) {
        removeAVL(this.root,k);
    }
 
    public void removeAVL(AvlNode p,Position q) {
        if(p==null) {
            return;
        } else {
            if(p.key.getWordIndex()>q.getWordIndex())  {
                removeAVL(p.left,q);
            } else if(p.key.getWordIndex()<q.getWordIndex()) {
                removeAVL(p.right,q);
            } else if(p.key.getWordIndex()==q.getWordIndex()) {
                removeFoundNode(p);
            }
        }
    }
 
    public void removeFoundNode(AvlNode q) {
        AvlNode r;
        if(q.left==null || q.right==null) {
            if(q.parent==null) {
                this.root=null;
                q=null;
                return;
            }
            r = q;
        } else {
            r = successor(q);
            q.key = r.key;
        }
        AvlNode p;
        if(r.left!=null) {
            p = r.left;
        } else {
            p = r.right;
        }
        
        if(p!=null) {
            p.parent = r.parent;
        }
        
        if(r.parent==null) {
            this.root = p;
        } else {
            if(r==r.parent.left) {
                r.parent.left=p;
            } else {
                r.parent.right = p;
            }
            recursiveBalance(r.parent);
        }
        r = null;
    }
 

    public AvlNode rotateLeft(AvlNode n) {
        
        AvlNode v = n.right;
        v.parent = n.parent;
        
        n.right = v.left;
        
        if(n.right!=null) {
           n.right.parent=n;
        }
        
        v.left = n;
        n.parent = v;
        
        if(v.parent!=null) {
            if(v.parent.right==n) {
                v.parent.right = v;
            } else if(v.parent.left==n) {
                v.parent.left = v;
            }
        }
        
        setBalance(n);
        setBalance(v);
        
        return v;
    }
 
    public AvlNode rotateRight(AvlNode n) {
        
        AvlNode v = n.left;
        v.parent = n.parent;
        
        n.left = v.right;
        
        if(n.left!=null) {
            n.left.parent=n;
        }
        
        v.right = n;
        n.parent = v;
        
        
        if(v.parent!=null) {
            if(v.parent.right==n) {
                v.parent.right = v;
            } else if(v.parent.left==n) {
                v.parent.left = v;
            }
        }
        
        setBalance(n);
        setBalance(v);
        
        return v;
    }
 
    public AvlNode doubleRotateLeftRight(AvlNode u) {
        u.left = rotateLeft(u.left);
        return rotateRight(u);
    }
 
    public AvlNode doubleRotateRightLeft(AvlNode u) {
        u.right = rotateRight(u.right);
        return rotateLeft(u);
    }
 
    public AvlNode successor(AvlNode q) {
        if(q.right!=null) {
            AvlNode r = q.right;
            while(r.left!=null) {
                r = r.left;
            }
            return r;
        } else {
            AvlNode p = q.parent;
            while(p!=null && q==p.right) {
                q = p;
                p = q.parent;
            }
            return p;
        }
    }
 
    private int height(AvlNode cur) {
        if(cur==null) {
           return -1;
        }
        if(cur.left==null && cur.right==null) {
           return 0;
        } else if(cur.left==null) {
           return 1+height(cur.right);
        } else if(cur.right==null) {
           return 1+height(cur.left);
        } else {
           return 1+maximum(height(cur.left),height(cur.right));
        }
    }
 
    private int maximum(int a, int b) {
        if(a>=b) {
            return a;
        } else {
            return b;
        }
    }
 
    private void setBalance(AvlNode cur) {
        cur.balance = height(cur.right)-height(cur.left);
    }
 
    final protected ArrayList<AvlNode> inorder() {
        ArrayList<AvlNode> ret = new ArrayList<AvlNode>();
        inorder(root, ret);
        return ret;
    }
 
    final protected void inorder(AvlNode n, ArrayList<AvlNode> io) {
        if (n == null) {
            return;
        }
        inorder(n.left, io);
        io.add(n);
        inorder(n.right, io);
    }
 
    public static void main (String[] args)
	{
	    AvlTree test = new AvlTree();
	    PageEntry p1 = new PageEntry("joey");
	    PageEntry p2 = new PageEntry("chandler");
	    PageEntry p3 = new PageEntry("phoebe");
	    test.insert(new Position(p1, 56));
	    test.insert(new Position(p2, 43));
	    test.insert(new Position(p3, 32));
	    test.insert(new Position(p1, 29));
	    test.insert(new Position(p2, 17));
	    test.insert(new Position(p3, 9));
	    ArrayList tmp = test.inorder();
	    System.out.println("Inorder : " + tmp);
	}	    
}

//References : http://blog.blackbam.at/2012/05/04/avl-tree-implementation-in-java/