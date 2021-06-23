import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

import java.io.*;
import java.net.*;

// #6
class Map {
	List<String> array;
	Hashtable<String, Integer> table;

	public Map() {
		array = new ArrayList<>();
		table = new Hashtable<>();
	}

	void add(String key) {
		if (!table.contains(key)) {
			array.add(key);
			table.put(key, array.size() - 1);
		}
	}

	void delete(String key) {
		if (table.containsKey(key)) {
			int index = table.get(key);
			table.remove(key);

			array.set(index, array.get(array.size() - 1));
			array.remove(array.size() - 1);

			table.put(array.get(index), index);
		}
	}

	void print() {
		System.out.println(array.toString());
		System.out.println(table.toString());
	}
}

public class Exercise {
	// #1
	static boolean isPalindrome(String text) {
		text=text.replaceAll("\\s+","");
		for (int i = 0; i < text.length() / 2; i++) {
			if (text.charAt(i) == text.charAt(text.length() - i - 1))
				continue;
			return false;
		}
		return true;
	}

	// #2
	static int minSplit(int amount) {
		int minSplitNumber = 0;

		if (amount / 50 != 0) {
			minSplitNumber += amount / 50;
			amount = amount % 50;
			System.out.print("50*" + minSplitNumber + " | ");
		}
		if (amount / 20 != 0) {
			minSplitNumber += amount / 20;
			System.out.print("20*" + (amount / 20) + " | ");
			amount = amount % 20;

		}
		if (amount / 10 != 0) {
			minSplitNumber += amount / 10;
			System.out.print("10*" + (amount / 10) + " | ");
			amount = amount % 10;
		}
		if (amount / 5 != 0) {
			minSplitNumber += amount / 5;
			System.out.print("5*" + (amount / 5) + " | ");
			amount = amount % 5;
		}
		if (amount / 1 != 0) {
			minSplitNumber += amount / 1;
			System.out.println("1*" + (amount / 1) + "\n");
		}
		return minSplitNumber;
	}

	// #3
	static int notContains(int[] array) {
		List<Integer> set = new ArrayList<>();
		Arrays.sort(array);

		for (int i : array) {
			if (i >= 1 && !set.contains(i))
				set.add(i);
		}

		if (!set.contains(1))
			return 1;
		for (int i : set) {
			if (set.contains(i + 1))
				continue;
			else
				return i + 1;
		}
		return 0;
	}

	// #4
	static boolean isProperly(String sequence) {
		int n = 0;
		for (char c : sequence.toCharArray()) {

			if (c == '(')
				n++;
			if (c == ')')
				n--;
			if (n == -1)
				return false;
		}
		return n == 0;
	}

	// #5
	/*
	 * static int countVariants(int stairsCount) { if(stairsCount == 1) return 1;
	 * 
	 * if(stairsCount == 2) return 2; return
	 * countVariants(stairsCount-1)+countVariants(stairsCount-2); }
	 */
	// #5
	static int countVariants(int stairsCount) {
		int current = 2;
		int previous = 1;
		int temp = 0;

		for (int i = 2; i < stairsCount; i++) {
			temp = current;
			current = current + previous;
			previous = temp;
		}

		return current;
	}
	
	// #8
	private static double calculateRate(String from, String to, Hashtable<String,Double> map) {
		if(map.containsKey(from) && map.containsKey(to)) {
			return map.get(from)/map.get(to);
		} else {
			System.out.println("No info for these currencies!");
		}
		return 0.0;
	}

	static double exchangeRate(String from, String to)  {
		Hashtable<String,Double> map = new Hashtable<>();
		try {
			URL rssUrl = new URL("http://www.nbg.ge/rss.php");
			BufferedReader in = new BufferedReader(new InputStreamReader(rssUrl.openStream()));
			String sourceCode="";
			String line;
			String[] s=null;
			
			String country="";
			double rate=0.0;
			
			int counter=0;
			while((line = in.readLine()) != null) {
				if(line.contains("<td>")) {
					int firstPos = line.indexOf("<td>");
					String temp = line.substring(firstPos);
					temp = temp.replace("<td>", "");
					int lastPos = temp.indexOf("</td>");
					temp = temp.substring(0,lastPos);
					sourceCode += temp+"\n";										
				}
			}
			
			s=sourceCode.split("\n");
			
			for(int i=0; i<s.length-3; i++) {
				if(i%5==0) {
					country=s[i]; // name of the currency
					if (s[i+1].contains("1000")) // rate
						rate=Double.parseDouble(s[i+2])/1000;
					else if(s[i+1].contains("100"))
						rate=Double.parseDouble(s[i+2])/100;
					else if(s[i+1].contains("10"))
						rate=Double.parseDouble(s[i+2])/10;
					else if(s[i+1].contains("1"))
						rate=Double.parseDouble(s[i+2]);														
					map.put(country, rate);
				}				
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 		
		return calculateRate(from,to,map);
	}
	public static void main(String[] args) {

		// #1
		Scanner sc = new Scanner(System.in);

		System.out.print("#1 (isPalindrome)\nEnter Text: ");
		String text = sc.nextLine();

		System.out.print("\n\"" + text + "\" is ");
		if (!isPalindrome(text))
			System.out.print("not ");
		System.out.println("a palindrome.");

		// #2
		System.out.print("\n#2 (minSplit)\nEnter Amount: ");
		String s = sc.next();
		int amount = 0;
		try {
			amount = Integer.parseInt(s);
			System.out.println("The minimum amount of coins to split " + amount + " is: " + minSplit(amount));
		} catch (Exception e) {
			System.out.println("Unsupported Input!");
		}

		// #3
		System.out.println("\n#3 (notContains) with three different test cases ");
		System.out.println("TEST 1:");
		int[] array = { -10, 12, 20, 1, 5 };
		for (int i : array)
			System.out.print(i + " ");
		System.out.println("\nMin integer, greater than 0, not included in the array: " + notContains(array));

		System.out.println("\nTEST 2:");
		int[] arr = { 2, 3, 4, 6, 0, 7 };
		for (int i : arr)
			System.out.print(i + " ");

		System.out.println("\nMin integer, greater than 0, not included in the array: " + notContains(arr));

		System.out.println("\nTEST 3:");
		int[] a = { 1, 2, 3, 4, 5, 6, 7, 8 };
		for (int i : a)
			System.out.print(i + " ");
		System.out.println("\nMin integer, greater than 0, not included in the array: " + notContains(a));

		// #4
		System.out.print("\n#4 (isProperly)\nEnter Sequence without spaces: ");
		String sequence = "";
		sequence = sc.next();
		System.out.println(isProperly(sequence));
		// System.out.println(isProperly("())()"));
		// System.out.println(isProperly("))(("));

		// #5
		System.out.println("\n#5 (countVariants)");
		System.out.print("What is the number of stairs? ");
		String r = "";
		r =	sc.next();
		int stairs = 0;
		try {
			stairs = Integer.parseInt(r);
			System.out.println("Number of Possible Paths: " + countVariants(stairs));
		} catch (Exception e) {
			System.out.println("Unsupported Input!");
		}

		// #6
		System.out.println("\n#6 (constant time deletion)");
		Map map = new Map();
		map.add("a");
		map.add("b");
		map.add("c");
		map.add("d");
		map.add("e");
		map.add("f");
		map.add("g");

		System.out.print("MAP INITIALLY: ");
		map.print();

		map.delete("a");
		map.delete("e");
		System.out.print("MAP UPDATED: ");
		map.print();
		
		System.out.println("\n#8 (exchangeRate)");
		System.out.printf("Canadian Dollar (CAD) USA Dollar (USD): %.4f\n",exchangeRate("CAD","USD"));
		System.out.printf("United Arab Emirate (AED) to Armenian Dram (AMD): %.4f\n",exchangeRate("AED", "AMD"));
		
		sc.close();
	}
}
