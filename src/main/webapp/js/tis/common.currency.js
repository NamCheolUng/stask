/*****************
 * 사용법
 * input text 의 class 에 currency 추가
 * form submit 전에 header.js 의 header.fn_onFormSubmit() 호출
 */

$(document).ready(function() {
	$(".currency").keyup(function() {
		var x = this.value.replace(/[^0-9]/g, '');
		this.value = x.replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,");
	});
	
	fn_numberToCurrency();
});

function fn_numberToCurrency() {
	$(".currency").each(function(obj) {
		var x = this.value.replace(/[^0-9]/g, '');
		this.value = x.replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,");
	});
}

function fn_currencyToNumner() {
	$(".currency").each(function(obj) {
		this.value = this.value.replace(',', '');
	});
}