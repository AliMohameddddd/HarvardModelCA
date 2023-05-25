package Components;	
import java.util.Arrays;

class Processor {
	  protected Register[] registers;
	  private InstructionMemory instructionMemory;
	  protected String[]mips;
	  private DataMemory dataMemory;
	  private int programCounter;
	  protected StatusRegister statusRegister;
	  protected int clockCycle;
	  protected int opcode;
	  protected int R1;
	  protected int R1Address;
	  protected int R2orIMM;
	  protected int R2Address;
	  protected int opcodeexec;
	  protected int R1exec;
	  protected int R1Addressexec;
	  protected int R2orIMMexec;
	  protected int R2Addressexec;
	  protected String type;
	  protected ALU myALU;

	  public Processor() {
	    registers = new Register[66];
	    instructionMemory = new InstructionMemory();
	    dataMemory = new DataMemory();
	    programCounter = 0;
	    statusRegister = new StatusRegister();
	    clockCycle = 1;
	    myALU = new ALU();

	    for (int i = 0; i < 66; i++) {
	      registers[i] = new Register("R" + i);
	    }
	  }
	  
	  public String fetch() {
		if (programCounter >= mips.length) {
			return "null";
		}
	
		String[] parts = mips[programCounter].split(" ");
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
	
		return bits;
	}
	
	private static String getOpcodeBits(String opcode) {
		int opcodeValue = Arrays.asList("ADD", "SUB", "MUL", "MOVI", "BEQZ", "ANDI","EOR", "BR", "SAL", "SAR", "LDR", "STR")
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
	
	private void decode() {
		int address = this.instructionMemory.lastInstruction - 1;
		if (address >= 0) {
			Instruction instruction = this.instructionMemory.readInstruction(address);
			this.opcodeexec = (instruction.instructionBits & 0b1111000000000000) >>> 12;
			int r1Address = (instruction.instructionBits & 0b0000111111000000) >>> 6;
			this.R1Addressexec = r1Address;
			this.R1exec = this.registers[r1Address].getValue();
			this.R2orIMMexec = instruction.instructionBits & 0b0000000000111111;
	
			if (opcodeexec >= 0 && opcodeexec < 3 || opcodeexec >= 6 && opcodeexec < 8) {
				this.R2Address = this.R2orIMMexec;
				this.R2orIMMexec = this.registers[this.R2orIMMexec].getValue();
				this.type = "R";
			} else {
				this.R2Address = -1;
				this.type = "I";
			}
		}
	}
	
	
	
	
		

	  public int readRegister(String registerName) {
	    int registerIndex = Integer.parseInt(registerName.substring(1)); // Extract register index
	    return registers[registerIndex].getValue();
	  }

	  public void execute() {
		    int result;
		    
		    switch (this.opcode) {
		        case 0:
		            result = myALU.add(this.R1exec, this.R2orIMMexec);
		            this.registers[this.R1Addressexec].setValue(result);
		            break;
		        case 1:
		            result = myALU.subtract(this.R1exec, this.R2orIMMexec);
		            this.registers[this.R1Addressexec].setValue(result);
		            break;
		        case 2:
		            result = myALU.multiply(this.R1exec, this.R2orIMMexec);
		            this.registers[this.R1Addressexec].setValue(result);
		            break;
		        case 3:
		            result = myALU.Mov(this.R1exec, this.R2orIMMexec);
		            this.registers[this.R1Addressexec].setValue(result);
		            break;
		        case 4:
		            result = myALU.or(this.R1exec, this.R2orIMMexec);
		            programCounter = result; // Assuming BR sets the program counter
		            break;
		        case 5:
		            result = myALU.shiftLeft(this.R1exec, this.R2orIMMexec);
		            this.registers[this.R1Addressexec].setValue(result);
		            break;
		        case 6:
		            if (this.R1exec == 0) {
		                programCounter += this.R2orIMMexec;
		            } else {
		                programCounter++;
		            }
		            break;
		        case 7:
		            result = myALU.and(this.R1exec, this.R2orIMMexec);
		            this.registers[this.R1Addressexec].setValue(result);
		            break;
		        case 8:
		            result = myALU.shiftLeft(this.R1exec, this.R2orIMMexec);
		            this.registers[this.R1Addressexec].setValue(result);
		            break;
		        case 9:
		            result = myALU.shiftRight(this.R1exec, this.R2orIMMexec);
		            this.registers[this.R1Addressexec].setValue(result);
		            break;
		        case 10:
		            result = dataMemory.readData(this.R2orIMM);
		            this.registers[this.R1Addressexec].setValue(result);
		            break;
		        case 11:
		            dataMemory.writeData(this.R1exec, this.R2orIMMexec);
		            break;
		        default:
		            System.out.println("Invalid opcode: " + this.opcode);
		            break;
		    }
		  
		}
	  

	  public int readMemory(int address) {
	    return dataMemory.readData(address);
	  }

	  public void writeMemory(int address, int value) {
	    dataMemory.writeData(address, value);
	}

	public void runPipeline() {
		int instructionCount = mips.length;
		int cycles = instructionCount + 2;
		int clockCycle = 1;
	
		for (int cycle = 1; cycle <= cycles; cycle++) {
			System.out.println("Cycle " + cycle);
	        
			if (cycle <= instructionCount && programCounter <= instructionCount) {
				String instructionName = mips[programCounter];
				System.out.println("Instruction " + programCounter + " (" + instructionName + ")");
				String fetchOutput = fetch();
				int instructionBits = Integer.parseInt(fetchOutput, 2);
				Instruction instruction = new Instruction(instructionBits);	
				InstructionMemory instructionMemory = new InstructionMemory();
                instructionMemory.writeInstruction(instruction);
				System.out.println("Fetch: " + fetchOutput);

				
			}
	
			// Instruction Decode stage
			if (cycle > 1 && cycle <= instructionCount + 1 && programCounter - 1 >= 0) {
				String instructionName = mips[programCounter - 1];
			 
				System.out.println("Instruction " + (programCounter - 1) + " (" + instructionName + ")");
				decode();
				System.out.println("Decode: " + opcodeexec + " opcode, " + R1exec + " R1, " + R1Addressexec + " R1Address, " + R2orIMMexec + " R2orIMM, " + R2Addressexec + " R2Address, " + type + " type,");
			}
	
			// Execute stage
			if (cycle > 2 && cycle <= instructionCount + 2 && programCounter - 2 >= 0) {
				String instructionName = mips[programCounter - 2];
				System.out.println("Instruction " + (programCounter - 2) + " (" + instructionName + ")");
				System.out.println("Execute:");
				execute();
			}
	
			System.out.println();
			programCounter++;
		}
	
	}
}
	
	
	
	
	
		
	
	
	
	
	
	
	
