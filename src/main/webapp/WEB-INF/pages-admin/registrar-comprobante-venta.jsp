<%-- 
    Document   : registrar-comprobante
    Created on : 18/12/2012, 08:52:06 AM
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
                $(document).on('click', function(e) {            
                    if (!$(e.target).is(".dropdown .dropdown-slider a span")) {
                        $('.dropdown-slider').slideUp();
                        $('span.toggle').removeClass('active');
                    }
                }); // END document.bind
                //**************    
                
                $("#fechaEmisionComprobante").datepicker({dateFormat: 'dd/mm/yy'});   

                // variables para calcular totales
                var total = 0.00;
                var base = 0.00;
                var igv = 0.00; 
                
                $(".btnOk").on("click", function(e) {
                    e.preventDefault();
                    
                    var cantidad = $(e.target).parent().siblings("td:nth-child(1)").children("input").val();
                    var descripcion = $(e.target).parent().siblings("td:nth-child(2)").children("input").val();
                    var pUnitario = $(e.target).parent().siblings("td:nth-child(3)").children("input").val();
                    var importe = $(e.target).parent().siblings("td:nth-child(4)").children("input").val();
                    
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
                                     <td>" + '<s:label name="" value="'+ cantidad + '" />' + "</td>\n\
                                     <td>" + '<s:label name="" value="'+ descripcion + '" />' + "</td>\n\
                                     <td>" + '<s:label name="" value="'+ pUnitarioF + '" />' + "</td>\n\
                                     <td>" + '<s:label name="" value="'+ importeF + '" />' + "</td>\n\
                                     <td></td>\n\
                                     </tr>";
                    
                    $(e.target).parent().parent().before(nuevaLinea);
                    $("#cantidadInput").val("").focus();
                    $("#descripcionInput").val("");
                    $("#pUnitarioInput").val("");
                    $("#importeInput").val("");
                    
                    
                    // se calculan los totales
                    total += parseFloat(importe);
                    base = total * 0.82;
                    igv = total * 0.18;
                    
                    
                    // ******puede general problemas al leer desde struts2***************************************
                    $("#total").text($.number(total, 2));
                    
                });
                
                $("#pUnitarioInput").on("input", function() {
                    var importe = parseFloat($("#cantidadInput").val()) * parseFloat($("#pUnitarioInput").val());
                    $("#importeInput").val($.number(importe, 2));
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
                        <h1 class="medium">Registrar nuevo comprobante de venta</h1>
                    </div>                    

                    <div id="form_wrapper">                        

                        <div id="errorDiv" class="ui-widget">
                            <div class="ui-state-error ui-corner-all" style="padding: .4em .7em;">
                                <span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
                                <div id="errorList"><s:actionerror /></div>                               
                            </div>
                        </div>

                        <s:form action="" method="post" id="rCompraForm" cssClass="inputForm">

                            <fieldset id="rCompraForm_comprobantePago">
                                <legend class="little2">Información del comprobante de pago</legend>
                                <dl>
                                    <dt>Fecha de emisión del comprobante de pago o documento</dt>
                                    <dd><s:textfield name="" id="fechaEmisionComprobante" placeholder="" /></dd>                                    
                                    <dt>Tipo de comprobante (Tabla 10)</dt>
                                    <dd>
                                        <select>
                                            <option>Tipo 1</option> 
                                            <option>Tipo 2</option> 
                                            <option>Tipo 3</option> 
                                            <option>Tipo 4</option> 
                                            <option>Tipo 5</option> 
                                        </select>
                                    </dd>                                    
                                    <dt>Serie del comprobante</dt>
                                    <dd><s:textfield name="" id="" placeholder="" /></dd>  
                                    <dt>Número del comprobante</dt>
                                    <dd><s:textfield name="" id="" placeholder="" /></dd>                               
                                </dl>
                            </fieldset>
                            <fieldset id="rVentaForm_comprador">
                                <legend class="little2">Información del comprador</legend>
                                <dl>
                                    <dt>Tipo de documento de identidad (Tabla 2)</dt>
                                    <dd>
                                        <select>
                                            <option>Tipo 1</option>
                                            <option>Tipo 2</option>
                                            <option>Tipo 3</option>
                                            <option>Tipo 4</option>
                                        </select>
                                    </dd>
                                    <dt>Número de documento de identidad</dt>
                                    <dd><s:textfield name="" id="" placeholder="" /></dd>
                                    <dt>Apellidos y nombres, denominación o razón social</dt>
                                    <dd><s:textfield name="" id="" placeholder="" /></dd>                                      
                                </dl>
                            </fieldset>
                            <fieldset>
                                <legend class="little2">Detalles</legend>
                                <table id="inputDetallesComprobanteVenta" class="detallesComprobanteTable">
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
                                        <tr>
                                            <td><input id="cantidadInput" type="text" class="border1" /></td>
                                            <td><input id="descripcionInput" type="text" class="border1" /></td>
                                            <td><input id="pUnitarioInput" type="text" class="border1" /></td>
                                            <td><input id="importeInput" type="text" class="border1" readonly /></td>
                                            <td><button class="btnOk button"><span class="label">OK</span></button></td>
                                        </tr>                                        
                                    </tbody>
                                    <tfoot>
                                        <tr>                                                                                       
                                            <th colspan="3" class="right">Total</th>
                                            <th><s:label id="total" name="" value="0.00" /></th>
                                            <th></th>
                                        </tr>
                                        <tr>                                            
                                            <th colspan="3" class="right">Base</th>
                                            <th><s:label id="base" name="" value="0.00" /></th>
                                            <th></th>                                             
                                        </tr>
                                        <tr>                                            
                                            <th colspan="3" class="right">Igv</th>
                                            <th><s:label id="igv" name="" value="0.00" /></th>
                                            <th></th> 
                                        </tr>
                                    </tfoot>
                                </table>
                            </fieldset>

                            <div class="footer_form2">
                                <s:submit type="submit" cssClass="oneClick" value="Registrar" />
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
