# quizBot (java)  
Telegram-бот игра "Викторина".   

Бот в Telegram: [@ArtQuizzes_bot](https://t.me/ArtQuizzes_bot)  
 

## Лицензия  
Этот проект лицензирован по лицензии MIT.  
Подробности в файле LICENSE  

## Автор  
Олеся Пономарева  

## В проекте использованы:  
Java  
MySQL  
JSON (jackson)  
GitHub - repository  
Telegram Bot API  

Полный список используемых зависимостей и версий компонентов можно найти в pom.xml  

В проект добавлена база данных для учета пользователей, которая развернута на виртуальном хостинге https://beget.com/ru.

### Демонстрация функционала:  

1. Активация бота и меню
   
![IMG_8182](https://github.com/user-attachments/assets/9fae6987-bfae-4095-b2e8-c16c20c00a41)  
  
![file](https://github.com/user-attachments/assets/00579e36-c1bd-4efb-aa4e-ca36a0770ea8)  
  
  
  

 
  
2. Обработка ответов пользователя (верных и неверных). запроса статистики и ее очистка
   
![IMG_3797](https://github.com/user-attachments/assets/584023a0-d3c5-4d9b-b2f4-b16815c1d7a8)  

![IMG_3798](https://github.com/user-attachments/assets/33fef31a-ac8a-44ef-a545-c7ac5b84af0f)  

![IMG_3802](https://github.com/user-attachments/assets/f526b93b-d3a1-4ba5-8525-eda23ca3e6bc)  

![IMG_3803](https://github.com/user-attachments/assets/3f59e286-13c0-4499-9ad6-5d32370f8663)  

![IMG_3804](https://github.com/user-attachments/assets/e0fbd174-82dd-4727-aa90-f7c719c3910e)  



### Сборка и запуск бота:  
Запуск бота в среде разработки (IntelliJ IDEA) производится через запуск класса Main.java.  
Также возможно создать исполняемый jar-файл и запускать его (как в среде разработки, так и на витртуальных ресурсах). В проекте (pom.xml) реализована возможность создания такого файла через плагин Apache Assembly  
