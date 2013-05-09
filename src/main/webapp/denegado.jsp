<%-- 
    Document   : denegado
    Created on : 24/11/2012, 05:47:03 AM
    Author     : Venegas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link href='http://fonts.googleapis.com/css?family=Dosis' rel='stylesheet' type='text/css'>        
        <title>Acceso denegado | Jazz Contadores</title>
        <link rel="stylesheet" type="text/css" href="css/style.css">        
        <link rel="shortcut icon" href="favicon1.ico">
        <link rel="icon" type="image/ico" href="favicon1.ico">

        <script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>

    </head>
    <body>
        <header>
            <div id="header_content">
                <div id="logo"><a href="<%= request.getContextPath()%>"><img src="img/logo_principal2.jpg" width="240" height="60" alt="Jazz Contadores" /></a></div>
                <div id="navigation">
                    <div id="levelOnecontainer">
                        <div style="float: left;">
                            <ul class="levelOne">
                                <li><a href="<%= request.getContextPath()%>" >Inicio</a><img class="menue_seperator" src="img/seperator.jpg" width="2" height="2" /></li>
                                <li><a href="<%= request.getContextPath()%>/estudio" class="active" >Nuestro estudio</a><img class="menue_seperator" src="img/seperator.jpg" width="2" height="2" /></li>
                                <li><a href="<%= request.getContextPath()%>/acceso" >Acceso</a><img class="menue_seperator" src="img/seperator.jpg" width="2" height="2" /></li>
                                <li><a href="<%= request.getContextPath()%>/contacto" >Contáctanos</a><img class="menue_seperator" src="img/seperator.jpg" width="2" height="2" /></li>                                
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </header>

        <div id="fixed_main">
            <div id="intro_strap">
                <div class="large centre">
                    <h1 class="little">Acceso denegado</h1>
                    Usted no tiene los suficientes permisos para ver la página.
                </div>                    
            </div>            
        </div>

        <!-- footer -->                    
        <%@ include file="/WEB-INF/jspf/footer.jspf" %>       
    </body>
</html>
