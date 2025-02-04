package org.polesmih.util.model;

import lombok.SneakyThrows;
import org.polesmih.util.model.pojo.Question;

import java.lang.reflect.Field;
import java.util.Random;

public class GetFieldValue {


//    стандрартное получение значения строкового поля из объекта java
    @SneakyThrows
    public static String getFieldValue(Question question, String fieldName) {
//        получаем доступ к значению поля через рефлексию
        Field field = question.getClass().getDeclaredField(fieldName);
//        условие, позволяющее обращаться к приватным полям
        field.setAccessible(true);
//        извлекаем значение
        Object value = field.get(question);
        return String.valueOf(value);
    }

// получение поля-массива объекта java (класс Question)
    @SneakyThrows
    public static String[] getArrayOptions(Question question, String fieldName) {
//        получаем значения поля массива объекта
        Field field = question.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return (String[]) field.get(question);
    }

// получение поля-массива объекта java (класс Question) с перемешанными внутри значениями
    @SneakyThrows
    public static String[] getRandomArrayOptions(Question question, String fieldName) {
//        перемешиваем элементы мыссива и получаем новый массив
        return shuffleArr(getArrayOptions(question, fieldName));
    }

//    метод перемешивания элементов массива
    public static String[] shuffleArr(String[] array) {
        Random random = new Random();
        for (int i = array.length - 1; i >= 0; i--) {
            int index = random.nextInt(i + 1);
            String temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
        return array;
    }

}
