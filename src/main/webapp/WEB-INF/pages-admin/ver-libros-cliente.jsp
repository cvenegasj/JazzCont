<%-- 
    Document   : ver-libros-rg-simp
    Created on : 24/12/2012, 01:38:09 PM
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

        <!-- Botones estilo Google+ -->
        <link type="text/css" href="<s:url value="/css/css3-buttons.css"/>" rel="stylesheet" />

        <link type="text/css" href="<s:url value="/css/custom-theme/jquery-ui-1.9.1.custom.min.css"/>" rel="stylesheet" />	
        <script type="text/javascript" src="<s:url value="/js/jquery-1.7.2.min.js"/>"></script>        
        <script type="text/javascript" src="<s:url value="/js/jquery-ui-1.9.1.custom.min.js"/>"></script> 

        <script type="text/javascript" src="<s:url value="/js/jquery.placeholder.min.js"/>"></script> 
        <script type="text/javascript" src="<s:url value="/js/scripts.js"/>"></script>

        <script type="text/javascript"> 
            $(function() {
                
                // Toggle the dropdown menu's
                $(".dropdown .button").on('click', function () {
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
                
                $("#selectLibroCompras").change(function() {
                    var selectedVal = $("#selectLibroCompras option:selected").val();
                    var selectedText = $("#selectLibroCompras option:selected").text();
                    
                    if(selectedVal == -1) {
                        return;
                    }

                    var urlDestino = "LibroComprasAction_show?ruc=<%= request.getParameter("ruc")%>&idLibro=" + selectedVal + "&prd=" + selectedText;
                    window.location = urlDestino;
                });
                
                $("#selectLibroVentas").change(function() {
                    var selectedVal = $("#selectLibroVentas option:selected").val();
                    var selectedText = $("#selectLibroVentas option:selected").text();
                    
                    if(selectedVal == -1) {
                        return;
                    }

                    var urlDestino = "LibroVentasAction_show?ruc=<%= request.getParameter("ruc")%>&idLibro=" + selectedVal + "&prd=" + selectedText;
                    window.location = urlDestino;
                });
                
                $("#selectLibroDiarioS").change(function() {
                    var selectedVal = $("#selectLibroDiarioS option:selected").val();
                    var selectedText = $("#selectLibroDiarioS option:selected").text();
                    
                    if(selectedVal == -1) {
                        return;
                    }

                    var urlDestino = "LibroDiarioSimplificadoAction_show?ruc=<%= request.getParameter("ruc")%>&idLibro=" + selectedVal + "&prd=" + selectedText;
                    window.location = urlDestino;
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
                        <h1 class="medium">Libros contables: Régimen General, Contabilidad Simplificada</h1>
                    </div>                    

                    <div id="libros_wrapper">

                        <div class="libro_wrapper">
                            <a id="libroCompras" href="#">Libro de Registro de Compras</a><span> - obligatorio</span>                                
                            <div class="libroMeses">
                                <s:select id="selectLibroCompras" name="" list="empresaCliente.librosRegistroCompras" 
                                          headerKey="-1" headerValue="Seleccione mes"
                                          listKey="idLibroRegistroCompras" listValue="%{new java.text.SimpleDateFormat(\"MM-yyyy\").format(periodo)}" />
                            </div>
                        </div> 
                        <div class="libro_wrapper">
                            <a id="libroVentas" href="#">Libro de Registro de Ventas e Ingresos</a><span> - obligatorio</span>
                            <div class="libroMeses">
                                <s:select id="selectLibroVentas" name="" list="empresaCliente.librosRegistroVentas" 
                                          headerKey="-1" headerValue="Seleccione mes"
                                          listKey="idLibroRegistroVentas" listValue="new java.text.SimpleDateFormat(\"MM-yyyy\").format(periodo)" />
                            </div>
                        </div>
                        <div class="libro_wrapper">
                            <a id="libroDiarioS" href="#">Libro Diario Simplificado</a><span> - obligatorio</span>
                            <div class="libroMeses">                               
                                <s:select id="selectLibroDiarioS" name="" list="empresaCliente.librosDiarioSimplificados" 
                                          headerKey="-1" headerValue="Seleccione mes"
                                          listKey="idLibroDiarioSimplificado" listValue="new java.text.SimpleDateFormat(\"MM-yyyy\").format(periodo)" />
                            </div>
                        </div>  
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
