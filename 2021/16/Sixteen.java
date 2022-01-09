import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class Sixteen {
	public static int count = 0;
	public static long answer = 0;
	public static String expression = "";

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		long end = 0;

		String fp = "input";
		FileReader fr = null;
		try {
			fr = new FileReader(fp);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			if (fr != null) {
				Scanner s = new Scanner(fr);
				String transmission = s.nextLine().strip();
				System.out.println("Hexadecimal transmission: " + transmission);
				String bits = "";
				for (int i = 0; i < transmission.length(); i++) {
					int dec = HexFormat.fromHexDigits(transmission.substring(i, i + 1));
					String bin = decToBin(dec);
					bits += bin;
				}
				end = System.currentTimeMillis();
				System.out.println("That took: " + (end - start) + " ms");
				System.out.println("Transmission in binary: " + bits);

				int position = 0;
				int offset = 0;
				int packetLength = 0;
				String packetVersion = "";
				String packetType = "";

				while (position + 3 < bits.length()) {

					packetVersion = bits.substring(position + offset, position + offset + 3);
					// System.out.println(binToDec(packetVersion));
					// System.out.println(position + offset);
					offset += 3;
					count += binToDec(packetVersion);

					packetType = bits.substring(position + offset, position + offset + 3);
					offset += 3;

					// System.out.println(packetVersion + " " + packetType);

					if (packetType.equals("100")) {
						// literal
						String num = "";

						while (true) {
							String temp = bits.substring(position + offset, position + offset + 5);
							num += temp.substring(1, 5);
							offset += 5;
							if (temp.substring(0, 1).equals("0")) {
								break;
							}

						}
						expression += binToDec(num) + ", ";

					} else {
						// operator
						String lengthID = bits.substring(position + offset, position + offset + 1);
						offset += 1;
						long type = binToDec(packetType);
						if (type == 0) {
							expression += "sum(";
						}
						if (type == 1) {
							expression += "product(";
						}
						if (type == 2) {
							expression += "min(";
						}
						if (type == 3) {
							expression += "max(";
						}
						if (type == 5) {
							expression += "greater than(";
						}
						if (type == 6) {
							expression += "less than(";
						}
						if (type == 7) {
							expression += "equal to(";
						}

						if (lengthID.equals("1")) {
							// next 11 bits are a number that represents the number of sub-packets
							// immediately contained by this packet
							// System.out.println("operator 1");
							String numberSubPackets = bits.substring(position + offset, position + offset + 11);
							offset += 11;
							long numberSubPack = binToDec(numberSubPackets);
							long[] temp = parseSubByNumber(bits, position, offset, numberSubPack, type);
							offset += (int) temp[0];
							answer = temp[1];

							expression += ")";

						} else {
							// next 15 bits are a number that represents the total length in bits of the
							// sub-packets contained by this packet
							// System.out.println("operator 0");
							String lengthSubPackets = bits.substring(position + offset, position + offset + 15);
							offset += 15;
							long lengthSubPack = binToDec(lengthSubPackets);
							long[] temp = parseSubByLength(bits, position, offset, lengthSubPack, type);
							offset += (int) temp[0];
							answer = temp[1];

							expression += ")";
						}

					}

					position += offset;
					offset = 0;

				}

			}
			System.out.println(count);
			System.out.println(expression);
			System.out.println(answer);
		} catch (Exception e) {
			System.out.println("decoded expression: " + expression);
			System.out.println("part 1: " + count);
			System.out.println("part 2: " + answer);
			long end2 = System.currentTimeMillis();
			System.out.println("That took: " + (end2 - end) + " ms");

		}

	}

	private static long[] parseSubByLength(String bits, int position, int offset, long length, long operator) {
		long[] returnVals = new long[2];
		// TODO Auto-generated method stub
		int initialOffset = offset;
		long localAnswer = 0;
		boolean set = false;
		long temp1 = 0;
		boolean tempA = false;
		long temp2 = 0;
		boolean tempB = false;
		long currentNum = 0;

		while (offset - initialOffset < length) {
			String packetVersion = "";
			try {
				packetVersion = bits.substring(position + offset, position + offset + 3);
			} catch (Exception e) {

			}
			offset += 3;
			// System.out.println(binToDec(packetVersion));
			// System.out.println(position + offset);
			count += binToDec(packetVersion);
			String packetType = "";

			try {
				packetType = bits.substring(position + offset, position + offset + 3);
			} catch (Exception e) {
				break;
			}
			offset += 3;
			long type = binToDec(packetType);

			if (packetType.equals("100")) {
				// literal
				String num = "";
				while (true) {
					String temp = bits.substring(position + offset, position + offset + 5);
					num += temp.substring(1, 5);
					offset += 5;
					if (temp.substring(0, 1).equals("0")) {
						break;
					}

				}

				currentNum = binToDec(num);
				expression += binToDec(num) + ", ";

			} else {
				// operator
				String lengthID = bits.substring(position + offset, position + offset + 1);
				offset += 1;

				if (type == 0) {
					expression += "sum(";
				}
				if (type == 1) {
					expression += "product(";
				}
				if (type == 2) {
					expression += "min(";
				}
				if (type == 3) {
					expression += "max(";
				}
				if (type == 5) {
					expression += "greater than(";
				}
				if (type == 6) {
					expression += "less than(";
				}
				if (type == 7) {
					expression += "equal to(";
				}

				if (lengthID.equals("1")) {
					// next 11 bits are a number that represents the number of sub-packets
					// immediately contained by this packet
					// System.out.println("operator 1");
					// System.out.println(position);
					String numberSubPackets = bits.substring(position + offset, position + offset + 11);
					offset += 11;
					long numberSubPack = binToDec(numberSubPackets);
					long[] temp = parseSubByNumber(bits, position, offset, numberSubPack, type);
					offset += (int) temp[0];
					currentNum = temp[1];
					expression += ")";

				} else {
					// next 15 bits are a number that represents the total length in bits of the
					// sub-packets contained by this packet
					// System.out.println("operator 0");
					String lengthSubPackets = bits.substring(position + offset, position + offset + 15);
					offset += 15;
					long lengthSubPack = binToDec(lengthSubPackets);
					long[] temp = parseSubByLength(bits, position, offset, lengthSubPack, type);
					offset += (int) temp[0];
					currentNum = temp[1];
					expression += ")";

				}

			}

			if (operator == 0) {
				localAnswer += currentNum;
			}
			if (operator == 1) {
				localAnswer = set == false ? ((1 + localAnswer) * currentNum) : localAnswer * currentNum;
				set = true;

			}
			if (operator == 2) {
				if (currentNum < localAnswer || set == false) {
					localAnswer = currentNum;
					set = true;
				}
			}
			if (operator == 3) {
				if (currentNum > localAnswer || set == false) {
					localAnswer = currentNum;
					set = true;
				}
			}
			if (operator == 5) {
				if (tempA == false) {
					temp1 = currentNum;
					tempA = true;
				} else {
					tempB = true;
					temp2 = currentNum;
					localAnswer = temp1 > temp2 ? 1 : 0;
				}
			}
			if (operator == 6) {
				if (tempA == false) {
					temp1 = currentNum;
					tempA = true;
				} else {
					tempB = true;
					temp2 = currentNum;
					localAnswer = temp1 < temp2 ? 1 : 0;
				}
			}
			if (operator == 7) {
				if (tempA == false) {
					temp1 = currentNum;
					tempA = true;
				} else {
					tempB = true;
					temp2 = currentNum;
					localAnswer = temp1 == temp2 ? 1 : 0;
				}
			}

			// add to arraylist of packets
		}
		returnVals[0] = offset - initialOffset;
		returnVals[1] = localAnswer;
		return returnVals;

	}

	private static long[] parseSubByNumber(String bits, int position, int offset, long number, long operator) {
		// TODO Auto-generated method stub
		long[] returnVals = new long[2];
		// TODO Auto-generated method stub
		int initialOffset = offset;
		long localAnswer = 0;
		boolean set = false;
		long temp1 = 0;
		boolean tempA = false;
		long temp2 = 0;
		boolean tempB = false;
		long currentNum = 0;
		for (int i = 0; i < number; i++) {

			String packetVersion = "";
			try {
				packetVersion = bits.substring(position + offset, position + offset + 3);
			} catch (Exception e) {
				break;
			}
			offset += 3;
			// System.out.println(binToDec(packetVersion));
			// System.out.println(position + offset);
			count += binToDec(packetVersion);

			String packetType = "";

			try {
				packetType = bits.substring(position + offset, position + offset + 3);
			} catch (Exception e) {
				break;
			}
			offset += 3;
			long type = binToDec(packetType);

			if (packetType.equals("100")) {
				// literal
				String num = "";
				while (true) {
					String temp = bits.substring(position + offset, position + offset + 5);
					num += temp.substring(1, 5);
					offset += 5;
					if (temp.substring(0, 1).equals("0")) {
						break;
					}

				}

				currentNum = binToDec(num);
				expression += binToDec(num) + ", ";

			} else {
				// operator
				String lengthID = bits.substring(position + offset, position + offset + 1);
				offset += 1;

				if (type == 0) {
					expression += "sum(";
				}
				if (type == 1) {
					expression += "product(";
				}
				if (type == 2) {
					expression += "min(";
				}
				if (type == 3) {
					expression += "max(";
				}
				if (type == 5) {
					expression += "greater than(";
				}
				if (type == 6) {
					expression += "less than(";
				}
				if (type == 7) {
					expression += "equal to(";
				}

				if (lengthID.equals("1")) {
					// next 11 bits are a number that represents the number of sub-packets
					// immediately contained by this packet
					// System.out.println("operator 1");
					// System.out.println(position);
					String numberSubPackets = bits.substring(position + offset, position + offset + 11);
					offset += 11;
					long numberSubPack = binToDec(numberSubPackets);
					long[] temp = parseSubByNumber(bits, position, offset, numberSubPack, type);
					offset += (int) temp[0];
					currentNum = temp[1];
					expression += ")";

				} else {
					// next 15 bits are a number that represents the total length in bits of the
					// sub-packets contained by this packet
					// System.out.println("operator 0");
					String lengthSubPackets = bits.substring(position + offset, position + offset + 15);
					offset += 15;
					long lengthSubPack = binToDec(lengthSubPackets);
					long[] temp = parseSubByLength(bits, position, offset, lengthSubPack, type);
					offset += (int) temp[0];
					currentNum = temp[1];
					expression += ")";

				}

			}

			if (operator == 0) {
				localAnswer += currentNum;
			}
			if (operator == 1) {
				localAnswer = set == false ? ((1 + localAnswer) * currentNum) : localAnswer * currentNum;
				set = true;

			}
			if (operator == 2) {
				if (currentNum < localAnswer || set == false) {
					localAnswer = currentNum;
					set = true;
				}
			}
			if (operator == 3) {
				if (currentNum > localAnswer || set == false) {
					localAnswer = currentNum;
					set = true;
				}
			}
			if (operator == 5) {
				if (tempA == false) {
					temp1 = currentNum;
					tempA = true;
				} else {
					tempB = true;
					temp2 = currentNum;
					localAnswer = temp1 > temp2 ? 1 : 0;
				}
			}
			if (operator == 6) {
				if (tempA == false) {
					temp1 = currentNum;
					tempA = true;
				} else {
					tempB = true;
					temp2 = currentNum;
					localAnswer = temp1 < temp2 ? 1 : 0;
				}
			}
			if (operator == 7) {
				if (tempA == false) {
					temp1 = currentNum;
					tempA = true;
				} else {
					tempB = true;
					temp2 = currentNum;
					localAnswer = temp1 == temp2 ? 1 : 0;
				}
			}

			// add to arraylist of packets
		}
		returnVals[0] = offset - initialOffset;
		returnVals[1] = localAnswer;
		return returnVals;

	}

	private static String decToBin(int dec) {
		String bin = "";
		for (int i = 3; i >= 0; i--) {
			if (dec >= (int) Math.pow(2, i)) {
				dec = dec - (int) Math.pow(2, i);
				bin += "1";
			} else {
				bin += "0";
			}
		}
		return bin;
	}

	private static long binToDec(String bin) {
		long dec = 0;
		for (int i = bin.length() - 1; i >= 0; i--) {
			dec += Integer.parseInt(bin.substring(i, i + 1)) * (long) Math.pow(2, bin.length() - i - 1);
		}
		return dec;
	}

}