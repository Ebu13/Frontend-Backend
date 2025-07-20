function calculateLoan() {
  var loanAmountValue = document.getElementById("loan-amount").value;
  var interestRateValue = document.getElementById("interest-rate").value;
  var monthsToPayValue = document.getElementById("months-to-pay").value;

  var interest =
    (loanAmountValue * (interestRateValue * 0.01)) / monthsToPayValue;
  var monthlyPayment = (loanAmountValue / monthsToPayValue + interest).toFixed(
    2
  );

  document.getElementById(
    "payment"
  ).innerHTML = `Monthly Payment: ${monthlyPayment}`;
}
