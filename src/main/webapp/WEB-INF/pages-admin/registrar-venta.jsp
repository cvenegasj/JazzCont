<%-- 
    Document   : registrar-venta
    Created on : 18/12/2012, 08:21:12 AM
    Author     : Venegas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link href='http://fonts.googleapis.com/css?family=Dosis' rel='stylesheet' type='text/css'>        
        <title>App | Jazz Contadores</title>
        <link rel="stylesheet" type="text/css" href="<s:url value="/css/style.css"/>">        
        <link rel="shortcut icon" href="<s:url value="/favicon1.ico"/>">
        <link rel="icon" type="image/ico" href="<s:url value="/favicon1.ico"/>">

        <link type="text/css" href="<s:url value="/css/custom-theme/jquery-ui-1.9.1.custom.min.css"/>" rel="stylesheet" />	
        <script type="text/javascript" src="<s:url value="/js/jquery-1.7.2.min.js"/>"></script>        
        <script type="text/javascript" src="<s:url value="/js/jquery-ui-1.9.1.custom.min.js"/>"></script> 

        <script type="text/javascript" src="<s:url value="/js/jquery.placeholder.min.js"/>"></script> 
        <script type="text/javascript" src="<s:url value="/js/scripts.js"/>"></script>

        <!-- Botones estilo Google+ -->
        <link type="text/css" href="<s:url value="/css/css3-buttons.css"/>" rel="stylesheet" />

        <!-- Token input -->
        <link type="text/css" href="<s:url value="/css/token-input.css"/>" rel="stylesheet" />
        <script type="text/javascript" src="<s:url value="/js/jquery.tokeninput.js"/>"></script>

        <!-- JQuery number plugin -->
        <script type="text/javascript" src="<s:url value="/js/jquery.number.min.js"/>"></script>

        <script type="text/javascript"> 
            $(function() {
                // Toggle the dropdown menu's
                $(".dropdown .button").on('click', function() {
                    $(this).parent().find('.dropdown-slider').slideToggle('fast');
                    $(this).find('span.toggle').toggleClass('active');
                    return false;
                });
                
                // Close open dropdown slider/s by clicking elsewhwere on page
                $(document).on('click', function (e) {            
                    if (!$(e.target).is(".dropdown .dropdown-slider a span")) {
                        $('.dropdown-slider').slideUp();
                        $('span.toggle').removeClass('active');
                    }
                }); // END document.bind
                //**************    
                
                $("#fechaEmisionComprobante, #fechaVencimiento").datepicker({dateFormat: 'dd/mm/yy'});  
                
                $("#rsComprador").tokenInput("ClienteAjaxAction_listCompradoresByCliente?ruc=<s:property value="empresaCliente.ruc" />", {
                    queryParam: "term",
                    searchDelay: 0,                   
                    tokenLimit: 1,
                    hintText: "Ingrese la razón social o nombre del comprador",
                    searchingText: "buscando...",
                    propertyToSearch: "razonSocialONombres",
                    tokenValue: "razonSocialONombres",
                    onAdd: function(item) {
                        $("#compradorNroDoc").val(item.numeroDocumentoIdentidad).prop("readonly", true);
                        $("#compradorTipoDoc").val(item.tipoDocNumero);
                        $("#compradorTipoDoc option:not(:selected)").prop('disabled', true);
                    },
                    onDelete: function(item) {
                        $("#compradorNroDoc").val("").prop("readonly", false);
                        $("#compradorTipoDoc").val("-1");
                        $("#compradorTipoDoc option:not(:selected)").prop('disabled', false);
                    }
                });
                
                $("#descripcionProductoInput").tokenInput("ClienteAjaxAction_listProductosVentasByCliente?ruc=<s:property value="empresaCliente.ruc" />", {
                    queryParam: "term",
                    searchDelay: 0,                   
                    tokenLimit: 1,
                    hintText: "Ingrese el nombre del producto",
                    searchingText: "buscando...",
                    propertyToSearch: "nombre",
                    tokenValue: "nombre"
                });
                
                // variables para calcular totales
                var total = 0.00;
                var base = 0.00;
                var igv = 0.00; 
                // para agregar detalles
                var indexDetalle = 0;                
                
                $("#btnOk").on("click", function(e) {
                    e.preventDefault();
                    
                    var cantidad = $("#cantidadInput").val();
                    var descripcion = "";
                    
                    if ($("#descripcionProducto1").hasClass("hide")) {
                        descripcion = $("#descripcionProductoInput2").val();
                    } else if ($("#descripcionProducto2").hasClass("hide")) {
                        descripcion = $("#descripcionProductoInput").val();
                    }
                    
                    var pUnitario = $("#pUnitarioInput").val();
                    var importe = $("#importeInput").val();
                    
                    // validaciones
                    if ($.trim(cantidad) == "" || $.trim(descripcion) == "" || $.trim(pUnitario) == "" || $.trim(importe) == "") {
                        alert("Ingrese todos los campos de la línea");   
                        return;
                    }
                    
                    if (/^\d+$/.test(cantidad) == false) {
                        alert("El formato de la cantidad es incorrecta");
                        return;
                    }
                    
                    if (/^\d+(\.\d{1,2})?$/.test(pUnitario) == false) {
                        alert("El formato del Precio Unitario es incorrecto");
                        return;
                    }
                    
                    if (/^\d+(\.\d{1,2})?$/.test(importe) == false) {
                        alert("El formato del Importe es incorrecto");
                        return;
                    }
                    
                    var p = cantidad * pUnitario;
                    
                    if ($.number(p, 2) != $.number(importe, 2)) {
                        alert("El importe no es correcto.");
                        return;
                    }                                        
                    // *************
                    
                    var pUnitarioF = $.number(pUnitario, 2);
                    var importeF = $.number(importe, 2);
                    
                    var nuevaLinea = "<tr class=\"linea\">\n\
                                     <td>" + '<s:textfield name="detalleLRV.comprobanteVenta.detallesComprobanteVenta[' + indexDetalle + '].cantidad" value="'+ cantidad + '" readonly="true" />' + "</td>\n\
                                     <td>" + '<s:textfield name="detalleLRV.comprobanteVenta.detallesComprobanteVenta[' + indexDetalle + '].productoVentas.nombre" value="'+ descripcion + '" readonly="true" />' + "</td>\n\
                                     <td>" + '<s:textfield name="detalleLRV.comprobanteVenta.detallesComprobanteVenta[' + indexDetalle + '].precioUnitario" value="'+ pUnitarioF + '" readonly="true" />' + "</td>\n\
                                     <td>" + '<s:textfield name="detalleLRV.comprobanteVenta.detallesComprobanteVenta[' + indexDetalle + '].subtotal" value="'+ importeF + '" readonly="true" />' + "</td>\n\
                                     <td></td>\n\
                                     </tr>";
                    
                    $("#inputLineDetallesComprobante").before(nuevaLinea);
                    
                    // limpiar valores de linea input
                    $("#descripcionProductoInput").tokenInput("clear");
                    $("#descripcionProductoInput2").val("");
                    $("#pUnitarioInput").val("");
                    $("#importeInput").val("");
                    $("#cantidadInput").val("").focus();
                    $("#registrarNuevoProducto").prop('checked', false);
                    $("#descripcionProducto1").removeClass("hide");
                    $("#descripcionProducto2").addClass("hide");
                                        
                    // se calculan los totales
                    total += parseFloat(importe);
                    base = total * 0.82;
                    igv = total * 0.18;
                    
                    // se establecen los campos
                    $("#base").val($.number(base, 2));
                    $("#igv").val($.number(igv, 2)); 
                    $("#total").val($.number(total, 2));
                    $("#baseImponible").val($.number(base, 2));
                    $("#igvResumen").val($.number(igv, 2));
                    $("#importeTotal").val($.number(total, 2));
                    
                    indexDetalle++;                    
                });
                
                $("#pUnitarioInput").on("input", function() {
                    var importe = parseFloat($("#cantidadInput").val()) * parseFloat($("#pUnitarioInput").val());
                    $("#importeInput").val($.number(importe, 2));
                });
                
                // casilla para registrar nuevo comprador
                $("#registrarNuevoComprador").on("click", function() {
                    if ($(this).is(":checked")) { 
                        $("#rs1").addClass("hide");
                        $("#rs2").removeClass("hide");
                        $("#rsComprador").prop("name", "");
                        $("#rsCompradorNuevo").prop("name", "detalleLRV.comprobanteVenta.comprador.razonSocialONombres");
                        // limpiamos los campos                        
                        $("#compradorNroDoc").val("").prop("readonly", false);
                        $("#compradorTipoDoc").val("-1");
                        $("#compradorTipoDoc option:not(:selected)").prop('disabled', false);
                    } else {
                        $("#rs1").removeClass("hide"); 
                        $("#rs2").addClass("hide");
                        $("#rsComprador").prop("name", "detalleLRV.comprobanteVenta.comprador.razonSocialONombres");   
                        $("#rsCompradorNuevo").prop("name", "").val(""); 
                        // limpiamos los campos
                        $("#compradorNroDoc").val("").prop("readonly", false);
                        $("#compradorTipoDoc").val("-1");
                        $("#compradorTipoDoc option:not(:selected)").prop('disabled', false);
                        $("#rsComprador").tokenInput("clear");
                    }
                });
                
                // casilla para registrar nuevo producto
                $("#registrarNuevoProducto").on("click", function() {
                    if ($(this).is(":checked")) { 
                        $("#descripcionProducto1").addClass("hide");
                        $("#descripcionProducto2").removeClass("hide");                                            
                    } else {
                        $("#descripcionProducto1").removeClass("hide"); 
                        $("#descripcionProducto2").addClass("hide");                                                          
                    }
                });
            });     
        </script>

    </head>
    <body>
        <header>
            <div id="header_content">
                <div id="logo"><a href="<%= request.getContextPath()%>"><img src="<s:url value="/img/logo_principal2.jpg"/>" width="240" height="60" alt="Jazz Contadores" /></a></div>
                <div id="navigation">
                    <div id="levelOnecontainer">
                        <div style="float: left;">
                            <ul class="levelOne">
                                <li><a href="<%= request.getContextPath()%>" >Inicio</a><img class="menue_seperator" src="<s:url value="/img/seperator.jpg"/>" width="2" height="2" /></li>
                                <li><a href="<%= request.getContextPath()%>/estudio" >Nuestro estudio</a><img class="menue_seperator" src="<s:url value="/img/seperator.jpg"/>" width="2" height="2" /></li>
                                <li><a href="<%= request.getContextPath()%>/acceso" class="active" >Acceso</a><img class="menue_seperator" src="<s:url value="/img/seperator.jpg"/>" width="2" height="2" /></li>
                                <li><a href="<%= request.getContextPath()%>/contacto" >Contáctanos</a><img class="menue_seperator" src="<s:url value="/img/seperator.jpg"/>" width="2" height="2" /></li>                                
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </header>

        <div id="fixed_main"> 

            <div id="sesion">
                <%@ include file="/WEB-INF/jspf/barra_sesion_admin.jspf" %>                
            </div>           

            <div id="widthArea">

                <%@ include file="/WEB-INF/jspf/lateral_izq_admin.jspf" %>

                <div id="contentArea">

                    <%@ include file="/WEB-INF/jspf/header_cliente_sesion_admin.jspf" %>

                    <div id="headerContentArea">
                        <h1 class="medium">Registrar nueva venta</h1>
                    </div>                    

                    <div id="form_wrapper">                        

                        <div id="errorDiv" class="ui-widget">
                            <div class="ui-state-error ui-corner-all" style="padding: .4em .7em;">
                                <span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
                                <div id="errorList"><s:actionerror /></div>                               
                            </div>
                        </div>

                        <s:form action="DetalleLibroRegistroVentasAction_save?ruc=%{empresaCliente.ruc}" method="post" cssClass="inputForm">

                            <fieldset id="rVentaForm_comprobantePago">
                                <legend class="little2">Información del comprobante de pago</legend>
                                <dl>
                                    <dt>Fecha de emisión del comprobante de pago o documento</dt>
                                    <dd><s:textfield name="detalleLRV.comprobanteVenta.fechaEmision" id="fechaEmisionComprobante" placeholder="" /></dd>
                                    <dt>Fecha de vencimiento y/o pago</dt>
                                    <dd><s:textfield name="detalleLRV.comprobanteVenta.fechaVencimiento" id="fechaVencimiento" placeholder="" /></dd>
                                    <dt>Tipo de comprobante (Tabla 10)</dt>
                                    <dd>
                                        <s:select name="detalleLRV.comprobanteVenta.tipoComprobantePagoODocumento.numero" list="tiposComprobantes" 
                                                  headerKey="-1" headerValue="Seleccione el tipo de comprobante"
                                                  listKey="numero" listValue="%{descripcion.length() <= 40 ? numero + \" - \" + descripcion.substring(0, descripcion.length()) : numero + \" \" + descripcion.substring(0, 40) + \"...\"}" />  
                                    </dd>
                                    <dt>Número de serie o número de serie de la máquina registradora</dt>                                                                    
                                    <dd><s:textfield name="detalleLRV.comprobanteVenta.serie" id="" placeholder="" /></dd>
                                    <dt>Número del comprobante de pago</dt>                                                                    
                                    <dd><s:textfield name="detalleLRV.comprobanteVenta.numero" id="" placeholder="" /></dd>
                                </dl>
                            </fieldset>
                            <fieldset id="rVentaForm_comprador">
                                <legend class="little2">Información del comprador</legend>
                                <dl>
                                    <dt>
                                    <input type="checkbox" name="" value="false" id="registrarNuevoComprador"/>
                                    <label for="registrarNuevoComprador">Registrar nuevo comprador</label>                                    
                                    </dt>
                                    <dd></dd>
                                    <dt>Apellidos y nombres, denominación o razón social</dt>
                                    <dd>
                                        <div id="rs1"><s:textfield name="detalleLRV.comprobanteVenta.comprador.razonSocialONombres" id="rsComprador" placeholder="" /></div>
                                        <div id="rs2" class="hide"><s:textfield name="" id="rsCompradorNuevo" /></div>
                                    </dd>    
                                    <dt>Tipo de documento de identidad (Tabla 2)</dt>
                                    <dd>
                                        <s:select id="compradorTipoDoc" name="detalleLRV.comprobanteVenta.comprador.tipoDocumentoIdentidad.numero" list="tiposDocumentos" 
                                                  headerKey="-1" headerValue="Seleccione el tipo de documento de identidad"
                                                  listKey="numero" listValue="%{descripcion.length() <= 40 ? numero + \" - \" + descripcion.substring(0, descripcion.length()) : numero + \" \" + descripcion.substring(0, 40) + \"...\"}" />  
                                    </dd>
                                    <dt>Número de documento de identidad</dt>
                                    <dd><s:textfield name="detalleLRV.comprobanteVenta.comprador.numeroDocumentoIdentidad" id="compradorNroDoc" placeholder="" /></dd>                                                                      
                                </dl>
                            </fieldset>    
                            <fieldset id="rVentaForm_resumen">
                                <legend class="little2">Resumen</legend>
                                <dl>
                                    <dt>Valor facturado de la exportación</dt>
                                    <dd><s:textfield name="" id="" placeholder="" /></dd>
                                    <dt>Base imponible de la operación gravada</dt>
                                    <dd><s:textfield name="detalleLRV.baseImponibleOpGravada" id="baseImponible" placeholder="(autocalculado)" /></dd>
                                    <dt>Importe total de la operación exonerada</dt>
                                    <dd><s:textfield name="" id="" placeholder="" /></dd>
                                    <dt>Importe total de la operación inafecta</dt>
                                    <dd><s:textfield name="" id="" placeholder="" /></dd>
                                    <dt>ISC</dt>
                                    <dd><s:textfield name="" id="" placeholder="" /></dd>
                                    <dt>IGV y/o IPM</dt>
                                    <dd><s:textfield name="detalleLRV.igv_ipm" id="igvResumen" placeholder="(autocalculado)" /></dd>
                                    <dt>Otros tributos y cargos que no forman parte de la base imponible</dt>
                                    <dd><s:textfield name="" id="" placeholder="" /></dd>
                                    <dt>Importe total del comprobante de pago</dt>
                                    <dd><s:textfield name="detalleLRV.importeTotal" id="importeTotal" placeholder="(autocalculado)" /></dd>
                                </dl>
                            </fieldset> 
                            <fieldset id="rVentaForm_otros">
                                <legend class="little2">Otros datos</legend>
                                <dl>
                                    <dt>Tipo de cambio</dt>
                                    <dd><s:textfield name="" id="" placeholder="" /></dd>                                    
                                </dl>
                            </fieldset>
                            <fieldset id="rVentaForm_comprobanteReferenciado">
                                <legend class="little2">Referencia del comprobante de pago o documento original que se modifica</legend>
                                <dl>                                    
                                    <dt>Fecha de emisión</dt>
                                    <dd><s:textfield name="" id="" placeholder="" /></dd>
                                    <dt>Tipo de comprobante que se modifica</dt>
                                    <dd>
                                        <s:select name="" list="tiposComprobantes" 
                                                  headerKey="-1" headerValue="Seleccione el tipo de comprobante"
                                                  listKey="numero" listValue="%{descripcion.length() <= 40 ? numero + \" - \" + descripcion.substring(0, descripcion.length()) : numero + \" \" + descripcion.substring(0, 40) + \"...\"}" />  
                                    </dd>
                                    <dt>Número de serie</dt>
                                    <dd><s:textfield name="" id="" placeholder="" /></dd>
                                    <dt>Número del comprobante de pago o documento</dt>
                                    <dd><s:textfield name="" id="" placeholder="" /></dd>                                                                     
                                </dl>
                            </fieldset> 
                            <fieldset>
                                <legend class="little2">Detalles</legend>
                                <table id="inputDetallesCompra" class="detallesComprobanteTable">
                                    <thead>
                                        <tr>
                                            <th style="width: 50px">Cantidad</th>
                                            <th style="width: 200px">Descripción</th>
                                            <th style="width: 70px">P. Unitario</th>
                                            <th style="width: 70px">Importe</th>
                                            <th style="width: 40px"></th>                                            
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr id="inputLineDetallesComprobante">
                                            <td class="paddingTopBig"><input id="cantidadInput" type="text" class="border1" /></td>
                                            <td>
                                                <input style="float: left;" type="checkbox" name="" value="false" id="registrarNuevoProducto" tabindex="-1" />
                                                <label style="float: left;" class="little3" for="registrarNuevoProducto">Registrar nuevo producto</label> 
                                                <div id="descripcionProducto1">
                                                    <input id="descripcionProductoInput" type="text" class="border1" />                                                    
                                                </div>
                                                <div id="descripcionProducto2" class="hide">
                                                    <input id="descripcionProductoInput2" type="text" class="border1" />
                                                </div>                                                
                                            </td>
                                            <td class="paddingTopBig"><input id="pUnitarioInput" type="text" class="border1" /></td>
                                            <td class="paddingTopBig"><input id="importeInput" type="text" class="border1" readonly /></td>
                                            <td class="paddingTopBig"><button id="btnOk" class="button"><span class="label">OK</span></button></td>
                                        </tr>                                        
                                    </tbody>
                                    <tfoot>
                                        <tr>
                                            <th colspan="3" class="right">Base</th>
                                            <th><s:textfield id="base" name="detalleLRV.comprobanteVenta.base" value="0.00" readonly="true" /></th>
                                            <th></th>                                             
                                        </tr>
                                        <tr> 
                                            <th colspan="3" class="right">IGV</th>
                                            <th><s:textfield id="igv" name="detalleLRV.comprobanteVenta.igv" value="0.00" readonly="true" /></th>
                                            <th></th> 
                                        </tr>
                                        <tr>                                                                                      
                                            <th colspan="3" class="right">Total</th>
                                            <th><s:textfield id="total" name="detalleLRV.comprobanteVenta.importeTotal" value="0.00" readonly="true" /></th>
                                            <th></th>
                                        </tr>
                                    </tfoot>
                                </table>
                            </fieldset>

                            <div class="footer_form2">
                                <s:submit type="submit" cssClass="" value="Registrar" />
                            </div>
                        </s:form> 
                    </div>


                </div>
                <div class="spacer"></div>

                <%@ include file="/WEB-INF/jspf/dialog_select_cliente.jspf" %> 

            </div>
        </div>

        <!-- footer -->                    
        <%@ include file="/WEB-INF/jspf/footer.jspf" %>        
    </body>
</html>
