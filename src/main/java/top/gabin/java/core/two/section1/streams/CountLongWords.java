package top.gabin.java.core.two.section1.streams;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.regex.Pattern;

public class CountLongWords {
    public static void main(String[] args) throws IOException, URISyntaxException {
        String filePath = "../cities.txt";
        Path path = Paths.get(CountLongWords.class.getResource(filePath).toURI());
        String content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
        String regex = "\\PL+";
        String[] split = content.split(regex);
        long count = 0;
        for (String s : Arrays.asList(split)) {
            if (s.length() > 12) count++;
        }
        System.out.println(count);
        count = Pattern.compile(regex).splitAsStream(content)
                .filter(s -> s.length() > 12)
                .count();
        System.out.println(count);
    }
}
