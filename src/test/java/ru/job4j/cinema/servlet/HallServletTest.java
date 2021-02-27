package ru.job4j.cinema.servlet;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.cinema.store.MemStore;
import ru.job4j.cinema.store.PsqlStore;
import ru.job4j.cinema.store.Store;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Class HallServletTest
 * Класс тестирует работу сервлета HallServlet при отправке запроса на сервер.
 * @author Dmitry Razumov
 * @version 1
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(PsqlStore.class)
public class HallServletTest {

    @Test
    public void whenPost() throws ServletException, IOException {
        Store store = MemStore.instOf();
        PowerMockito.mockStatic(PsqlStore.class);
        when(PsqlStore.instOf()).thenReturn(store);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        RequestDispatcher rd = mock(RequestDispatcher.class);
        JSONObject json = new JSONObject();
        json.put("11", "Ряд 1 Место 1");
        String places = json.toString();
        String fio = "Ivan Petrov";
        String phone = "+7-123-456-78-90";
        when(req.getParameter("places")).thenReturn(places);
        when(req.getParameter("fio")).thenReturn(fio);
        when(req.getParameter("phone")).thenReturn(phone);
        when(req.getRequestDispatcher(any(String.class))).thenReturn(rd);
        new HallServlet().doPost(req, resp);
        assertThat(store.findAccount(phone).getFio(), is(fio));
        assertThat(store.findHalls().get(0).getPlace(), is(11));
    }
}