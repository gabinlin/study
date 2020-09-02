package top.gabin.java.core.one.section12;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Stream;

public class BlockQueueTest {

    private static final int QUEUE_SIZE = 10;
    private static final int SEARCH_THREADS = 100;
    private static final Path DUMMY = Paths.get("");
    private static BlockingQueue<Path> queue = new ArrayBlockingQueue(QUEUE_SIZE);

    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            System.out.println("请输入一个文件目录");
            String directory = in.nextLine();
            System.out.println("请输入搜索的关键字");
            String keyword = in.nextLine();

            Runnable emuFiles = () -> {
                emuFile(Paths.get(directory));
                try {
                    queue.put(DUMMY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(queue.size());
            };
            new Thread(emuFiles).start();

            for (int i = 0; i < SEARCH_THREADS; i++) {
                Runnable search = () -> {
                    boolean done = false;
                    while (!done) {
                        try {
                            Path file = queue.take();
                            if (file == DUMMY) {
                                queue.add(file);
                                done = true;
                            } else {
                                searchFile(file, keyword);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                new Thread(search).start();
            }
        }
    }

    private static void searchFile(Path file, String keyword) {
        try (Scanner in = new Scanner(file, String.valueOf(StandardCharsets.UTF_8))) {
            int lineNumber = 0;
            while (in.hasNextLine()) {
                String nextLine = in.nextLine();
                lineNumber++;
                if (nextLine.contains(keyword)) {
                    System.out.println(String.format("文件：%s 第%d包含关键词", file.getFileName(), lineNumber));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void emuFile(Path path) {
        if (Files.isDirectory(path)) {
            try {
                Stream<Path> list = Files.list(path);
                list.forEach(path1 -> emuFile(path1));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                queue.put(path);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
