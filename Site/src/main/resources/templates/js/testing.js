async function getClients() {
    const response = await fetch('/api/getClients', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    })
    return await response.json()
  }

  let engineerTakesTheClient = (id) => { //функция создания окна подтверждения
    class Client {
      constructor(id) { //конструктор карточки
        this.id = id;
      }
    }
    let client = new Client(id);
    console.log(JSON.stringify(client));
    const response = fetch('/api/takeClient', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(client)
    });
    location.reload(); 
  }
  
  let tableNewAppeal = document.getElementById('table-new-appeal');

  class NewAppeal { //каждая карточка продукта - класс
  
    constructor(client) { //конструктор для такблицы с новыми обращениями
      this.clientList = [client.id, client.name, client.phone, client.createdData];
    }
  
    createAppeal() { //функция создания html разметки
      let tr = tableNewAppeal.insertRow(); //создаем tr
      
      this.clientList.forEach(element => {
        let td = tr.insertCell();
        td.appendChild(document.createTextNode(element));
        tr.appendChild(td);
      })
  
      let td = tr.insertCell();
      let buttonTake = document.createElement("button");
      buttonTake.classList.add("badge", "badge-success");
      buttonTake.innerHTML = "Take";
      buttonTake.type = 'button';

      td.appendChild(buttonTake);
      tr.appendChild(td);
  
      buttonTake.onclick = () => engineerTakesTheClient(this.clientList[0]); //по клику на кнопку вызываем функцию создания окна подтверждения
    } 
  }

  let tableCommonAppeal = document.getElementById('table-common-appeal');

  class CommonAppeal { // класс таблицы
  
    constructor(client) { //конструктор для такблицы с новыми обращениями
      this.clientList = [client.id, client.name, client.phone, client.createdData, client.engName];
    }
  
    createAppeal() { //функция создания html разметки
      let tr = tableCommonAppeal.insertRow(); //создаем tr
      
      this.clientList.forEach(element => {
        let td = tr.insertCell();
        td.appendChild(document.createTextNode(element));
        tr.appendChild(td);
      })
    } 
  }

  getClients().then((data) => {
    let newAppealList = [];
    let allApplealList = [];

    data.forEach(client => {
      if (client.engName == null) {
        let newClient = new NewAppeal(client);
        newClient.createAppeal();
        newAppealList.push(newClient);
      } else {
        let commonClient = new CommonAppeal(client);
        commonClient.createAppeal();
        allApplealList.push(commonClient);
      }
    });
  });