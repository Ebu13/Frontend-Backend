const products = [
  {
    name: "Ürün 1",
    description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
    image:
      "https://lp2.hm.com/hmgoepprod?set=quality%5B79%5D%2Csource%5B%2F52%2Fa4%2F52a42f3eb484bae1746101cceae6a53fa4f94392.jpg%5D%2Corigin%5Bdam%5D%2Ccategory%5B%5D%2Ctype%5BDESCRIPTIVESTILLLIFE%5D%2Cres%5Bm%5D%2Chmver%5B2%5D&call=url[file:/product/main]",
  },
  {
    name: "Ürün 2",
    description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
    image:
      "https://lp2.hm.com/hmgoepprod?set=quality%5B79%5D%2Csource%5B%2Fc2%2F82%2Fc282a2e39288d84f0c1ce9f818057d452d31f27d.jpg%5D%2Corigin%5Bdam%5D%2Ccategory%5B%5D%2Ctype%5BDESCRIPTIVESTILLLIFE%5D%2Cres%5Bm%5D%2Chmver%5B2%5D&call=url[file:/product/main]",
  },
  {
    name: "Ürün 3",
    description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
    image:
      "https://lp2.hm.com/hmgoepprod?set=quality%5B79%5D%2Csource%5B%2F5f%2F9c%2F5f9c0426431d9a1455bd26bf185278368b0e13ae.jpg%5D%2Corigin%5Bdam%5D%2Ccategory%5B%5D%2Ctype%5BDESCRIPTIVESTILLLIFE%5D%2Cres%5Bm%5D%2Chmver%5B2%5D&call=url[file:/product/main]",
  },
];

const productRow = document.getElementById("productRow");

function createProducts() {
  for (const product of products) {
    const col = document.createElement("div");
    col.classList.add("col-md-4");

    const card = document.createElement("div");
    card.classList.add("card");

    const image = document.createElement("img");
    image.src = product.image;
    image.classList.add("card-img-top");
    image.alt = product.name;

    const cardBody = document.createElement("div");
    cardBody.classList.add("card-body");

    const title = document.createElement("h5");
    title.classList.add("card-title");
    title.textContent = product.name;

    const description = document.createElement("p");
    description.classList.add("card-text");
    description.textContent = product.description;

    const addToCartBtn = document.createElement("a");
    addToCartBtn.href = "#";
    addToCartBtn.classList.add("btn", "btn-primary");
    addToCartBtn.textContent = "Sepete Ekle";

    cardBody.appendChild(title);
    cardBody.appendChild(description);
    cardBody.appendChild(addToCartBtn);

    card.appendChild(image);
    card.appendChild(cardBody);

    col.appendChild(card);

    productRow.appendChild(col);
  }
}

createProducts();
