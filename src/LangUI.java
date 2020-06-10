import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class LangUI {
    public static void main(String[] args) {
        System.out.println();
        String file = "";
        try {
            File myObj = new File("myProgram.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                //System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try(FileReader reader = new FileReader("myProgram.txt"))
        {
            // читаем посимвольно
            int c;
            while((c=reader.read())!=-1){
                    file = file + (char)c;
            }

            file = file.replaceAll("\n","");

            Lexer lexer = new Lexer(file);
            List<Token> tokens = lexer.getTokens();

//            System.out.println("\nAmount of tokens: " + tokens.size());

            for (int i = 0; i < tokens.size(); i++) {
                Token token = tokens.get(i);
                String a = token.getValue();
//                System.out.print("`" +  a + "` ");
                //System.out.print(" " + token.getValue());
            }
//            System.out.println("");

            Parser parser = new Parser( lexer.getTokens() );
            if (parser.lang()) {

                if (!parser.checkBrackets()) {
                    System.out.println("ERROR IN BRACKETS!");
                }

                ReversePolisNotation rpNotation = new ReversePolisNotation(lexer.getTokens());
                List<Token> tk = rpNotation.translate();
                StackMachine machine = new StackMachine(tk);
                machine.setVarTable(parser.getVarTable());
                machine.setMarksPosiions(rpNotation.getmMrksPosiions());
                machine.run();

                LinkedList list = new LinkedList();
                list.add(15);
                list.add(16);
                list.add(17);
                list.add(18);

                System.out.println("0 : " + list.getByIndex(0));
                System.out.println("1 : " + list.getByIndex(1));
                System.out.println("2 : " + list.getByIndex(2));
                System.out.println("4 : " + list.getByIndex(4));
            }

        }
        catch(IOException | LangParseEsception ex){
            System.out.println("Программа упала: "  + ex.getMessage());
        }
    }
}
