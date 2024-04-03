async function getClients() {
    const response = await fetch('/api/getClients', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    })
    return await response.json()
}

async function getEndedClients() {
  const response = await fetch('/api/getEndedClients', {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
  })
  return await response.json()
}

async function getEndedClients(currentPage) {
  const response = await fetch('/api/getEndedClients?page='+currentPage, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
  })
  return await response.json()
}

async function getActiveClients(currentPage) {
  const response = await fetch('/api/getActiveClients?page='+currentPage, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
  })
  return await response.json()
}

async function getEngineerLogin() {
    const response = await fetch('/api/getEngineerLogin', {
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
let tableCommonAppeal = document.getElementById('table-common-appeal');
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

class CommonAppeal { // класс всех взятых обращний

    constructor(client) { //конструктор для таблицы с новыми обращениями
        this.clientList = [
          client.id, client.name, 
          client.phone, client.createdData, 
          client.engName
        ];
    }

    createAppeal() { //функция создания html разметки
        let tr = tableCommonAppeal.insertRow(); //создаем tr
        engineerTakesTheClient // TODO ?
        this.clientList.forEach(element => {
          let td = tr.insertCell();
          td.appendChild(document.createTextNode(element));
          tr.appendChild(td);
        })
    } 
}



async function main() {
    let dataEnded = await getEndedClients();
    let dataActive = await getActiveClients();
    // const loginAuthEngineer = await getEngineerLogin();
    
    let currentPageCommon = 1;
    let currentPageNew = 1;
    console.log(dataEnded);
    console.log(dataActive);
    
    function createEndedAppeal(dataEnded) {
      const content = dataEnded.content;
      content.forEach(client => {
        let newClient = new CommonAppeal(client);
        newClient.createAppeal();
      });
    }

    function createActiveAppeal(dataActive) {
      const content = dataActive.content;
      content.forEach(client => {
        let newClient = new NewAppeal(client);
        newClient.createAppeal();
      });
    }

    function displayPaginationCommon(data, curPage, cssValue) {
      const paginationEl = document.querySelector(cssValue);
      paginationEl.innerHTML = "";
      const pagesCount = data.totalPages;

      const ulEl = document.createElement("ul");
      ulEl.classList.add('pagination__list');

      // 1! 2! 3! 4! 5! 6! 7!
      if (pagesCount <=7) {
        for (let i = 0; i < pagesCount; i++) {
          const liEl = displayPaginationBtnCommon(i + 1, i+1);
          ulEl.appendChild(liEl);
        }
        paginationEl.appendChild(ulEl);
      }
      // 1! 2! 3! 4! 5 6 7 ... 8
      else if (curPage <= 4 && pagesCount >= 8) {
        for (let i = 0; i < 7; i++) {
          const liEl = displayPaginationBtnCommon(i + 1, i+1);
          ulEl.appendChild(liEl);
        }
        let liEl = displayPaginationBtnCommon(7, '...');
        ulEl.appendChild(liEl);

        let liElend = displayPaginationBtnCommon(pagesCount, pagesCount);
        ulEl.appendChild(liElend);

        paginationEl.appendChild(ulEl);
      } 
      // 1 ... 3 4 5! 6 7 ... 10
      // 1 ... 4 5 6! 7 8 ... 10
      else if (curPage >= 5 && curPage <= pagesCount-4) {
        let liElfirst = displayPaginationBtnCommon(1, 1);
        ulEl.appendChild(liElfirst);

        let liElAfter = displayPaginationBtnCommon(curPage-3, "...");
        ulEl.appendChild(liElAfter);

        for (let i = curPage-3; i < curPage+2; i++) {
          const liEl = displayPaginationBtnCommon(i+1, i+1);
          ulEl.appendChild(liEl);
        }

        let liElBefore = displayPaginationBtnCommon(curPage+3, "...");
        ulEl.appendChild(liElBefore);

        let liElend = displayPaginationBtnCommon(pagesCount, pagesCount);
        ulEl.appendChild(liElend);
        paginationEl.appendChild(ulEl);
      } 
      // 1 ...5 6 7! 8! 9! 10!
      else if (curPage >= pagesCount-3) {
        let liElfirst = displayPaginationBtnCommon(1, 1);
        ulEl.appendChild(liElfirst);

        let liElAfter = displayPaginationBtnCommon(curPage-3, "...");
        ulEl.appendChild(liElAfter);

        for (let i = pagesCount-3-2-1; i < pagesCount; i++) {
          const liEl = displayPaginationBtnCommon(i + 1, i+1);
          ulEl.appendChild(liEl);
        }
        paginationEl.appendChild(ulEl);
      } 
    }
  
    function displayPaginationBtnCommon(page, text) {
      const liEl = document.createElement("li");
      liEl.classList.add('pagination__item');
      liEl.innerText = text;
  
      if (currentPageCommon == page) liEl.classList.add('pagination__item--active');
  
      liEl.addEventListener('click', async () => {
        currentPageCommon = page;
        let numPage = currentPageCommon - 1;

        dataEnded = await getEndedClients(numPage);
        console.log(dataEnded)
        clearTable("table-common-appeal");
        createEndedAppeal(dataEnded);
        displayPaginationCommon(dataEnded, currentPageCommon, ".pagination-common");
      })
  
      return liEl;
    }

    function displayPaginationNew(data, curPage, cssValue) {
      const paginationEl = document.querySelector(cssValue);
      paginationEl.innerHTML = "";
      const pagesCount = data.totalPages;
  
      const ulEl = document.createElement("ul");
      ulEl.classList.add('pagination__list-new');
  
      // 1! 2! 3! 4! 5! 6! 7!
      if (pagesCount <= 7) {
        for (let i = 0; i < pagesCount; i++) {
          const liEl = displayPaginationBtnNew(i + 1, i+1);
          ulEl.appendChild(liEl);
        }
        paginationEl.appendChild(ulEl);
      }
      // 1! 2! 3! 4! 5 6 7 ... 8
      else if (curPage <= 4 && pagesCount >= 8) {
        for (let i = 0; i < 7; i++) {
          const liEl = displayPaginationBtnNew(i + 1, i+1);
          ulEl.appendChild(liEl);
        }
        let liEl = displayPaginationBtnNew(7, '...');
        ulEl.appendChild(liEl);
  
        let liElend = displayPaginationBtnNew(pagesCount, pagesCount);
        ulEl.appendChild(liElend);
  
        paginationEl.appendChild(ulEl);
      } 
      // 1 ... 3 4 5! 6 7 ... 10
      // 1 ... 4 5 6! 7 8 ... 10
      else if (curPage >= 5 && curPage <= pagesCount-4) {
        let liElfirst = displayPaginationBtnNew(1, 1);
        ulEl.appendChild(liElfirst);
  
        let liElAfter = displayPaginationBtnNew(curPage-3, "...");
        ulEl.appendChild(liElAfter);
  
        for (let i = curPage-3; i < curPage+2; i++) {
          const liEl = displayPaginationBtnNew(i+1, i+1);
          ulEl.appendChild(liEl);
        }
  
        let liElBefore = displayPaginationBtnNew(curPage+3, "...");
        ulEl.appendChild(liElBefore);
  
        let liElend = displayPaginationBtnNew(pagesCount, pagesCount);
        ulEl.appendChild(liElend);
        paginationEl.appendChild(ulEl);
      } 
      // 1 ...5 6 7! 8! 9! 10!
      else if (curPage >= pagesCount-3) {
        let liElfirst = displayPaginationBtnNew(1, 1);
        ulEl.appendChild(liElfirst);
  
        let liElAfter = displayPaginationBtnNew(curPage-3, "...");
        ulEl.appendChild(liElAfter);
  
        for (let i = pagesCount-3-2-1; i < pagesCount; i++) {
          const liEl = displayPaginationBtnNew(i + 1, i+1);
          ulEl.appendChild(liEl);
        }
        paginationEl.appendChild(ulEl);
      } 
    }
  
    function displayPaginationBtnNew(page, text) {
      const liEl = document.createElement("li");
      liEl.classList.add('pagination__item-new');
      liEl.innerText = text;
      if (currentPageNew == page) liEl.classList.add('pagination__item-new--active');
  
      liEl.addEventListener('click', async () => {
        currentPageNew = page;
        let numPage = currentPageNew - 1;
  
        dataActive = await getActiveClients(numPage);
        console.log(dataActive)
        clearTable("table-new-appeal");
        createActiveAppeal(dataActive);
        displayPaginationNew(dataActive, currentPageNew, ".pagination-new");
      })
  
      return liEl;
    }

    function clearTable(cssValueTable) {
      let table = document.getElementById(cssValueTable);
      let rowCount = table.rows.length;
      for (let i = 1; i < rowCount; i++) {
          table.deleteRow(1);
      }
      
    }

    function createAppeal(data) {
      const content = data.content;
        let newAppealList = [];
        let allApplealList = [];
        
        content.forEach(client => {
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
    }
    function initEngineer() {
        let profileName = document.getElementById('nav-profile-name');
        profileName.innerHTML = loginAuthEngineer.login;
    }


    createEndedAppeal(dataEnded);
    createActiveAppeal(dataActive);

    displayPaginationCommon(dataEnded, currentPageCommon, ".pagination-common");
    displayPaginationNew(dataActive, currentPageNew, ".pagination-new");
    // initEngineer();
}

main();