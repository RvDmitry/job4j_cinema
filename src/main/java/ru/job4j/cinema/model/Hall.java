package ru.job4j.cinema.model;

import java.util.Objects;

/**
 * Class Hall
 * Класс характеризует кинозал.
 * @author Dmitry Razumov
 * @version 1
 */
public class Hall {
    /**
     * Идентификатор места в кинозале.
     */
    private int id;
    /**
     * Номер места.
     */
    private final int place;
    /**
     * Идентификатор посетителя кинозала.
     */
    private final int accountsId;

    /**
     * Конструктор инициализирует место в кинозале.
     * @param place Номер места.
     * @param accountsId Идентификатор посетителя.
     */
    public Hall(int place, int accountsId) {
        this.place = place;
        this.accountsId = accountsId;
    }

    /**
     * Метод возвращает идентификатор места.
     * @return Идентификатор места.
     */
    public int getId() {
        return id;
    }

    /**
     * Метод задает идентификатор места.
     * @param id Идентификатор места.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Метод возвращает номер места.
     * @return Номер места.
     */
    public int getPlace() {
        return place;
    }

    /**
     * Метод возвращает идентификатор посетителя.
     * @return Идентификатор посетителя.
     */
    public int getAccountsId() {
        return accountsId;
    }

    @Override
    public String toString() {
        return "Hall{" + "id=" + id + ", place=" + place + ", accountsId=" + accountsId + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Hall hall = (Hall) o;
        return place == hall.place;
    }

    @Override
    public int hashCode() {
        return Objects.hash(place);
    }
}
