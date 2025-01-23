package org.polesmih.util.gameTools;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.polesmih.json.ParserJson;
import org.polesmih.util.pojo.Question;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GetObject {


//    получение случайного объекта из коллекции объектов json
    public static Question getRandomObject(String fileName) {
            List<Question> questionList = ParserJson.fileToList(fileName);
            Random random = new Random();
            return questionList.get(random.nextInt(questionList.size()));
    }



// получение случайного уникального (не повторяющегося) объекта из коллекции объектов json
    @SneakyThrows
    public static Question getUniqueRandomObject(String jsonFileName, String pathUserFile, String mark, Long userId) {

// список объектов из json-файла
        List<Question> questionList = ParserJson.fileToList(jsonFileName);

// список строк с id-вопросов из файла пользователя
        List<String> gameQuestionList = Files.readAllLines(FileManager.getPathUserFile(pathUserFile, mark, userId));

// создание списка объектов только с теми вопросами, которых еще не было (фильтрация по id)
        List<Question> uniqueQuestionList = questionList
                .stream()
                .filter(q -> !gameQuestionList.contains(q.getId()))
                .collect(Collectors.toList());

        Question uniqueQuestion;

// если список объектов с вопросами, которых еще не было, не пустой, выбираем из него случайный объект
        if (uniqueQuestionList.size() > 0) {
            uniqueQuestion = uniqueQuestionList
                    .stream()
                    .skip(new Random().nextInt(uniqueQuestionList.size()))
                    .findFirst()
                    .orElse(null);

// если все вопросы уже были - очищаем файл и при этом выдаем случайный объект из списка объектов json-файла
        } else {
            FileManager.cleanFile(pathUserFile, mark, userId);
            uniqueQuestion = getRandomObject(jsonFileName);

        }
        return uniqueQuestion;
    }


// получение последней строки с № id из файла пользователя с вопросами
    @SneakyThrows
    public static String getLastLineFromUserFile (String pathUserFile, String mark, long userId) {

        LineIterator lineIterator = FileUtils.lineIterator(
                new File(FileManager.getPathUserFile(pathUserFile, mark, userId).toUri()), "UTF-8");
        String lastLine = "";
        while (lineIterator.hasNext()) {
            lastLine = lineIterator.nextLine();
        }
        return lastLine;

    }

//// получение объекта с параментрами id, взятого из последней строки файла пользователя с вопросами
//    public static Question getQuestionOnLastLineFromUserFile(String jsonFileName,
//                                                             String pathUserFile, String mark, long userId) {
//
//        List<Question> questionList = ParserJson.fileToList(jsonFileName);
//        String id = getLastLineFromUserFile(pathUserFile, mark, userId);
//
//        return questionList.stream()
//                .filter(q -> q.getId().equals(id))
//                .findAny()
//                .orElse(null);
//    }


}
