import java.util.*;

public class ReversePolisNotation {
    Map<String, Integer> marksPosiions = new HashMap<String, Integer>();

    private final List<Token> tokens;
    public List<Token> operands = new ArrayList<>();
    public List<Token> variables = new ArrayList<>();
    public List<Token> result = new ArrayList<>();


    Token IfForValue = null;
    boolean isWhile = false;
    boolean isIF    = false;

    int markCunter = 1;
    int markPositioinCounter = 0;

    public List<LexemType> op = new ArrayList<>();

    Stack<Token> stack = new Stack<>();

    public ReversePolisNotation(List<Token> tokens) {
        this.tokens = tokens;

        op.add(LexemType.OP);
        op.add(LexemType.ROUND_CLOSE_BRACKET);
        op.add(LexemType.ROUND_OPEN_BRACKET);
        op.add(LexemType.COMPARISION_OP);
        op.add(LexemType.ASSIGN_OP);
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public int GetPriority (Token token) {
        switch (token.getValue()) {
            case "=":
                return 0;

            case "*":
            case "/":
                return 4;

            case ">":
            case "<":
                return 3;

            case "+":
            case "-":
                return 2;

            default:
                return 1;
        }
    }

    public void translate () {

        for (Token token : tokens) {

            if (token.getType().equals(LexemType.SEMICOLON)) {
                while (stack.size() > 0)
                    addToken(stack.pop());
                continue;
            }

            if (token.getType().equals(LexemType.KEY_WHILE) || token.getType().equals(LexemType.KEY_IF)) {
                Token m1 = new Token("P" + markCunter);
                marksPosiions.put("P" + markCunter, markPositioinCounter);
                markCunter++;
                IfForValue = m1;

                if (token.getType().equals(LexemType.KEY_WHILE))
                    isWhile = true;

                if (token.getType().equals(LexemType.KEY_IF))
                    isIF = true;

                continue;
            }

            if (token.getType().equals(LexemType.FIGURE_OPEN_BRACKET)) {

                if (isWhile || isIF) {
                    addToken(IfForValue);
                    Token m2 = new Token("!F");
                    addToken(m2);
                }

                continue;
            }

            if (token.getType().equals(LexemType.FIGURE_CLOSE_BRACKET)) {
                if (isWhile) {
                    Token m1 = new Token("P" + markCunter);
                    Token m2 = new Token("!");

                    while (stack.size() > 0)
                        addToken(stack.pop());

                    addToken(m1);
                    addToken(m2);

                    // обязательно идет после addtoken (счетчик сдвигатся)
                    marksPosiions.put("P" + markCunter, marksPosiions.get("P" + (markCunter-1)));
                    marksPosiions.put("P" + (markCunter - 1), markPositioinCounter);

                    markCunter++; // сдвиг счетчика меток
                    isWhile = false;
                }

                if (isIF) {

                    while (stack.size() > 0)
                        addToken(stack.pop());


                    // обязательно идет после addtoken (счетчик сдвигатся)
                    marksPosiions.put("P" + (markCunter - 1), markPositioinCounter);

                    markCunter++; // сдвиг счетчика меток
                    isIF = false;
                }


                continue;
            }




            if (op.contains(token.getType())) {
                if ((stack.size() > 0) && (token.getType() != LexemType.ROUND_OPEN_BRACKET)) {
                    if (token.getType() == LexemType.ROUND_CLOSE_BRACKET) {
                        Token s = stack.pop();
                        while (s.getType() != LexemType.ROUND_OPEN_BRACKET) {
                            addToken(s);
                            s = stack.pop();
                        }
                    } else {
                        if (GetPriority(token) > GetPriority(stack.peek())) {
                            stack.add(token);
                        } else {
                            while (stack.size() > 0 && GetPriority(token) <= GetPriority(stack.peek()))
                                addToken(stack.pop());
                            stack.add(token);
                        }
                    }
                }
                else
                    stack.add(token);
            }
            else
                addToken(token);
        }
        if (stack.size() > 0)
            for (Token token : stack)
                addToken(token);

        debugMark();

        for (Token token : result) {
            System.out.print(token.getValue()+ " ");
        }
    }


    public  void returnAllOperansToResult() {
        for (Token token : operands) {
            addToken(token);
            operands.remove(token);
        }
    }

    public void addToken (Token token) {
        result.add(token);
        System.out.println(token.getValue() + " : " + markPositioinCounter);
        markPositioinCounter++;
    }

    private void debugMark() {
        System.out.println("");
        System.out.println("-----------");
        for (Map.Entry entry : marksPosiions.entrySet()) {
            System.out.println(" " + entry.getKey() + ":    "
                    + entry.getValue());
            System.out.println("-----------");
        }
    }
}


// int aifk = 12 - 6 * 32 - 1;
// aifk = 12 6 32 * - 1 -
// k записываем в рузельтат
// =


// перевод в обратную польскую запись
// int i = a + 12;
// int, i, a, 12, +, =


// if (a == b) {
//   int k = 12;
// }
// int b = 13;


//a, b, ==, if, {, int, k, 12, =, }, int, b, 13, =