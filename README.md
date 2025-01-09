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

В проект добавлена база данных для учета результатов пользователей, которая развернута на виртуальном хостинге https://beget.com/ru.

### Демонстрация функционала:  

1. Активация бота и меню
   
![IMG_8039](https://github.com/user-attachments/assets/8c5aaf1b-6440-4c92-8ff9-f7336da14762)  

![IMG_8040](https://github.com/user-attachments/assets/5a8b686d-19c6-4d4c-b9f2-2bd53721d950)  

![IMG_8043](https://github.com/user-attachments/assets/79f06a55-f4c1-4e65-aba1-4f47c9509b4d)  

 
  
2. Обработка ответов пользователя
   
![IMG_8041](https://github.com/user-attachments/assets/788be097-12ce-4696-b0d3-a0a793f35c16)  

![IMG_8042](https://github.com/user-attachments/assets/b2b08f90-0e4a-4735-9d1e-8dc2ffcaf5a4)  

  
  
### Сборка и запуск бота:  
Запуск бота в среде разработки (IntelliJ IDEA) производится через запуск класса Main.java.  
Также возможно создать исполняемый jar-файл и запускать его (как в среде разработки, так и на витртуальных ресурсах). В проекте (pom.xml) реализована возможность создания такого файла через плагин Apache Assembly  
