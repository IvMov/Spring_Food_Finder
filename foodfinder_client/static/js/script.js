console.log("labas");

function getMenuByCaloriesForDay() {

  let hide = document.getElementById("test-wrapper");
      hide.innerHTML = "";
      hide.classList.add("hide");

  let daysNrInput = document.getElementById("days-input").value;

  let caloriesInput = document.getElementById("calories-input").value;

  let menuOutput = document.getElementById("menu-output");

  fetch(`http://localhost:8080/menu/days/${daysNrInput}/${caloriesInput}`)
    .then(response => response.json())
    .then((menues) => {
      console.log(menues);

      for (let i = 0; i < daysNrInput; i++) {

        let menu = menues[i];

        createDayWrapperStructure(i, menuOutput);

        console.log("nu alio")
        console.log(menu);

        createDayMenuFromApi(i, menu);

      }

    });

}

function getMenuByMacronutriensForDay() {

  let daysNrInput = document.getElementById("days-input").value;

  let proteinsInput = document.getElementById("protein-input").value;
  let fatInput = document.getElementById("fat-input").value;
  let carbsInput = document.getElementById("carbs-input").value;

  let menuOutput = document.getElementById("menu-output");

  fetch(`http://localhost:8080/menu/days/${daysNrInput}/${proteinsInput}/${fatInput}/${carbsInput}`)
    .then(response => response.json())
    .then((menues) => {

      for (let i = 0; i < daysNrInput; i++) {

        createDayWrapperStructure(i, menuOutput);

        let menu = menues[i];
      

        createDayMenuFromApi(i, menu);

      }

    });

}


function createDayWrapperStructure(i, menuOutput) {

  let dayWrapper = document.createElement("div");
  dayWrapper.classList.add("day-wrapper");

  menuOutput.append(dayWrapper);

  let dayH2 = document.createElement("h2");
  dayH2.classList.add("day-h2");
  dayH2.textContent = `Day ${i + 1} menu`;

  dayWrapper.append(dayH2);

  let mealsDiv = document.createElement("div");
  mealsDiv.classList.add("meals");

  dayWrapper.append(mealsDiv);

  let mealsTable = document.createElement("table");
  mealsTable.classList.add("meals-table");

  mealsDiv.append(mealsTable);

  for (let j = 1; j <= 3; j++) {
    let mealsTableTr = document.createElement("tr");

    mealsTable.append(mealsTableTr);

    let mealsTableTrTh = document.createElement("th");
    if (j == 1) {
      mealsTableTrTh.textContent = "Breakfast:";
    } else if (j == 2) {
      mealsTableTrTh.textContent = "Dinner:";
    } else if (j == 3) {
      mealsTableTrTh.textContent = "Supper:";
    }

    mealsTableTr.append(mealsTableTrTh);

    let mealsTableTrTd = document.createElement("td");
    mealsTableTrTd.id = `meal-${j}-day-${i + 1}`;

    mealsTableTr.append(mealsTableTrTd);

    let mealmealsTableTrTdA = document.createElement("td");

    mealsTableTr.append(mealmealsTableTrTdA);

    let tableA = document.createElement("a");
    tableA.id = `link-${j}-day-${i + 1}`;
    tableA.setAttribute("target", "_blank")
    tableA.textContent = "Check receipt";

    mealmealsTableTrTdA.append(tableA);

  }


  let detailsDiv = document.createElement("div");
  detailsDiv.classList.add("details");

  dayWrapper.append(detailsDiv);

  let detailsTable = document.createElement("table");
  detailsTable.classList.add("details-table");

  detailsDiv.append(detailsTable);

  for (let k = 1; k <= 4; k++) {

    let detailsTableTr = document.createElement("tr");

    detailsTable.append(detailsTableTr);

    let detailsTableTrTh = document.createElement("th");

    if (k == 1) {
      detailsTableTrTh.textContent = "Calories: ";
    } else if (k == 2) {
      detailsTableTrTh.textContent = "Proteins: ";
    } else if (k == 3) {
      detailsTableTrTh.textContent = "Fats: ";
    } else if (k == 4) {
      detailsTableTrTh.textContent = "Carbohydrates: ";
    }

    detailsTableTr.append(detailsTableTrTh);

    let detailsTableTrTd = document.createElement("td");
    detailsTableTrTd.id = `details-${k}-day-${i + 1}`;

    detailsTableTr.append(detailsTableTrTd);

    let detailsTableTrTdSi = document.createElement("td");
    if (k == 1) {
      detailsTableTrTdSi.textContent = "kcal";
    } else {
      detailsTableTrTdSi.textContent = "gram"
    }
  }
}

function createDayMenuFromApi(i, menu) {
  for (let j = 1; j <= 3; j++) {

    let mealTitle = document.getElementById(`meal-${j}-day-${i+1}`);
    mealTitle.textContent = menu.meals[j-1].title;

    let mealLink = document.getElementById(`link-${j}-day-${i+1}`);
    let link = menu.meals[j-1].sourceUrl;
    mealLink.setAttribute("href", link);
  }

  let caloriesOutput = document.getElementById(`details-1-day-${i + 1}`);
  caloriesOutput.textContent = menu.nutrients.calories;
  
  let proteinsOutput = document.getElementById(`details-2-day-${i + 1}`);
  proteinsOutput.textContent = menu.nutrients.calories;

  let fatsOutput = document.getElementById(`details-3-day-${i + 1}`);
  fatsOutput.textContent = menu.nutrients.calories;
  
  let carbsOutput = document.getElementById(`details-4-day-${i + 1}`);
  carbsOutput.textContent = menu.nutrients.calories;
}