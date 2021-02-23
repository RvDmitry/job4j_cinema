package ru.job4j.cinema.store;

import ru.job4j.cinema.model.Account;
import ru.job4j.cinema.model.Hall;

import java.util.List;

/**
 * Interface Store
 * Интерфейс определяет методы взаимодействия с хранилищем данных.
 * @author Dmitry Razumov
 * @version 1
 */
public interface Store {
    /**
     * Метод осуществляет сохранение посетителя в хранилище данных.
     * @param account Объект характеризующий посетителя кинотеатра.
     * @return Посетитель.
     */
    Account save(Account account);

    /**
     * Метод осуществляет сохранение информации о купленном месте в кинозале.
     * @param hall Объект характеризующий место в кинозале.
     * @return Место.
     */
    Hall save(Hall hall);

    /**
     * Метод осуществляет поиск посетителя кинотеатра по его телефону.
     * @param phone Телефон.
     * @return Посетитель.
     */
    Account findAccount(String phone);

    /**
     * Метод возвращает список купленных мест в кинозале из хранилища данных.
     * @return Список мест.
     */
    List<Hall> findHalls();
}
