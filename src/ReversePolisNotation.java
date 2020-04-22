import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ReversePolisNotation {
    private final List<Token> tokens;
    public List<Token> operands = new ArrayList<>();
    public List<Token> variables = new ArrayList<>();
    public List<Token> result = new ArrayList<>();


    Token IfForValue = null;
    boolean isIforFunc = false;
    int ifAndForCounter = 0;

    public List<LexemType> op = new ArrayList<>();


    Stack<Token> stack = new Stack<>();

    public ReversePolisNotation(List<Token> tokens) {
        this.tokens = tokens;

        op.add(LexemType.OP);
        op.add(LexemType.ROUND_CLOSE_BRACKET);
        op.add(LexemType.ROUND_OPEN_BRACKET);
        op.add(LexemType.COMPARISION_OP);
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public int GetPriority (Token token) {
        switch (token.getValue()) {
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
                    result.add(stack.pop());
                continue;
            }

            if (token.getType().equals(LexemType.KEY_IF)) {
                ifAndForCounter++;
                IfForValue = token;
                isIforFunc = true;
                continue;
            }
            if (token.getType().equals(LexemType.FIGURE_OPEN_BRACKET)) {
                result.add(IfForValue);
                result.add(token);
                continue;
            }

            if (token.getType().equals(LexemType.FIGURE_CLOSE_BRACKET)) {
                isIforFunc = false;
                result.add(token);
                continue;
            }




            if (op.contains(token.getType())) {
                if ((stack.size() > 0) && (token.getType() != LexemType.ROUND_OPEN_BRACKET)) {
                    if (token.getType() == LexemType.ROUND_CLOSE_BRACKET) {
                        Token s = stack.pop();
                        while (s.getType() != LexemType.ROUND_OPEN_BRACKET) {
                            result.add(s);
                            s = stack.pop();
                        }
                    }
                    else {
                        if (GetPriority(token) > GetPriority(stack.peek())) {
                            stack.add(token);
                        }
                        else {
                            while (stack.size() > 0 && GetPriority(token) <= GetPriority(stack.peek()))
                                result.add(stack.pop());
                            stack.add(token);
                        }
                    }
                }
                else
                    stack.add(token);
            }
            else
                result.add(token);
        }
        if (stack.size() > 0)
            for (Token token : stack)
                result.add(token);





        for (Token token : result) {
            System.out.print(token.getValue()+ " ");
        }
    }


    public  void returnAllOperansToResult() {
        for (Token token : operands) {
            result.add(token);
            operands.remove(token);
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