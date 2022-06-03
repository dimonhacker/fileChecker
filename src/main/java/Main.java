import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    static String nameOfRootPath = "D:/Program Files/7-Zip";


    public static void main(String[] args) {

        Path rootPath = Paths.get(nameOfRootPath);
        boolean flag = Files.exists(rootPath);
        if (!flag) {
            System.out.println("Is not a directory");
            return;
        }

        List<Path> pathList;
        try {
            pathList = getAllFiles(rootPath, "txt");
            if (pathList == null) return;
            Comparator<Path> comparator = Comparator.comparing(Path::getFileName);
            Collections.sort(pathList, comparator);
            String res = writeResultFile(pathList, nameOfRootPath);
            System.out.printf("Done: %s", res);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Path> getAllFiles(Path path, String extension) throws IOException {
        List<Path> allFiles;
        try (Stream<Path> pathStream = Files.walk(path)) {
            allFiles = pathStream
                    .filter(Files::isRegularFile)
                    .filter(f -> f.toFile().getAbsolutePath().endsWith(extension))
                    .collect(Collectors.toList());
        }
        return allFiles;
    }

    private static String writeResultFile(List<Path> pathList, String resultPath) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        if (!resultPath.endsWith("/")) resultPath += "/";
        for (Path p : pathList) {
            Stream<String> str = Files.lines(p);
            str.forEach(stringBuilder::append);
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(resultPath + "resultFileFromListFiles.txt"));
        writer.append(stringBuilder.toString());
        writer.close();
        return resultPath + "resultFileFromListFiles.txt";
    }


}
