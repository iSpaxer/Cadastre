$(document).ready(function() {
  $("#phone-1").mask("+7 (999) 999-99-99");
  $("#phone-2").mask("+7 (999) 999-99-99");
});

$(document).ready(() => { // DOM готов к взаимодейтсвию

  const onScrollHeader = () => { // объявляем основную функцию onScrollHeader

      const header = $('.header') // находим header и записываем в константу

      let prevScroll = $(window).scrollTop() // узнаем на сколько была прокручена страница ранее
      let currentScroll // на сколько прокручена страница сейчас (пока нет значения)
      
      $(window).scroll(() => { // при прокрутке страницы

          currentScroll = $(window).scrollTop() // узнаем на сколько прокрутили страницу
          
          const headerHidden = () => header.hasClass('header_hidden') // узнаем скрыт header или нет
          // console.log(currentScroll + " --- " ) 
          if (currentScroll < 100 && headerHidden()) {
              header.removeClass('header_hidden');
              prevScroll = currentScroll;
              console.log("urrentScroll < 116 && headerHidden()")
              return;
          }
          if (currentScroll > 80 && currentScroll > prevScroll && !headerHidden()) { // если прокручиваем страницу вниз и header не скрыт
              header.addClass('header_hidden') // то скрываем header
          }
          else if ((currentScroll < (prevScroll-8))  && headerHidden()) { // если прокручиваем страницу вверх и header скрыт
              header.removeClass('header_hidden') // то отображаем header
          }

          prevScroll = currentScroll // записываем на сколько прокручена страница на данный момент

      })

  }

  onScrollHeader() // вызываем основную функцию onScrollHeader

})

let sendFormClient = (form) => { 
  const formData = new FormData(form); // create a new FormData object from the form data
  const data = {}; // create an empty object to store the form data
  
  // loop through the FormData object and add each key-value pair to the data object
  for (let [key, value] of formData.entries()) {
    if (key == "phone") {
      value = value.replace(/[^\d]/g, ''); 
    }
    data[key] = value;
  }
  
  // convert the data object to a JSON string using JSON.stringify()
  const jsonData = JSON.stringify(data);
  console.log(jsonData);
  // send the JSON data to the server using an AJAX request (example using fetch())
  fetch('/api/saveClient', {
      method: 'POST',
      headers: {
      'Content-Type': 'application/json'
      },
      body: jsonData
  })
  .then(response => { 
    if (response.ok) {               // TODO not
      modalController({
        modal: '.modal1',
        btnClose: '.modal__close',
        time: 500,
        form: form
      });
    }
    else {
      modalController({
        modal: '.modal2',
        btnClose: '.modal__close',
        time: 500,
      });
    }
  })
  .then(data => console.log(data))
  .catch(error => console.error(error));
  // location.reload(); 
}

const modalController = ({modal, btnClose, time = 300, form = null}) => {
  const modalElem = document.querySelector(modal);

  console.log(modal +  " " +  btnClose);
  console.log(modalElem  + " " +  btnClose);

  modalElem.style.cssText = `
    display: flex;
    visibility: hidden;
    opacity: 0;
    z-index:99;
    transition: opacity ${time}ms ease-in-out;
  `;

  const closeModal = event => {
    const target = event.target;

    if (
      target === modalElem ||
      (btnClose && target.closest(btnClose)) || event.code === 'Escape') {
      
      modalElem.style.opacity = 0;

      setTimeout(() => {
        modalElem.style.visibility = 'hidden';
        scrollController.enabledScroll();
      }, time);

      window.removeEventListener('keydown', closeModal);
    }

    if (form != null) {
      form.reset();
    }
  }

  const openModal = () => {
    modalElem.style.visibility = 'visible';
    modalElem.style.opacity = 1;
    window.addEventListener('keydown', closeModal);
    scrollController.disabledScroll();
  };

  // buttonElems.forEach(btn => {
  //   btn.addEventListener('click', openModal);
  // });

  modalElem.addEventListener('click', closeModal);

  openModal();
};

const scrollController = {
  scrollPosition: 0,
  disabledScroll() {
    scrollController.scrollPosition = window.scrollY;
    document.body.style.cssText = `
      overflow: hidden;
      position: fixed;
      top: -${scrollController.scrollPosition}px;
      left: 0;
      height: 100vh;
      width: 100vw;
      padding-right: ${window.innerWidth - document.body.offsetWidth}px
    `;
    document.documentElement.style.scrollBehavior = 'unset';
  },
  enabledScroll() {
    document.body.style.cssText = '';
    window.scroll({top: scrollController.scrollPosition})
    document.documentElement.style.scrollBehavior = '';
  },
};

const mainForm = document.querySelector('.main-form');
mainForm.addEventListener('submit', function(event) {
  event.preventDefault(); // prevent the default form submission behavior
  sendFormClient(mainForm);
});

// TODO
// modalController({
//   modal: '.modal1',
//   btnClose: '.modal__close',
//   time: 500,
// });

const secondForm = document.querySelector('.section-questions__content-input');
secondForm.addEventListener('submit', function(event) {
  event.preventDefault(); // prevent the default form submission behavior
  sendFormClient(secondForm);
});

// _______________________________________-

async function getPricelist() {
  const response = await fetch('/api/getPricelist', {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
  })
  return await response.json()
}

class TableTitle { // класс нового обращения
  
  constructor(pricelistAll, nameLineArray) { //конструктор для такблицы с новыми обращениями
    this.pricelistSortArray = [[pricelistAll[0].mezhevaniye, pricelistAll[1].mezhevaniye],
                               [pricelistAll[0].tech_plan, pricelistAll[1].tech_plan], 
                               [pricelistAll[0].akt_inspection, pricelistAll[1].akt_inspection],
                               [pricelistAll[0].scheme_location,pricelistAll[1].scheme_location],
                               [pricelistAll[0].takeaway_borders, pricelistAll[1].takeaway_borders]];
    this.nameLineArray = nameLineArray;
    this.tableTitle = document.getElementById('table-title'); 
  }

  sorting() {

    
  }

  createTable() { //функция создания html разметки

    let maxI = this.nameLineArray.length;
    for (let i = 0; i < maxI; i++) {
      let tr = this.tableTitle.insertRow(); //создаем tr
        tr.classList.add("table__text");

      let th = document.createElement("th");
        th.classList.add("table__text-works", "table__text-works--first");
        let cell = document.createTextNode(this.nameLineArray[i]); 
        let p = document.createElement("p");
        p.appendChild(cell);
        th.appendChild(p);
      tr.appendChild(th);

      for (let j = 0; j < 2; j++) {
        let td = tr.insertCell();
        td.appendChild(document.createTextNode(this.pricelistSortArray[i][j])); //TODO
        tr.appendChild(td);
      }
    }



    // let tr = this.tableTitle.insertRow(); //создаем tr
    // tr.classList.add("table__text");
    //   // let td = tr.insertCell();
    //   let th = document.createElement("th");
    //   let cell = document.createTextNode("12312");
    //   th.classList.add("table__text-works", "table__text-works--first");
    //   th.appendChild(cell);
    // tr.appendChild(th);
    // this.clientList.forEach(element => {
    //   let td = tr.insertCell();
    //   td.appendChild(document.createTextNode(1));
    //   tr.appendChild(td);
    // })
  } 
}

getPricelist().then((pricelistAll) => {
  nameLineArray = ["Межевание земельного участка",
                   "Технический план",
                   "Акт обследования",
                   "Схема расположения",
                   "Вынос границ в натуру"];
  let table = new TableTitle(pricelistAll, nameLineArray);
  table.createTable();
});












