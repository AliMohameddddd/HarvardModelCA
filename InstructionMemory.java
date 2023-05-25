package Components;

    public class InstructionMemory {
    private Instruction[] instructionMemory;
    public int lastInstruction;

    public InstructionMemory() {
        instructionMemory = new Instruction[1024];
        lastInstruction=0;
    }

    // Instruction Memory Operations

    public Instruction readInstruction(int address) {
        if (address >= 0 && address < instructionMemory.length) {
            return instructionMemory[address];
        } else {
            throw new IllegalArgumentException("Invalid instruction address: " + address);
        }
    }
    

    public void writeInstruction(Instruction value) {
        instructionMemory[lastInstruction] = value;
        lastInstruction++;
    }
    
    public int getInstructionMemorySize() {
        return instructionMemory.length / 2;
    }
    
    public Instruction[] getInstructionMemory() {
        return instructionMemory;
    }
    
}