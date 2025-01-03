package org.polesmih.util;

import lombok.SneakyThrows;
import org.polesmih.bot.settings.ConfigSettings;
import org.polesmih.json.ParserJson;
import org.polesmih.util.pojo.Question;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GetObject {


    private final static ConfigSettings settings = ConfigSettings.getInstance();


    //    получение случайного объекта из коллекции объектов json
    public static Question getRandomObject(String fileName) {
            List<Question> questionList = ParserJson.fileToList(fileName);
            Random random = new Random();
            return questionList.get(random.nextInt(questionList.size()));
    }

    @SneakyThrows
    public static Question getUniqueRandomObject(String jsonFileName, String gameQuestionFileName, Long userId) {

// список объектов из json-файла
        List<Question> questionList = ParserJson.fileToList(jsonFileName);

// список строк с вопросами из файла пользователя
        List<String> gameQuestionList = Files.readAllLines(Paths.get(gameQuestionFileName));

// создание списка объектов только с теми вопросами, которых еще не было
        List<Question> uniqueQuestionList = questionList
                .stream()
                .filter(q -> !gameQuestionList.contains(q.getQuestion()))
                .collect(Collectors.toList());

        Question uniqueQuestion = null;

        if (uniqueQuestionList.size() != 0) {

            uniqueQuestion = uniqueQuestionList
                    .stream()
                    .skip(new Random().nextInt(uniqueQuestionList.size()))
                    .findFirst()
                    .orElse(null);

        } else {
            FileManager.cleanFile(gameQuestionFileName, userId);
        }

        return uniqueQuestion;
    }

//
//    @SneakyThrows
//    public static List<Question> getUniqueQuestionList (String jsonFileName, String gameQuestionFileName) {
//
//// список объектов из json-файла
//        List<Question> questionList = ParserJson.fileToList(jsonFileName);
//
//// список строк с вопросами из файла пользователя
//        List<String> gameQuestionList = Files.readAllLines(Paths.get(gameQuestionFileName));
//
//// создание списка объектов только с теми вопросами, которых еще не было
//        List<Question> uniqueQuestionList = questionList
//                .stream()
//                .filter(q -> !gameQuestionList.contains(q.getQuestion()))
//                .collect(Collectors.toList());
//
//        return uniqueQuestionList;
//    }
//
//
//


}
