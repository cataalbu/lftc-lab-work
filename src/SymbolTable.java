import java.util.ArrayList;

public class SymbolTable {
    ArrayList<ArrayList<String>> table;
    private int size = 100;

    public SymbolTable() {
        this.table = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            this.table.add(new ArrayList<>());
        }
    }
    public int hash(String key) {
        int s = 0;
        for (int i = 0; i < key.length(); i++) {
            s += key.charAt(i);
        }
        return s % size;
    }
    public int[] add(String key) {
        int hashed = hash(key);
        ArrayList<String> list = table.get(hashed);
        if(!list.contains(key))
             list.add(key);
        int index = list.indexOf(key);
        return new int[] {hashed, index};
    }

    public int[] getPosition(String key) {
        int hashed = hash(key);
        int index = table.get(hashed).indexOf(key);

        return new int[]{hashed, index};
    }

    @Override
    public String toString() {
        StringBuilder st = new StringBuilder();
        for (int i = 0; i < size; i++) {
            ArrayList<String> list = table.get(i);
            if (!list.isEmpty()) {
                st.append(i).append(": ").append(list).append("\n");
            }
        }
        return "Symbol table: \n" + st;
    }
}
