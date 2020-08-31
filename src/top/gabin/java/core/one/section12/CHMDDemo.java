package top.gabin.java.core.one.section12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CHMDDemo {
    public static ConcurrentHashMap<String, Long> concurrentHashMap = new ConcurrentHashMap<>();

    public static void process(Path path) {
        try (Scanner scanner = new Scanner(path)){
            while (scanner.hasNext()) {
                String word = scanner.next();
                concurrentHashMap.merge(word, 1L, Long::sum);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Set<Path> getAllPaths(Path rootPath) throws IOException {
        try (Stream<Path> walk = Files.walk(rootPath)){
            return walk.filter(path -> path.getFileName().toString().endsWith(".java")).collect(Collectors.toSet());
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        int processors = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(processors);

        Path rootPath = Paths.get("/Users/linjiabin/IdeaProjects/designPatterns");
        Set<Path> allPaths = getAllPaths(rootPath);
        for (Path path : allPaths) {
            executorService.execute(()-> process(path));
        }
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.MINUTES);

        concurrentHashMap.forEach((key, value) -> System.out.println(key + ":" + value));
    }

}
