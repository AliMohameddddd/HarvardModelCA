    package Components;





        public class Main {
            public static void main(String[] args) {
                Processor processor = new Processor();
            String instruction1 = "MOVI R1 10";
            String instruction4 = "MOVI R2 11";
            String instruction2= "ADD R1 R2";
            String instruction3 = "LDR R1 10";
            String instruction5 = "SUB R1 10";
            String instruction6 = "LDR R1 10";
            
            processor.mips = new String[] {
                instruction1,
                instruction4,
                instruction2,
                instruction3,
                instruction5,
                instruction6
            };

            processor.runPipeline();

            System.out.println("Clock Cycle: " + processor.clockCycle);

            
           


            System.out.println("Content of All Registers:");
            for (Register register : processor.registers) {
                
                System.out.println(register.toString());  
            }
        }

    }
            
