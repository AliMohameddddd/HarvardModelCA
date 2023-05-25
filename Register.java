    package Components;

    public class Register {
        private String name;
        private static int size=8;
        private int value;

        public Register(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public int getSize() {
            return size;
        }
        
        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }


        public String toString() {
            return name + " (" + size + " bits): " + value;
        }

        public void clear() {
            value = 0;
        }

        public void increment() {
            value++;
        }

        public void decrement() {
            value--;
        }

        public void bitwiseAnd(int value) {
            this.value &= value;
        }

        public void bitwiseOr(int value) {
            this.value |= value;
        }

        public void bitwiseXor(int value) {
            this.value ^= value;
        }

        public void bitwiseNot() {
            value = ~value;
        }
    }

