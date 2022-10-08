var username = new URL(location.href).searchParams.get("username");
var user;

$(document).ready(function () {

    $(function () {
        $('[data-toggle="tooltip"]').tooltip();
    });

    getUsuario().then(function () {

        $("#mi-perfil-btn").attr("href", "profile.html?username=" + username);
        
        $("#user-username").html(user.username);

        getHabitacion(false, "ASC");

        $("#ordenar-piso").click(ordenarHabitacion);
    });

    async function getUsuario() {

        await $.ajax({
            type: "GET",
            dataType: "html",
            url: "./ServletUsuarioPedir",
            data: $.param({
                username: username
            }),
            success: function (result) {
                let parsedResult = JSON.parse(result);

                if (parsedResult !== false) {
                    user = parsedResult;
                } else {
                    console.log("Error recuperando los datos del usuario");
                }
            }
        });

    }
    function getHabitacion(ordenar, orden) {

        $.ajax({
            type: "GET",
            dataType: "html",
            url: "./ServletHabitacionListar",
            data: $.param({
                ordenar: ordenar,
                orden: orden
            }),
            success: function (result) {
                let parsedResult = JSON.parse(result);

                if (parsedResult !== false) {
                    mostrarHabitacion(parsedResult);
                } else {
                    console.log("Error recuperando los datos de las habitaciones");
                }
            }
        });
    }
    function mostrarHabitacion(habitacion) {

        let contenido = "";
        $.each(habitacion, function (index, habitacion) {
            habitacion = JSON.parse(habitacion);
            let precio_dia;
            if (habitacion.disponible > 0) {
                if (user.premium) {
                    if (habitacion.novedad) {
                        precio_dia = 45000;
                    } else {
                        precio_dia = 40000;
                    }
                } else {
                    if (habitacion.novedad) {
                        precio_dia = 60000;
                    } else {
                        precio_dia = 50000;
                    }
                }
                contenido += '<tr><th scope="row">' + habitacion.id_habitacion + '</th>' +
                        '<td>' + habitacion.piso + '</td>' +
                        '<td>' + habitacion.tipo + '</td>' +
                        '<td>' + habitacion.ciudad + '</td>' +
                        '<td>' + habitacion.disponible + '</td>' +
                        '<td><input type="checkbox" name="novedad" id_habitacion="novedad' + habitacion.id_habitacion + '" disabled ';
                if (habitacion.novedad) {
                    contenido += 'checked';
                }
                contenido += '></td>' +
                        '<td>' + precio_dia + '</td>' +
                        '<td><button onclick="reservarHabitacion(' + habitacion.id_habitacion + ',' + precio_dia + ');" class="btn btn-success" ';
              
                if (user.username) {
                contenido += ' enabled ';
            }

                contenido += '>Reservar</button></td></tr>';

            }
        });
        $("#habitacion-tbody").html(contenido);
    }

    function ordenarHabitacion() {
        if ($("#icono-ordenar").hasClass("fa-sort")) {
            getHabitacion(true, "ASC");
            $("#icono-ordenar").removeClass("fa-sort");
            $("#icono-ordenar").addClass("fa-sort-down");
        } else if ($("#icono-ordenar").hasClass("fa-sort-down")) {
            getHabitacion(true, "DESC");
            $("#icono-ordenar").removeClass("fa-sort-down");
            $("#icono-ordenar").addClass("fa-sort-up");
        } else if ($("#icono-ordenar").hasClass("fa-sort-up")) {
            getHabitacion(false, "ASC");
            $("#icono-ordenar").removeClass("fa-sort-up");
            $("#icono-ordenar").addClass("fa-sort");
        }
    }

});

function reservarHabitacion(id_habitacion, precio_dia) {

    $.ajax({
        type: "GET",
        dataType: "html",
        url: "./ServletHabitacionReservar",
        data: $.param({
            id_habitacion: id_habitacion,
            username: username
        }),
        success: function (result) {
            let parsedResult = JSON.parse(result);
            if (parsedResult !== false) {
                restarDinero(precio_dia).then(function () {
                    location.reload();
                });

            } else {
                console.log("Error en la reserva de la habitacion");
            }
        }
    });

}

async function restarDinero(precio_dia) {

    await $.ajax({
        type: "GET",
        dataType: "html",
        url: "./ServletUsuarioRestarDinero",
        data: $.param({
            username: username,
            saldo: parseFloat(user.saldo - precio_dia)
        }),
        success: function (result) {
            let parsedResult = JSON.parse(result);
            if (parsedResult !== false) {
                console.log("Saldo actualizado");
            } else {
                console.log("Error en el proceso de pago");
            }
        }
    });
}
