package ru.job4j.cinema.store;

import org.junit.Test;
import ru.job4j.cinema.model.Account;
import ru.job4j.cinema.model.Hall;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class MemStoreTest {

    @Test
    public void whenSaveAccount() {
        String fio = "Petrov";
        String phone = "+71234567890";
        Account account = new Account(fio, phone);
        Store store = MemStore.instOf();
        store.save(account);
        assertThat(store.findAccount(phone).getFio(), is(fio));
    }

    @Test
    public void whenSaveHall() {
        Store store = MemStore.instOf();
        int place = 11;
        Hall hall = new Hall(place, 1);
        store.save(hall);
        assertThat(store.findHalls().get(0).getPlace(), is(place));
    }
}