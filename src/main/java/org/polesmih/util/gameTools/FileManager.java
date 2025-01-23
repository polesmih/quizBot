package org.polesmih.util.gameTools;

import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileManager {

    public static Path getPathUserFile(String pathUserFile, String mark, long userId) {
        return Paths.get(pathUserFile + mark + userId + ".txt");
    }


    public static void writeToFile(String pathUserFile, String mark, Long userId, String indicator) {
        try {
            // Создание файла, конвертация String в byte-массив и запись в файл (даже если файл существует)
            Files.write(
                    getPathUserFile(pathUserFile, mark, userId),
                    indicator.getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public static void cleanFile(String pathUserFile, String mark, Long userId) {

        //The FileChannel класс обеспечивает truncate() метод, который может обрезать файл до заданного размера.
        FileChannel.open(
                getPathUserFile(pathUserFile, mark, userId),
                StandardOpenOption.WRITE).truncate(0).close();
    }


}
