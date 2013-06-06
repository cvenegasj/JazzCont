<%-- 
    Document   : ver-libros
    Created on : 05/06/2013, 03:32:12 PM
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
                $("#selectLibroCompras").change(function() {
                    var selectedVal = $("#selectLibroCompras option:selected").val();
                    var selectedText = $("#selectLibroCompras option:selected").text();
                    
                    if(selectedVal == -1) {
                        return;
                    }

                    var urlDestino = "LibroComprasAction_show?idLibro=" + selectedVal + "&prd=" + selectedText;
                    window.location = urlDestino;
                });
                
                $("#selectLibroVentas").change(function() {
                    var selectedVal = $("#selectLibroVentas option:selected").val();
                    var selectedText = $("#selectLibroVentas option:selected").text();
                    
                    if(selectedVal == -1) {
                        return;
                    }

                    var urlDestino = "LibroVentasAction_show?idLibro=" + selectedVal + "&prd=" + selectedText;
                    window.location = urlDestino;
                });
                
                $("#selectLibroDiarioS").change(function() {
                    var selectedVal = $("#selectLibroDiarioS option:selected").val();
                    var selectedText = $("#selectLibroDiarioS option:selected").text();
                    
                    if(selectedVal == -1) {
                        return;
                    }

                    var urlDestino = "LibroDiarioSimplificadoAction_show?idLibro=" + selectedVal + "&prd=" + selectedText;
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
                <%@ include file="/WEB-INF/jspf/barra_sesion_cliente.jspf" %>                
            </div>           

            <div id="widthArea">

                <%@ include file="/WEB-INF/jspf/lateral_izq_cliente.jspf" %>

                <div id="contentArea">                    

                    <div id="headerContentArea">
                        <h1 class="medium">Libros contables: 
                            <s:if test="%{#session.sesionUsuario.empresaCliente.getTipoRegimen() == 'NRUS'}">
                                <span> Nuevo RUS (NRUS)</span>
                            </s:if>
                            <s:if test="%{#session.sesionUsuario.empresaCliente.getTipoRegimen() == 'RER'}">
                                <span> Régimen Especial (RER)</span>
                            </s:if>
                            <s:if test="%{#session.sesionUsuario.empresaCliente.getTipoRegimen() == 'RG'}">
                                <span> Régimen General (RG)</span>
                            </s:if>
                        </h1>
                    </div>                    

                    <div id="divR_wrapper">

                        <div class="libro_wrapper">
                            <a id="libroCompras" href="#">Libro de Registro de Compras</a>
                            <s:if test="%{#session.sesionUsuario.empresaCliente.getTipoRegimen() == 'RER' || #session.sesionUsuario.empresaCliente.getTipoRegimen() == 'RG'}">
                                <span> - obligatorio</span>
                            </s:if>                            

                            <div class="libroMeses">
                                <s:select id="selectLibroCompras" name="" list="librosCompras" 
                                          headerKey="-1" headerValue="Seleccione mes"
                                          listKey="idLibroRegistroCompras" listValue="%{new java.text.SimpleDateFormat(\"MM-yyyy\").format(periodo)}" />
                            </div>
                        </div> 
                        <div class="libro_wrapper">
                            <a id="libroVentas" href="#">Libro de Registro de Ventas e Ingresos</a>
                            <s:if test="%{#session.sesionUsuario.empresaCliente.getTipoRegimen() == 'RER' || empresaCliente.empresaCliente.getTipoRegimen() == 'RG'}">
                                <span> - obligatorio</span>
                            </s:if>  

                            <div class="libroMeses">
                                <s:select id="selectLibroVentas" name="" list="librosVentas" 
                                          headerKey="-1" headerValue="Seleccione mes"
                                          listKey="idLibroRegistroVentas" listValue="new java.text.SimpleDateFormat(\"MM-yyyy\").format(periodo)" />
                            </div>
                        </div>
                        <!-- Libro diario simplificado -->
                        <s:if test="%{#session.sesionUsuario.empresaCliente.isDiarioSimplificadoHabilitado()}">
                            <div class="libro_wrapper">
                                <a id="libroDiarioS" href="#">Libro Diario Simplificado</a>
                                <div class="libroMeses">                               
                                    <s:select id="selectLibroDiarioS" name="" list="librosDiarioSimplificados" 
                                              headerKey="-1" headerValue="Seleccione mes"
                                              listKey="idLibroDiarioSimplificado" listValue="new java.text.SimpleDateFormat(\"MM-yyyy\").format(periodo)" />
                                </div>
                            </div>  
                        </s:if>

                    </div>
                </div>
                <div class="spacer"></div>                

            </div>
        </div>

        <!-- footer -->                    
        <%@ include file="/WEB-INF/jspf/footer.jspf" %>        
    </body>
</html>

