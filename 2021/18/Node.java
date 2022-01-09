import java.util.ArrayList;

public class Node {
	public static int COUNT = 100;
	public Node parent;
	public Node left;
	public Node right;
	public ArrayList<Node> traversal = new ArrayList<Node>();
	public String st;

	
	
	
	public Node(String num) {
		this.st = num;
	}
	
	public Node(String number, Node parent) {
		this.parent = parent;
		this.st = number;
		

		
		
		String[] stack = new String[2000];
		String letter = "";
		int top = 0;
		int endOfFirst = 0;
		
		//System.out.println(number);
		
		if (number.startsWith("[")) {
			number = number.substring(1, number.length() - 1);
		}

		if (number.startsWith("[")) {

			for (int i = 0; i < number.length(); i++) {
				letter = number.substring(i, i + 1);
				if (letter.equals("[")) {
					stack[top] = letter;
					top++;

				}

				if (letter.equals("]")) {
					stack[top - 1] = "";
					top--;
				}
				if (top == 0 && i != 0) {
					endOfFirst = i + 1;
					break;
				}
			}
		}
		
		else {endOfFirst = number.indexOf(",");}
		
		
		String left = number.substring(0, endOfFirst);
		String right = number.substring(endOfFirst+1);
		
		if (left.length() < 3) {
			this.left = new Node(left);
			this.left.parent = this;
		}
		else {
			this.left = new Node(left, this);
		}
		
		if (right.length() < 3) {
			this.right = new Node(right);
			this.right.parent = this;
		}
		else {
			this.right = new Node(right, this);
		}
		
		
	
		

		
		

	}
	
	public Node() {
		// TODO Auto-generated constructor stub
	}

	public void print (int space)
	{

	 
	    // Increase distance between levels
	    space += COUNT;
	 
	    // Process right child first
	  
	    try {
	    this.right.print(space);
	    }
	    catch (Exception e) {
	    	
	    }
	    

	 
	    // Print current node after space
	    // count
	    System.out.print("\n");
	    for (int i = COUNT; i < space; i++)
	        System.out.print(" ");
	    System.out.print(this.st + " r:" + this.right + " n:" + this + " p:" + this.parent);
	 
	    // Process left child
	   
	    try {
		    this.left.print(space);
		    }
		    catch (Exception e) {
		    	
		    }
	
	}
	
	public void parse(String number, Node parent) {
		
	}

	public void add(String strip) {
		// method to create node for newly added number
		// then add this and new number node to a common parent
		
	}
	
	
	public void clearTraverse() {
		this.traversal.clear();
	}
	
	public void traverse(Node node, Node thisNode) {
		try {
		traverse(node.left, thisNode);
		} catch (Exception e) {}
		
		if (node.left == null) {
			thisNode.traversal.add(node);
		}
		
		try {
		traverse(node.right, thisNode);
		} catch (Exception e) {}
	}

	public long magnitude(Node node) {
		
		
		
		if ((node.left.left == null && node.left.right == null) && (node.right.left == null && node.right.right == null)) {
			return (long) (3*Integer.parseInt(node.left.st) + 2*Integer.parseInt(node.right.st));
		}
		
		else if((node.left.left == null && node.left.right == null) && !(node.right.left == null && node.right.right == null)) {
			return (long) (3*Integer.parseInt(node.left.st) + 2*magnitude(node.right));
		}
		
		else if (!(node.left.left == null && node.left.right == null) && (node.right.left == null && node.right.right == null)) {
			return (long) (3*magnitude(node.left) + 2*Integer.parseInt(node.right.st));
		}
		else {
		
		return (long) (3*magnitude(node.left) + 2*magnitude(node.right));
		}
		
		

	}

	
}
