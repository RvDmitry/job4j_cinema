package ru.job4j.cinema.store;

import ru.job4j.cinema.model.Account;
import ru.job4j.cinema.model.Hall;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class MemStore
 * Класс реализует хранилище для посетителей и мест кинозала для тестирования логики сервлета.
 * @author Dmitry Razumov
 * @version 1
 */
public class MemStore implements Store {
    /**
     * Синглтон, создает объект хранилища.
     */
    private static final MemStore INST = new MemStore();
    /**
     * Поле генерирует идентификатор посетителя кинозала.
     */
    private static final AtomicInteger ACCOUNT_ID = new AtomicInteger();
    /**
     * Поле генерирует идентификатор места в кинозале.
     */
    private static final AtomicInteger HALL_ID = new AtomicInteger();
    /**
     * Коллекция хранит посетителей.
     */
    private final Map<Integer, Account> accounts = new ConcurrentHashMap<>();
    /**
     * Коллекция хранит места.
     */
    private final Map<Integer, Hall> halls = new ConcurrentHashMap<>();

    /**
     * Конструктор хранилища.
     */
    private MemStore() { }

    /**
     * Метод возвращает объект хранилища.
     * @return Синглтон.
     */
    public static MemStore instOf() {
        return INST;
    }

    @Override
    public Account save(Account account) {
        account.setId(ACCOUNT_ID.incrementAndGet());
        accounts.put(account.getId(), account);
        return account;
    }

    @Override
    public Hall save(Hall hall) {
        hall.setId(HALL_ID.incrementAndGet());
        halls.put(hall.getId(), hall);
        return hall;
    }

    @Override
    public Account findAccount(String phone) {
        for (var item : accounts.entrySet()) {
            if (item.getValue().getPhone().equals(phone)) {
                return item.getValue();
            }
        }
        return null;
    }

    @Override
    public List<Hall> findHalls() {
        return new ArrayList<>(halls.values());
    }
}
