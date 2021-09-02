let url = "http://vmi632710.contaboserver.net:8090/api";
let f = new Date();
let fecha = f.getDate() + "/" + (f.getMonth() + 1) + "/" + f.getFullYear();
var xmlhttp = new XMLHttpRequest();
var xmlhttp_aux = new XMLHttpRequest();
var respuesta = "";
var velocidad = '0';
var colorg = "#ffffff";

//SLIDER

function rangoSlider(value) {
    document.getElementById('valor_velocidad').innerHTML = value;
    velocidad = value;
}

//BOTON MOVER ADELANTE

function mover_adelante() {

    document.getElementById('boton_adelante').style.backgroundColor = "#abd9f5";

    let h = new Date();
    let hora = "-" + h.getHours() + ":" + h.getMinutes() + ":" + h.getSeconds();
    let api = "/moveradelante/enviar_izquierdo";
    let api_aux = "/moveradelante/enviar_derecho";
    var movimiento;
    var movimiento_aux;
    let fallo;
    let fallo_aux;
    xmlhttp.timeout = 10000
    xmlhttp_aux.timeout = 10000
    xmlhttp.onerror = function () {
        swal({
            title: "Atención!",
            text: "No se estableció conexión con el servicio mover adelante.",
            icon: "warning",
        });
    }
    xmlhttp.open("POST", url + api);
    xmlhttp_aux.open("POST", url + api_aux);
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp_aux.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp.send(JSON.stringify({ "movimiento": "Adelante" + velocidad, "fecha": fecha + hora }));
    xmlhttp_aux.send(JSON.stringify({ "movimiento": "Adelante" + velocidad, "fecha": fecha + hora }));
    xmlhttp.onload = function () {
        if (xmlhttp.status == 200) {
            respuesta = JSON.parse(this.responseText);
            movimiento = respuesta.movimiento;
        }
        else {
            fallo = "true";

        }
    }
    xmlhttp_aux.onload = function () {
        if (xmlhttp_aux.status == 200) {
            respuesta_aux = JSON.parse(this.responseText);
            movimiento_aux = respuesta_aux.movimiento;
        }
        else {
            fallo_aux = "true";
        }
    }
    setTimeout(() => {
        if (xmlhttp.status == 200 || xmlhttp_aux.status == 200) {
            if (fallo == "true" || fallo_aux == "true") {
                swal({
                    title: "Error!",
                    text: "Ocurrio un error inesperado: " + xmlhttp.status + ".",
                    icon: "error",
                });
            }
            else {
                if (movimiento == "true" && movimiento_aux == "true") {
                    swal({
                        title: "Realizado!",
                        text: "El robot se movió adelante.",
                        icon: "success",
                    });
                }
                else if (movimiento == "Obstaculo" && movimiento_aux == "Obstaculo") {
                    swal({
                        title: "Cuidado!",
                        text: "Existe un obstáculo adelante.",
                        icon: "warning",
                    });
                }
                else {
                    swal({
                        title: "Fallido!",
                        text: "El robot no pudo moverse adelante.",
                        icon: "error",
                    });
                }
            }
        };
    }, 2000);
    xmlhttp.ontimeout = function (e) {
        console.log("Timeout")
    };
    parar_adelante();
}

function parar_adelante() {
    document.getElementById('boton_adelante').style.backgroundColor = "";


    /*     xmlhttp.open("GET", url + "/ver");
        xmlhttp.send();
        xmlhttp.onreadystatechange = function(){
            if(this.readyState==4 && this.status==200){
                console.log(xmlhttp.responseText)
            }
        } */
}

//BOTON MOVER ATRAS

function mover_atras() {

    document.getElementById('boton_atras').style.backgroundColor = "#abd9f5";

    let h = new Date();
    let hora = "-" + h.getHours() + ":" + h.getMinutes() + ":" + h.getSeconds();
    let api = "/moveratras/enviar_izquierdo";
    let api_aux = "/moveratras/enviar_derecho";
    var movimiento;
    var movimiento_aux;
    let fallo;
    let fallo_aux;
    xmlhttp.timeout = 10000
    xmlhttp_aux.timeout = 10000
    xmlhttp.onerror = function () {
        swal({
            title: "Atención!",
            text: "No se estableció conexión con el servicio mover atras.",
            icon: "warning",
        });
    }
    xmlhttp.open("POST", url + api);
    xmlhttp_aux.open("POST", url + api_aux);
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp_aux.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp.send(JSON.stringify({ "movimiento": "Atras" + velocidad, "fecha": fecha + hora }));
    xmlhttp_aux.send(JSON.stringify({ "movimiento": "Atras" + velocidad, "fecha": fecha + hora }));
    xmlhttp.onload = function () {
        if (xmlhttp.status == 200) {
            respuesta = JSON.parse(this.responseText);
            movimiento = respuesta.movimiento;
        }
        else {
            fallo = "true";

        }
    }
    xmlhttp_aux.onload = function () {
        if (xmlhttp_aux.status == 200) {
            respuesta_aux = JSON.parse(this.responseText);
            movimiento_aux = respuesta_aux.movimiento;
        }
        else {
            fallo_aux = "true";
        }
    }
    setTimeout(() => {
        if (xmlhttp.status == 200 || xmlhttp_aux.status == 200) {
            if (fallo == "true" || fallo_aux == "true") {
                swal({
                    title: "Error!",
                    text: "Ocurrio un error inesperado: " + xmlhttp.status + ".",
                    icon: "error",
                });
            }
            else {
                if (movimiento == "true" && movimiento_aux == "true") {
                    swal({
                        title: "Realizado!",
                        text: "El robot se movió atras.",
                        icon: "success",
                    });
                }
                else {
                    swal({
                        title: "Fallido!",
                        text: "El robot no pudo moverse atrás.",
                        icon: "error",
                    });
                }
            }
        };
    }, 2000);
    parar_atras();

}

function parar_atras() {
    document.getElementById('boton_atras').style.backgroundColor = "";

}


//BOTON MOVER IZQUIERDA

function mover_izquierda() {

    document.getElementById('boton_izquierda').style.backgroundColor = "#abd9f5";

    let h = new Date();
    let hora = "-" + h.getHours() + ":" + h.getMinutes() + ":" + h.getSeconds();
    let api = "/moverizquierda/enviar_izquierdo";
    let api_aux = "/moverizquierda/enviar_derecho";
    var movimiento;
    var movimiento_aux;
    let fallo;
    let fallo_aux;
    xmlhttp.timeout = 10000
    xmlhttp_aux.timeout = 10000
    xmlhttp.onerror = function () {
        swal({
            title: "Atención!",
            text: "No se estableció conexión con el servicio mover izquierda.",
            icon: "warning",
        });
    }
    xmlhttp.open("POST", url + api);
    xmlhttp_aux.open("POST", url + api_aux);
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp_aux.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp.send(JSON.stringify({ "movimiento": "Izquierda" + velocidad, "fecha": fecha + hora }));
    xmlhttp_aux.send(JSON.stringify({ "movimiento": "Izquierda" + velocidad, "fecha": fecha + hora }));
    xmlhttp.onload = function () {
        if (xmlhttp.status == 200) {
            respuesta = JSON.parse(this.responseText);
            movimiento = respuesta.movimiento;
        }
        else {
            fallo = "true";

        }
    }
    xmlhttp_aux.onload = function () {
        if (xmlhttp_aux.status == 200) {
            respuesta_aux = JSON.parse(this.responseText);
            movimiento_aux = respuesta_aux.movimiento;
        }
        else {
            fallo_aux = "true";
        }
    }
    setTimeout(() => {
        if (xmlhttp.status == 200 || xmlhttp_aux.status == 200) {
            if (fallo == "true" || fallo_aux == "true") {
                swal({
                    title: "Error!",
                    text: "Ocurrio un error inesperado: " + xmlhttp.status + ".",
                    icon: "error",
                });
            }
            else {
                if (movimiento == "true" && movimiento_aux == "true") {
                    swal({
                        title: "Realizado!",
                        text: "El robot se movió a la izquierda.",
                        icon: "success",
                    });
                }
                else {
                    swal({
                        title: "Fallido!",
                        text: "El robot no pudo moverse a la izquierda.",
                        icon: "error",
                    });
                }
            }
        };
    }, 2000);
    parar_izquierda();
}

function parar_izquierda() {
    document.getElementById('boton_izquierda').style.backgroundColor = ""

}


//BOTON MOVER DERECHA

function mover_derecha() {

    document.getElementById('boton_derecha').style.backgroundColor = "#abd9f5";

    let h = new Date();
    let hora = "-" + h.getHours() + ":" + h.getMinutes() + ":" + h.getSeconds();
    let api = "/moverderecha/enviar_izquierdo";
    let api_aux = "/moverderecha/enviar_derecho";
    var movimiento;
    var movimiento_aux;
    let fallo;
    let fallo_aux;
    xmlhttp.timeout = 10000
    xmlhttp_aux.timeout = 10000
    xmlhttp.onerror = function () {
        swal({
            title: "Atención!",
            text: "No se estableció conexión con el servicio mover derecha.",
            icon: "warning",
        });
    }
    xmlhttp.open("POST", url + api);
    xmlhttp_aux.open("POST", url + api_aux);
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp_aux.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp.send(JSON.stringify({ "movimiento": "Derecha" + velocidad, "fecha": fecha + hora }));
    xmlhttp_aux.send(JSON.stringify({ "movimiento": "Derecha" + velocidad, "fecha": fecha + hora }));
    xmlhttp.onload = function () {
        if (xmlhttp.status == 200) {
            respuesta = JSON.parse(this.responseText);
            movimiento = respuesta.movimiento;
        }
        else {
            fallo = "true";

        }
    }
    xmlhttp_aux.onload = function () {
        if (xmlhttp_aux.status == 200) {
            respuesta_aux = JSON.parse(this.responseText);
            movimiento_aux = respuesta_aux.movimiento;
        }
        else {
            fallo_aux = "true";
        }
    }
    setTimeout(() => {
        if (xmlhttp.status == 200 || xmlhttp_aux.status == 200) {
            if (fallo == "true" || fallo_aux == "true") {
                swal({
                    title: "Error!",
                    text: "Ocurrio un error inesperado: " + xmlhttp.status + ".",
                    icon: "error",
                });
            }
            else {
                if (movimiento == "true" && movimiento_aux == "true") {
                    swal({
                        title: "Realizado!",
                        text: "El robot se movió a la derecha.",
                        icon: "success",
                    });
                }
                else {
                    swal({
                        title: "Fallido!",
                        text: "El robot no pudo moverse a la derecha.",
                        icon: "error",
                    });
                }
            }
        };
    }, 2000);
    parar_derecha();
}

function parar_derecha() {
    document.getElementById('boton_derecha').style.backgroundColor = ""

}

//BOTON MOVER BRAZO ARRIBA

function mover_brazoarriba() {

    document.getElementById('boton_brazoarriba').style.backgroundColor = "#abd9f5";

    let h = new Date();
    let hora = "-" + h.getHours() + ":" + h.getMinutes() + ":" + h.getSeconds();
    let api = "/subirbrazo/enviar";
    xmlhttp.onerror = function () {
        swal({
            title: "Atención!",
            text: "No se estableció conexión con el servicio subir brazo.",
            icon: "warning",
        });
    }
    xmlhttp.open("POST", url + api);
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp.send(JSON.stringify({ "movimiento": "Subir Brazo", "fecha": fecha + hora }));
    xmlhttp.onload = function () {
        if (xmlhttp.status == 200) {
            respuesta = JSON.parse(this.responseText);
            if (respuesta.movimiento == "true") {
                swal({
                    title: "Realizado!",
                    text: "El brazo subió.",
                    icon: "success",
                });
            }
            else if (respuesta.movimiento == "full") {
                swal({
                    title: "No es posible!",
                    text: "El brazo no puede subir más.",
                    icon: "info",
                });
            }
            else {
                swal({
                    title: "Fallido!",
                    text: "El brazo no pudo subir.",
                    icon: "error",
                });
            }
        }
        else {
            swal({
                title: "Error!",
                text: "Ocurrio un error inesperado: " + xmlhttp.status + ".",
                icon: "error",
            });
        }
    }
    parar_brazoarriba();
}

function parar_brazoarriba() {
    document.getElementById('boton_brazoarriba').style.backgroundColor = ""

}

//BOTON MOVER BRAZO ABAJO

function mover_brazoabajo() {

    document.getElementById('boton_brazoabajo').style.backgroundColor = "#abd9f5";

    let h = new Date();
    let hora = "-" + h.getHours() + ":" + h.getMinutes() + ":" + h.getSeconds();
    let api = "/bajarbrazo/enviar";
    xmlhttp.onerror = function () {
        swal({
            title: "Atención!",
            text: "No se estableció conexión con el servicio bajar brazo.",
            icon: "warning",
        });
    }
    xmlhttp.open("POST", url + api);
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp.send(JSON.stringify({ "movimiento": "Bajar Brazo", "fecha": fecha + hora }));
    xmlhttp.onload = function () {
        if (xmlhttp.status == 200) {
            respuesta = JSON.parse(this.responseText);
            if (respuesta.movimiento == "true") {
                swal({
                    title: "Realizado!",
                    text: "El brazo bajó.",
                    icon: "success",
                });
            }
            else if (respuesta.movimiento == "full") {
                swal({
                    title: "No es posible!",
                    text: "El brazo no puede bajar más.",
                    icon: "info",
                });
            }
            else {
                swal({
                    title: "Fallido!",
                    text: "El brazo no pudo bajar.",
                    icon: "error",
                });
            }
        }
        else {
            swal({
                title: "Error!",
                text: "Ocurrio un error inesperado: " + xmlhttp.status + ".",
                icon: "error",
            });
        }
    }
    parar_brazoabajo();
}

function parar_brazoabajo() {
    document.getElementById('boton_brazoabajo').style.backgroundColor = ""

}

//BOTON MOVER ANTEBRAZO ARRIBA

function mover_antebrazoarriba() {

    document.getElementById('boton_antebrazoarriba').style.backgroundColor = "#abd9f5";

    let h = new Date();
    let hora = "-" + h.getHours() + ":" + h.getMinutes() + ":" + h.getSeconds();
    let api = "/subirantebrazo/enviar";
    xmlhttp.onerror = function () {
        swal({
            title: "Atención!",
            text: "No se estableció conexión con el servicio subir antebrazo.",
            icon: "warning",
        });
    }
    xmlhttp.open("POST", url + api);
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp.send(JSON.stringify({ "movimiento": "Subir Antebrazo", "fecha": fecha + hora }));
    xmlhttp.onload = function () {
        if (xmlhttp.status == 200) {
            respuesta = JSON.parse(this.responseText);
            if (respuesta.movimiento == "true") {
                swal({
                    title: "Realizado!",
                    text: "El antebrazo subió.",
                    icon: "success",
                });
            }
            else if (respuesta.movimiento == "full") {
                swal({
                    title: "No es posible!",
                    text: "El antebrazo no puede subir más.",
                    icon: "info",
                });
            }
            else {
                swal({
                    title: "Fallido!",
                    text: "El antebrazo no pudo subir.",
                    icon: "error",
                });
            }
        }
        else {
            swal({
                title: "Error!",
                text: "Ocurrio un error inesperado: " + xmlhttp.status + ".",
                icon: "error",
            });
        }
    }
    parar_antebrazoarriba();

}

function parar_antebrazoarriba() {
    document.getElementById('boton_antebrazoarriba').style.backgroundColor = ""

}

//BOTON MOVER ANTEBRAZO ABAJO

function mover_antebrazoabajo() {

    document.getElementById('boton_antebrazoabajo').style.backgroundColor = "#abd9f5";

    let h = new Date();
    let hora = "-" + h.getHours() + ":" + h.getMinutes() + ":" + h.getSeconds();
    let api = "/bajarantebrazo/enviar";
    xmlhttp.onerror = function () {
        swal({
            title: "Atención!",
            text: "No se estableció conexión con el servicio bajar antebrazo.",
            icon: "warning",
        });
    }
    xmlhttp.open("POST", url + api);
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp.send(JSON.stringify({ "movimiento": "Bajar Antebrazo", "fecha": fecha + hora }));
    xmlhttp.onload = function () {
        if (xmlhttp.status == 200) {
            respuesta = JSON.parse(this.responseText);
            if (respuesta.movimiento == "true") {
                swal({
                    title: "Realizado!",
                    text: "El antebrazo bajó.",
                    icon: "success",
                });
            }
            else if (respuesta.movimiento == "full") {
                swal({
                    title: "No es posible!",
                    text: "El antebrazo no puede bajar más.",
                    icon: "info",
                });
            }
            else {
                swal({
                    title: "Fallido!",
                    text: "El antebrazo no pudo bajar.",
                    icon: "error",
                });
            }
        }
        else {
            swal({
                title: "Error!",
                text: "Ocurrio un error inesperado: " + xmlhttp.status + ".",
                icon: "error",
            });
        }
    }
    parar_antebrazoabajo();
}

function parar_antebrazoabajo() {
    document.getElementById('boton_antebrazoabajo').style.backgroundColor = ""

}

//BOTON MOVER MANO IZQUIERDA

function mover_manoizquierda() {

    document.getElementById('boton_manoizquierda').style.backgroundColor = "#abd9f5";

    let h = new Date();
    let hora = "-" + h.getHours() + ":" + h.getMinutes() + ":" + h.getSeconds();
    let api = "/manoizquierda/enviar";
    xmlhttp.onerror = function () {
        swal({
            title: "Atención!",
            text: "No se estableció conexión con el servicio mano a la izquierda.",
            icon: "warning",
        });
    }
    xmlhttp.open("POST", url + api);
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp.send(JSON.stringify({ "movimiento": "Mano a la Izquierda", "fecha": fecha + hora }));
    xmlhttp.onload = function () {
        if (xmlhttp.status == 200) {
            respuesta = JSON.parse(this.responseText);
            if (respuesta.movimiento == "true") {
                swal({
                    title: "Realizado!",
                    text: "La mano se movio a la izquierda.",
                    icon: "success",
                });
            }
            else if (respuesta.movimiento == "full") {
                swal({
                    title: "No es posible!",
                    text: "La mano ya no se puede mover a la izquierda.",
                    icon: "info",
                });
            }
            else {
                swal({
                    title: "Fallido!",
                    text: "La mano no pudo moverse a la izquierda.",
                    icon: "error",
                });
            }
        }
        else {
            swal({
                title: "Error!",
                text: "Ocurrio un error inesperado: " + xmlhttp.status + ".",
                icon: "error",
            });
        }
    }
    parar_manoizquierda();
}

function parar_manoizquierda() {
    document.getElementById('boton_manoizquierda').style.backgroundColor = ""

}

//BOTON MOVER MANO DERECHA

function mover_manoderecha() {

    document.getElementById('boton_manoderecha').style.backgroundColor = "#abd9f5";

    let h = new Date();
    let hora = "-" + h.getHours() + ":" + h.getMinutes() + ":" + h.getSeconds();
    let api = "/manoderecha/enviar";
    xmlhttp.onerror = function () {
        swal({
            title: "Atención!",
            text: "No se estableció conexión con el servicio mano a la derecha.",
            icon: "warning",
        });
    }
    xmlhttp.open("POST", url + api);
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp.send(JSON.stringify({ "movimiento": "Mano a la Derecha", "fecha": fecha + hora }));
    xmlhttp.onload = function () {
        if (xmlhttp.status == 200) {
            respuesta = JSON.parse(this.responseText);
            if (respuesta.movimiento == "true") {
                swal({
                    title: "Realizado!",
                    text: "La mano se movio a la derecha.",
                    icon: "success",
                });
            }
            else if (respuesta.movimiento == "full") {
                swal({
                    title: "No es posible!",
                    text: "La mano ya no se puede mover a la derecha.",
                    icon: "info",
                });
            }
            else {
                swal({
                    title: "Fallido!",
                    text: "La mano no pudo moverse a la derecha.",
                    icon: "error",
                });
            }
        }
        else {
            swal({
                title: "Error!",
                text: "Ocurrio un error inesperado: " + xmlhttp.status + ".",
                icon: "error",
            });
        }
    }
    parar_manoderecha();
}

function parar_manoderecha() {
    document.getElementById('boton_manoderecha').style.backgroundColor = ""

}

//BOTON MOVER ABRIR PINZA

function mover_abrirpinza() {

    document.getElementById('boton_abrirpinza').style.backgroundColor = "#abd9f5";

    let h = new Date();
    let hora = "-" + h.getHours() + ":" + h.getMinutes() + ":" + h.getSeconds();
    let api = "/abrirpinza/enviar";
    xmlhttp.onerror = function () {
        swal({
            title: "Atención!",
            text: "No se estableció conexión con el servicio abrir pinza.",
            icon: "warning",
        });
    }
    xmlhttp.open("POST", url + api);
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp.send(JSON.stringify({ "movimiento": "Abrir Pinza", "fecha": fecha + hora }));
    xmlhttp.onload = function () {
        if (xmlhttp.status == 200) {
            respuesta = JSON.parse(this.responseText);
            if (respuesta.movimiento == "true") {
                swal({
                    title: "Realizado!",
                    text: "La pinza se abrio.",
                    icon: "success",
                });
            }
            else if (respuesta.movimiento == "full") {
                swal({
                    title: "No es posible!",
                    text: "La pinza ya se encuentra abierta.",
                    icon: "info",
                });
            }
            else {
                swal({
                    title: "Fallido!",
                    text: "La pinza no pudo abrirse.",
                    icon: "error",
                });
            }
        }
        else {
            swal({
                title: "Error!",
                text: "Ocurrio un error inesperado: " + xmlhttp.status + ".",
                icon: "error",
            });
        }
    }
    parar_abrirpinza();

}

function parar_abrirpinza() {
    document.getElementById('boton_abrirpinza').style.backgroundColor = "";

}

//BOTON MOVER CERRAR PINZA

function mover_cerrarpinza() {

    document.getElementById('boton_cerrarpinza').style.backgroundColor = "#abd9f5";

    let h = new Date();
    let hora = "-" + h.getHours() + ":" + h.getMinutes() + ":" + h.getSeconds();
    let api = "/cerrarpinza/enviar";
    xmlhttp.onerror = function () {
        swal({
            title: "Atención!",
            text: "No se estableció conexión con el servicio cerrar pinza.",
            icon: "warning",
        });
    }
    xmlhttp.open("POST", url + api);
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp.send(JSON.stringify({ "movimiento": "Cerrar Pinza", "fecha": fecha + hora }));
    xmlhttp.onload = function () {
        if (xmlhttp.status == 200) {
            respuesta = JSON.parse(this.responseText);
            if (respuesta.movimiento == "true") {
                swal({
                    title: "Realizado!",
                    text: "La pinza se cerro.",
                    icon: "success",
                });
            }
            else if (respuesta.movimiento == "full") {
                swal({
                    title: "No es posible!",
                    text: "La pinza ya se encuentra cerrada.",
                    icon: "info",
                });
            }
            else {
                swal({
                    title: "Fallido!",
                    text: "La pinza no pudo cerrarse.",
                    icon: "error",
                });
            }
        }
        else {
            swal({
                title: "Error!",
                text: "Ocurrio un error inesperado: " + xmlhttp.status + ".",
                icon: "error",
            });
        }
    }
    parar_cerrarpinza();
}

function parar_cerrarpinza() {
    document.getElementById('boton_cerrarpinza').style.backgroundColor = ""

}


//BOTON MOVER CAMARA ARRIBA

function mover_camaraarriba() {

    document.getElementById('boton_camaraarriba').style.backgroundColor = "#abd9f5";

    let h = new Date();
    let hora = "-" + h.getHours() + ":" + h.getMinutes() + ":" + h.getSeconds();
    let api = "/subircamara/enviar";
    xmlhttp.onerror = function () {
        swal({
            title: "Atención!",
            text: "No se estableció conexión con el servicio subir cámara.",
            icon: "warning",
        });
    }
    xmlhttp.open("POST", url + api);
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp.send(JSON.stringify({ "movimiento": "Subir Camara", "fecha": fecha + hora }));
    xmlhttp.onload = function () {
        if (xmlhttp.status == 200) {
            respuesta = JSON.parse(this.responseText);
            if (respuesta.movimiento == "true") {
                swal({
                    title: "Realizado!",
                    text: "La cámara subió.",
                    icon: "success",
                });
            }
            else if (respuesta.movimiento == "full") {
                swal({
                    title: "No es posible!",
                    text: "La cámara no puede subir más.",
                    icon: "info",
                });
            }
            else {
                swal({
                    title: "Fallido!",
                    text: "La cámara no pudo subir.",
                    icon: "error",
                });
            }
        }
        else {
            swal({
                title: "Error!",
                text: "Ocurrio un error inesperado: " + xmlhttp.status + ".",
                icon: "error",
            });
        }
    }
    parar_camaraarriba();
}

function parar_camaraarriba() {
    document.getElementById('boton_camaraarriba').style.backgroundColor = ""

}

//BOTON MOVER CAMARA ABAJO

function mover_camaraabajo() {

    document.getElementById('boton_camaraabajo').style.backgroundColor = "#abd9f5";

    let h = new Date();
    let hora = "-" + h.getHours() + ":" + h.getMinutes() + ":" + h.getSeconds();
    let api = "/bajarcamara/enviar";
    xmlhttp.onerror = function () {
        swal({
            title: "Atención!",
            text: "No se estableció conexión con el servicio bajar cámara.",
            icon: "warning",
        });
    }
    xmlhttp.open("POST", url + api);
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp.send(JSON.stringify({ "movimiento": "Bajar Camara", "fecha": fecha + hora }));
    xmlhttp.onload = function () {
        if (xmlhttp.status == 200) {
            respuesta = JSON.parse(this.responseText);
            if (respuesta.movimiento == "true") {
                swal({
                    title: "Realizado!",
                    text: "La cámara bajó.",
                    icon: "success",
                });
            }
            else if (respuesta.movimiento == "full") {
                swal({
                    title: "No es posible!",
                    text: "La cámara no puede bajar más.",
                    icon: "info",
                });
            }
            else {
                swal({
                    title: "Fallido!",
                    text: "La cámara no pudo bajar.",
                    icon: "error",
                });
            }
        }
        else {
            swal({
                title: "Error!",
                text: "Ocurrio un error inesperado: " + xmlhttp.status + ".",
                icon: "error",
            });
        }
    }
    parar_camaraabajo();
}

function parar_camaraabajo() {
    document.getElementById('boton_camaraabajo').style.backgroundColor = ""

}

//BOTONES LUCES 
//BOTON ENCENDIDO Y APAGADO
var clicked = false;
function toggle() {
    if (!clicked) {
        clicked = true;
        document.getElementById('boton_lp').innerHTML = "Encendidas";
        document.getElementById('boton_lp').style.background = "#abd9f5";
        let h = new Date();
        let hora = "-" + h.getHours() + ":" + h.getMinutes() + ":" + h.getSeconds();
        let api = "/encenderluces/enviar";
        xmlhttp.onerror = function () {
            swal({
                title: "Atención!",
                text: "No se estableció conexión con el servicio encender luces.",
                icon: "warning",
            });
        }
        xmlhttp.open("POST", url + api);
        //xmlhttp.open("POST", "https://springrobotapp.ddns.net:443/api/luces");
        xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
        xmlhttp.send(JSON.stringify({ "accion": "Encender", "fecha": fecha + hora }));
        xmlhttp.onload = function () {
            if (xmlhttp.status == 200) {
                respuesta = JSON.parse(this.responseText);
                if (respuesta.accion == "true") {
                    swal({
                        title: "Realizado!",
                        text: "Las luces se han encendido.",
                        icon: "success",
                    });
                }
                else {
                    swal({
                        title: "Fallido!",
                        text: "Las luces no pudieron encenderse.",
                        icon: "error",
                    });
                }
            }
            else {
                swal({
                    title: "Error!",
                    text: "Ocurrio un error inesperado: " + xmlhttp.status + ".",
                    icon: "error",
                });
            }
        }
    }
    //APAGADO
    else {

        clicked = false;
        document.getElementById('boton_lp').innerHTML = "Apagadas";
        document.getElementById('boton_lp').style.background = "#fff";
        let h = new Date();
        let hora = "-" + h.getHours() + ":" + h.getMinutes() + ":" + h.getSeconds();
        let api = "/apagarluces/enviar";
        xmlhttp.onerror = function () {
            swal({
                title: "Atención!",
                text: "No se estableció conexión con el servicio apagar luces.",
                icon: "warning",
            });
        }
        xmlhttp.open("POST", url + api);
        //xmlhttp.open("POST", "https://springrobotapp.ddns.net:443/api/luces");
        xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
        xmlhttp.send(JSON.stringify({ "accion": "Apagar", "fecha": fecha + hora }));
        xmlhttp.onload = function () {
            if (xmlhttp.status == 200) {
                respuesta = JSON.parse(this.responseText);
                if (respuesta.accion == "true") {
                    swal({
                        title: "Realizado!",
                        text: "Las luces se han apagado.",
                        icon: "success",
                    });
                }
                else {
                    swal({
                        title: "Fallido!",
                        text: "Las luces no pudieron apagarse.",
                        icon: "error",
                    });
                }
            }
            else {
                swal({
                    title: "Error!",
                    text: "Ocurrio un error inesperado: " + xmlhttp.status + ".",
                    icon: "error",
                });
            }
        }
    }
}


//COLOR LUCES
var color = ["#ff0000", "#ffff00", "#ff00ff", "#0000ff", "#00ffff", "#00ff00", "#ffffff"];
var i = 0;
function colorL() {
    document.getElementById('boton_lc').style.background = color[i];
    let h = new Date();
    let hora = "-" + h.getHours() + ":" + h.getMinutes() + ":" + h.getSeconds();
    let api = "/cambiarcolorluces/enviar";
    xmlhttp.onerror = function () {
        swal({
            title: "Atención!",
            text: "No se estableció conexión con el servicio color de luces.",
            icon: "warning",
        });
    }
    xmlhttp.open("POST", url + api);
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp.send(JSON.stringify({ "accion": color[i], "fecha": fecha + hora }));
    i++;
    if (i == 7) {
        i = 0;
    }
    xmlhttp.onload = function () {
        if (xmlhttp.status == 200) {
            respuesta = JSON.parse(this.responseText);
            if (respuesta.accion == "true") {
                swal({
                    title: "Realizado!",
                    text: "El color de las luces ha cambiado.",
                    icon: "success",
                });
            }
            else {
                swal({
                    title: "Fallido!",
                    text: "El color de las luces no pudo cambiarse.",
                    icon: "error",
                });
            }
        }
        else {
            swal({
                title: "Error!",
                text: "Ocurrio un error inesperado: " + xmlhttp.status + ".",
                icon: "error",
            });
        }
    }
}