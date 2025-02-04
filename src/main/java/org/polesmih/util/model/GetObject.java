package org.polesmih.util.model;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.polesmih.json.ParserJson;
import org.polesmih.bot.settings.FileManager;
import org.polesmih.util.model.pojo.Question;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GetObject {


//    получение случайного объекта из коллекции объектов json
    public static Question getRandomObject(String jsonFileName) {
            List<Question> questionList = ParserJson.fileToList(jsonFileName);
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
        if (uniqueQuestionList.size() == 0) {
            FileManager.cleanFile(pathUserFile, mark, userId);
            uniqueQuestion = getRandomObject(jsonFileName);

// если все вопросы уже были - очищаем файл и при этом выдаем случайный объект из списка объектов json-файла
        } else {

            uniqueQuestion = uniqueQuestionList
                    .stream()
                    .skip(new Random().nextInt(uniqueQuestionList.size()))
                    .findFirst()
                    .orElse(null);
        }
        return uniqueQuestion;
    }


// получение последней строки с № id из файла пользователя с вопросами
    @SneakyThrows
    public static String getLastLineFromUserFile(String pathUserFile, String mark, long userId) {

        LineIterator lineIterator = FileUtils.lineIterator(
                new File(FileManager.getPathUserFile(pathUserFile, mark, userId).toUri()), "UTF-8");
        String lastLine = "";
        while (lineIterator.hasNext()) {
            lastLine = lineIterator.nextLine();
        }
        return lastLine;

    }

// получение объекта с параментрами id, взятого из последней строки файла пользователя с вопросами
    public static Question getQuestionOnLastLineFromUserFile(String jsonFileName,
                                                             String pathUserFile, String mark, long userId) {

        List<Question> questionList = ParserJson.fileToList(jsonFileName);
        String id = getLastLineFromUserFile(pathUserFile, mark, userId);

        return questionList.stream()
                .filter(q -> q.getId().equals(id))
                .findAny()
                .orElse(null);
    }

//
//    public static String allElementsAllObjectsToString(String fileName) {
//        List<Question> questionList = new ArrayList<Question>(ParserJson.fileToList(fileName));
//
//        return questionList.stream()
//                .map(Question :: toString)
//                .collect(Collectors.toList()).toString();
//    }
//
//    public static String getOptionsList (String jsonFileName) {
//        List<Question> questionsList = ParserJson.fileToList(jsonFileName);
//
//// получаем список всех значений полей-массивов ответов всех объектов json-файла
//        List<String[]> optionsListAllQuestions = questionsList
//                .stream()
//                .map(Question::getOptions)
//                .collect(Collectors.toList());
//
//// возвращаем список значений в виде строки, где варианты ответа разделены запятой
//        return optionsListAllQuestions
//                .stream()
//                .map(Object :: toString)
//                .collect(Collectors.joining(", "));
//    }


}
