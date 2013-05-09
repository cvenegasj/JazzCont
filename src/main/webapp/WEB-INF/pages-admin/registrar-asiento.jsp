<%-- 
    Document   : registrar-asiento
    Created on : 18/12/2012, 08:20:39 AM
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
                
                $("#fechaAsiento").datepicker({dateFormat: 'dd/mm/yy'}); 
                
                $(".jazzToken").tokenInput([{id: 3, name: "test"}, {id: 5, name: "test2"}, {id: 7, name: "test3"}, {id: 9, name: "test4"}], {
                    searchDelay: 0,                   
                    tokenLimit: 1,
                    hintText: "Ingrese el nombre de la cuenta",
                    tokenValue: "name"
                });
                
                $("#btnCargo").click(function(event) {
                    event.preventDefault();
                    // verificar los campos
                    var importe = $("#inputImporteCargo").val();
                    var cuenta = $("#inputCuentaCargo").val();
                    
                    if ($.trim(importe) == "") {
                        alert("Ingrese el importe");
                        return;
                    }
                    
                    if ($.trim(cuenta) == "") {
                        alert("Ingrese el nombre de la cuenta"); 
                        return;
                    }
                    
                    if (/^\d+(\.\d{1,2})?$/.test(importe)) {
                        // todo ok, se agrega la siguiente fila 
                        var importeF = $.number(importe, 2);
                        var fila = "<tr>\n\
                                    <td class='right'>" + '<s:label name="" value="'+ importeF + '" />' + "</td>\n\
                                    <td>" + '<s:label name="" value="'+ cuenta + '" />' + "</td>\n\
                                    <td></td>\n\
                                    <td></td>\n\
                                    <td></td>\n\
                                    <td></td>\n\
                                    <td></td>\n\
                                    </tr>";
            
                        $(event.target).parents("tr").before(fila);
                        // se limpian los input text
                        $("#inputCuentaCargo").tokenInput("clear").focus();
                        $("#inputImporteCargo").val("");
                        
                        
                    } else {
                        alert("El formato del importe es incorrecto, solo debe contener números y 2 dígitos decimales");
                    }
                    
                })
                
                $("#btnAbono").click(function(event) {
                    event.preventDefault();
                    // verificar los campos
                    var importe = $("#inputImporteAbono").val();
                    var cuenta = $("#inputCuentaAbono").val();
                    
                    if ($.trim(importe) == "") {
                        alert("Ingrese el importe");
                        return;
                    }
                    
                    if ($.trim(cuenta) == "") {
                        alert("Ingrese la cuenta"); 
                        return;
                    }
                    
                    if (/^\d+(\.\d{1,2})?$/.test(importe)) {
                        // todo ok, se agrega la siguiente fila 
                        var importeF = $.number(importe, 2);
                                                
                        var fila = "<tr>\n\
                                    <td></td>\n\
                                    <td></td>\n\
                                    <td></td>\n\
                                    <td></td>\n\
                                    <td>" + '<s:label name="" value="'+ cuenta + '" />' + "</td>\n\
                                    <td class=\"right\">" + '<s:label name="" value="'+ importeF + '" />' + "</td>\n\
                                    <td></td>\n\
                                    </tr>";
            
                        $(event.target).parents("tr").before(fila);
                        
                        // borra la letra a
                        $(event.target).parents("tr").children("td:nth-child(4)").html("");
                        // posiciona la letra a
                        $("#tbodyAbono tr:first-child").children("td:nth-child(4)").html("a");
                        
                        
                        // se limpian los input text                        
                        $("#inputCuentaAbono").tokenInput("clear").focus();
                        $("#inputImporteAbono").val("");
                        
                    } else {
                        alert("El formato del importe es incorrecto, solo debe contener números y 2 dígitos decimales");
                    }
                    
                })
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
                        <h1 class="medium">Registrar nuevo asiento</h1>
                    </div>                    

                    <div id="form_wrapper">                        

                        <div id="errorDiv" class="ui-widget">
                            <div class="ui-state-error ui-corner-all" style="padding: .4em .7em;">
                                <span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
                                <div id="errorList"><s:actionerror /></div>                               
                            </div>
                        </div>

                        <s:form action="" method="post" cssClass="inputForm">

                            <fieldset id="rAsientoForm_comprobantePago">
                                <legend class="little2">Datos de asiento</legend>
                                <dl>
                                    <dt>Fecha</dt>
                                    <dd><s:textfield name="" id="fechaAsiento" placeholder="" /></dd>
                                    <dt>Glosa o descripción de la operación</dt>
                                    <dd><s:textfield name="" id="glosaAsiento" placeholder="" /></dd>
                                </dl>
                            </fieldset>
                            <fieldset>
                                <legend class="little2">Cuentas afectadas</legend>
                                <table id="detallesAsiento">
                                    <thead>
                                        <tr>
                                            <th style="width: 70px">Importe</th>
                                            <th style="width: 190px">Cuentas que se cargan</th>
                                            <th style="width: 40px"></th>
                                            <th style="width: 35px"></th>
                                            <th style="width: 190px">Cuentas que se abonan</th>
                                            <th style="width: 70px">Importe</th>
                                            <th style="width: 40px"></th>
                                        </tr>
                                    </thead>
                                    <tbody id="tbodyCargo">
                                        <tr>
                                            <td><input type="text" id="inputImporteCargo" class="inputLittle border1" /></td>
                                            <td><input type="text" id="inputCuentaCargo" class="jazzToken" /></td>
                                            <td><button id="btnCargo" class="button"><span class="label">OK</span></button></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                        </tr>
                                    </tbody>
                                    <tbody id="tbodyAbono">
                                        <tr>
                                            <td></td>                                            
                                            <td></td>
                                            <td></td>
                                            <td>a</td>
                                            <td><input type="text" id="inputCuentaAbono" class="jazzToken" /></td>
                                            <td><input type="text" id="inputImporteAbono" class="inputLittle border1" /></td>
                                            <td><button id="btnAbono" class="button"><span class="label">OK</span></button></td>
                                        </tr>
                                    </tbody>
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
