import java.util.ArrayList;
import java.util.List;

public class ReversePolisNotation {
    private final List<Token> tokens;
    public List<Token> operands = new ArrayList<>();
    public List<Token> variables = new ArrayList<>();
    public List<Token> result = new ArrayList<>();;

    public ReversePolisNotation(List<Token> tokens) {
        this.tokens = tokens;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void translate () {
        for(Token token : tokens) {
            //System.out.println(token.getValue());
        }
    }
}


// перевод в обратную польскую запись
// int i = a + 12;
// int, i, a, 12, +, =


// if (a == b) {
//   int k = 12;
// }
// int b = 13;


//a, b, ==, if, {, int, k, 12, =, }, int, b, 13, =