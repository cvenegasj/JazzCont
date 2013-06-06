<%-- 
    Document   : ver-libro-ventas
    Created on : 05/06/2013, 03:32:46 PM
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
        <!-- Ext JS css -->
        <link type="text/css" href="<s:url value="/extjs-4.2.0/resources/css/ext-all-neptune.css"/>" rel="stylesheet" />

        <link type="text/css" href="<s:url value="/css/custom-theme/jquery-ui-1.9.1.custom.min.css"/>" rel="stylesheet" />	
        <script type="text/javascript" src="<s:url value="/js/jquery-1.7.2.min.js"/>"></script>        
        <script type="text/javascript" src="<s:url value="/js/jquery-ui-1.9.1.custom.min.js"/>"></script> 

        <script type="text/javascript" src="<s:url value="/js/jquery.placeholder.min.js"/>"></script> 
        <script type="text/javascript" src="<s:url value="/js/scripts.js"/>"></script>

        <!-- JQuery number plugin -->
        <script type="text/javascript" src="<s:url value="/js/jquery.number.min.js"/>"></script>

        <!-- Ext JS js -->
        <script type="text/javascript" src="<s:url value="/extjs-4.2.0/ext-all-debug.js"/>"></script>        
        <script type="text/javascript" src="<s:url value="/js/reg-ventas-cliente-ext.js"/>"></script>

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
                
                $("#dialogDetallesComprobante").dialog({
                    autoOpen: false,
                    modal: true,
                    resizable: false,                    
                    width: 515,   
                    height: 485,
                    buttons: {
                        "Aceptar": function() {
                            $(this).dialog("close");                                                        
                        }
                    },                    
                    position: "center"
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
                        <h1 class="medium">Libro de Registro de Ventas e Ingresos</strong></h1><br />
                        <div class="fRight">
                            <div class="dropdown">
                                <a href="#" class="button"><span class="icon icon96">&nbsp;</span><span class="label">Opciones</span><span class="toggle">&nbsp;</span></a>
                                <div class="dropdown-slider">
                                    <a href="#" class="ddm"><span class="label">Descargar como PDF</span></a>
                                    <a href="#" class="ddm"><span class="label">Descargar como Excel</span></a>                                   
                                </div> <!-- /.dropdown-slider -->
                            </div> <!-- /.dropdown -->                            
                        </div>
                        <div class="spacer"></div>
                    </div>                   

                    <div id="libroContableWrapper">
                        <div id="libroVentasExt"></div>               
                    </div> 

                </div>
                <div class="spacer"></div>                

                <!-- dialogDetallesComprobante -->
                <div id="dialogDetallesComprobante" title="Detalles del comprobante">

                </div>
                <!-- ********** -->

            </div>
        </div>

        <!-- footer -->                    
        <%@ include file="/WEB-INF/jspf/footer.jspf" %>        
    </body>
</html>
