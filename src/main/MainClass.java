package main;

import blockchain.BlockChain;

import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.Scanner;

public class MainClass {
	public static void main(String[] args) throws NoSuchAlgorithmException {
		
		final int ARGUMENTS = 2;
		Scanner scan = new Scanner(System.in);
		BlockChain<Integer> blockChain = null;
		Comparator<Integer> cmp = new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				return o1 - o2;
			}

		};

		FromString<Integer> fs = new FromString<Integer>() {

			@Override
			public Integer convert(String s) {
				boolean flag = true;
				int acu = 0;
				for (int i = 0; i < s.length() && flag; i++) {
					if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
						acu *= 10;
						acu += s.charAt(i) - '0';
					} else {
						flag = false;
					}
				}
				if (!flag)
					return null;
				return acu;
			}

		};

		if (args.length == ARGUMENTS) {
			Integer value = fs.convert(args[1]);

			if (args[0].toLowerCase().equals("zeros") && value != null) {
				blockChain = new BlockChain<Integer>(value, cmp);

				while (scan.hasNext()) {
					String command = scan.nextLine();

					if (command.toLowerCase().equals("exit"))
						break;

					if (blockChain != null) {
						if (command.length() > 3 && command.substring(0, 4).toLowerCase().equals("add ")) {
							Integer elem = fs.convert(command.substring(4));
							blockChain.add("add", elem);
						} else if (command.length() > 6 && command.substring(0, 7).toLowerCase().equals("remove ")) {
							Integer elem = fs.convert(command.substring(7));
							blockChain.remove("remove", elem);
						} else if (command.length() > 6 && command.substring(0, 7).toLowerCase().equals("lookup ")) {
							Integer elem = fs.convert(command.substring(7));
							blockChain.lookUp("lookUp", elem);
						} else if (command.toLowerCase().equals("validate")) {
							blockChain.validate();
						} else if (command.length() > 6 && command.substring(0, 7).toLowerCase().equals("modify ")) {
							blockChain.modify(command);
						} else {
							System.out.println("Invalid command");
						}
					} else {
						System.out.println("Invalid command");
					}
				}
				scan.close();
			}
		} else {

			System.out.println("Invalid command: java​ ​-jar​ ​tpe.jar​ ​zeros​ ​number");
		}
	}
}
