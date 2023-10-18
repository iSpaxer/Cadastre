async function getPricelist() {
    const response = await fetch('/api/getPricelist', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    })
    return await response.json()
  }

  class PricelistTable { // класс нового обращения
  
    constructor(pricelist, nameColumn) { //конструктор для таблицы
      this.pricelist = pricelist;
      this.nameColumn = nameColumn;
      console.log(this.nameColumn);
      this.arrayInput = [];
    }
  
    createTable() { //функция создания html разметки
      let tablePricelist = document.getElementById('table-pricelist');
      let tr = tablePricelist.insertRow(); //создаем tr
      
      let arrayPricelist = [this.pricelist.mezhevaniye, this.pricelist.tech_plan, 
        this.pricelist.akt_inspection, this.pricelist.scheme_location, this.pricelist.takeaway_borders];


      let td2 = tr.insertCell();
      td2.appendChild(document.createTextNode(this.nameColumn));
      tr.appendChild(td2);

      arrayPricelist.forEach(element => {

          let td = tr.insertCell();
          let input = document.createElement("input");
            input.setAttribute('type', 'text');
            input.setAttribute('value', element);
            this.arrayInput.push(input);

          td.appendChild(input);
          tr.appendChild(td);
      })
      let td = tr.insertCell();
      console.log(this.pricelist.last_change)
      td.appendChild(document.createTextNode(this.pricelist.last_change));
      tr.appendChild(td);
    } 

    createButtonUpdateTable(pricelist) {
      let buttonSavePricelist = document.createElement("button");
      buttonSavePricelist.classList.add("badge", "badge-success");
      buttonSavePricelist.innerHTML = "Cохранить изменения";
      buttonTake.onclick = () => updatePricelist(pricelist); //по клику на кнопку вызываем функцию отправки API запроса
    }

    updatePricelist = (pricelist) => {
      const response = fetch('/api/updatePricelist', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(pricelist)
      });
      location.reload(); 

    }
  }

  let arrayPricelistAllTable = [];
  getPricelist().then((pricelistAll) => {
      
      sms1 = "Срок выполнения";
      sms2 = "Стоимость"
      crutch = 0;

      pricelistAll.forEach(pricelist => {
        let pricelistTable = crutch == 0 ? new PricelistTable(pricelist, sms1) : new PricelistTable(pricelist, sms2); 
        pricelistTable.createTable();
        arrayPricelistAllTable.push(pricelistTable);
        crutch++;
      });
  });


  // ___________________________________________
  let sendForm = (form) => { 
    const formData = new FormData(form); // create a new FormData object from the form data
    const data = {}; // create an empty object to store the form data
    
    // loop through the FormData object and add each key-value pair to the data object
    for (let [key, value] of formData.entries()) {
      data[key] = value;
    }
    
    // convert the data object to a JSON string using JSON.stringify()
    const jsonData = JSON.stringify(data);
    console.log(jsonData);
    // send the JSON data to the server using an AJAX request (example using fetch())
  }

  let readAllInput = (arrayPricelistAll) => {
    // const jsonData2 = JSON.stringify(arrayPricelistAll[0].arrayInput[0].value);
    // console.log(jsonData2 + " " + arrayPricelistAll[0].arrayInput[0].value);
    
    class AllInput {
      constructor (mezhevaniye, tech_plan, akt_inspection, scheme_location, takeaway_borders) {
        this.mezhevaniye      = mezhevaniye;
        this.tech_plan        = tech_plan;
        this.akt_inspection   = akt_inspection;
        this.scheme_location  = scheme_location;
        this.takeaway_borders = takeaway_borders;
      }
    }
    
    let arrayInputJSON = [];
    for (i = 0; i < arrayPricelistAll.length; i++) {
      let allInput = new AllInput(arrayPricelistAll[i].arrayInput[0].value, arrayPricelistAll[i].arrayInput[1].value, arrayPricelistAll[i].arrayInput[2].value,
        arrayPricelistAll[i].arrayInput[3].value, arrayPricelistAll[i].arrayInput[4].value);
        arrayInputJSON.push(allInput);
    }
    const jsonData = JSON.stringify(arrayInputJSON);
    console.log(jsonData);
  
    fetch('/api/updatePricelist', {
      method: 'POST',
      headers: {
      'Content-Type': 'application/json'
      },
      body: jsonData
    }).then(response => {
      console.log(response.status);
      location.reload();
    });
  }

  const pricelistForm = document.querySelector('.update-table-pricelist-form');
  pricelistForm.addEventListener('submit', function(event) {
    console.log("УРа!")
    event.preventDefault(); // prevent the default form submission behavior
    // sendForm(pricelistForm);
    readAllInput(arrayPricelistAllTable);
  });

  // const buttonPricelist = document.getElementById("button-pricelist-update").addEventListener("click", (event) => {
  //   event.preventDefault();
  //   sendForm(pricelistForm);
  // })