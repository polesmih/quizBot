package org.polesmih.util;

import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileManager {

    public static Path getPathUserFile(String pathUserFile, long userId) {
        return Paths.get(pathUserFile + userId + ".txt");
    }

    @SneakyThrows
    public static void createFile(String pathUserFile, Long userId) {
        File userFile = new File(pathUserFile + userId + ".txt");
        if (!userFile.exists()) {
            userFile.createNewFile();
        } else {
            cleanFile(pathUserFile, userId);
        }
    }

    public static void writeToFile(String pathUserFile, Long userId, String userAnswer) {
        try {
            // Создание файла, конвертация String в byte-массив и запись в файл (даже если файл существует)
            Files.write(
                    getPathUserFile(pathUserFile, userId),
                    userAnswer.getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public static void cleanFile(String pathUserFile, Long userId) {

        //The FileChannel класс обеспечивает truncate() метод, который может обрезать файл до заданного размера.
        FileChannel.open(
                getPathUserFile(pathUserFile, userId),
                StandardOpenOption.WRITE).truncate(0).close();
    }


}
