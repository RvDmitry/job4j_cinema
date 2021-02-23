package ru.job4j.cinema.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cinema.model.Account;
import ru.job4j.cinema.model.Hall;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Class PsqlStore
 * Класс осуществляет взаимодействие с базой данных.
 * @author Dmitry Razumov
 * @version 1
 */
public class PsqlStore implements Store {
    /**
     * Поле создает логер.
     */
    private static final Logger LOG = LoggerFactory.getLogger(PsqlStore.class.getName());

    /**
     * Поле содержит пул соединений с БД.
     */
    private final BasicDataSource pool = new BasicDataSource();

    /**
     * Конструктор создает соединение с БД.
     */
    private PsqlStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new FileReader("cinemaDB.properties")
        )) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    /**
     * Класс создает синглтон.
     */
    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }

    /**
     * Метод возвращает синглтон.
     * @return Синглтон.
     */
    public static Store instOf() {
        return Lazy.INST;
    }

    /**
     * Метод сохраняет посетителя в базу данных.
     * @param account Объект характеризующий посетителя кинотеатра.
     * @return Посетитель.
     */
    @Override
    public Account save(Account account) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "INSERT INTO accounts(fio, phone) VALUES (?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, account.getFio());
            ps.setString(2, account.getPhone());
            ps.executeUpdate();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    account.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Ошибка записи.", e);
        }
        return account;
    }

    /**
     * Метод сохраняет место в базу данных.
     * @param hall Объект характеризующий место в кинозале.
     * @return Место.
     */
    @Override
    public Hall save(Hall hall) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "INSERT INTO hall(place, accounts_id) VALUES (?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, hall.getPlace());
            ps.setInt(2, hall.getAccountsId());
            ps.executeUpdate();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    hall.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Ошибка записи.", e);
        }
        return hall;
    }

    /**
     * Метод ищет посетителя в базе данных по номеру его телефона.
     * @param phone Телефон.
     * @return Посетитель.
     */
    @Override
    public Account findAccount(String phone) {
        Account account = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("select * from accounts where phone = ?")) {
            ps.setString(1, phone);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String fio = rs.getString("fio");
                    account = new Account(fio, phone);
                    account.setId(id);
                }
            }
        } catch (Exception e) {
            LOG.error("Ошибка запроса.", e);
        }
        return account;
    }

    /**
     * Метод возвращает список купленных мест в кинозале из базы данных.
     * @return Список мест.
     */
    @Override
    public List<Hall> findHalls() {
        List<Hall> halls = new ArrayList<>();
        Hall hall = null;
        try (Connection cn = pool.getConnection();
             Statement st = cn.createStatement()) {
            ResultSet rs = st.executeQuery("select * from hall");
            while (rs.next()) {
                int id = rs.getInt("id");
                int place = rs.getInt("place");
                int accountsId = rs.getInt("accounts_id");
                hall = new Hall(place, accountsId);
                hall.setId(id);
                halls.add(hall);
            }
        } catch (Exception e) {
            LOG.error("Ошибка запроса.", e);
        }
        return halls;
    }
}
