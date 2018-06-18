/* modal */
$('#modal01').on('shown.bs.modal', function () {
	$('#modal01').focus()
})

$('#modal02').on('shown.bs.modal', function () {
	$('#modal02').focus()
})

$('#modal03').on('shown.bs.modal', function () {
	$('#modal03').focus()
})

$('#modal04').on('shown.bs.modal', function () {
	$('#modal04').focus()
})

/* kendo ui input */
$(document).ready(function () {
  $(".datetimepicker").kendoDateTimePicker({
	  //value: new Date(),
	  format: "yyyy/MM/dd HH:mm",
	  dateInput: false,
	  interval: 60
  });

  $(".datepicker").kendoDatePicker({
	  format: "yyyy/MM/dd"
  });
  $(".select").kendoDropDownList();
  $(".files").kendoUpload();
});

/* popover */
$(function () {
  $('[data-toggle="popover"]').popover()
})

/* tooltip */
$(function () {
$('[data-toggle="tooltip"]').tooltip()
})