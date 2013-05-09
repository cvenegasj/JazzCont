$(function() {
    /******** generales ************/
    $("input:submit").button();  
    //placeholder para navegadores que no soportan
    $('input, textarea').placeholder();
                
    $(".oneClick").click(function() {
        $(this).button("disable");      
    });

    if ($("#errorList").html() != "") {
        $("#errorDiv").show();
    }
    
    /******** intro-admin.jsp  *********/
    
    // evento que abre el menú de la barra de sesión
    $("#opcionesSesionLink").on("click", function() {
        $("#menuSesion").slideToggle(200);
        return false;
    }); 

    $(document).on("click", function(event) {                
        if (!$(event.target).is("#menuSesion li a")) {
            $("#menuSesion").slideUp(200); 
        }
    });
    
    // evento de la barra lateral izquierda 
    $("#firstpane p.menu_head").click(function()
    {   
        if ($(this).next("div.menu_body").is(':visible')) {
            //oculta;                        
            $(this).css({
                backgroundImage:"url(https://lh5.googleusercontent.com/-mQHPLQrjxcI/TlQJSHgEsZI/AAAAAAAABno/HbkoM9nbaWQ/flecha-abajo.png)"
            });
        } else {
            //muestra
            $(this).css({
                backgroundImage:"url(https://lh5.googleusercontent.com/-BBN4_uSiCs4/TlQJSAXI5CI/AAAAAAAABnk/cmdGRetC38U/flecha-arriba.png)"
            });
        }
                    
        $(this).next("div.menu_body").slideToggle(300).siblings("div.menu_body").slideUp("slow");
        $(this).siblings().css({
            backgroundImage:"url(https://lh5.googleusercontent.com/-mQHPLQrjxcI/TlQJSHgEsZI/AAAAAAAABno/HbkoM9nbaWQ/flecha-abajo.png)"
        });
        $("#firstpane p.menu_head_link").css({
            backgroundImage:"none"
        }); 
    });
    
    
    
    
    //evento para manejar la seleccion del item de la lista del diálogo
    /******** verificar si funciona en display multitouch ********/
    //se usa 'on' porque los elementos son gerados dinamicamente en el dom
    $("body").on("click", ".dElementoListaClientes", function(event) {
        $(this).addClass("dElcSelected");
        $(this).parent().siblings().children().removeClass("dElcSelected");
    });  
                
    $("#registrarAsiento, #registrarCompra, #registrarVenta, #registrarComprobanteCompra, #registrarComprobanteVenta").on("click", function(event) {
        event.preventDefault();                  
                    
        // se modifica el input hidden con el tipo de desencadenador
        var disp = event.target.id;
        $("#dTipoDialogo").prop("value", disp);                    
        /*                                
        if (disp == "registrarAsiento") {                        
            $("#dCriterioBusqueda").children("option[value='rg']").show();
            $("#dCriterioBusqueda").children("option[value='rer'], option[value='nrus']").hide();
        } else if (disp == "registrarCompra" || disp == "registrarVenta") {
            $("#dCriterioBusqueda").children("option[value='rg'], option[value='rer']").show();
            $("#dCriterioBusqueda").children("option[value='nrus']").hide();
        } else if (disp == "registrarComprobanteCompra") {
            $("#dCriterioBusqueda").children("option[value='nrus']").show();
            $("#dCriterioBusqueda").children("option[value='rg'], option[value='rer']").hide();
        } else if (disp == "registrarComprobanteVenta") {
            $("#dCriterioBusqueda").children("option[value='nrus']").show();
            $("#dCriterioBusqueda").children("option[value='rg'], option[value='rer']").hide();
        }*/
                    
        // se muestra el diálogo                    
        $( "#dialogSelectCustomer" ).dialog( "open" );                    
                    
        // se carga la lista de todos los clientes ajax
        cargarListaClientes("all", "");
                    
    });                
    
                
    // evento del select del cuadro de diálogo seleccionar cliente
    $("#dCriterioBusqueda").change(function() {
        var sRegimen = $("#dCriterioBusqueda option:selected").val();
        var sCadena = $("#dSearch").val();
                    
        // carga Ajax
        cargarListaClientes(sRegimen, sCadena);                
                
    }); 
                
    $("#dSearch").on('input', function(e){
        var sRegimen = $("#dCriterioBusqueda").val();
        var sCadena = $("#dSearch").val();
                    
        // carga Ajax
        cargarListaClientes(sRegimen, sCadena); 
    });
    
    $("#dialogSelectCustomer").dialog({
        autoOpen: false,
        modal: true,
        resizable: false,                    
        width: 460,   
        height: 470,
        buttons: {
            "Aceptar": function() {
                //redireccionar
                var disp = $("#dTipoDialogo").prop("value"); 
                var seleccionado = $("#dListaClientes .dElcSelected").get(0);                           
                            
                if (typeof seleccionado === "undefined") {
                    // no se ha seleccionado nada
                    alert("No se ha seleccionado ningún cliente.");
                } else {
                    var url;
                    var regClienteSeleccionado = $(seleccionado).children(".dElcRegimen").prop("value");
                    var rucSeleccionado = $(seleccionado).children(".dElcRuc").prop("value");
                    //alert(regClienteSeleccionado + "  " + rucSeleccionado);
                                
                    // se verifica si el cliente puede realizar la operación
                    if (disp == 'registrarAsiento') {
                        url = "AsientoAction_add?ruc=" + rucSeleccionado;
                    } else if (disp == 'registrarCompra') {
                        url = "DetalleLibroRegistroComprasAction_add?ruc=" + rucSeleccionado;
                    } else if (disp == 'registrarVenta') {
                        url = "DetalleLibroRegistroVentasAction_add?ruc=" + rucSeleccionado;
                    } else if (disp == 'registrarComprobanteCompra') {
                        url = "ComprobanteCompraAction_add?ruc=" + rucSeleccionado;
                    } else if (disp == 'registrarComprobanteVenta') {
                        url = "ComprobanteVentaAction_add?ruc=" + rucSeleccionado;
                    } else {
                        alert("El cliente seleccionado pertenece al régimen: " + regClienteSeleccionado + ", y no puede realizar la operación.");
                        return;
                    }
                                
                    //redirect
                    window.location = url;                                
                }   
            },
            "Cancelar": function() {
                // se reinician los valores del dialogo
                //$("#dListaClientes ul li div").removeClass("dElcSelected");
                $("#dCriterioBusqueda option:eq(0)").prop('selected', true);
                $("#dSearch").val("");
                $( this ).dialog( "close" );
            }
        },
        close: function() {
            $("#dCriterioBusqueda option:eq(0)").prop('selected', true);
            $("#dSearch").val("");
        },
        position: "center"
    });   
    
    function cargarListaClientes(regimen, cadena) {
        // ajax-load.gif
        $("#dListaClientes").html("<span class=\"loadingGif\"></span>");
                    
        $.ajax({
            url: "ClienteAjaxAction_showDialogClientes",
            type: "POST",
            data: {
                regimen: regimen, 
                cadena: cadena
            },
            dataType: "json",
            error: function(XMLHttpRequest, textStatus, errorThrown){
                alert('Error ' + textStatus);
                alert(errorThrown);
                alert(XMLHttpRequest.responseText);
            },
            success: function(data){         
                //alert('SUCCESS' + '\nDATA: ' + data.salida);
                $("#dListaClientes").html(data.salida);
            }                    
        });                                    
    } 
    
    
}); 

