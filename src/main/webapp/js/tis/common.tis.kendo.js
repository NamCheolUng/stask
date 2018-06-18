function fn_initSearchDatePicker(startKendoDatePickerName, endKendoDatePickerName) {
	var startKendoDatePicker = $("#" + startKendoDatePickerName).kendoDatePicker({
		change: function() {
			var startDate = this.value();
    		endKendoDatePicker.min(startDate);
	    },
		format: "yyyy/MM/dd"
	}).data("kendoDatePicker");
	
	var endKendoDatePicker = $("#" + endKendoDatePickerName).kendoDatePicker({
		change: function() {
			var endDate = this.value();
			startKendoDatePicker.max(endDate);
		},
		format: "yyyy/MM/dd"
	}).data("kendoDatePicker");
	
	$("#" + startKendoDatePickerName).attr("readonly", true);
	$("#" + endKendoDatePickerName).attr("readonly", true);

	startKendoDatePicker.max(endKendoDatePicker.value());
	endKendoDatePicker.min(startKendoDatePicker.value());
}
/*년월형태*/
function fn_initSearchDatePickerYearMonth(startKendoDatePickerName, endKendoDatePickerName) {
	var startKendoDatePicker = $("#" + startKendoDatePickerName).kendoDatePicker({
		change: function() {
			var startDate = this.value();
    		endKendoDatePicker.min(startDate);
	    },
		format: "yyyy/MM",
		start : "year",
		depth : "year",
	}).data("kendoDatePicker");
	
	var endKendoDatePicker = $("#" + endKendoDatePickerName).kendoDatePicker({
		change: function() {
			var endDate = this.value();
			startKendoDatePicker.max(endDate);
		},
		format: "yyyy/MM",
		start : "year",
		depth : "year",

	}).data("kendoDatePicker");
	
	$("#" + startKendoDatePickerName).attr("readonly", true);
	$("#" + endKendoDatePickerName).attr("readonly", true);

	startKendoDatePicker.max(endKendoDatePicker.value());
	endKendoDatePicker.min(startKendoDatePicker.value());
}
/*날짜+시간형태*/
function fn_initSearchDatePickerTime(startKendoDatePickerName, endKendoDatePickerName) {
	
	var startKendoDatePicker = $("#" + startKendoDatePickerName).kendoDateTimePicker({
		change: function() {
			var startDate = this.value();
    		endKendoDatePicker.min(startDate);
	    },
		format: "yyyy/MM/dd HH:mm",
		timeFormat:"HH:mm",
	}).data("kendoDateTimePicker");
	
	var endKendoDatePicker = $("#" + endKendoDatePickerName).kendoDateTimePicker({
		change: function() {
			var endDate = this.value();
			startKendoDatePicker.max(endDate);
		},
		format: "yyyy/MM/dd HH:mm",
		timeFormat:"HH:mm",
	}).data("kendoDateTimePicker");
	endKendoDatePicker.min(startKendoDatePicker.value());
}