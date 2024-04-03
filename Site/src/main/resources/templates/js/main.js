document.querySelector('.nav-icon').addEventListener('click', function () {
	this.classList.toggle('nav-icon--active');
	document.querySelector('.block').classList.toggle('block--active');
});

window.setTimeout(() => {
	let slides = document.querySelectorAll('.owl-item');
	let titles = document.querySelectorAll('.slider-item__card-row-title') 

	let arrows = document.querySelectorAll('.arrow-icon');

	arrows.forEach(arrow => {
		arrow.addEventListener('click', function () {
			slides.forEach((item, id) => {
			if (item.classList.contains('center')) {
				titles[id].classList.add('active');
			} 
			else {
				titles[id].classList.remove('active');
			}
			})
		})
	})

	console.log(slides)
}, 1000)

let main_header = document.querySelector('#main-header');
let services = document.querySelector('#services');
let table = document.querySelector('#table');
let contact = document.querySelector('#contact');

main_header.addEventListener('click', function (){
	document.querySelector('.nav-icon').classList.remove('nav-icon--active')
	document.querySelector('.block').classList.remove('block--active');
	
	$('html, body').animate({
        scrollTop: $('#main-section').offset().top
    }, {
        duration: 370,   // по умолчанию «400» 
        easing: "linear" // по умолчанию «swing» 
    });
})

services.addEventListener('click', function (){
	document.querySelector('.nav-icon').classList.remove('nav-icon--active')
	document.querySelector('.block').classList.remove('block--active');
	
	$('html, body').animate({
        scrollTop: $('#services-section').offset().top +20
    }, {
        duration: 370,   // по умолчанию «400» 
        easing: "linear" // по умолчанию «swing» 
    });
})

table.addEventListener('click', function (){
	document.querySelector('.nav-icon').classList.remove('nav-icon--active')
	document.querySelector('.block').classList.remove('block--active');
	
	$('html, body').animate({
        scrollTop: $('#table-section').offset().top
    }, {
        duration: 370,   // по умолчанию «400» 
        easing: "linear" // по умолчанию «swing» 
    });
})

contact.addEventListener('click', function (){
	document.querySelector('.nav-icon').classList.remove('nav-icon--active')
	document.querySelector('.block').classList.remove('block--active');
	
	$('html, body').animate({
        scrollTop: $('#contacts-section').offset().top
    }, {
        duration: 370,   // по умолчанию «400» 
        easing: "linear" // по умолчанию «swing» 
    });
})

