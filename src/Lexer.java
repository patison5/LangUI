import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {

    private final String rawInput;

    public Lexer(String rawInput) {
        this.rawInput = rawInput;
    }


    public List<Token> tokens() {
        List<Token> listOfTokens = new ArrayList<>();
        String[] substr = this.rawInput.split(" ");

        for (String str : substr) {
            for (LexemType lexem : LexemType.values()) {
                Matcher matcher = lexem.getPattern().matcher(str);
                boolean flag = false;

                if (matcher.find()) {
                    listOfTokens.add(new Token(lexem, str));
                    flag = true;
                }

                if (flag) break;
            }
        }
        if (listOfTokens.size() == 0)
            return Collections.emptyList();
        else
            return listOfTokens;
    }
}