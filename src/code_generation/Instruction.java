package code_generation;

import java.util.ArrayList;

public class Instruction {

    public Operation op;
    public String op1, op2, dest, stringRepr;

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
            case _PRINT:
            case _SCAN:
            case _PARAM: // param op1
                instr.op1 = token[1];
                break;
            case _RTN:
                if (token.length == 2)
                    instr.op1 = token[1];
                else
                    instr.op1 = null;
                break;
        }
        return instr;
    }

    public String toAssembly() {
        Procedure proc;
        String instr = "";
        switch (this.op) {
            case _COPY:
                instr = setOperandInRegister(op1);
                instr += "\tmov dword\t[" + dest + "], eax";
                break;
            case _ADD:
            case _SUB:
            case _AND:
            case _OR:
            case _PROD:
                instr = setOperandsInRegister();
                instr += "\t" + this.op.toNASM() + "\teax, ebx\n";
                instr += "\tmov\t[" + dest + "], eax";
                break;
            case _DIV:
                instr = "\txor\tedx, edx\n";
                instr += setOperandsInRegister();
                instr += "\tdiv\tebx\n";
                instr += "\tmov\t[" + dest + "], eax";
                break;
            case _NEG:
            case _NOT:
                instr = setOperandInRegister(op1);
                instr += "\t" + this.op.toNASM() + "\teax\n";
                instr += "\tmov\t[" + dest + "], eax";
                break;
            case _LT:
            case _LE:
            case _EQ:
            case _NE:
            case _GE:
            case _GT:
                String ne = CodeGenerator.newLabel();
                String end = CodeGenerator.newLabel();
                instr = setOperandsInRegister();
                instr += "\tcmp\teax, ebx\n";
                instr += "\t" + this.op.toNASM() + "\t" + ne + "\n"; // dword??
                instr += "\tmov dword [" + dest + "], 0\n";
                instr += "\tjmp\t" + end + "\n";
                instr += "\t" + ne + ":\tnop\n";
                instr += "\tmov dword [" + dest + "], -1\n"; // dword??
                instr += "\t" + end + ":\tnop";
                break;
            case _SKIP:
                instr = "\t" + op1 + " :\tnop";
                break;
            case _GOTO:
                instr = "\tjmp\t" + op1;
                break;
            case _IF:
                instr = setOperandInRegister(op1);
                instr += "\tcmp\teax, 0\n";
                instr += "\tje\t" + dest;
                break;
            case _PMB:
                proc = CodeGenerator.procTable.get(op1);
                if (op1.equals("main")) {
                    CodeGenerator.assemblyInstr.add("\tmain:");
                }
                int pos = 4;
                for (String param : proc.params) {
                    pos += 4;
                    instr += "\tmov\teax, [" + pos + "+ esp]\n";
                    instr += "\tmov\t[" + param + "], eax\n";
                }
                break;
            case _CALL:
                instr = "\tsub\tesp, 4\n";
                proc = CodeGenerator.procTable.get(op1);
                instr += "\tcall\t" + proc.start + "\n";
                instr += "\tpop\teax\n";
                instr += "\tadd\tesp, " + (proc.params.size() * 4);
                break;
            case _RTN:
                if (this.op1 != null) {
                    instr = setOperandInRegister(op1);
                    instr += "\tmov\t[4+esp], eax\n";
                }
                instr += "\tret";
                break;
            case _PARAM:
                instr = setOperandInRegister(op1);
                instr += "\tpush\teax";
                break;
            case _PRINT:
                instr = setOperandInRegister(op1);
                instr += "\tpush\teax\n";
                instr += "\tpush\tfmtout\n";
                instr += "\tcall\tprintf\n";
                instr += "\tadd\tesp, 8";
                break;
            case _SCAN:
                instr = "\tpush\t" + op1 + "\n";
                instr += "\tpush\tfmtin\n";
                instr += "\tcall\tscanf\n";
                instr += "\tadd\tesp, 8";

        }
        return "; " + this.stringRepr + "\n" + instr;

    }

    private boolean isParameter(String op) {
        for (String name : CodeGenerator.procTable.keySet()) {
            ArrayList<String> declaredParams = CodeGenerator.procTable.get(name).params;
            for (String param : declaredParams) {
                if (param.equals(op)) {
                    return true;
                }
            }
        }
        return false;
    }

    private String setOperandInRegister(String op) {
        String instr = "";
        if (CodeGenerator.varTable.keySet().contains(op) || isParameter(op)) {
            instr = "\tmov\teax, [" + op + "]\n";
        } else {
            instr = "\tmov\teax, " + op + "\n";
        }
        return instr;
    }

    private String setOperandsInRegister() {
        String instr = "";
        if (CodeGenerator.varTable.keySet().contains(op1)) {
            instr = "\tmov\teax, [" + op1 + "]\n";
        } else {
            instr = "\tmov\teax, " + op1 + "\n";
        }
        if (CodeGenerator.varTable.keySet().contains(op2)) {
            instr += "\tmov\tebx, [" + op2 + "]\n";
        } else {
            instr += "\tmov\tebx, " + op2 + "\n";
        }
        return instr;
    }

    public boolean isExpression() {
        switch (this.op) {
            case _COPY:
            case _ADD:
            case _SUB:
            case _PROD:
            case _DIV:
            case _NEG:
            case _AND:
            case _OR:
            case _NOT:
            case _LT:
            case _LE:
            case _EQ:
            case _NE:
            case _GE:
            case _GT:
                return true;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
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
        _PARAM,
        _PRINT,
        _SCAN;

        @Override
        public String toString() {
            return this.name().toLowerCase();
        }

        public static Operation getOperation(String repr) {
            for (Operation op : Operation.values()) {
                if (repr.contains(op.toString())) {
                    return op;
                }
            }
            return null;
        }

        public String toNASM() {
            switch (this) {
                case _COPY:
                    return "mov";
                case _ADD:
                    return "add";
                case _SUB:
                    return "sub";
                case _PROD:
                    return "imul";
                case _DIV:
                    return "div";
                case _NEG:
                    return "neg";
                case _AND:
                    return "and";
                case _OR:
                    return "or";
                case _NOT:
                    return "not";
                case _LT:
                    return "jl";
                case _LE:
                    return "jle";
                case _EQ:
                    return "je";
                case _NE:
                    return "jne";
                case _GE:
                    return "jge";
                case _GT:
                    return "jg";
                case _GOTO:
                    return "jmp";
                default:
                    return null;
            }
        }
    }
}