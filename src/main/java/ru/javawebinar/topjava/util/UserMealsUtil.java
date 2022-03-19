package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 1500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 1410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(12, 0), LocalTime.of(20, 1), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(12, 0), LocalTime.of(20, 1), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> caloriesInDay = new HashMap<>();
        for(UserMeal meal : meals) {
            LocalDate mealDateTime = meal.getDateTime().toLocalDate();
            LocalTime mealTime = meal.getDateTime().toLocalTime();
            if (mealTime.compareTo(startTime) >= 0 && endTime.compareTo(mealTime) > 0){
                caloriesInDay.merge(mealDateTime, meal.getCalories(), Integer::sum);
            }
        }

        List<UserMealWithExcess> mealWithExcess  = new ArrayList<>();
        for (UserMeal meal : meals) {
            LocalTime mealTime = meal.getDateTime().toLocalTime();
            if (mealTime.compareTo(startTime) >= 0 && endTime.compareTo(mealTime) > 0){
                mealWithExcess.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), caloriesInDay.get(meal.getDateTime().toLocalDate()) > caloriesPerDay));
            }
        }

        return mealWithExcess;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        Map <LocalDate, Integer> mealWithExcess = meals.stream()
                .filter(x -> x.getDateTime().toLocalTime().compareTo(startTime)>=0)
                .filter(x -> endTime.compareTo(x.getDateTime().toLocalTime()) > 0)
                .collect(Collectors.groupingBy(UserMeal::getDate,
                        Collectors.summingInt(UserMeal::getCalories)));

        System.out.println(mealWithExcess);
//        List <UserMealWithExcess> mealWithExcesses = new ArrayList<>();
        return null;
    }
}
