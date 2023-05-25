package Components;

public class DataMemory {
    private int[] dataMemory;

    public DataMemory() {
        dataMemory = new int[8];
    }

    // Data Memory Operations

    public int readData(int address) {
        return dataMemory[address];
    }

    public void writeData(int address, int value) {
        dataMemory[address] = value;
    }

    public int[] readDataWord(int address) {
        int[] word = new int[2];
        word[0] = dataMemory[address];
        word[1] = dataMemory[address + 1];
        return word;
    }

    public void writeDataWord(int address, byte[] word) {
        dataMemory[address] = word[0];
        dataMemory[address + 1] = word[1];
    }


    public int[] dumpDataMemory() {
        return dataMemory;
    }


    public int getDataMemorySize() {
        return dataMemory.length;
    }
}