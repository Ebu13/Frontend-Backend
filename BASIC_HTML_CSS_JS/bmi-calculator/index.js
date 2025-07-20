function calculateBMI() {
  var height = document.getElementById("height").value / 100;
  var weight = document.getElementById("weight").value;
  var bmi = weight / (height * height);
  document.getElementById("bmi-result").value = bmi.toFixed(2);

  var conditionEl = document.getElementById("condition");
  if (bmi < 18.5) {
    conditionEl.textContent = "Zayıf";
  } else if (bmi >= 18.5 && bmi < 25) {
    conditionEl.textContent = "Normal";
  } else if (bmi >= 25 && bmi < 30) {
    conditionEl.textContent = "Kilolu";
  } else if (bmi >= 30){
    conditionEl.textContent = "Obez";
  }
}
