package Components;

public class ALU {
    private StatusRegister statusRegister;

    public ALU() {
        statusRegister = new StatusRegister();
    }

    public int add(int operand1, int operand2) {
        int result = operand1 + operand2;
        updateCarryFlag(result);
        updateOverflowFlag(operand1, operand2, result);
        updateNegativeFlag(result);
        updateZeroFlag(result);
        return result;
    }

    public int subtract(int operand1, int operand2) {
        int result = operand1 - operand2;
        updateCarryFlag(result);
        updateOverflowFlag(operand1, -operand2, result);
        updateNegativeFlag(result);
        updateZeroFlag(result);
        return result;
    }

    public int multiply(int operand1, int operand2) {
        int result = operand1 * operand2;
        updateCarryFlag(result);
        updateOverflowFlag(operand1, operand2, result);
        updateNegativeFlag(result);
        updateZeroFlag(result);
        return result;
    }

    public int and(int operand1, int operand2) {
        int result = operand1 & operand2;
        updateNegativeFlag(result);
        updateZeroFlag(result);
        return result;
    }

    public int or(int operand1, int operand2) {
        int result = operand1 | operand2;
        updateNegativeFlag(result);
        updateZeroFlag(result);
        return result;
    }

    public int xor(int operand1, int operand2) {
        int result = operand1 ^ operand2;
        updateNegativeFlag(result);
        updateZeroFlag(result);
        return result;
    }
    
    public int Mov(int operand1, int operand2) {
        int result =operand2;
        return result;
    }


    public int shiftLeft(int operand1, int operand2) {
        int result = operand1 << operand2;
        updateNegativeFlag(result);
        updateZeroFlag(result);
        return result;
    }

    public int shiftRight(int operand1, int operand2) {
        int result = operand1 >> operand2;
        updateNegativeFlag(result);
        updateZeroFlag(result);
        return result;
    }

    public int divide(int operand1, int operand2) {
        return operand1 / operand2;
    }

    public int modulus(int operand1, int operand2) {
        return operand1 % operand2;
    }

    private void updateCarryFlag(int result) {
        statusRegister.setCarryFlag(result > Integer.MAX_VALUE || result < Integer.MIN_VALUE);
    }

    private void updateOverflowFlag(int operand1, int operand2, int result) {
        boolean overflow = (operand1 > 0 && operand2 > 0 && result < 0)
                            || (operand1 < 0 && operand2 < 0 && result > 0);
        statusRegister.setOverflowFlag(overflow);
    }

    private void updateZeroFlag(int result) {
        statusRegister.setZeroFlag(result == 0);
    }

    private void updateNegativeFlag(int result) {
        statusRegister.setNegativeFlag(result < 0);
        statusRegister.setSignFlag(result < 0);
    }
}
