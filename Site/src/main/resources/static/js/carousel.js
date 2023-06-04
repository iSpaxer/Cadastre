$(document).ready(function () {
	// Находим блок с слайдами
	const owl = $('.slider');
	// Запускаем карусель
	owl.owlCarousel({
        slideBy:1,
        center: true,
        loop: true
	});

	// Находим кастомные кнопки Вперед / Назад
	const prev = $('.slider-services__arrow--left');
	const next = $('.slider-services__arrow--right');

	// Клик на кнопку Назад и прокрутка карусели
	prev.click(function () {
		owl.trigger('prev.owl.carousel', [1000] );
	});

	// Клик на кнопку Вперед и прокрутка карусели
	next.click(function () {
		owl.trigger('next.owl.carousel', [1000] );
	});
});

