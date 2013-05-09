<%-- 
    Document   : nuestroestudio
    Created on : 05/11/2012, 04:01:38 PM
    Author     : Venegas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link href='http://fonts.googleapis.com/css?family=Dosis' rel='stylesheet' type='text/css'>        
        <title>Nuestro estudio | Jazz Contadores</title>
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
                    <h1 class="little">Nuestro estudio</h1>
                    Información acerca de nuestro estudio contable.
                </div>                    
            </div>
            <div id="main_strap">
                <div id="islands">
                    <div id="island_left">
                        <h2 class="medium">Misión</h2>
                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
                            Ut hendrerit, lorem ut ullamcorper fringilla, magna purus 
                            molestie tellus, quis tempor nulla augue in dui. Duis non 
                            orci sed sapien dignissim elementum pretium id tellus. Morbi 
                            lobortis, metus in cursus tempor, nisi felis vulputate sem, 
                            ac bibendum nunc sem blandit nunc. Sed porttitor consequat 
                            mauris, scelerisque mollis arcu laoreet tempor. Praesent vitae 
                            libero leo, sed ultricies ipsum. </p>                            
                    </div>
                    <div id="island_right">
                        <h2 class="medium">Visión</h2>
                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
                            Ut hendrerit, lorem ut ullamcorper fringilla, magna purus 
                            molestie tellus, quis tempor nulla augue in dui. Duis non 
                            orci sed sapien dignissim elementum pretium id tellus. Morbi 
                            lobortis, metus in cursus tempor, nisi felis vulputate sem, 
                            ac bibendum nunc sem blandit nunc. Sed porttitor consequat 
                            mauris, scelerisque mollis arcu laoreet tempor. Praesent vitae 
                            libero leo, sed ultricies ipsum. </p>    
                    </div>   
                    <div class="spacer"></div>
                </div> 
                
            </div>
        </div>

        <!-- footer -->                    
        <%@ include file="/WEB-INF/jspf/footer.jspf" %>      
    </body>
</html>
