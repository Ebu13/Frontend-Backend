const quoteButtonElement = document.getElementById("btn");
const quoteElement = document.getElementById("quote");
const authorElement = document.getElementById("author");

const apiURL = "https://api.quotable.io/random";

async function getQuote() {
  try {
    updateButton("Loading...", true);
    updateQuote("Updating...");
    updateAuthor("Updating...");

    const response = await fetch(apiURL);
    const data = await response.json();

    const quoteContent = data.content;
    const quoteAuthor = data.author;

    updateQuote(quoteContent);
    updateAuthor("~ " + quoteAuthor);
    updateButton("Get a quote", false);

    console.log(data);
  } catch (error) {
    console.log(error);
    updateQuote("An error happened, try again later");
    updateAuthor("An error happened");
    updateButton("Get a quote", false);
  }
}

function updateButton(text, disabled) {
  quoteButtonElement.innerText = text;
  quoteButtonElement.disabled = disabled;
}

function updateQuote(text) {
  quoteElement.innerText = text;
}

function updateAuthor(text) {
  authorElement.innerText = text;
}

getQuote();

quoteButtonElement.addEventListener("click", getQuote);