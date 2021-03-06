$(document).ready(function () {


    $('[data-toggle="popover"]').popover();


    $('#datetimepicker1').datetimepicker({
        format: 'DD/MM/YYYY HH:mm',
        minDate: new Date(),
        stepping: 10,
        locale: getLang()
    });

    $("#free").click(enable_cb);
    $("#unlimited").click(enable);


    // We can attach the `fileselect` event to all file inputs on the page
    $(document).on('change', ':file', function() {
        var input = $(this),
            numFiles = input.get(0).files ? input.get(0).files.length : 1,
            label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
        input.trigger('fileselect', [numFiles, label]);
    });

    // We can watch for our custom `fileselect` event like this
    $(document).ready( function() {
        $(':file').on('fileselect', function(event, numFiles, label) {

            var input = $(this).parents('.input-group').find(':text'),
                log = numFiles > 1 ? numFiles + ' files selected' : label;

            if( input.length ) {
                input.val(log);
            } else {
                if( log ) alert(log);
            }

        });
    });


    $('.spinner .btn:first-of-type').on('click', function() {
        $('.spinner input').val( parseInt($('.spinner input').val(), 10) + 1);
    });
    $('.spinner .btn:last-of-type').on('click', function() {
        if($('.spinner input').val( parseInt($('.spinner input').val(), 10)).val() > 1){
            $('.spinner input').val( parseInt($('.spinner input').val(), 10) - 1);
        }

    });

    $(function() {
        $("#city")
            .autocomplete(
                {
                    source : 'http://localhost:8082/autocomplete',
                    minLength : 1,
                });
    });

});

function getLang()
{
    if (navigator.languages != undefined)
        return navigator.languages[0];
    else
        return navigator.language;
}

function enable_cb() {
    if (this.checked) {
        $("#exampleInputAmount").attr("disabled", true);
    } else {
        $("#exampleInputAmount").removeAttr("disabled");
    }
}

function enable() {
    if (this.checked) {
        $("#offerQuantity").attr("disabled", true);
    } else {
        $("#offerQuantity").removeAttr("disabled");
    }
}