<%-- 
    Document   : registrar-compra
    Created on : 18/12/2012, 08:20:57 AM
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
                
                $("#fechaEmisionComprobante, #fechaVencimiento, #fechaEmisionConstanciaDep, #fechaEmisionCompMod").datepicker({dateFormat: 'dd/mm/yy'});  
                
                $("#rsProveedor").tokenInput("ClienteAjaxAction_listProveedoresByCliente?ruc=<s:property value="empresaCliente.ruc" />", {
                    queryParam: "term",
                    searchDelay: 0,                   
                    tokenLimit: 1,
                    hintText: "Ingrese la razón social del proveedor",
                    searchingText: "buscando...",
                    propertyToSearch: "razonSocial",
                    tokenValue: "razonSocial",
                    onAdd: function(item) {
                        $("#proveedorNroDoc").val(item.numeroDocumentoIdentidad).prop("readonly", true);
                        $("#proveedorTipoDoc").val(item.tipoDocNumero);
                        $("#proveedorTipoDoc option:not(:selected)").prop('disabled', true);
                    },
                    onDelete: function(item) {
                        $("#proveedorNroDoc").val("").prop("readonly", false);
                        $("#proveedorTipoDoc").val("-1");
                        $("#proveedorTipoDoc option:not(:selected)").prop('disabled', false);
                    }
                });
                
                $("#descripcionProductoInput").tokenInput("ClienteAjaxAction_listProductosComprasByCliente?ruc=<s:property value="empresaCliente.ruc" />", {
                    queryParam: "term",
                    searchDelay: 0,                   
                    tokenLimit: 1,
                    hintText: "Ingrese el nombre del producto",
                    searchingText: "buscando...",
                    propertyToSearch: "nombre",
                    tokenValue: "nombre",
                    onAdd: function(item) {
                        $("#pUnitarioInput").val(item.precio);                       
                        var importe = $("#cantidadInput").val() * item.precio;
                        $("#importeInput").val(importe);
                    },
                    onDelete: function() {
                        $("#pUnitarioInput").val("");
                        $("#importeInput").val("");                       
                    }
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
                                     <td>" + '<s:textfield name="detalleLRC.comprobanteCompra.detallesComprobanteCompra[' + indexDetalle + '].cantidad" value="'+ cantidad + '" readonly="true" cssClass="inputLittle right" />' + "</td>\n\
                                     <td>" + '<s:textfield name="detalleLRC.comprobanteCompra.detallesComprobanteCompra[' + indexDetalle + '].productoCompras.nombre" value="'+ descripcion + '" readonly="true" cssClass="inputLarge2" />' + "</td>\n\
                                     <td>" + '<s:textfield name="detalleLRC.comprobanteCompra.detallesComprobanteCompra[' + indexDetalle + '].precioUnitario" value="'+ pUnitarioF + '" readonly="true" cssClass="inputLittle right" />' + "</td>\n\
                                     <td>" + '<s:textfield name="detalleLRC.comprobanteCompra.detallesComprobanteCompra[' + indexDetalle + '].subtotal" value="'+ importeF + '" readonly="true" cssClass="inputLittle right" />' + "</td>\n\
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
                    $("#baseImponibleAG").val($.number(base, 2));
                    $("#igvAG").val($.number(igv, 2));
                    $("#importeTotal").val($.number(total, 2));
                    
                    indexDetalle++;                    
                });
                
                $("#pUnitarioInput").on("input", function() {
                    var importe = parseFloat($("#cantidadInput").val()) * parseFloat($("#pUnitarioInput").val());
                    $("#importeInput").val($.number(importe, 2));
                });
                
                // Para calcular los igvs automáticamente
                $("#baseImponibleAG").on("input", function() {
                    var base = parseFloat($(this).val());
                    var igv =  base * 0.18;
                    var total = base + igv;
                    $("#igvAG").val($.number(igv, 2));                    
                    $("#importeTotal").val($.number(total, 2));
                });
                                
                // casilla para registrar nuevo proveedor
                $("#registrarNuevoProveedor").on("click", function() {
                    if ($(this).is(":checked")) { 
                        $("#rs1").addClass("hide");
                        $("#rs2").removeClass("hide");
                        $("#rsProveedor").prop("name", "");
                        $("#rsProveedorNuevo").prop("name", "detalleLRC.comprobanteCompra.proveedor.razonSocial");
                        // limpiamos los campos                        
                        $("#proveedorNroDoc").val("").prop("readonly", false);
                        $("#proveedorTipoDoc").val("-1");
                        $("#proveedorTipoDoc option:not(:selected)").prop('disabled', false);
                    } else {
                        $("#rs1").removeClass("hide"); 
                        $("#rs2").addClass("hide");
                        $("#rsProveedor").prop("name", "detalleLRC.comprobanteCompra.proveedor.razonSocial");   
                        $("#rsProveedorNuevo").prop("name", "").val(""); 
                        // limpiamos los campos
                        $("#proveedorNroDoc").val("").prop("readonly", false);
                        $("#proveedorTipoDoc").val("-1");
                        $("#proveedorTipoDoc option:not(:selected)").prop('disabled', false);
                        $("#rsProveedor").tokenInput("clear");
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
                        <h1 class="medium">Registrar nueva compra</h1>
                    </div>                    

                    <div id="form_wrapper">                        

                        <div id="errorDiv" class="ui-widget">
                            <div class="ui-state-error ui-corner-all" style="padding: .4em .7em;">
                                <span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
                                <div id="errorList"><s:actionerror /></div>                               
                            </div>
                        </div>

                        <s:form action="DetalleLibroRegistroComprasAction_save?ruc=%{empresaCliente.ruc}" method="post" cssClass="inputForm">

                            <fieldset id="rCompraForm_comprobantePago">
                                <legend class="little2">Información del comprobante de pago</legend>
                                <dl>
                                    <dt>Fecha de emisión del comprobante de pago o documento</dt>
                                    <dd><s:textfield name="detalleLRC.comprobanteCompra.fechaEmision" id="fechaEmisionComprobante" placeholder="" /></dd>
                                    <dt> Fecha de vencimiento o fecha de pago</dt>
                                    <dd><s:textfield name="detalleLRC.comprobanteCompra.fechaVencimientoOpago" id="fechaVencimiento" placeholder="" /></dd>
                                    <dt>Tipo de comprobante (Tabla 10)</dt>
                                    <dd>                                        
                                        <s:select name="detalleLRC.comprobanteCompra.tipoComprobantePagoODocumento.numero" list="tiposComprobantes" 
                                                  headerKey="-1" headerValue="Seleccione el tipo de comprobante"
                                                  listKey="numero" listValue="%{descripcion.length() <= 40 ? numero + \" - \" + descripcion.substring(0, descripcion.length()) : numero + \" \" + descripcion.substring(0, 40) + \"...\"}" />  

                                    </dd>
                                    <dt>Serie</dt>
                                    <dd><s:textfield name="detalleLRC.comprobanteCompra.serie" id="" placeholder="" /></dd>
                                    <dt>Código de dependencia aduanera (Tabla 11)</dt>
                                    <dd>                                        
                                        <s:select name="detalleLRC.comprobanteCompra.codigoAduana.numero" list="codigosAduana" 
                                                  headerKey="-1" headerValue="Seleccione código de dependencia aduanera"
                                                  listKey="numero" listValue="%{descripcion.length() <= 40 ? numero + \" - \" + descripcion.substring(0, descripcion.length()) : numero + \" \" + descripcion.substring(0, 40) + \"...\"}" />  
                                    </dd>
                                    <dt>Año de emisión de la DUA o DSI</dt>
                                    <dd><s:textfield name="detalleLRC.comprobanteCompra.anioEmisionDuaOdsi" id="" placeholder="" /></dd>
                                    <dt>Número del comprobante de pago</dt>
                                    <dd><s:textfield name="detalleLRC.comprobanteCompra.numero" id="" placeholder="" /></dd>                                    
                                </dl>
                            </fieldset>
                            <fieldset id="rCompraForm_proveedor">
                                <legend class="little2">Información del proveedor</legend>
                                <dl>
                                    <dt>
                                    <input type="checkbox" name="" value="false" id="registrarNuevoProveedor"/>
                                    <label for="registrarNuevoProveedor">Registrar nuevo proveedor</label>                                    
                                    </dt>
                                    <dd></dd>
                                    <dt>Apellidos y nombres, denominación o Razón Social</dt>
                                    <dd>
                                        <div id="rs1"><s:textfield name="detalleLRC.comprobanteCompra.proveedor.razonSocial" id="rsProveedor" placeholder="" /></div>
                                        <div id="rs2" class="hide"><s:textfield name="" id="rsProveedorNuevo" /></div>
                                    </dd>
                                    <dt>Tipo de documento de identidad (Tabla 2)</dt>
                                    <dd>
                                        <s:select id="proveedorTipoDoc" name="detalleLRC.comprobanteCompra.proveedor.tipoDocumentoIdentidad.numero" list="tiposDocumentos" 
                                                  headerKey="-1" headerValue="Seleccione el tipo de documento de identidad"
                                                  listKey="numero" listValue="%{descripcion.length() <= 40 ? numero + \" - \" + descripcion.substring(0, descripcion.length()) : numero + \" \" + descripcion.substring(0, 40) + \"...\"}" />  
                                    </dd>
                                    <dt>Número de documento de identidad</dt>
                                    <dd><s:textfield name="detalleLRC.comprobanteCompra.proveedor.numeroDocumentoIdentidad" id="proveedorNroDoc" placeholder="" /></dd>                                    
                                </dl>
                            </fieldset>    
                            <fieldset id="rCompraForm_adquisicionesGravadas1">
                                <legend class="little2">
                                    Adquisiciones gravadas
                                </legend>
                                <dl>
                                    <dt>Tipo de adquisición</dt>
                                    <dd>
                                        <select name="detalleLRC.tipoAdquisicionGravada">
                                            <option value="gravada/exportacion">Gravadas/exportación</option>
                                            <option value="gravada/exportacion - no gravada">Gravadas/exportación y no gravadas</option>
                                            <option value="no gravada">No gravadas</option>                                            
                                        </select>
                                    </dd>                                    
                                    <dt>Base imponible</dt>
                                    <dd><s:textfield name="" id="baseImponibleAG" placeholder="(autocalculado)" readonly="true" /></dd>
                                    <dt>IGV</dt>
                                    <dd><s:textfield name="" id="igvAG" placeholder="(autocalculado)" readonly="true" /></dd>
                                </dl>
                            </fieldset>                             
                            <fieldset id="rCompraForm_resumen">
                                <legend class="little2">Resumen</legend>
                                <dl>
                                    <dt>Valor de las adquisiciones no gravadas</dt>
                                    <dd><s:textfield name="detalleLRC.valorAdquisicionesNoGravadas" id="" placeholder="" /></dd>
                                    <dt>ISC</dt>
                                    <dd><s:textfield name="detalleLRC.isc" id="" placeholder="" /></dd>
                                    <dt>Otros tributos y cargos</dt>
                                    <dd><s:textfield name="detalleLRC.otrosTributosYCargos" id="" placeholder="" /></dd>
                                    <dt>Importe total</dt>
                                    <dd><s:textfield name="detalleLRC.importeTotal" id="importeTotal" placeholder="(autocalculado)" /></dd>
                                </dl>
                            </fieldset>

                            <fieldset id="rCompraForm_otros">
                                <legend class="little2">Otros datos</legend>
                                <dl>
                                    <dt>Número de comprobante de pago emitido por sujeto no domiciliado</dt>
                                    <dd><s:textfield name="detalleLRC.numeroCompPagoSujNoDom" id="" placeholder="" /></dd>
                                    <dt>Número de constancia de depósito de detracción</dt>
                                    <dd><s:textfield name="detalleLRC.numeroConstDepDetraccion" id="" placeholder="" /></dd>
                                    <dt>Fecha de emisión de constancia de depósito de detracción</dt>
                                    <dd><s:textfield name="detalleLRC.fechaEmisionConstDepDetraccion" id="fechaEmisionConstanciaDep" placeholder="" /></dd>
                                    <dt>Tipo de cambio</dt>
                                    <dd><s:textfield name="detalleLRC.tipoCambio" id="" placeholder="" /></dd>                                    
                                </dl>
                            </fieldset>    

                            <fieldset id="rCompraForm_comprobanteReferenciado">
                                <legend class="little2">Referencia del comprobante de pago o documento original que se modifica</legend>
                                <dl>
                                    <dt>Fecha de emisión</dt>
                                    <dd><s:textfield name="" id="fechaEmisionCompMod" placeholder="" /></dd>
                                    <dt>Tipo de comprobante que se modifica</dt>
                                    <dd>
                                        <select>
                                            <option>Tipo 1</option> 
                                            <option>Tipo 2</option> 
                                            <option>Tipo 3</option> 
                                            <option>Tipo 4</option> 
                                            <option>Tipo 5</option> 
                                        </select>
                                    </dd>
                                    <dt>Número de serie</dt>
                                    <dd><s:textfield name="" id="" placeholder="" /></dd>
                                    <dt>Número de comprobante</dt>
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
                                            <td class="paddingTopBig"><input id="cantidadInput" type="text" class="inputLittle border1" /></td>
                                            <td>
                                                <input style="float: left;" type="checkbox" name="" value="false" id="registrarNuevoProducto" tabindex="-1" />
                                                <label style="float: left;" class="little3" for="registrarNuevoProducto">Registrar nuevo producto</label> 
                                                <div id="descripcionProducto1">
                                                    <input id="descripcionProductoInput" type="text" class="inputLarge2 border1" />                                                    
                                                </div>
                                                <div id="descripcionProducto2" class="hide">
                                                    <input id="descripcionProductoInput2" type="text" class="inputLarge2 border1" />
                                                </div>                                                
                                            </td>
                                            <td class="paddingTopBig"><input id="pUnitarioInput" type="text" class="inputLittle border1" /></td>
                                            <td class="paddingTopBig"><input id="importeInput" type="text" class="inputLittle border1" readonly /></td>
                                            <td class="paddingTopBig"><button id="btnOk" class="button"><span class="label">OK</span></button></td>
                                        </tr>                                        
                                    </tbody>
                                    <tfoot>
                                        <tr>
                                            <th colspan="3" class="right">Base</th>
                                            <th><s:textfield id="base" name="detalleLRC.comprobanteCompra.base" cssClass="inputLittle right" value="0.00" readonly="true" /></th>
                                            <th></th>                                             
                                        </tr>
                                        <tr> 
                                            <th colspan="3" class="right">IGV</th>
                                            <th><s:textfield id="igv" name="detalleLRC.comprobanteCompra.igv" cssClass="inputLittle right" value="0.00" readonly="true" /></th>
                                            <th></th> 
                                        </tr>
                                        <tr>                                                                                      
                                            <th colspan="3" class="right">Total</th>
                                            <th><s:textfield id="total" name="detalleLRC.comprobanteCompra.importeTotal" cssClass="inputLittle right" value="0.00" readonly="true" /></th>
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
