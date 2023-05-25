package Components;

public class StatusRegister {
    private boolean carryFlag;
    private boolean overflowFlag;
    private boolean negativeFlag;
    private boolean signFlag;
    private boolean zeroFlag;

    public StatusRegister() {
        this.carryFlag = false;
        this.overflowFlag = false;
        this.negativeFlag = false;
        this.signFlag = false;
        this.zeroFlag = false;
    }

    public boolean getCarryFlag() {
        return carryFlag;
    }

    public boolean getOverflowFlag() {
        return overflowFlag;
    }

    public boolean getNegativeFlag() {
        return negativeFlag;
    }

    public boolean getSignFlag() {
        return signFlag;
    }

    public boolean getZeroFlag() {
        return zeroFlag;
    }

    public void setCarryFlag(boolean value) {
        this.carryFlag = value;
    }

    public void setOverflowFlag(boolean value) {
        this.overflowFlag = value;
    }

    public void setNegativeFlag(boolean value) {
        this.negativeFlag = value;
    }

    public void setSignFlag(boolean value) {
        this.signFlag = value;
    }

    public void setZeroFlag(boolean value) {
        this.zeroFlag = value;
    }

}
