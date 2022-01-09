import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class Eighteen {
	public static Node pointer;
	public static int count = 0;
	public static long answer = 0;
	public static String expression = "";
	public static Node top;
	public static int index = 0;

	public static void main(String[] args) {
		
		long start = System.currentTimeMillis();
		String fp = "input";
		FileReader fr = null;
		try {
			fr = new FileReader(fp);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (fr != null) {
			
			//part 2
			Scanner s = new Scanner(fr);
			
			String [] snailNums =  new String[100];
			
			for (int i = 0; i < 100; i++) {
				snailNums[i] = s.nextLine().strip();
			}
			
			long magnitude = 0;
			
			for (int i = 0; i < 100; i++) {
				for (int j = 0; j < 100; j++) {
					if (j != i) {
						Node node = new Node(snailNums[i], null);
						top = node;
						
						Node newNode = new Node(snailNums[j], null);
						Node newTop = new Node();

						newTop.st = "[" + top.st + "," + snailNums[j] + "]";
						newTop.left = top;
						newTop.right = newNode;
						top.parent = newTop;
						newNode.parent = newTop;
						
						top = newTop;
						
						top = reduce(top);
						magnitude = top.magnitude(top) > magnitude ? top.magnitude(top) : magnitude;
						
					}
				}
			}
			
			System.out.println("Part 2: " + magnitude);
			
//part 1
//			start = System.currentTimeMillis();
//			Node node = new Node(s.nextLine().strip(), null);
//			top = node;
//			top = reduce(top);
//			
//			//top.print(1);
//
//		
//			while (s.hasNext()) {
//
//				
//				
//				String line = s.nextLine().strip();
//				
//				
//				Node newNode = new Node(line, null);
//				Node newTop = new Node();
//
//				newTop.st = "[" + top.st + "," + line + "]";
//				newTop.left = top;
//				newTop.right = newNode;
//				top.parent = newTop;
//				newNode.parent = newTop;
//				
//				top = newTop;
//				
//				top = reduce(top);
//				
//				
//
//			}
//			long magnitude = top.magnitude(top);
			
			//System.out.println("mag: " + magnitude);
			
		
			
			long end = System.currentTimeMillis();
			
			System.out.println(end - start + " ms");
			

		}
		

	}

	private static Node reduce(Node node) {
		
		
		while (true) {
			
			top.clearTraverse();
			top.traverse(top, top);
			
//			System.out.println("~~~~~~~~");
//			for (Node temp : top.traversal) {
//				
//				System.out.println(temp.st);
//			}
//			
			
			
			if (needsExploded(node, 0)) {
				System.out.println("****" + pointer.st);
				explode(pointer);
				
			}
			else if (needsSplit(node)) {
				System.out.println("£££££" + index);
				split(index);
				
			}
			else {break;}
			//node.print(1);
			

				 
		}
		
		
		
		return node;



	}

	private static void split(int i) {
		Node node = top.traversal.get(i);
		
		//System.out.println("split");
		
		node.left = new Node(Integer.toString(Integer.parseInt(node.st)/2));
		node.left.parent = node;
		node.right = new Node(Integer.toString((Integer.parseInt(node.st)/2) + (Integer.parseInt(node.st)%2)));
		node.right.parent = node;
		
		node.st = "[" + node.left.st + "," + node.right.st + "]";
		
	}

	private static boolean needsSplit(Node node) {
		for (int i = 0; i < top.traversal.size(); i++) {
			Node temp = top.traversal.get(i);
			if (Integer.parseInt(temp.st) >= 10) {
				index = i;
				System.out.println(index + "(((((");
				return true;
			}
		}
		return false;
	}

		
	

	private static void explode(Node node) {
		int index = 0;
		for (int i = 0; i < top.traversal.size(); i++) {
			if (node == top.traversal.get(i)) {
				index = i;
			}
		}
	
		if (index == 0) {
			// replace node with number
			node.parent.st = "0";
			
			int right = index + 2;
			
			Node rightNode = top.traversal.get(right);

//			System.out.println(node.st);
//			System.out.println("node:" + node);
//			System.out.println("parent: " + node.parent);
//			System.out.println(node.parent.right.st);
			
			rightNode.st = Integer.toString(Integer.parseInt(rightNode.st) + Integer.parseInt(node.parent.right.st));
			
			

		}
		else if (index == top.traversal.size() - 2){
			
			node.parent.st = "0";
			
			int left = index - 1;
			Node leftNode = top.traversal.get(left);
			leftNode.st = Integer.toString(Integer.parseInt(leftNode.st) + Integer.parseInt(node.st));
			//leftNode.parent = node.parent.parent;

			//System.out.println("left" + leftNode.st);
		}
		
		else {
			int right = index + 2;
			int left = index - 1;
			
			Node leftNode = top.traversal.get(left);
			Node rightNode = top.traversal.get(right);
			
			rightNode.st = Integer.toString(Integer.parseInt(rightNode.st) + Integer.parseInt(node.parent.right.st));
			leftNode.st = Integer.toString(Integer.parseInt(leftNode.st) + Integer.parseInt(node.st));
			
			node.parent.st = "0";
			
		}
		
		node.parent.left = null;
		node.parent.right = null;
		

		}

		

		

		
		
		
		
		
		
		
		
	
		
	

	private static boolean needsExploded(Node node, int depth) {
		
		// base case
		if (depth == 5) {

			pointer = node;
			System.out.println("****" + pointer.st);

			return true;
		}

		// reached leaf
		try {
			if (needsExploded(node.left, depth + 1)) {
				return true;
			}
		} catch (Exception e) {

		}

		try {
			if (needsExploded(node.right, depth + 1)) {
				return true;
			}
		} catch (Exception e) {
		}

		return false;
	}


}
