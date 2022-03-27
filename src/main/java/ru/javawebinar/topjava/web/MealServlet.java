package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class MealServlet extends HttpServlet {

    private ConcurrentHashMap<Integer, Meal> meals;

    @Override
    public void init() throws ServletException {
        final Object meals = getServletContext().getAttribute("meals");
        if (!(meals instanceof ConcurrentHashMap)){
            throw new IllegalStateException("Хранилище не инициализировано");
        }

        this.meals = (ConcurrentHashMap<Integer, Meal>) meals;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameterMap().containsKey("edit") || request.getParameterMap().containsKey("add")){
            if (request.getParameter("id") != null){
                int id = Integer.parseInt(request.getParameter("id"));
                request.setAttribute("meal", meals.get(id));
            }
            request.getRequestDispatcher("/meal-add-edit.jsp").forward(request, response);
        }

        List <Meal> mealList = new ArrayList<>(this.meals.values());
        List<MealTo> mealsToList = MealsUtil.filteredByStreams(mealList, LocalTime.of(0, 0), LocalTime.of(23, 59), 2000);

        request.setAttribute("meals", mealsToList);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF8");
        if (request.getParameterMap().containsKey("delete")){
            meals.remove(Integer.parseInt(request.getParameter("id")));
            doGet(request, response);
            return;
        }
        final String description = request.getParameter("description");
        final int calories = Integer.parseInt(request.getParameter("calories"));
        final LocalDateTime localDateTime = LocalDateTime.parse(request.getParameter("datetime"));

        if (request.getParameter("id") != ""){
            int id = Integer.parseInt(request.getParameter("id"));
            Meal meal = meals.get(id);
            meal.setDescription(description);
            meal.setCalories(calories);
            meal.setDateTime(localDateTime);
        } else {
            final Meal meal = new Meal(localDateTime, description, calories);
            meals.put(meal.getId(),meal);
        }
        if (request.getParameterMap().containsKey("delete")){
            meals.remove(Integer.parseInt(request.getParameter("id")));
        }
        doGet(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        meals.values();
    }
}