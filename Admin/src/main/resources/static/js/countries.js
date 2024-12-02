$('document').ready(function() {
    $('table #editButton').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $.get(href, function (country, status) {
            $('#idEdit').val(country.id);
            $('#nameEdit').val(country.name);
        });
        $('#editModal').modal();
    });
});