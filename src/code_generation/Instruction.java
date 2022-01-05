package code_generation;

public class Instruction {

    private Operation op;
    private String op1, op2, dest, stringRepr;

    public Instruction(String instr) {
        op1 = op2 = dest = null;
        this.stringRepr = instr;
    }

    public static Instruction parse(String instrRepr) {
        Instruction instr = new Instruction(instrRepr);
        String[] token = instrRepr.split("\\s+");
        instr.op = Operation.getOperation(instrRepr);
        switch (instr.op) {
            case _COPY: // dest = copy b
                instr.dest = token[0];
                instr.op1 = token[3];
                break;
            /* dest = op1 op op2 */
            case _ADD:
            case _SUB:
            case _PROD:
            case _DIV:
            case _AND:
            case _OR:
            case _LT:
            case _LE:
            case _EQ:
            case _NE:
            case _GE:
            case _GT:
                instr.dest = token[0];
                instr.op1 = token[2];
                instr.op2 = token[4];
                break;
            /* dest = op op1 */
            case _NEG:
            case _NOT:
                instr.dest = token[0];
                instr.op1 = token[3];
                break;
            case _SKIP: // op1 skip
                instr.op1 = token[0];
                break;
            case _GOTO: // goto op1
                instr.op1 = token[1];
                break;
            case _IF: // if op1 goto e
                instr.op1 = token[1];
                instr.dest = token[3];
                break;
            case _PMB: // pmb op1
            case _CALL: // call op1
            case _RTN:
            case _PARAM: // param op1
                instr.op1 = token[1];
                break;
        }
        return instr;
    }

    @Override
    public String toString(){
        return this.stringRepr;
    }

    public static enum Operation {
        _COPY,
        _ADD,
        _SUB,
        _PROD,
        _DIV,
        _NEG,
        _AND,
        _OR,
        _NOT,
        _LT,
        _LE,
        _EQ,
        _NE,
        _GE,
        _GT,
        _SKIP,
        _GOTO,
        _IF,
        _PMB,
        _CALL,
        _RTN,
        _PARAM;

        @Override
        public String toString() {
            return this.name().toLowerCase();
        }

        public static Operation getOperation(String repr) { 
            for (Operation op : Operation.values()) {
                if (repr.contains(op.toString()))
                    return op;
            }
            return null;
        }
    }
}