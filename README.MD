# Web app Foodfinder - "Application for searching vegetarian menu for day".

---

This app based on / used:

- Spring Boot;
- REST Api;
- Docker-compose;
- MySQL;
- pure- CSS;
- pure - JavaScript;
- using external API.

---

## Features

    - web page responsive;
    - you can change for how many days you need menu;
    - can cahnge max calories quantity for day menu;
    - can also serch day menu by (~~macronutrients~~) - random day menu;
    - resul will be with url to webpages - where you can find receip of meal and other nessesary information.
    
---
### End points

| Method    | HTTP                                                                                         |
|-----------|----------------------------------------------------------------------------------------------|
| `GET`     | `http://localhost:8080/menu/days/${daysNrInput}/${caloriesInput}`                            |
| `GET`     | `http://localhost:8080/menu/days/${daysNrInput}/${proteinsInput}/${fatInput}/${carbsInput}`  |


## Requirement to start application:

1. installed Java version 17 or higher;
2. maven;
3. installed docker and docker-compose;
4. installed IntelliJ IDEA - community version is enough.

___

## How to start app:

1. open project folder in IntelliJ IDEA;
2. open terminal in root folder `/foodfinder`;
3. to start MySQL database - type in terminal command  `docker-compose up -d` or `sudo docker-compose up -d` in Linux;
4. run Spring Boot application `FoodFinderApplication` - server part; 
5. open folder `foodfinder/foodfinder_client/static/`;
6. open `index.html` in any browser (Google Chrome recomended - cose CSS without prefixes);
7. have fun and bon appetit!

