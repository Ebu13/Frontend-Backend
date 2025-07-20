const monthNameEl = document.getElementById("month-name");
const fullDateEl = document.getElementById("full-date");
const daysEl = document.getElementById("days");

const date = new Date();
const monthInx = date.getMonth();
const lastDay = new Date(date.getFullYear(), monthInx + 1, 0).getDate();
const firstDay = new Date(date.getFullYear(), monthInx, 1).getDay() - 1;

const months = [
  "Ocak",
  "Şubat",
  "Mart",
  "Nisan",
  "Mayıs",
  "Haziran",
  "Temmuz",
  "Ağustos",
  "Eylül",
  "Ekim",
  "Kasım",
  "Aralık",
];

monthNameEl.innerText = months[monthInx];
fullDateEl.innerText = date.toLocaleDateString("tr");

let days = "";

for (let i = firstDay; i > 0; i--) {
  days += `<div class="empty"></div>`;
}
for (let i = 1; i <= lastDay; i++) {
  if (i === date.getDate()) {
    days += `<div class="today">${i}</div>`;
  } else {
    days += `<div>${i}</div>`;
  }
}

daysEl.innerHTML = days;