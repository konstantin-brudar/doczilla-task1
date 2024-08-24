import java.io.*;
import java.nio.file.*;
import java.util.*;

public class TextFilesConcatenator {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("""
            Program concatenates all text files in directory to one output file
            Usage: java TextFilesConcatenator <root-dir> <out-file>
            <root-dir> - root directory containing text files
            <out-file> - path to output file
            """);
            System.exit(0);
        }

        final String ROOT_DIR = args[0];
        final String OUTPUT_FILE = args[1];
        try {
            List<String> fileList = getAllFiles(ROOT_DIR);
            Map<String, List<String>> dependencies = getDependencies(fileList);
            Graph dependenciesGraph = new Graph(dependencies);
            dependenciesGraph.print();
            concatenateFiles(fileList, OUTPUT_FILE);
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

    private static void concatenateFiles(List<String> fileList, String outputFile) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            for (String file : fileList) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        writer.write(line);
                        writer.newLine();
                    }
                }
            }
        }
    }

    private static List<String> getRequirements(String file) throws IOException {
        List<String> requires = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("require")) {
                    String path = line.substring(line.indexOf("'") + 1, line.lastIndexOf("'"));
                    requires.add(path);
                }
            }
        }
        return requires;
    }

    private static Map<String, List<String>> getDependencies(List<String> fileList) throws IOException {
        Map<String, List<String>> dependencies = new HashMap<>();
        for (String file : fileList) {
            List<String> requires = getRequirements(file);
            dependencies.put(file, requires);
        }
        return dependencies;
    }
}
