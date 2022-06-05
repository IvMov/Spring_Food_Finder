package iv.movchanets.foodfinder.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import iv.movchanets.foodfinder.entity.Meal;
import iv.movchanets.foodfinder.entity.Menu;
import iv.movchanets.foodfinder.entity.Nutrients;
import iv.movchanets.foodfinder.repository.MealRepository;
import iv.movchanets.foodfinder.repository.MenuRepository;
import iv.movchanets.foodfinder.repository.NutrientsRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MenuService {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MealRepository mealRepository;

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    NutrientsRepository nutrientsRepository;

    public List<Menu> getDaysMenusByDaysAndCalories(int days, int calories) throws IOException, InterruptedException {

        int savedMenus = getSavedMenuQuantity(calories);

        List<Menu> menus = new ArrayList<>();

        if (savedMenus <= 3) {
            for (int i = 0; i < days; i++) {
                menus.add(getNewCaloriesDayMenuToDatabase(calories));
            }
        } else if (savedMenus <= 7) {
            for (int i = 0; i < days; i++) {
                int randomizer = getRandomNumber(10);
                if (randomizer < 5) {
                    menus.add((getDayMenuByCalories(calories)));
                } else {
                    menus.add(getNewCaloriesDayMenuToDatabase(calories));
                }
            }
        } else {
            for (int i = 0; i < days; i++) {
                menus.add(getDayMenuByCalories(calories));
            }
        }

        return menus;

    }

    public List<Menu> getDaysMenusByDaysAndMacronutrients(int days, int protein, int fat, int carbs) throws IOException, InterruptedException {

        int savedMenus = getSavedMenuQuantityMacro(protein, fat, carbs);

        List<Menu> menus = new ArrayList<>();

        if (savedMenus <= 3) {
            for (int i = 0; i < days; i++) {
                getNewMacronutrientDayMenuToDatabase();
                menus.add(getDayMenuByMacronutrients(protein, fat, carbs));
            }
        } else if (savedMenus <= 7) {
            for (int i = 0; i < days; i++) {
                int randomizer = getRandomNumber(10);
                if (randomizer < 7) {
                    getNewMacronutrientDayMenuToDatabase();
                    menus.add(getDayMenuByMacronutrients(protein, fat, carbs));
                }
            }
        } else {
            for (int i = 0; i < days; i++) {
                menus.add(getDayMenuByMacronutrients(protein, fat, carbs));
            }
        }

        return menus;

    }

    private Menu getNewCaloriesDayMenuToDatabase(int calories) throws IOException, InterruptedException, JSONException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/mealplans/generate?timeFrame=day&targetCalories=" + calories + "&diet=vegetarian"))
                .header("X-RapidAPI-Host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                .header("X-RapidAPI-Key", "4dbecea2e4msh9811d42eddfb80ep1eafaajsn139d59eb48ff")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject dayMenuJson = new JSONObject(response.body());
        JSONArray mealsJson = dayMenuJson.getJSONArray("meals");

        Set<Meal> meals = new HashSet<>();

        for (int i = 0; i < 3; i++) {
            JSONObject mealJson = mealsJson.getJSONObject(i);
            Meal meal = objectMapper.readValue(String.valueOf(mealJson), Meal.class);
            meals.add(meal);
            mealRepository.save(meal);
        }

        JSONObject nutrientsJson = dayMenuJson.getJSONObject("nutrients");

        Nutrients nutrientsObject = objectMapper.readValue(String.valueOf(nutrientsJson), Nutrients.class);

        Menu dayMenuObject = new Menu();
        dayMenuObject.setMeals(meals);

        dayMenuObject.setNutrients(nutrientsRepository.save(nutrientsObject));

        return menuRepository.save(dayMenuObject);

    }

    private void getNewMacronutrientDayMenuToDatabase() throws IOException, InterruptedException, JSONException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/mealplans/generate?timeFrame=day&diet=vegetarian"))
                .header("X-RapidAPI-Host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                .header("X-RapidAPI-Key", "4dbecea2e4msh9811d42eddfb80ep1eafaajsn139d59eb48ff")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject dayMenuJson = new JSONObject(response.body());
        JSONArray mealsJson = dayMenuJson.getJSONArray("meals");

        Set<Meal> meals = new HashSet<>();

        for (int i = 0; i < 3; i++) {
            JSONObject mealJson = mealsJson.getJSONObject(i);
            Meal meal = objectMapper.readValue(String.valueOf(mealJson), Meal.class);
            meals.add(meal);
            mealRepository.save(meal);
        }

        JSONObject nutrientsJson = dayMenuJson.getJSONObject("nutrients");

        Nutrients nutrientsObject = objectMapper.readValue(String.valueOf(nutrientsJson), Nutrients.class);
        Menu dayMenuObject = new Menu();

        dayMenuObject.setMeals(meals);

        dayMenuObject.setNutrients(nutrientsRepository.save(nutrientsObject));

        menuRepository.save(dayMenuObject);

    }

    private int getSavedMenuQuantity(int calories) {

        List<Menu> menus = menuRepository.findAll().stream()
                .filter(menu -> (menu.getNutrients().getCalories() <= (calories + 20)) && (menu.getNutrients().getCalories() >= (calories - 200)))
                .toList();

        return menus.size();
    }

    private int getSavedMenuQuantityMacro(int protein, int fat, int carbs) {

        List<Menu> menus = menuRepository.findAll().stream()
                .filter(menu -> (menu.getNutrients().getProtein() >= (protein - 2)) && (menu.getNutrients().getFat() >= (fat - 2)) && (menu.getNutrients().getCarbohydrates() >= (carbs - 2)))
                .toList();

        return menus.size();
    }

    private Menu getDayMenuByCalories(int calories) {
        List<Menu> menus = menuRepository.findAll().stream()
                .filter(menu -> (menu.getNutrients().getCalories() <= (calories + 20)) && (menu.getNutrients().getCalories() >= (calories - 200)))
                .toList();
        return menus.get(getRandomNumber(menus.size()));
    }

    private Menu getDayMenuByMacronutrients(int protein, int fat, int carbs) throws IOException, InterruptedException {
        List<Menu> menus = menuRepository.findAll().stream()
                .filter(menu -> (menu.getNutrients().getProtein() >= (protein - 2)) && (menu.getNutrients().getFat() >= (fat - 2)) && (menu.getNutrients().getCarbohydrates() >= (carbs - 2)))
                .toList();

        if (menus.size() > 0) {
            return menus.get(getRandomNumber(menus.size() - 1));
        } else {
                menus = menuRepository.findAll();
            return menus.get(getRandomNumber(menus.size() - 1));
        }

    }

    private int getRandomNumber(int max) {
        return (int) Math.floor(Math.random() * (max - 1)) + 1;
    }
}
