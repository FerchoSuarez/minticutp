var username = new URL(location.href).searchParams.get("username");
var user;

$(document).ready(function () {


    fillUsuario().then(function () {
        
        getReservas(user.username);
        
    });

    $("#reservar-btn").attr("href", `home.html?username=${username}`);

    $("#form-modificar").on("submit", function (event) {

        event.preventDefault();
        modificarUsuario();
    });

    $("#aceptar-eliminar-cuenta-btn").click(function () {

        eliminarCuenta().then(function () {
            location.href = "index.html";
        });
    });

});

async function fillUsuario() {
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

                $("#input-username").val(parsedResult.username);
                $("#input-contrasena").val(parsedResult.contrasena);
                $("#input-nombre").val(parsedResult.nombre);
                $("#input-apellidos").val(parsedResult.apellidos);
                $("#input-ciudad_origen").val(parsedResult.ciudad_origen);
                $("#input-email").val(parsedResult.email);
                $("#input-celular").val(parsedResult.celular);
                $("#input-premium").prop("checked", parsedResult.premium);

            } else {
                console.log("Error recuperando los datos del usuario");
            }
        }
    });
}

function getReservas(username) {

    $.ajax({
        type: "GET",
        dataType: "html",
        url: "./ServletReservarListar",
        data: $.param({
            username: username
        }),
        success: function (result) {
            let parsedResult = JSON.parse(result);

            if (parsedResult !== false) {

                mostrarHistorial(parsedResult);

            } else {
                console.log("Error recuperando los datos de las reservas");
            }
        }
    });
}

function mostrarHistorial(habitacion) {
    let contenido = "";
    if (habitacion.length >= 1) {
        $.each(habitacion, function (index, habitacion) {
            habitacion = JSON.parse(habitacion);

            contenido += '<tr><th scope="row">' + habitacion.id_habitacion + '</th>' +
                    '<td>' + habitacion.piso + '</td>' +
                    '<td>' + habitacion.tipo + '</td>' +
                    '<td><input type="checkbox" name="novedad" id_habitacion="novedad' + habitacion.id_habitacion
                    + '" disabled ';
            if (habitacion.novedad) {
                contenido += 'checked';
            }
            contenido += '></td><td>' + habitacion.fecha_ingreso + '</td>' +
                    '<td><button id_habitacion="devolver-btn" onclick= "devolverHabitacion(' + habitacion.id_habitacion
                    + ');" class="btn btn-danger">Devolver habitacion</button></td></tr>';

        });
        $("#historial-tbody").html(contenido);
        $("#historial-table").removeClass("d-none");
        $("#historial-vacio").addClass("d-none");

    } else {
        $("#historial-vacio").removeClass("d-none");
        $("#historial-table").addClass("d-none");
    }
}


function devolverHabitacion(id_habitacion) {

    $.ajax({
        type: "GET",
        dataType: "html",
        url: "./ServletHabitacionDevolver",
        data: $.param({
            username: username,
            id_habitacion: id_habitacion
        }),
        success: function (result) {

            if (result !== false) {

                location.reload();

            } else {
                console.log("Error devolviendo el Habitacion");
            }
        }
    });

}

function modificarUsuario() {

    let username = $("#input-username").val();
    let contrasena = $("#input-contrasena").val();
    let nombre = $("#input-nombre").val();
    let apellidos = $("#input-apellidos").val();
    let ciudad_origen = $("#input-ciudad_origen").val();
    let email = $("#input-email").val();
    let celular = $("#input-celular").val();
    let premium = $("#input-premium").prop('checked');
    $.ajax({
        type: "GET",
        dataType: "html",
        url: "./ServletUsuarioModificar",
        data: $.param({
            username: username,
            contrasena: contrasena,
            nombre: nombre,
            apellidos: apellidos,
            ciudad_origen : ciudad_origen,
            email: email,
            celular: celular,
            premium: premium
        }),
        success: function (result) {

            if (result !== false) {
                $("#modificar-error").addClass("d-none");
                $("#modificar-exito").removeClass("d-none");
            } else {
                $("#modificar-error").removeClass("d-none");
                $("#modificar-exito").addClass("d-none");
            }

            setTimeout(function () {
                location.reload();
            }, 3000);

        }
    });

}

async function eliminarCuenta() {

    await $.ajax({
        type: "GET",
        dataType: "html",
        url: "./ServletUsuarioEliminar",
        data: $.param({
            username: username
        }),
        success: function (result) {

            if (result !== false) {

                console.log("Usuario eliminado");

            } else {
                console.log("Error eliminando el usuario");
            }
        }
    });
}




