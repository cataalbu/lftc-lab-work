import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Scanner {

    private final ArrayList<String> tokens;
    private final String[] reservedWordsEnum = {"array", "string", "int", "const", "var", "while", "if", "then", "else", "of", "program", "read", "write"};
    private final String[] operatorsEnum = {"+", "-", "*", "/",  "=", "<", "<=", "==", "<>", ">="};

    private ArrayList<Pair<String, Pair<Integer, Integer>>> pif;
    private String program;
    private int index = 0;
    private int currentLine = 1;

    private SymbolTable st;

    public Scanner() {
        tokens = new ArrayList<>();
        st = new SymbolTable();
        pif = new ArrayList<>();
        try {
            getTokens();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setProgram(String program) {
        this.program = program;
    }

    private void getTokens() throws IOException {
        File tokenFile = new File("src/token.in");
        BufferedReader buffReader = Files.newBufferedReader(tokenFile.toPath());
        String line;
        while((line = buffReader.readLine())!= null) {
            String[] words = line.split(" ");
                tokens.add(words[0]);
        }
    }

    private Boolean checkIfReservedWord() {
        return true;
    }

    private Boolean checkIfIdentifier() {
        return true;
    }

    private Boolean checkIfConstant() {
        return true;
    }


    private void scan(String fileName) {

        try {
            Path file = Path.of(fileName);
            setProgram(Files.readString(file));
            index = 0;
            currentLine = 1;
            pif = new ArrayList<>();
            st = new SymbolTable();
            while (index < program.length()) {

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}








