package org.polesmih.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.polesmih.util.model.pojo.Question;

import java.util.ArrayList;
import java.util.List;

public class ParserJson {

//    получение списка объектов с вопросами и узлов в них из Json-файла
    static ObjectMapper mapper = new ObjectMapper();

    @SneakyThrows
    public static List<Question> fileToList(String fileName) {

        List<Question> questionList = new ArrayList<>();
        String jsonString = ReadingJsonFile.jsonToString(fileName);
        JsonNode jsonArray = mapper.readTree(jsonString);

//        заполнение данными объект java (класс Question)
//        из json-файла и получить список объектов java
        for (JsonNode element : jsonArray) {
            Question question = mapper.treeToValue(element, Question.class);
            questionList.add(question);
        }
        return questionList;
    }


}
