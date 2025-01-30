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
  
![IMG_8183](https://github.com/user-attachments/assets/911ba20b-99ad-4377-87a5-a4d7b5c89ed9)  
  

 
  
2. Обработка ответов пользователя (верных и неверных) и запроса статистики
   
![IMG_8184](https://github.com/user-attachments/assets/4c309fe9-7ab4-4a1b-a26b-0892a7c84fb8)  

![IMG_8185](https://github.com/user-attachments/assets/377218ca-dcde-4467-9142-35a58304174c)  

![IMG_8186](https://github.com/user-attachments/assets/b59c8b96-2c2c-4a2b-9f5b-cc8f41984e57)  


  
  
### Сборка и запуск бота:  
Запуск бота в среде разработки (IntelliJ IDEA) производится через запуск класса Main.java.  
Также возможно создать исполняемый jar-файл и запускать его (как в среде разработки, так и на витртуальных ресурсах). В проекте (pom.xml) реализована возможность создания такого файла через плагин Apache Assembly  
