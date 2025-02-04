package org.polesmih.util.model;

import org.polesmih.util.UpdateUtil;
import org.polesmih.util.model.pojo.Question;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;

public class ReceiveOptAnsDesc {

    public static String receiveAnswer(Update update, String jsonFileName,
                                       String pathUserFile, String mark) {

        long userId = UpdateUtil.getUserFromUpdate(update).getId();

        Question currentQuestion = GetObject.getQuestionOnLastLineFromUserFile(jsonFileName,
                pathUserFile, mark, userId);

        return GetFieldValue.getFieldValue(currentQuestion, "answer");
    }

    public static String receiveDescription(Update update, String jsonFileName,
                                            String pathUserFile, String mark) {

        long userId = UpdateUtil.getUserFromUpdate(update).getId();

        Question currentQuestion = GetObject.getQuestionOnLastLineFromUserFile(jsonFileName,
                pathUserFile, mark, userId);

        return GetFieldValue.getFieldValue(currentQuestion, "description");
    }

    public static String receiveOptions(Update update, String jsonFileName,
                                            String pathUserFile, String mark) {

        long userId = UpdateUtil.getUserFromUpdate(update).getId();

        Question currentQuestion = GetObject.getQuestionOnLastLineFromUserFile(jsonFileName,
                pathUserFile, mark, userId);

        String[] options = GetFieldValue.getRandomArrayOptions(currentQuestion, "options");

        return Arrays.toString(options);
    }

}
