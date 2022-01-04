package code_generation;

public class Instruction {

    private Operation op;
    private String op1, op2, dest;

    public Instruction() {
        op1 = op2 = dest = null;
    }

    public static Instruction parse(String instrReprs) {
        Instruction instr = new Instruction();
        String[] token = instrReprs.split("\\s+");
        instr.op = Operation.getOperation(instrReprs);
        switch (instr.op) {
            case COPY: // dest = copy b
                instr.dest = token[0];
                instr.op1 = token[3];
                break;
            /* dest = op1 op op2 */
            case ADD:
            case SUB:
            case PROD:
            case DIV:
            case AND:
            case OR:
            case LT:
            case LE:
            case EQ:
            case NE:
            case GE:
            case GT:
                instr.dest = token[0];
                instr.op1 = token[2];
                instr.op2 = token[4];
                break;
            /* dest = op op1 */
            case NEG:
            case NOT:
                instr.dest = token[0];
                instr.op1 = token[3];
                break;
            case SKIP: // op1 skip
                instr.op1 = token[0];
                break;
            case GOTO: // goto op1
                instr.op1 = token[1];
                break;
            case IF: // if op1 goto e
                instr.op1 = token[1];
                instr.dest = token[3];
                break;
            case PMB: // pmb op1
            case CALL: // call op1
            case RTN:
            case PARAM: // param op1
                instr.op1 = token[1];
                break;
        }
        return instr;
    }

    public static enum Operation {
        COPY,
        ADD,
        SUB,
        PROD,
        DIV,
        NEG,
        AND,
        OR,
        NOT,
        LT,
        LE,
        EQ,
        NE,
        GE,
        GT,
        SKIP,
        GOTO,
        IF,
        PMB,
        CALL,
        RTN,
        PARAM;

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