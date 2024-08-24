import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("""
            Usage: java Main <path>
            <path> - root directory with text files
            """);
            System.exit(0);
        }

        final String ROOT_DIR = args[0];
        try {
            List<String> fileList = getAllFiles(ROOT_DIR);
            fileList.forEach(System.out::println);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    private static List<String> getAllFiles(String rootDir) throws IOException {
        List<String> fileList = new ArrayList<>();
        Files.walk(Paths.get(rootDir))
            .filter(Files::isRegularFile)
            .filter(path -> path.toString().endsWith(".txt"))
            .map(Path::toString)
            .sorted()
            .forEach(fileList::add);
        return fileList;
    }
}
