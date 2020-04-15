import java.util.regex.Pattern;

public enum LexemType {
    KEY_IF("^if$"),
    KEY_ELSE("^else$"),
    KEY_WHILE("^while$"),
    KEY_FOR("^for$"),
    KEY_DATA_TYPE("^int$|^string$|^bool$|^void$"),
    KEY_PRINTF("^printf$"),
    VAR("[a-z]"),
    DIGIT("0|([1-9][0-9]*)"),
    ASSIGN_OP("="),
    SQUARE_OPEN_BRACKET("\\["),
    SQUARE_CLOSE_BRACKET("\\]"),
    ROUND_OPEN_BRACKET("\\("),
    ROUND_CLOSE_BRACKET("\\)"),
    FIGURE_OPEN_BRACKET("\\{"),
    FIGURE_CLOSE_BRACKET("\\}"),
    OP("\\+|-|\\*|/"),
    COMPARISION_OP("<|>"),
    DOUBLE_QUOTES("\""),
    SEMICOLON("\\;");

    private Pattern pattern;

    LexemType(String regexp) {
        this.pattern = Pattern.compile(regexp);
    }

    public Pattern getPattern () {
        return this.pattern;
    }

}
