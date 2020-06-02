import java.util.List;
import java.util.Stack;

public class StackMachine {
    private final List<Token> tokens;
    private Stack<String> buffer = new Stack<>();
    private int counter = 0;

    public StackMachine(List<Token> tokens) {
        this.tokens = tokens;
    }


    public int run () {
        System.out.println("\nStarting stack machine...");
        Token token;

//        for (int i = 0; i < tokens.size(); i++) {
//            token = tokens.get(i);
//
//            if (token.getType() == LexemType.VAR)
//
//            System.out.println(token.getType());
//        }


        while (counter < tokens.size()) {
            token = tokens.get(counter);

            if (token.getType() == LexemType.VAR) {
                buffer.push(token.getValue());
            } else if (token.getType() == LexemType.DIGIT) {
                buffer.push(token.getValue());
            }
            counter++;
        }

        return 0;
    }
}
