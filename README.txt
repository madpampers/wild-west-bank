Skip to content
This repository
Search
Pull requests
Issues
Marketplace
Explore
 @madpampers
 Sign out
 Watch 0
  Star 0  Fork 0 madpampers/wild-west-bank
 Code  Issues 0  Pull requests 0  Projects 0  Wiki  Insights  Settings
No description, website, or topics provided.
 Edit
Add topics
 8 commits
 1 branch
 0 releases
 1 contributor
 Java 44.1%	 HTML 34.5%	 Shell 11.9%	 Batchfile 9.2%	 CSS 0.3%
Java	HTML	Shell	Batchfile	CSS
Clone or download  Create new file Upload files Find file Branch: master New pull request
Latest commit 05ae0a4  5 minutes ago @madpampers  madpampers Update README.txt
src	fixed some bugs	an hour ago
.gitignore	initial commit	an hour ago
README.txt	Update README.txt	5 minutes ago
mvnw	initial commit	an hour ago
mvnw.cmd	initial commit	an hour ago
pom.xml	initial commit	an hour ago
 README.txt
Мини-проект по мотивам задания из статьи на хабре (https://habrahabr.ru/company/dataart/blog/234003/ в самом конце)

Веб-приложение, выполняющее некоторые функции банка.

В БД хранятся данные о:
    -клиентах банка
    -счетах клиентов
    -операциях со счетами

Приложение содержит:

    -список клиентов банка, с возможность добавления новых клиентов
    -страницы с подробными сведениями и клиентах, счетах, совершенных операциях со счетами
    -возможность редактирования данных клиентов
    -возможность добавления/удаления счетов
    -возможность пополнить или снять деньги со счета
    -возможность совершить перевод между счетами


Использованные технологии: Spring boot, Spring Web MVC, Spring Data.
База данных: H2.





Некоторые функции не доделаны, планировалась автоматическая конвертация валюты при переводе между счетами, новости на 
главной странице, фильтрация по клиенту, счетам или дате на странице со списком транзакций, проверки накорректность вводимых данных и пр.
Решил пока продемонстрировать как есть.