let sendFormPostRequest = (form) => { 
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
    fetch('/api/updateEngineerPassword', {
        method: 'POST',
        headers: {
        'Content-Type': 'application/json'
        },
        body: jsonData
    })
  } 

  const setNewPassword = document.querySelector('.set-new-password-form');
  setNewPassword.addEventListener('submit', function(event) {
    event.preventDefault(); // prevent the default form submission behavior
    sendFormPostRequest(setNewPassword);
  });


  async function getClients() {
    const response = await fetch('/api/getClients', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    })
    return await response.json()
  }

  let engineerTakesTheClient = (id) => { //функция создания отправки API запроса с клиентом, которого хотят взят
    class Client {
      constructor(id) {
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

  class NewAppeal { // класс нового обращения
  
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
  
      buttonTake.onclick = () => engineerTakesTheClient(this.clientList[0]); //по клику на кнопку вызываем функцию отправки API запроса
    } 
  }

  let tableCommonAppeal = document.getElementById('table-common-appeal');

  class CommonAppeal { // класс всех взятых обращний
  
    constructor(client) { //конструктор для таблицы с новыми обращениями
      this.clientList = [client.id, client.name, client.phone, client.createdData, client.engName];
    }
  
    createAppeal() { //функция создания html разметки
      let tr = tableCommonAppeal.insertRow(); //создаем tr
      engineerTakesTheClient
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

  async function getEngineerLogin() {
    const response = await fetch('/api/getEngineerLogin', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    })
    return await response.json()
  }

  let profileName = document.getElementById('nav-profile-name');
  getEngineerLogin().then((engLogin) => {
    profileName.innerHTML = engLogin.login;
  })