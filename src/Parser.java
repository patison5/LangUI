import java.util.List;

public class Parser {

    private final List<Token> tokens;
    private int counter = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public void lang() throws LangParseEsception {
        while(tokens.size() > counter) {
            expr();
        }
    }

    private void expr() throws LangParseEsception {
        value_expr();
    }

    private  void variableCreation() throws LangParseEsception {
        KEY_DATA_TYPE();
        VAR();
        ASSIGN_OP();
        variableValue();
        SEMICOLON();
    }
    private  void variableAssigment() throws LangParseEsception {
        VAR();
        ASSIGN_OP();
        variableValue();
        SEMICOLON();
    }

    private void printF() throws LangParseEsception {
        KEY_PRINTF();
        ROUND_OPEN_BRACKET();
        value();
        ROUND_CLOSE_BRACKET();
        SEMICOLON();
    }

    private void forLoop () throws LangParseEsception {
        KEY_FOR();
        ROUND_OPEN_BRACKET();

        variableCreation();

        VAR();
        COMPARISION_OP();
        value();
        SEMICOLON();

        //вот тут поправить... все же хочу i = i + 1;
        VAR();
        ASSIGN_OP();
        value();
        ROUND_CLOSE_BRACKET();

        FIGURE_OPEN_BRACKET();
        int step2 = counter;
        try {
            while (!(tokens.get(counter).getType().equals(LexemType.FIGURE_CLOSE_BRACKET)))
                value_expr();

            FIGURE_CLOSE_BRACKET();
        } catch (LangParseEsception e) {
            counter = step2;
            FIGURE_CLOSE_BRACKET();
        }
    }

    private void ifCondition() throws LangParseEsception {
        KEY_IF();
        ROUND_OPEN_BRACKET();
        value();
        int step1 = counter;

        // сделать поддержку `>=` и `<=`
        try {
            COMPARISION_OP();
        } catch (LangParseEsception e) {
            counter = step1;
            ASSIGN_OP();
            ASSIGN_OP();
        }
        value();
        ROUND_CLOSE_BRACKET();
        FIGURE_OPEN_BRACKET();
        int step2 = counter;

        try {
            while (!(tokens.get(counter).getType().equals(LexemType.FIGURE_CLOSE_BRACKET)))
                value_expr();

            FIGURE_CLOSE_BRACKET();
        } catch (LangParseEsception e) {
            counter = step2;
            FIGURE_CLOSE_BRACKET();
        }
    }

    private  void value_expr () throws LangParseEsception {
        int step = counter;

        try {
            variableCreation();
        } catch (LangParseEsception e) {
            counter = step;
            try {
                variableAssigment();
            } catch (LangParseEsception e2) {
                counter = step;
                try {
                    ifCondition();
                } catch (LangParseEsception e3) {
                    counter = step;
                    try {
                        forLoop();
                    } catch (LangParseEsception e4){
                        counter = step;
                        try {
                            printF();
                        } catch (LangParseEsception e5){
                            System.out.println(e5);
                        }
                    }
                }
            }
        }
    }


    private Token match() throws LangParseEsception {
        Token token = null;

        if (counter < tokens.size()) {
            token = tokens.get(counter);
            counter++;
        } else throw new LangParseEsception("FATAL ERROR: Закончились токены проверки...");

        return token;
    }


    private void variableValue() throws LangParseEsception {
        value();

        while (tokens.size() > counter) {
            if (tokens.get(counter).getType().equals(LexemType.SEMICOLON))
                break;

            OP();
            value();
        }
    }

    private void value() throws LangParseEsception {
        int newCount = counter;

        try {
            VAR();
        } catch (LangParseEsception e){
            counter = newCount;
            DIGIT();
        }
    }

    private void matchToken(Token token, LexemType type) throws LangParseEsception {
        if (!token.getType().equals(type)) {
            throw new LangParseEsception("FATAL ERROR: " + type
                    + " expected but "
                    + token.getType().name() + ": '" + token.getValue()
                    + "' found");
        }
    }

    private void VAR() throws LangParseEsception {
        matchToken(match(), LexemType.VAR);
    }
    private void KEY_DATA_TYPE() throws LangParseEsception {
        matchToken(match(), LexemType.KEY_DATA_TYPE);
    }
    private void KEY_IF() throws LangParseEsception {
        matchToken(match(), LexemType.KEY_IF);
    }
    private void ROUND_OPEN_BRACKET() throws LangParseEsception {
        matchToken(match(), LexemType.ROUND_OPEN_BRACKET);
    }
    private void ROUND_CLOSE_BRACKET() throws LangParseEsception {
        matchToken(match(), LexemType.ROUND_CLOSE_BRACKET);
    }
    private void FIGURE_OPEN_BRACKET() throws LangParseEsception {
        matchToken(match(), LexemType.FIGURE_OPEN_BRACKET);
    }
    private void FIGURE_CLOSE_BRACKET() throws LangParseEsception {
        matchToken(match(), LexemType.FIGURE_CLOSE_BRACKET);
    }
    private void COMPARISION_OP() throws LangParseEsception {
        matchToken(match(), LexemType.COMPARISION_OP);
    }
    private void SEMICOLON() throws LangParseEsception {
        matchToken(match(), LexemType.SEMICOLON);
    }
    private void KEY_FOR() throws LangParseEsception {
        matchToken(match(), LexemType.KEY_FOR);
    }
    private void KEY_WHILE() throws LangParseEsception {
        matchToken(match(), LexemType.KEY_FOR);
    }
    private void KEY_ELSE() throws LangParseEsception {
        matchToken(match(), LexemType.KEY_FOR);
    }
    private void DIGIT() throws LangParseEsception {
        matchToken(match(), LexemType.DIGIT);
    }
    private void OP() throws LangParseEsception {
        matchToken(match(), LexemType.OP);
    }
    private void ASSIGN_OP() throws LangParseEsception {
        matchToken(match(), LexemType.ASSIGN_OP);
    }
    private void KEY_PRINTF() throws LangParseEsception {
        matchToken(match(), LexemType.KEY_PRINTF);
    }
    private void DOUBLE_QUOTES() throws LangParseEsception {
        matchToken(match(), LexemType.DOUBLE_QUOTES);
    }


}
