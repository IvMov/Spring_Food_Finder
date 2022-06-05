package iv.movchanets.foodfinder.controller;

import iv.movchanets.foodfinder.entity.Menu;
import iv.movchanets.foodfinder.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
public class PublicMenuController {

    @Autowired
    MenuService menuService;


    @GetMapping("/menu/days/{days}/{calories}")
    public List<Menu> getMenuByCalories(@PathVariable(name = "days") int days,
                                        @PathVariable(name = "calories") int calories) throws IOException, InterruptedException {
        return menuService.getDaysMenusByDaysAndCalories(days, calories);
    }

    @GetMapping("/menu/days/{days}/{minProtein}/{minFat}/{minCarbs}")
    public List<Menu> getMenuByMacronutrients(@PathVariable(name = "days") int days,
                                              @PathVariable(name = "minProtein") int protein,
                                              @PathVariable(name = "minFat") int fat,
                                              @PathVariable(name = "minCarbs") int carbs) throws IOException, InterruptedException {
        return menuService.getDaysMenusByDaysAndMacronutrients(days, protein, fat, carbs);
    }
}
