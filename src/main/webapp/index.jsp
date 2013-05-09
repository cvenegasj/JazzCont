<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link href='http://fonts.googleapis.com/css?family=Dosis' rel='stylesheet' type='text/css'>        
        <title>Jazz Contadores | Trujillo - Perú</title>
        <link rel="stylesheet" type="text/css" href="css/style.css">        
        <link rel="shortcut icon" href="favicon1.ico">
        <link rel="icon" type="image/ico" href="favicon1.ico">

        <script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
        <script type="text/javascript" src="js/responsiveslides.min.js"></script>

        <script type="text/javascript">
            $(function() {
                $(".rslides").responsiveSlides({
                    auto: true,
                    timeout: 6000,
                    pause: false,
                    pauseControls: false
                });                
            });
        </script>                
    </head>
    <body>
        <header>
            <div id="header_content">
                <div id="logo"><a href="<%= request.getContextPath()%>"><img src="img/logo_principal2.jpg" width="240" height="60" alt="Jazz Contadores" /></a></div>
                <div id="navigation">
                    <div id="levelOnecontainer">
                        <div style="float: left;">
                            <ul class="levelOne">
                                <li><a href="<%= request.getContextPath()%>" class="active" >Inicio</a><img class="menue_seperator" src="img/seperator.jpg" width="2" height="2" /></li>
                                <li><a href="<%= request.getContextPath()%>/estudio" >Nuestro estudio</a><img class="menue_seperator" src="img/seperator.jpg" width="2" height="2" /></li>
                                <li><a href="<%= request.getContextPath()%>/acceso" >Acceso</a><img class="menue_seperator" src="img/seperator.jpg" width="2" height="2" /></li>
                                <li><a href="<%= request.getContextPath()%>/contacto" >Contáctanos</a><img class="menue_seperator" src="img/seperator.jpg" width="2" height="2" /></li>                                
                            </ul>
                        </div>
                    </div>
                </div>               
            </div>
        </header>

        <div id="main">
            <div id="slides">
                <ul class="rslides">
                    <li><img src="img/cont_img01-2.jpg" alt="" /></li>
                    <li><img src="img/cont_img02.jpg" alt="" /></li>
                    <li><img src="img/cont_img03.jpg" alt="" /></li>
                    <li><img src="img/cont_img04.jpg" alt="" /></li>
                    <li><img src="img/cont_img05.jpg" alt="" /></li>
                </ul>                
            </div>
            
            <div id="intro_strap">
                <div id="intro_content">
                    <strong>Jazz Contadores</strong> es un estudio contable cuya misión principal es facilitar a sus clientes
                    las tareas contables realizadas diariamente; tales como cálculo y declaración de tributos, manejo 
                    de libros contables, así como de sus Estados Financieros según la legislación peruana.                    
                </div>
                <div id="social">Encuéntranos en  <a href="#"><img style="vertical-align: middle;" src="img/facebook_32.png" height="20" width="20"></a></div>

            </div>

        </div>

        <!-- footer -->                    
        <%@ include file="/WEB-INF/jspf/footer.jspf" %>       
    </body>
</html>
