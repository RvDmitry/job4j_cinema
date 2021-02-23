package ru.job4j.cinema.model;

import java.util.Objects;

/**
 * Class Account
 * Класс характеризует посетителя кинотеатра.
 * @author Dmitry Razumov
 * @version 1
 */
public class Account {
    /**
     * Идентификатор посетителя.
     */
    private int id;
    /**
     * ФИО посетителя.
     */
    private final String fio;
    /**
     * Номер телефона посетителя.
     */
    private final String phone;

    /**
     * Конструктор инициализирует посетителя.
     * @param fio ФИО.
     * @param phone Телефон.
     */
    public Account(String fio, String phone) {
        this.fio = fio;
        this.phone = phone;
    }

    /**
     * Метод возвращает идентификатор.
     * @return Идентификатор.
     */
    public int getId() {
        return id;
    }

    /**
     * Метод задает идентификатор.
     * @param id Идентификатор.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Метод возвращает ФИО.
     * @return ФИО.
     */
    public String getFio() {
        return fio;
    }

    /**
     * Метод возвращает номер телефона.
     * @return Телефон.
     */
    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "Account{" + "id=" + id + ", fio='" + fio + '\'' + ", phone='" + phone + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account account = (Account) o;
        return Objects.equals(phone, account.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phone);
    }
}
