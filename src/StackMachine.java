import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class StackMachine {
    private final List<Token> tokens;
    private Stack<String> buffer = new Stack<>();
    private int counter = 0;

    int a,b,c;


    HashMap<String, Integer> varTable;
    Map<String, Integer> marksPosiions = new HashMap<String, Integer>();

    public StackMachine(List<Token> tokens) {
        this.tokens = tokens;
    }


    public void setVarTable (HashMap<String, Integer> table) {
        this.varTable = table;
    }
    public void setMarksPosiions (Map<String, Integer> marks) {
        this.marksPosiions = marks;
    }


    public int run () {
        System.out.println("\nStarting stack machine...");
        Token token;

        debugTable();
        debugMark();

        while (counter < tokens.size()) {
            token = tokens.get(counter);

            System.out.println(token.getType());

            if (token.getType() == LexemType.VAR) {
                buffer.push(token.getValue());
            } else if (token.getType() == LexemType.DIGIT) {
                buffer.push(token.getValue());
            } else if (token.getType() == LexemType.OP) {
                System.out.println("##### checking #####");
                operation(token.getValue());
            }
            counter++;
        }

        return 0;
    }


    private void operation(String op) {
        System.out.println(buffer.peek());
        a = getVarFromTable(buffer.pop());
        b = getVarFromTable(buffer.pop());

        switch (op) {
            case "+":
                c = a + b;
                break;
            case "-":
                c = a - b;
                break;
            case "/":
                c = a / b;
                break;
            case "*":
                c = a * b;
                break;
        }

        System.out.println("OPERATION: " + String.valueOf(c));
        buffer.push(String.valueOf(c));
    }

    private int getVarFromTable (String value) {
        return varTable.get(value);
    }


    private void debugTable() {
        System.out.println("");
        System.out.printf("%-15s%-10s%n", "переменная", "значение");
        for (Map.Entry entry : varTable.entrySet()) {
            // Выводим имя поля
            System.out.printf("%-11s", entry.getKey());
            // Выводим значение поля
            System.out.printf("%5s%n", entry.getValue());
        }
        System.out.println();
    }
    private void debugMark() {
        System.out.println("");
        System.out.printf("%-10s%-10s%n", "метка", "значение");
        for (Map.Entry entry : marksPosiions.entrySet()) {
            // Выводим имя поля
            System.out.printf("%-7s", entry.getKey());
            // Выводим значение поля
            System.out.printf("%5s%n", entry.getValue());
        }
        System.out.println();
    }
}
