// async function getClients() {
//     const response = await fetch('/api/getClients', {
//       method: 'GET',
//       headers: {
//         'Content-Type': 'application/json',
//       },
//     })
//     return await response.json()
//   }

//   let engineerTakesTheClient = (id) => { //функция создания окна подтверждения
//     class Client {
//       constructor(id) { //конструктор карточки
//         this.id = id;
//       }
//     }
//     let client = new Client(id);
//     console.log(JSON.stringify(client));
//     const response = fetch('/api/takeClient', {
//       method: 'POST',
//       headers: {
//         'Accept': 'application/json',
//         'Content-Type': 'application/json',
//       },
//       body: JSON.stringify(client)
//     });
//     location.reload(); 
//   }
  
//   let tableNewAppeal = document.getElementById('table-new-appeal');

//   class NewAppeal { //каждая карточка продукта - класс
  
//     constructor(client) { //конструктор для такблицы с новыми обращениями
//       this.clientList = [client.id, client.name, client.phone, client.createdData];
//     }
  
//     createAppeal() { //функция создания html разметки
//       let tr = tableNewAppeal.insertRow(); //создаем tr
      
//       this.clientList.forEach(element => {
//         let td = tr.insertCell();
//         td.appendChild(document.createTextNode(element));
//         tr.appendChild(td);
//       })
  
//       let td = tr.insertCell();
//       let buttonTake = document.createElement("button");
//       buttonTake.classList.add("badge", "badge-success");
//       buttonTake.innerHTML = "Take";
//       buttonTake.type = 'button';

//       td.appendChild(buttonTake);
//       tr.appendChild(td);
  
//       buttonTake.onclick = () => engineerTakesTheClient(this.clientList[0]); //по клику на кнопку вызываем функцию создания окна подтверждения
//     } 
//   }

//   let tableCommonAppeal = document.getElementById('table-common-appeal');

//   class CommonAppeal { // класс таблицы
  
//     constructor(client) { //конструктор для такблицы с новыми обращениями
//       this.clientList = [client.id, client.name, client.phone, client.createdData, client.engName];
//     }
  
//     createAppeal() { //функция создания html разметки
//       let tr = tableCommonAppeal.insertRow(); //создаем tr
      
//       this.clientList.forEach(element => {
//         let td = tr.insertCell();
//         td.appendChild(document.createTextNode(element));
//         tr.appendChild(td);
//       })
//     } 
//   }

//   getClients().then((data) => {
//     let newAppealList = [];
//     let allApplealList = [];

//     data.forEach(client => {
//       if (client.engName == null) {
//         let newClient = new NewAppeal(client);
//         newClient.createAppeal();
//         newAppealList.push(newClient);
//       } else {
//         let commonClient = new CommonAppeal(client);
//         commonClient.createAppeal();
//         allApplealList.push(commonClient);
//       }
//     });
//   });


  function displayPaginationNew(data, curPage, cssValue) {
    const paginationEl = document.querySelector(cssValue);
    paginationEl.innerHTML = "";
    const pagesCount = data.totalPages;

    const ulEl = document.createElement("ul");
    ulEl.classList.add('pagination__list');

    // 1! 2! 3! 4! 5! 6! 7!
    if (pagesCount <=7) {
      for (let i = 0; i < 7; i++) {
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
    liEl.classList.add('pagination__item');
    liEl.innerText = text;
    if (currentPageNew == page) liEl.classList.add('pagination__item--active');

    liEl.addEventListener('click', async () => {
      currentPageNew = page;
      let numPage = currentPageNew - 1;

      dataActive = await getActiveClients(numPage);
      console.log(dataActive)
      clearTable("table-new-appeal");
      createEndedAppeal(dataActive);
      displayPaginationNew(dataActive, currentPageNew, ".pagination-new");
    })

    return liEl;
  }