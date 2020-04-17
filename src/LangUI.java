import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class LangUI {
    public static void main(String[] args) {
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
                if ((c == 59) || (c == 40) || (c == 41) || (c == 42) || (c == 43) || (c == 45) || (c == 47) || (c == 123) || (c == 125) || (c == 91) || (c == 93) || (c == 60) || (c == 61) || (c == 62))
                    file = file + " " + (char)c + " ";
                else
                    file = file + (char)c;
            }

            file = file.replaceAll("\n"," ");
            //file = file.replaceAll(";","");

            //System.out.println("");
            //System.out.println(file);

            Lexer lexer = new Lexer(file);
            List<Token> tokens = lexer.tokens();
            System.out.println("\nAmount of tokens: " + tokens.size());

            for (int i = 0; i < tokens.size(); i++) {
                Token token = tokens.get(i);
                //System.out.println(token.getType() + " `" + token.getValue() + "`");
                System.out.print("`" + token.getValue() + "` ");
            }
            System.out.println("");

            Parser parser = new Parser( lexer.tokens() );
            if (parser.lang()) {
                ReversePolisNotation rpNotation = new ReversePolisNotation(lexer.tokens());
                rpNotation.translate();
            }

        }
        catch(IOException | LangParseEsception ex){
            System.out.println("Программа упала: "  + ex.getMessage());
        }
    }
}
