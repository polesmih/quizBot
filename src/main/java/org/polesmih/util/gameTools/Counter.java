package org.polesmih.util.gameTools;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Counter {

    @SneakyThrows
    public static List<String> createUserLinesAnswers(String pathUserAnswerFile, String mark, long userId) {
        return Files.readAllLines(FileManager.getPathUserFile(pathUserAnswerFile, mark, userId));
    }

    public static long countRightUserAnswer(String pathUserAnswerFile, String mark, long userId) {
        return createUserLinesAnswers(pathUserAnswerFile, mark, userId).stream()
                .map(str -> str.charAt(0))
                .filter(Character::isUpperCase)
                .count();
    }

    public static long countAllUserAnswer(String pathUserAnswerFile, String mark, long userId) {
        return createUserLinesAnswers(pathUserAnswerFile, mark, userId).stream()
                .map(str -> str.charAt(0))
                .count();
    }

    public static double statistic (String pathUserAnswerFile, String mark, long userId) {
        return (((double) countRightUserAnswer(pathUserAnswerFile, mark, userId) /
                countAllUserAnswer(pathUserAnswerFile, mark, userId)) * 100);
    }


}
