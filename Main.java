package Components;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    private Processor Pro = new Processor();

    public ArrayList<String> readTxt(String file) {
        String filePath = "C:\\Users\\asus\\Desktop\\CA\\" + file;

        ArrayList<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Print the lines
        for (String line : lines) {
            System.out.println(line);
        }
        return lines;
    }

    public void Proccessing(ArrayList<String> lines) {
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] parts = line.split(" ");
            String opcode = parts[0];
            String bits = "";

            switch (opcode) {
                case "ADD":
                case "SUB":
                case "MUL":
                case "EOR":
                case "BR":
                    bits += getOpcodeBits(opcode);
                    bits += getRegisterBits(parts[1]);
                    bits += getRegisterBits(parts[2]);
                    break;
                case "MOVI":
                case "BEQZ":
                case "ANDI":
                case "SAL":
                case "SAR":
                case "LDR":
                case "STR":
                    bits += getOpcodeBits(opcode);
                    bits += getRegisterBits(parts[1]);
                    bits += getImmediateBits(parts[2]);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid opcode: " + opcode);
            }

            int instructionBits = Integer.parseInt(bits, 2);
            Instruction instruction = new Instruction(instructionBits);
            Pro.instructionMemory.writeInstruction(instruction);
        }
        
        
    }

    private static String getOpcodeBits(String opcode) {
        int opcodeValue = Arrays.asList("ADD", "SUB", "MUL", "MOVI", "BEQZ", "ANDI", "EOR", "BR", "SAL", "SAR", "LDR", "STR")
                .indexOf(opcode);

        return padZeros(Integer.toBinaryString(opcodeValue), 4);
    }

    private static String getRegisterBits(String register) {
        int registerValue = Integer.parseInt(register.substring(1));
        if (registerValue < 0 || registerValue > 65) {
            throw new IllegalArgumentException("Invalid register: " + register);
        }

        return padZeros(Integer.toBinaryString(registerValue), 6);
    }

    private static String getImmediateBits(String immediate) {
        int immediateValue = Integer.parseInt(immediate);
        if (immediateValue < 0 || immediateValue > 63) {
            throw new IllegalArgumentException("Invalid immediate value: " + immediate);
        }

        return padZeros(Integer.toBinaryString(immediateValue), 6);
    }

    private static String padZeros(String binaryString, int numBits) {
        StringBuilder paddedString = new StringBuilder(binaryString);
        while (paddedString.length() < numBits) {
            paddedString.insert(0, '0');
        }
        return paddedString.toString();
    }

    public static void main(String[] args) {
        Main main = new Main(); 
        ArrayList<String> lines = main.readTxt("MIPS.txt"); 

        main.Proccessing(lines);

        Processor processor = main.Pro; 
        processor.runPipeline();

        System.out.println("Clock Cycle: " + processor.clockCycle);

        System.out.println("Content of All Registers:");
        for (Register register : processor.registers) {
            System.out.println(register.toString());
        }
    }
}
