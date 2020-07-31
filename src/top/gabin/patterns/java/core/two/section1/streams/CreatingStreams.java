package top.gabin.patterns.java.core.two.section1.streams;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CreatingStreams {

    public static <T> void show(String title, Stream<T> stream) {
        final int SIZE = 10;
        List<T> firstElements = stream.limit(SIZE + 1)
                .collect(Collectors.toList());
        System.out.print(title + ":");
        String joints = firstElements.stream().map(e -> e.toString()).collect(Collectors.joining(","));
        System.out.println(joints);
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        String filePath = "../cities.txt";
        Path path = Paths.get(CountLongWords.class.getResource(filePath).toURI());
        String content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
        String regex = "\\PL+";
        Stream<String> words = Pattern.compile(regex).splitAsStream(content);
        show("work", words);
    }
}
