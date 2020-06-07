import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Parser {

    private final List<Token> tokens;
    private int counter = 0;
    private VariablesTable vTable;
    HashMap<String, Integer> varTable = new HashMap<String, Integer>();

    boolean openRoundBracketFound = false;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        vTable = new VariablesTable();
    }

    public HashMap<String, Integer> getVarTable () {
        return varTable;
    }

    public boolean lang() throws LangParseEsception {

        try {
            while(tokens.size() > counter) {
                expr();
            }

            System.out.println("######## ВСЕ ПРОШЛО УСПЕШНО ########");
            return true;
        } catch (LangParseEsception e) {
            System.out.println("######## Код не прошел проверку ########");
            System.out.println(e);
            System.out.println(tokens.size());
            System.out.println(counter);
            return false;
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

    private  void whileExpression () throws  LangParseEsception {
        KEY_WHILE();
        ROUND_OPEN_BRACKET();
        value();
        COMPARISION_OP();
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

    private void printF() throws LangParseEsception {
        KEY_PRINTF();
        ROUND_OPEN_BRACKET();
        value();
        ROUND_CLOSE_BRACKET();
        SEMICOLON();
    }

    private void functionCreation () throws LangParseEsception {
        KEY_DATA_TYPE();
        VAR();
        ROUND_OPEN_BRACKET();

        int step2 = counter;
        try {
            while (!(tokens.get(counter).getType().equals(LexemType.ROUND_CLOSE_BRACKET))){
                KEY_DATA_TYPE();
                variableValue();
                // проеврка на создание параметров
            }

            ROUND_CLOSE_BRACKET();
        } catch (LangParseEsception e) {
            counter = step2;
            ROUND_CLOSE_BRACKET();
        }
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

            //System.out.println("trying to create: " + tokens.get(step).getValue() +  " " + tokens.get(step+1).getValue());
            boolean create = vTable.addVariable(new VariablesTable.tVariable(tokens.get(step).getValue(), tokens.get(step+1).getValue()));
            varTable.put(tokens.get(step+1).getValue(), 0); // новый вариант (упрощенный)

            if (!create) {
                System.out.println("VARIABLE ALREADY EXISTS!");
                throw new LangParseEsception("VARIABLE ALREADY EXISTS!");
            }
        } catch (LangParseEsception e) {
            counter = step;
            try {
                variableAssigment();

                boolean assign = vTable.checkIfValueExist(new VariablesTable.tVariable(tokens.get(step).getValue()));

                if (!assign) {
                    System.out.println("VARIABLE doesn't EXISTS!");
                    throw new LangParseEsception("VARIABLE doesn't EXISTS!");
                }

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
                        } catch (LangParseEsception e5) {
                            counter = step;

                            whileExpression();
                        }
                    }
                }
            }
        }
    }


    private Token match() throws LangParseEsception {
        Token token = null;

        if (counter < tokens.size()) {
//            while (tokens.get(counter).getType().equals(LexemType.ROUND_OPEN_BRACKET) || tokens.get(counter).getType().equals(LexemType.ROUND_CLOSE_BRACKET))
//                counter++;

            token = tokens.get(counter);
            counter++;
        } else throw new LangParseEsception("FATAL ERROR: Закончились токены проверки...");

        return token;
    }

    private  void varValue () throws  LangParseEsception {
        int tmpCounter = counter;

        try {
            value();
            SEMICOLON();
            System.out.println("Trying to exit");
        } catch (LangParseEsception e) {
            counter = tmpCounter;
            try {
                value();
            } catch (LangParseEsception e2) {
                counter = tmpCounter;
                variableValue();
                OP();
                variableValue();
            }
        }
    }

    private  void brackets () throws  LangParseEsception {
        ROUND_OPEN_BRACKET();
        variableValue();
        ROUND_CLOSE_BRACKET();
    }

    private void skipBracket () {
//        if (tokens.get(counter).getType().equals(LexemType.ROUND_OPEN_BRACKET))
//            counter++;
//
//        if (tokens.get(counter).getType().equals(LexemType.ROUND_CLOSE_BRACKET))
//            counter++;

        while (tokens.get(counter).getType().equals(LexemType.ROUND_OPEN_BRACKET) || tokens.get(counter).getType().equals(LexemType.ROUND_CLOSE_BRACKET))
            counter++;
    }

    private void variableValue() throws LangParseEsception {

//        int step1 = counter;
//
//        try {
//            value();
//            OP();
//            int step2 = counter;
//
//            try {
//                value();
//            } catch (LangParseEsception e2) {
//                counter = step2;
//                brackets();
//            }
//        } catch (LangParseEsception e1) {
//            counter = step1;
//            value();
//        }




//        int tmpCounter = counter;
//        try {
//            ROUND_OPEN_BRACKET();
//            varValue();
//            ROUND_CLOSE_BRACKET();
//        } catch (LangParseEsception e) {
//            counter = tmpCounter;
//
//            try {
//                varValue();
//            } catch (LangParseEsception e2) {
//                System.out.println("Упало блять тут... " + e2);
//            }
//        }

        skipBracket();
        value();
        while (tokens.size() > counter) {
            skipBracket();
            if (tokens.get(counter).getType().equals(LexemType.SEMICOLON))
                break;
            skipBracket();
            OP();
            skipBracket();
            value();
        }

//        =============================================




//        int tmpCount = counter;
//
//        try {
//            value();
//        } catch (LangParseEsception e) {
//            counter = tmpCount;
//            ROUND_OPEN_BRACKET();
//            openRoundBracketFound = true;
//        }
//
//        while (tokens.size() > counter) {
//            if (tokens.get(counter).getType().equals(LexemType.SEMICOLON))
//                break;
//
//            if (openRoundBracketFound) {
//                int tmpCounter = counter;
//                try {
//                    OP();
//                } catch (LangParseEsception e) {
//                    counter = tmpCounter;
//                    ROUND_OPEN_BRACKET();
//                    openRoundBracketFound = true;
//                }
//            } else {
//                int tmpCounter = counter;
//                try {
//                    value();
//                } catch (LangParseEsception e) {
//                    counter = tmpCounter;
//                    ROUND_CLOSE_BRACKET();
//                    openRoundBracketFound = false;
//                }
//            }
//
//
//        }

    }


    public boolean checkBrackets () {
        Stack<Token> stack = new Stack<>();
        Token prevToken = tokens.get(0);
        for (Token token : tokens) {
            LexemType type = token.getType();

            if (type.equals(LexemType.ROUND_OPEN_BRACKET))
            {
                stack.push(token);
                if ((prevToken.getType().equals(LexemType.DIGIT)) || (prevToken.getType().equals(LexemType.VAR))) {
                    return false;
                }
            }
            if (type.equals(LexemType.ROUND_CLOSE_BRACKET)) {
                if (stack.size() <= 0)
                    return false;
                if (stack.peek().getType().equals(LexemType.ROUND_OPEN_BRACKET)) {
                    stack.pop();
                }
            }
            if (type.equals(LexemType.ROUND_CLOSE_BRACKET))
            {
                if (prevToken.getType().equals(LexemType.OP)) {
                    return false;
                }
            }
            prevToken = token;
        }

        if (stack.size() > 0) {
            return false;
        } else {
            return true;
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
            throw new LangParseEsception("ERROR: " + token.getType()
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
        matchToken(match(), LexemType.KEY_WHILE);
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
