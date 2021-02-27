package ru.job4j.cinema.servlet;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cinema.model.Account;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Class HallServlet
 * Сервлет осуществляет сохранение информации о купленных местах и о посетителях.
 * А также возвращает информацию о купленных местах.
 * @author Dmitry Razumov
 * @version 1
 */
@WebServlet("/hall")
public class HallServlet extends HttpServlet {
    /**
     * Поле содержит логер для записи информации в лог-файл.
     */
    private static final Logger LOG = LoggerFactory.getLogger(HallServlet.class.getName());

    /**
     * Метод возвращает информацию о купленных местах.
     * @param req Входящий запрос.
     * @param resp Исходящий запрос.
     * @throws ServletException Исключение.
     * @throws IOException Исключение.
     */
    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        List<Hall> halls = PsqlStore.instOf().findHalls();
        JSONObject json = new JSONObject();
        halls.forEach(hall -> json.put(String.valueOf(hall.getId()), hall.getPlace()));
        PrintWriter out = resp.getWriter();
        out.print(json);
        out.flush();
    }

    /**
     * Метод сохраняет информацию о купленном месте и о посетителе, который его купил.
     * @param req Входящий запрос.
     * @param resp Исходящий запрос.
     * @throws ServletException Исключение.
     * @throws IOException Исключение.
     */
    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String places = req.getParameter("places");
        String fio = req.getParameter("fio");
        String phone = req.getParameter("phone");
        LOG.info("Получены данные: места: {}, ФИО: {}, телефон: {}", places, fio, phone);
        List<String> tickets = new ArrayList<>();
        Account account = PsqlStore.instOf().findAccount(phone);
        if (account == null) {
            account = PsqlStore.instOf().save(new Account(fio, phone));
        }
        JSONObject json = new JSONObject(places);
        for (var place : json.keySet()) {
            Hall hall = PsqlStore.instOf().save(new Hall(Integer.parseInt(place), account.getId()));
            if (hall.getId() == 0) {
                LOG.info("Место {} уже было забронировано.", place);
                tickets.add(json.getString(place)
                        + " - уже кто-то забронировал! Выберите другое.");
            } else {
                LOG.info("Место {} забронировано посетителем {}", hall, account);
                tickets.add(json.getString(place)
                        + " - забронировано успешно!");
            }
        }
        req.setAttribute("tickets", tickets);
        req.getRequestDispatcher("/result.jsp").forward(req, resp);
    }
}
