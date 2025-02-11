# quizBot (java)  
Telegram-бот игра "Викторина".   

Бот в Telegram: [@ArtQuizzes_bot](https://t.me/ArtQuizzes_bot)  

[Статья на Хабр](https://habr.com/ru/articles/881332/)  
 

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

## Демонстрация функционала:  

### 1. Активация бота и меню
   
![IMG_8182](https://github.com/user-attachments/assets/9fae6987-bfae-4095-b2e8-c16c20c00a41)  
  
![file](https://github.com/user-attachments/assets/00579e36-c1bd-4efb-aa4e-ca36a0770ea8)  
  
  
  

 
  
### 2. Обработка ответов пользователя (верных и неверных). запроса статистики и ее очистка
   
![IMG_3797](https://github.com/user-attachments/assets/d1654c7b-5ed3-47ae-82cf-f51ee06f83f5)  
  

![IMG_3798](https://github.com/user-attachments/assets/137c0d50-a958-4127-917d-1d5806404029)  
  

![IMG_3802](https://github.com/user-attachments/assets/f65f8a65-1c0a-40a4-842b-527c052bab08)  
  

![IMG_3803](https://github.com/user-attachments/assets/b65a9246-ae49-4f1f-bf98-66907226e209)  
  

![IMG_3804](https://github.com/user-attachments/assets/8cc38d65-0287-4cae-b5af-d1a9f22cb7da)  
  



## Сборка и запуск бота:  
Запуск бота в среде разработки (IntelliJ IDEA) производится через запуск класса Main.java.  
Также возможно создать исполняемый jar-файл и запускать его (как в среде разработки, так и на витртуальных ресурсах). В проекте (pom.xml) реализована возможность создания такого файла через плагин Apache Assembly  
