import components.map.Map;
import components.program.Program;
import components.statement.Statement;
import components.statement.StatementKernel.Condition;

/**
 * Utility class with method to count the number of calls to primitive
 * instructions (move, turnleft, turnright, infect, skip) in a given
 * {@code Statement}.
 *
 * @author Put your name here
 *
 */
public final class CountPrimitiveCalls {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private CountPrimitiveCalls() {
    }

    /**
     * Reports the number of calls to primitive instructions (move, turnleft,
     * turnright, infect, skip) in a given {@code Statement}.
     *
     * @param s
     *            the {@code Statement}
     * @return the number of calls to primitive instructions in {@code s}
     * @ensures <pre>
     * countOfPrimitiveCalls =
     *  [number of calls to primitive instructions in s]
     * </pre>
     */
    public static int countOfPrimitiveCalls(Statement s) {
        int count = 0;
        switch (s.kind()) {
            case BLOCK: {
                /*
                 * Add up the number of calls to primitive instructions in each
                 * nested statement in the BLOCK.
                 */

                for (int i = 0; i < s.lengthOfBlock(); i++) {
                    Statement st = s.removeFromBlock(i);
                    count += countOfPrimitiveCalls(st);
                    s.addToBlock(i, st);
                }

                break;
            }
            case IF: {
                /*
                 * Find the number of calls to primitive instructions in the
                 * body of the IF.
                 */
                Statement tmp = s.newInstance();
                Condition c = s.disassembleIf(tmp);
                count += countOfPrimitiveCalls(tmp);
                s.assembleIf(c, tmp);
                break;
            }
            case IF_ELSE: {
                /*
                 * Add up the number of calls to primitive instructions in the
                 * "then" and "else" bodies of the IF_ELSE.
                 */
                Statement tmp1 = s.newInstance();
                Statement tmp2 = s.newInstance();
                Condition c = s.disassembleIfElse(tmp1, tmp2);
                count = countOfPrimitiveCalls(tmp1)
                        + countOfPrimitiveCalls(tmp2);
                s.assembleIfElse(c, tmp1, tmp2);
                break;
            }
            case WHILE: {
                /*
                 * Find the number of calls to primitive instructions in the
                 * body of the WHILE.
                 */
                Statement tmp = s.newInstance();
                Condition c = s.disassembleWhile(tmp);
                count += countOfPrimitiveCalls(tmp);
                s.assembleWhile(c, tmp);
                break;
            }
            case CALL: {
                /*
                 * This is a leaf: the count can only be 1 or 0. Determine
                 * whether this is a call to a primitive instruction or not.
                 */

                String instruction = s.disassembleCall();
                if (instruction.equals("move") || instruction.equals("turnleft")
                        || instruction.equals("turnright")
                        || instruction.equals("infect")
                        || instruction.equals("skip")) {
                    count = 1;
                }
                s.assembleCall(instruction);
                break;
            }
            default: {
                // this will never happen...can you explain why?
                break;
            }
        }
        return count;
    }

    /**
     * Reports the number of calls to a given instruction, {@code instr}, in a
     * given {@code Statement}.
     *
     * @param s
     *            the {@code Statement}
     * @param instr
     *            the instruction name
     * @return the number of calls to {@code instr} in {@code s}
     * @ensures countOfInstructionCalls = [number of calls to instr in s]
     */
    public static int countOfInstructionCalls(Statement s, String instr) {
        int count = 0;
        switch (s.kind()) {
            case BLOCK: {
                /*
                 * Add up the number of calls to primitive instructions in each
                 * nested statement in the BLOCK.
                 */

                for (int i = 0; i < s.lengthOfBlock(); i++) {
                    Statement tmp = s.removeFromBlock(i);
                    count += countOfInstructionCalls(tmp, instr);
                    s.addToBlock(i, tmp);
                }

                break;
            }
            case IF: {
                /*
                 * Find the number of calls to primitive instructions in the
                 * body of the IF.
                 */
                Statement tmp = s.newInstance();
                Condition c = s.disassembleIf(tmp);
                count += countOfInstructionCalls(tmp, instr);
                s.assembleIf(c, tmp);
                break;
            }
            case IF_ELSE: {
                /*
                 * Add up the number of calls to primitive instructions in the
                 * "then" and "else" bodies of the IF_ELSE.
                 */
                Statement tmp1 = s.newInstance();
                Statement tmp2 = s.newInstance();
                Condition c = s.disassembleIfElse(tmp1, tmp2);
                count = countOfInstructionCalls(tmp1, instr)
                        + countOfInstructionCalls(tmp2, instr);
                s.assembleIfElse(c, tmp1, tmp2);
                break;
            }
            case WHILE: {
                /*
                 * Find the number of calls to primitive instructions in the
                 * body of the WHILE.
                 */
                Statement tmp = s.newInstance();
                Condition c = s.disassembleWhile(tmp);
                count += countOfInstructionCalls(tmp, instr);
                s.assembleWhile(c, tmp);
                break;
            }
            case CALL: {
                /*
                 * This is a leaf: the count can only be 1 or 0. Determine
                 * whether this is a call to a primitive instruction or not.
                 */

                String instruction = s.disassembleCall();
                if (instruction.equals(instr)) {
                    count = 1;
                }
                s.assembleCall(instruction);
                break;
            }
            default: {
                // this will never happen...can you explain why?
                break;
            }
        }
        return count;
    }

    /**
     * Refactors the given {@code Statement} by renaming every occurrence of
     * instruction {@code oldName} to {@code newName}. Every other statement is
     * left unmodified.
     *
     * @param s
     *            the {@code Statement}
     * @param oldName
     *            the name of the instruction to be renamed
     * @param newName
     *            the new name of the renamed instruction
     * @updates s
     * @requires [newName is a valid IDENTIFIER]
     * @ensures <pre>
     * s = [#s refactored so that every occurrence of oldName is
     *   replaced by newName]
     * </pre>
     */
    public static void renameInstruction(Statement s, String oldName,
            String newName) {
        switch (s.kind()) {
            case BLOCK: {

                for (int i = 0; i < s.lengthOfBlock(); i++) {
                    Statement tmp = s.removeFromBlock(i);
                    renameInstruction(tmp, oldName, newName);
                    s.addToBlock(i, tmp);
                }

                break;
            }
            case IF: {

                Statement tmp = s.newInstance();
                Condition c = s.disassembleIf(tmp);
                renameInstruction(tmp, oldName, newName);
                s.assembleIf(c, tmp);
                break;
            }
            case IF_ELSE: {

                Statement tmp1 = s.newInstance();
                Statement tmp2 = s.newInstance();
                Condition c = s.disassembleIfElse(tmp1, tmp2);
                renameInstruction(tmp1, oldName, newName);
                renameInstruction(tmp2, oldName, newName);
                s.assembleIfElse(c, tmp1, tmp2);
                break;
            }
            case WHILE: {

                Statement tmp = s.newInstance();
                Condition c = s.disassembleWhile(tmp);
                renameInstruction(tmp, oldName, newName);
                s.assembleWhile(c, tmp);
                break;
            }
            case CALL: {

                String instruction = s.disassembleCall();
                if (instruction.equals(oldName)) {
                    instruction = newName;
                }
                s.assembleCall(instruction);
                break;
            }
            default: {

                break;
            }
        }
    }

    /**
     * Refactors the given {@code Program} by renaming instruction
     * {@code oldName}, and every call to it, to {@code newName}. Everything
     * else is left unmodified.
     *
     * @param p
     *            the {@code Program}
     * @param oldName
     *            the name of the instruction to be renamed
     * @param newName
     *            the new name of the renamed instruction
     * @updates p
     * @requires <pre>
     * oldName is in DOMAIN(p.context)  and
     * [newName is a valid IDENTIFIER]  and
     * newName is not in DOMAIN(p.context)
     * </pre>
     * @ensures <pre>
     * p = [#p refactored so that instruction oldName and every call
     *   to it are replaced by newName]
     * </pre>
     */
    public static void renameInstruction(Program p, String oldName,
            String newName) {
        Map<String, Statement> context = p.newContext();
        Map<String, Statement> tmp = p.newContext();
        p.swapContext(context);
        while (context.size() > 0) {
            Map.Pair<String, Statement> instruction = context.removeAny();
            String name = instruction.key();
            if (name.equals(oldName)) {
                name = newName;
            } else {
                renameInstruction(instruction.value(), oldName, newName);
            }
            tmp.add(name, instruction.value());
        }
        p.swapContext(tmp);
        Statement body = p.newBody();
        p.swapBody(body);
        renameInstruction(body, oldName, newName);
        p.swapBody(body);
    }

    /**
     * Refactors the given {@code Statement} so that every IF_ELSE statement
     * with a negated condition (NEXT_IS_NOT_EMPTY, NEXT_IS_NOT_ENEMY,
     * NEXT_IS_NOT_FRIEND, NEXT_IS_NOT_WALL) is replaced by an equivalent
     * IF_ELSE with the opposite condition and the "then" and "else" BLOCKs
     * switched. Every other statement is left unmodified.
     *
     * @param s
     *            the {@code Statement}
     * @updates s
     * @ensures <pre>
     * s = [#s refactored so that IF_ELSE statements with "not"
     *   conditions are simplified so the "not" is removed]
     * </pre>
     */
    public static void simplifyIfElse(Statement s) {
        switch (s.kind()) {
            case BLOCK: {

                for (int i = 0; i < s.lengthOfBlock(); i++) {
                    Statement tmp = s.removeFromBlock(i);
                    simplifyIfElse(tmp);
                    s.addToBlock(i, tmp);
                }

                break;
            }
            case IF: {

                Statement tmp = s.newInstance();
                Condition c = s.disassembleIf(tmp);
                simplifyIfElse(tmp);
                s.assembleIf(c, tmp);

                break;
            }
            case IF_ELSE: {

                Statement ifns = s.newInstance();
                Statement elsens = s.newInstance();
                Condition c = s.disassembleIfElse(ifns, elsens);
                String condition = c.name();
                if (condition.contains("NOT")) {
                    condition = condition.replace("NOT_", "");
                    s.assembleIfElse(Condition.valueOf(condition), elsens,
                            ifns);
                } else {
                    s.assembleIfElse(c, ifns, elsens);
                }
                break;
            }
            case WHILE: {

                Statement tmp = s.newInstance();
                Condition c = s.disassembleWhile(tmp);
                simplifyIfElse(tmp);
                s.assembleWhile(c, tmp);

                break;
            }
            case CALL: {
                // nothing to do here...can you explain why?
                break;
            }
            default: {
                // this will never happen...can you explain why?
                break;
            }
        }
    }
}
