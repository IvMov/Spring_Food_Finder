package iv.movchanets.foodfinder.controller;

import iv.movchanets.foodfinder.entity.Menu;
import iv.movchanets.foodfinder.service.MenutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
public class PublicMenuController {

    @Autowired
    MenutService menutService;


    @GetMapping("/menu/days/{days}/{calories}")
    public List<Menu> getMenuByCalories(@PathVariable(name = "days") int days,
                                        @PathVariable(name = "calories") int calories) throws IOException, InterruptedException {
         return menutService.getDayMenuByCalories(calories);
    }
}
