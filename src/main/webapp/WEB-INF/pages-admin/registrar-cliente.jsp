<%-- 
    Document   : registrar-cliente
    Created on : 29/12/2012, 07:48:29 PM
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
                
                $("#tc1, #tc2").hide();                               
                
                $("#selectRegimen").on("change", function() {
                    var selected = $(this).val();
                    //var contabilidadSelected = $("#selectContabilidad option:selected").val();
                    
                    if (selected == "RG") {
                        $("#tc1, #tc2").show(200); 
                        // seleccionar todos los libros de simplificada
                        $("#diarioSimplificado").prop("checked", true).on("click", function() {return false;});
                        $("#registroCompras").prop("checked", true).on("click", function() {return false;});
                        $("#registroVentas").prop("checked", true).on("click", function() {return false;});
                        
                    } else if (selected == "RER") {
                        $("#tc1, #tc2").hide();
                        // seleccionar solo Libro de compras y ventas
                        $("#diarioSimplificado").prop("checked", false).off("click");
                        $("#registroCompras").prop("checked", true).on("click", function() {return false;});
                        $("#registroVentas").prop("checked", true).on("click", function() {return false;});
                        
                    } else if (selected == "NRUS") {
                        $("#tc1, #tc2").hide();
                        // seleccionar solo Libro de compras y ventas
                        $("#diarioSimplificado").prop("checked", false).off("click");
                        $("#registroCompras").prop("checked", true).on("click", function() {return false;});
                        $("#registroVentas").prop("checked", true).on("click", function() {return false;});
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

                    <div id="headerContentArea">
                        <h1 class="medium">Registrar nuevo cliente</h1>
                    </div>                    

                    <div id="form_wrapper">                        

                        <div id="errorDiv" class="ui-widget">
                            <div class="ui-state-error ui-corner-all" style="padding: .4em .7em;">
                                <span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
                                <div id="errorList"><s:actionerror /></div>                               
                            </div>
                        </div>

                        <s:form action="EmpresaClienteAction_save" method="post" cssClass="inputForm">

                            <fieldset>
                                <legend class="little2">Datos de la empresa</legend>
                                <dl>
                                    <dt><s:label for="ruc" value="* RUC" /></dt>
                                    <dd><s:textfield name="empresaCliente.ruc" id="ruc" placeholder="" /></dd>
                                    <dt><s:label for="nombreEmpresa" value="* Nombre de la empresa" /></dt>
                                    <dd><s:textfield name="empresaCliente.nombreEmpresa" id="nombreEmpresa" placeholder="" /></dd>
                                    <dt><s:label for="razonSocial" value="* Razón Social" /></dt>
                                    <dd><s:textfield name="empresaCliente.razonSocial" id="razonSocial" placeholder="" /></dd>
                                    <dt><s:label for="selectRegimen" value="* Tipo de régimen" /></dt>
                                    <dd>
                                        <select name="empresaCliente.tipoRegimen" id="selectRegimen">
                                            <option value="NRUS" selected>NRUS</option>                                            
                                            <option value="RER">RER</option>
                                            <option value="RG">RG</option>   
                                        </select>                                        
                                    </dd>
                                    <dt id="tc1">Tipo de contabilidad</dt>
                                    <dd id="tc2">
                                        <select id="selectContabilidad">
                                            <option value="simplificada" selected>Simplificada</option>
                                            <!--<option value="completa">Completa</option>-->
                                        </select>
                                    </dd>
                                    <dt>* Libros de contabilidad</dt>
                                    <dd>
                                        <s:checkbox id="diarioSimplificado" name="empresaCliente.diarioSimplificadoHabilitado" fieldValue="true" value="false" onclick="" />
                                        <s:label for="diarioSimplificado" value="Libro Diario Simplificado" /><br />
                                        <s:checkbox id="registroCompras" name="empresaCliente.registroComprasHabilitado" fieldValue="true" value="true" onclick="return false" onkeydown="return false" />
                                        <s:label for="registroCompras" value="Libro de Registro de Compras" /><br />
                                        <s:checkbox id="registroVentas" name="empresaCliente.registroVentasHabilitado" fieldValue="true" value="true" onclick="return false" onkeydown="return false" />
                                        <s:label for="registroVentas" value="Libro de Registro de Ventas e Ingresos" />                                                                           
                                    </dd>                                    
                                    <dt><s:label for="selectEstado" value="Estado en el sistema" /></dt>
                                    <dd>
                                        <select name="empresaCliente.estado" id="selectEstado">
                                            <option value="habilitado">habilitado</option>
                                            <option value="deshabilitado">deshabilitado</option>
                                        </select>
                                    </dd>
                                    <dt><s:label for="descripcion" value="Descripción del negocio" /></dt>
                                    <dd><s:textarea name="empresaCliente.descripcion" id="descripcion" cols="45" rows="4" /></dd>
                                    <dt><s:label for="direccion" value="Dirección" /></dt>
                                    <dd><s:textfield name="empresaCliente.direccion" id="direccion" placeholder="" /></dd>
                                    <dt><s:label for="telefono1" value="Teléfono 1" /></dt>
                                    <dd><s:textfield name="empresaCliente.telefono1" id="telefono1" placeholder="" /></dd>
                                    <dt><s:label for="telefono2" value="Teléfono 2" /></dt>
                                    <dd><s:textfield name="empresaCliente.telefono2" id="telefono2" placeholder="" /></dd>
                                    <dt><s:label for="fechaFrontera" value="Fecha frontera" /></dt>
                                    <dd><s:textfield name="empresaCliente.fechaFrontera" id="fechaFrontera" placeholder="" /></dd>                                    
                                </dl>
                            </fieldset> 
                            <fieldset>
                                <legend class="little2">Datos del contacto</legend>
                                <dl>
                                    <dt><s:label for="cNombres" value="* Nombres" /></dt>
                                    <dd><s:textfield name="empresaCliente.contacto.nombres" id="cNombres" placeholder="" /></dd>
                                    <dt><s:label for="cApellidos" value="* Apellidos" /></dt>
                                    <dd><s:textfield name="empresaCliente.contacto.apellidos" id="cApellidos" placeholder="" /></dd>
                                    <dt><s:label for="cDireccion" value="Dirección" /></dt>
                                    <dd><s:textfield name="empresaCliente.contacto.direccion" id="cDireccion" placeholder="" /></dd>
                                    <dt><s:label for="cCelular" value="Celular" /></dt>
                                    <dd><s:textfield name="empresaCliente.contacto.celular" id="cCelular" placeholder="" /></dd>
                                    <dt><s:label for="cEmailPrincipal" value="* Email principal" /></dt>
                                    <dd><s:textfield name="empresaCliente.contacto.emailPrincipal" id="cEmailPrincipal" placeholder="" /></dd>
                                    <dt><s:label for="cContraseña" value="* Contraseña" /></dt>
                                    <dd><s:textfield name="empresaCliente.contacto.password" id="cContraseña" placeholder="" /></dd>
                                    <dt><s:label for="cEmailSecundario" value="Email secundario" /></dt>
                                    <dd><s:textfield name="empresaCliente.contacto.emailSecundario" id="cEmailSecundario" placeholder="" /></dd>
                                    <dt><s:label for="cEmailFacebook" value="Email facebook" /></dt>
                                    <dd><s:textfield name="empresaCliente.contacto.emailFacebook" id="cEmailFacebook" placeholder="" /></dd>
                                </dl>
                            </fieldset> 

                            <div class="footer_form2">
                                <s:submit type="submit" cssClass="oneClick" value="Registrar" />
                            </div>
                        </s:form> 
                    </div>


                </div>
                <div class="spacer"></div>

            </div>
        </div>

        <!-- footer -->                    
        <%@ include file="/WEB-INF/jspf/footer.jspf" %>        
    </body>
</html>
