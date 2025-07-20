const convertButton = document.getElementById("convert");
const amountInput = document.getElementById("amount");
const fromCurrencySelect = document.getElementById("fromCurrency");
const toCurrencySelect = document.getElementById("toCurrency");
const resultInput = document.getElementById("result");

convertButton.addEventListener("click", () => {
  const amount = parseFloat(amountInput.value);
  const fromCurrency = fromCurrencySelect.value;
  const toCurrency = toCurrencySelect.value;

  fetch(`https://api.exchangerate-api.com/v4/latest/${fromCurrency}`)
    .then((response) => response.json())
    .then((data) => {
      const exchangeRate = data.rates[toCurrency];
      const convertedAmount = amount * exchangeRate;

      resultInput.value = convertedAmount.toFixed(2);
    })
    .catch((error) => {
      console.error("Döviz kurları alınamadı: " + error);
    });
});
