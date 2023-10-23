import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        Path file = Path.of("src/p1.txt");
        String program = Files.readString(file);
        System.out.println(program);
    }
}