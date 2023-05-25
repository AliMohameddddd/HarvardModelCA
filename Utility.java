package Components;

public class Utility {
	
	public static int parseBinary (String binaryString) {
		return Integer.parseInt(binaryString, 2);
	}
	
	public static int parseHex (String hexString) {
		return Integer.parseInt(hexString, 16);
	}
	
	public static int parseDecimal (String decimalString) {
		return Integer.parseInt(decimalString);
	}
	
	public static String formatBinary (int value, int bits) {
		String binaryString = Integer.toBinaryString(value);
		while(binaryString.length()<bits) {
			binaryString="0"+binaryString;
		}
		return binaryString;
	}
	
	public static String formatHex (int value, int bits) {
		String hexString = Integer.toHexString(value);
		while(hexString.length()<bits) {
			hexString="0"+hexString;
		}
		return hexString;
	}
	
	public static String formatRegister (Register reg) {
		return reg.getName()+" "+ formatBinary(reg.getValue(),reg.getSize());
	}	
	

	public static String formatMemory(int address, int value) {
        return "[" + formatHex(address, 4) + "] = " + formatHex(value, 4);
    }
	
	
}
