/**
 *
 */
var currentCard;
var nextCard;
var prevCard;
var left;
var opacity;
var scale;
var animating;

$(".next").click(function() {
	if(animating) {
		return false;
	}

	animating = true;
	currentCard = $(this).closest("fieldset");
	nextCard = $(this).closest("fieldset").next();
	$("#progressbar li").eq($("fieldset").index(nextCard)).addClass("active");

	nextCard.show();
	currentCard.animate({opacity: 0}, {
		step: function(now, mx) {
			scale = 1 - (1 - now) * 0.2;
			left = (now * 50) + "%";
			opacity = 1 - now;
			currentCard.css({
				'transform' : 'scale('+scale+')',
				'position' : 'absolute'
			});

			nextCard.css({'left': left, 'opacity': opacity});
		},
		duration: 800,
		complete: function() {
			currentCard.hide();
			animating = false;
		},
		easing: 'swing'
	});
	
});

$(".previous").click(function() {
	if(animating) {
		return false;
	}
	animating = true;
	currentCard = $(this).closest("fieldset");
	prevCard = $(this).closest("fieldset").prev();
	$("#progressbar li").eq($("fieldset").index(currentCard)).removeClass("active");

	prevCard.show();
	currentCard.animate({opacity: 0}, {
		step: function(now, mx) {
			scale = 0.8 + (1 - now) * 0.2;
			left = ((1 - now) * 50) + "%";
			opacity = 1 - now;
			currentCard.css({
				'left': left
			});
			prevCard.css({
				'transform': 'scale('+scale+')', 
				'opacity': opacity
			});
		},
		duration: 800,
		complete: function() {
			currentCard.hide();
			animating = false;
			//adjustHeight();
		},
		easing: 'swing'
	});
	
});

function adjustHeight() {
	var totalHeight = 0;
	var activeFieldsetHeight = $("fieldset:visible").outerHeight(true);
	var mainTitleHeight = $(".main-title").outerHeight(true);
	var progressbarHeight = $("#progreebar").outerHeight(true);
	var selectLangFormHeight = $(".select-lang-form").outerHeight(true);
	
	console.log(activeFieldsetHeight + mainTitleHeight + progressbarHeight + selectLangFormHeight);
	
	totalHeight += (activeFieldsetHeight + mainTitleHeight + progressbarHeight + selectLangFormHeight);
	$("#registForm").css("height", totalHeight+"px");
}
