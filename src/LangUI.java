import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class LangUI {
    public static void main(String[] args) {

        if (false) {
            HashSet hashSet = new HashSet();
            hashSet.add("vishal", 20);
            hashSet.add("vishal", 25);
            hashSet.add("vihal", 20);

            System.out.println("['vishal']: " + hashSet.getByKey("vishal"));
            System.out.println("['vihal']: " + hashSet.getByKey("vihal"));
            return;
        }

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
                for (int i = 0; i < tk.size(); i++) {
                    System.out.println(tk.get(i).getValue() + " ");
                }

                StackMachine machine = new StackMachine(tk);
                machine.setVarTable(parser.getVarTable());
                machine.setMarksPosiions(rpNotation.getmMrksPosiions());
                machine.run();
            }

        }
        catch(IOException | LangParseEsception ex){
            System.out.println("Программа упала: "  + ex.getMessage());
        }
    }
}
