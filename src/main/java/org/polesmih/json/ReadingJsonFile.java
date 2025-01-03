package org.polesmih.json;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadingJsonFile {

//    получение данных в виде строк из Json-файла
    @SneakyThrows
    public static String jsonToString(String fileName) {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

}
