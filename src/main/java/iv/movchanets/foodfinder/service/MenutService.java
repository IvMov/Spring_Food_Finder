package iv.movchanets.foodfinder.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import iv.movchanets.foodfinder.entity.Meal;
import iv.movchanets.foodfinder.entity.Menu;
import iv.movchanets.foodfinder.entity.Nutrients;
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
import java.util.List;
import java.util.Set;

@Service
public class MenutService {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    NutrientsRepository nutrientsRepository;

    public List<Menu> getDayMenuByCalories(int calories) throws IOException, InterruptedException, JSONException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/mealplans/generate?timeFrame=day&targetCalories=" + calories + "&diet=vegetarian"))
                .header("X-RapidAPI-Host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                .header("X-RapidAPI-Key", "4dbecea2e4msh9811d42eddfb80ep1eafaajsn139d59eb48ff")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject dayMenuJson = new JSONObject(response.body());
        JSONArray mealsJson = dayMenuJson.getJSONArray("meals");

        Set<Meal> mealsObject = objectMapper.readValue(String.valueOf(mealsJson), new TypeReference<Set<Meal>>() {
        });

        JSONObject nutrientsJson = dayMenuJson.getJSONObject("nutrients");

        Nutrients nutrientsObject = objectMapper.readValue(String.valueOf(nutrientsJson), Nutrients.class);
        Menu dayMenuObject = new Menu();
        dayMenuObject.setMeals(mealsObject);
        dayMenuObject.setNutrients(nutrientsRepository.save(nutrientsObject));

        menuRepository.save(dayMenuObject);

        List<Menu> menues = new ArrayList<>();
        menues.add(dayMenuObject);

        return menues;


    }
}
