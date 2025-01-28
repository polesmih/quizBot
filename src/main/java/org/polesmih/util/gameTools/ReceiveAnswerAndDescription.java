package org.polesmih.util.gameTools;

import org.polesmih.util.UpdateUtil;
import org.polesmih.util.pojo.Question;
import org.telegram.telegrambots.meta.api.objects.Update;

public class ReceiveAnswerAndDescription {


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

}
