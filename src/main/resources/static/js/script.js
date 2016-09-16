$(document).ready(function () {


    var d = new Date();
    var strDate =  d.getDate() + "/" + (d.getMonth()+1) + "/" + d.getFullYear();

    $('.input-group.date').datepicker({
        format: "dd/mm/yyyy",
        startDate: strDate,
        autoclose: true,
        todayHighlight: true
    });
});

