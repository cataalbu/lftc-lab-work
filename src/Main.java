import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        SymbolTable symbolTable = new SymbolTable();
        symbolTable.add("a");
        int[] position = symbolTable.add("index");
        System.out.println("Position:"+ Arrays.toString(position) + "\n");
        System.out.println(symbolTable.toString());
        System.out.println("");
    }
}