import com.sun.source.doctree.SystemPropertyTree;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Scanner {

    private final ArrayList<String> predefinedTokens;
    private ArrayList<Pair<String, Pair<Integer, Integer>>> pif;
    private String program;
    private int currentLine = 1;

    private SymbolTable st;

    public Scanner() {
        predefinedTokens = new ArrayList<>();
        st = new SymbolTable();
        pif = new ArrayList<>();
        try {
            getPredefinedTokens();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setProgram(String program) {
        this.program = program;
    }

    private void getPredefinedTokens() throws IOException {
        File tokenFile = new File("src/token.in");
        BufferedReader buffReader = Files.newBufferedReader(tokenFile.toPath());
        String line;
        while((line = buffReader.readLine())!= null) {
            String[] words = line.split(" ");
            predefinedTokens.add(words[0]);
        }
    }

    private boolean isPredefinedToken(String token) {
        return predefinedTokens.contains(token);
    }

    private boolean isIntegerConstant(String token) {
            return token.matches("^0|[+-]?[1-9][0-9]*$");
    }

    private boolean isStringConstant(String token) {
        return token.matches("^\"[A-Za-z0-9]*\"$");
    }

    private boolean isIdentifier(String token) {
        return token.matches("^[A-Za-z][A-Za-z0-9]*$");
    }

    private void tokenize(String line) throws Exception {
        //               "string"  == <= >=    any of these          for signed numbers   alphanumeric  any character  any whitespace
        String regex = "\"[^\"]*\"|<>|==|<=|>=|[-+*/=<>,:(){}\"]|(?<=[=(])\\s*[-+]?\\d+|\\b[A-Za-z0-9]+\\b|.|\\S";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        List<String> lineTokens = new ArrayList<>();

        while (matcher.find()) {
            String token = matcher.group();
            token = token.trim();
            lineTokens.add(token);
        }

        for(var lineToken: lineTokens) {
            if(lineToken.length() == 0) {
                continue;
            }
            if(isPredefinedToken(lineToken)) {
                pif.add(new Pair<>(lineToken, new Pair<>(-1, -1)));
                continue;
            }

            if(isIntegerConstant(lineToken)) {
                var pos = st.getPosition(lineToken);
                if(pos[1] == -1) {
                    pos = st.add(lineToken);
                }
                pif.add(new Pair<>("const", new Pair<>(pos[0], pos[1])));
                continue;
            }

            if(isStringConstant(lineToken)) {
                var pos = st.getPosition(lineToken);
                if(pos[1] == -1) {
                    pos = st.add(lineToken);
                }
                pif.add(new Pair<>("const", new Pair<>(pos[0], pos[1])));
                continue;
            }

            if(isIdentifier(lineToken)) {
                var pos = st.getPosition(lineToken);
                if(pos[1] == -1) {
                    pos = st.add(lineToken);
                }
                pif.add(new Pair<>("id", new Pair<>(pos[0], pos[1])));
                continue;
            }
            System.out.println(lineToken);
            throw new Exception("Lexical error at line " + currentLine);
        }
    }

    public void scan(String fileName) {
        try {
            Path file = Path.of(fileName);
            setProgram(Files.readString(file));
            currentLine = 1;
            String[] lines = program.split("\n");
            pif = new ArrayList<>();
            st = new SymbolTable();
            for(var line: lines) {
                tokenize(line);
                currentLine++;
            }
            FileWriter fileWriter = new FileWriter("src/pif.out");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for(var element: pif) {
                bufferedWriter.write(element.toString() + "\n");
            }
            bufferedWriter.close();
            File stFile = new File("src/st.out");
            Files.writeString(stFile.toPath(), "");
            fileWriter = new FileWriter("src/st.out");
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(st.toString() + "\n");
            bufferedWriter.close();
            System.out.println("Lexical correct");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}








