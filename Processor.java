package Components;	
import java.util.Arrays;

class Processor {
	  protected Register[] registers;
	  protected InstructionMemory instructionMemory;
	
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
	  protected Instruction toBeDecoded;

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
	  
	  public Instruction fetch() {
           toBeDecoded=this.instructionMemory.getInstructionMemory()[programCounter];
		programCounter++;
		return   toBeDecoded;

		
	}
	
	
	
	private void decode() {
		this.opcodeexec= this.opcode;
	    	this.R1Addressexec = this.R1Address;
	    	this.R2orIMMexec = this.R2orIMM;
	    	this.R1exec = this.R1;
		int address = this.instructionMemory.lastInstruction - 1;
		if (address >= 0) {
			Instruction instruction = this.instructionMemory.readInstruction(address);
			this.opcode = (instruction.instructionBits & 0b1111000000000000) >>> 12;
			int r1Address = (instruction.instructionBits & 0b0000111111000000) >>> 6;
			
			this.R1 = this.registers[r1Address].getValue();
			this.R2orIMM = instruction.instructionBits & 0b0000000000111111;
	
			if (opcode >= 0 && opcode < 3 || opcode >= 6 && opcode < 8) {
				this.R2Address = this.R2orIMM;
				this.R2orIMM = this.registers[this.R2orIMM].getValue();
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
		int instructionCount = instructionMemory. lastInstruction;
		int cycles = instructionCount + 2;	

		for (int cycle = 1; cycle <= cycles; cycle++) {
			System.out.println("Cycle " + cycle);
	        
			if (cycle <= instructionCount && programCounter <= instructionCount) {
			  Instruction M=fetch();


				System.out.println("Fetch: " + M.instructionBits);

				
			}
	
			// Instruction Decode stage
			if (cycle > 1 && cycle <= instructionCount + 1 && programCounter - 1 >= 0) {
				int instructionName = this.instructionMemory.getInstructionMemory()[programCounter - 1].instructionBits;
			 
				System.out.println("Instruction " + (programCounter - 1) + " (" + instructionName + ")");
				decode();
				System.out.println("Decode: " + opcodeexec + " opcode, " + R1exec + " R1, " + R1Addressexec + " R1Address, " + R2orIMMexec + " R2orIMM, " + R2Addressexec + " R2Address, " + type + " type,");
			}
	
			// Execute stage
			if (cycle > 2 && cycle <= instructionCount + 2 && programCounter - 2 >= 0) {
				int instructionName =this.instructionMemory.getInstructionMemory()[programCounter - 2].instructionBits;
				System.out.println("Instruction " + (programCounter - 2) + " (" + instructionName + ")");
				System.out.println("Execute:");
				execute();
			}
	
			System.out.println();
			
		}
	
	}
}
	
	
	
	
	
		
	
	
	
	
	
	
	
