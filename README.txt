    Веб-приложение, выполняющее некоторые функции банка, по мотивам задания из статьи на хабре.
(https://habrahabr.ru/company/dataart/blog/234003/ в самом конце)



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
