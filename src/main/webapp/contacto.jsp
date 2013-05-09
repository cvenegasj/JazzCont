<%-- 
    Document   : contacto
    Created on : 05/11/2012, 04:34:13 PM
    Author     : Venegas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link href='http://fonts.googleapis.com/css?family=Dosis' rel='stylesheet' type='text/css'>        
        <title>Contacto | Jazz Contadores</title>
        <link rel="stylesheet" type="text/css" href="css/style.css">        
        <link rel="shortcut icon" href="favicon1.ico">
        <link rel="icon" type="image/ico" href="favicon1.ico">

        <link type="text/css" href="css/custom-theme/jquery-ui-1.9.1.custom.min.css" rel="stylesheet" />	
        <script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>        
        <script type="text/javascript" src="js/jquery-ui-1.9.1.custom.min.js"></script> 
        
        <script type="text/javascript" src="js/jquery.placeholder.min.js"></script> 
        
        <script type="text/javascript">
            $(function() {
                $( "input:submit, button").button(); 
                
                //placeholder para navegadores que no soportan
                $('input, textarea').placeholder();
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
                                <li><a href="<%= request.getContextPath()%>" >Inicio</a><img class="menue_seperator" src="img/seperator.jpg" width="2" height="2" /></li>
                                <li><a href="<%= request.getContextPath()%>/estudio" >Nuestro estudio</a><img class="menue_seperator" src="img/seperator.jpg" width="2" height="2" /></li>
                                <li><a href="<%= request.getContextPath()%>/acceso" >Acceso</a><img class="menue_seperator" src="img/seperator.jpg" width="2" height="2" /></li>
                                <li><a href="<%= request.getContextPath()%>/contacto" class="active" >Contáctanos</a><img class="menue_seperator" src="img/seperator.jpg" width="2" height="2" /></li>                                
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </header>

        <div id="fixed_main">
            <div id="intro_strap">
                <div class="large centre">
                    <h1 class="little">Contáctanos</h1>
                    Información para facilitar el contacto con nuestros clientes.
                </div>   
            </div>    
            <div id="main_strap">
                <!-- Contenido de contacto principal -->
                <div id="contact_main">
                    <div id="map">
                        <h2 class="little2">Ubícanos</h2>
                        <div id="map-shadow">
                            <div id="gmap">
                                <iframe width="485" height="265" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" src="https://maps.google.com/maps/ms?msa=0&amp;msid=206682954805226876784.0004cdf22f3b43d6c9780&amp;ie=UTF8&amp;t=m&amp;ll=-8.11045,-79.035194&amp;spn=0.005629,0.010386&amp;z=16&amp;output=embed"></iframe><br />
                            </div>

                        </div>
                    </div>
                    <div id="contact">
                        <h2 class="little2">Envíanos un mensaje</h2>
                        <div id="form_wrapper">
                            <form id="contact_form" action="" method="post">
                                <fieldset id="body_contact_form">
                                    <dl>
                                        <dt></dt>
                                        <dd><input type="text" name="nombre" id="" placeholder="Nombre" /></dd>
                                        <dt></dt>
                                        <dd><input type="text" name="email" id="" placeholder="Email" /></dd>
                                        <dt></dt>
                                        <dd><input type="text" name="tema" id="" placeholder="Tema" /></dd>
                                        <dt></dt>
                                        <dd><textarea id="" cols="70" rows="5" placeholder="Mensaje"></textarea></dd>                                            
                                    </dl>
                                </fieldset>
                                <div class="footer_form">
                                    <input type="submit" class=""  value="Enviar" />
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="spacer"></div>
                </div>
                <!-- Contenido de contacto secundario -->
                <div id="contact_secondary">
                    <div id="medium">
                        <div class="contact-data">
                            <h3 class="little2">Email</h3>
                            <p><a href="mailto:info@jazzcontadores.com.pe">info@jazzcontadores.com.pe</a></p>
                        </div>
                        <div class="contact-data">
                            <h3 class="little2">Dirección</h3>
                            <p>Lorem ipsum Lorem ipsum</p>
                        </div>
                        <div class="contact-data">
                            <h3 class="little2">Teléfonos</h3>
                            <p>+51 (044) 282829</p>
                            <p>+51 (044) 949185111</p>
                        </div>
                    </div>
                </div>
            </div>

        </div>

        <!-- footer -->                    
        <%@ include file="/WEB-INF/jspf/footer.jspf" %>        
    </body>
</html>
