<%-- 
    Document   : intro-admin
    Created on : 14/11/2012, 08:31:12 PM
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
                        <h1 class="medium">Lista de clientes registrados</h1>
                    </div>
                    <div id="divPad">
                        <div id="d1" class="lfloat">
                            <span>Criterio de orden:</span>
                            <select id="criterioOrden">
                                <option>Todos</option>
                                <option>A-F</option>
                                <option>G-M</option>
                                <option>N-T</option>
                                <option>U-Z</option>
                                <option>Régimen General</option>
                                <option>Régimen Especial</option>
                                <option>Nuevo Régimen Simplificado (NRUS)</option>
                            </select>
                        </div>
                        <div id="searchBar" class="rfloat">
                            <input type="text" name="search" id="search" placeholder="buscar clientes" />
                        </div>
                        <div class="spacer"></div>
                    </div>

                    <div id="listaClientes">
                        
                        <!-- elemento de la lista -->
                        <div class="elementoListaClientes">

                            <s:url action="EmpresaClienteAction_show" var="urlCliente" >
                                <s:param name="ruc">20103065211</s:param>
                            </s:url>

                            <s:a cssClass="aImgElc lfloat" href="%{urlCliente}">
                                <img class="img" alt="" src="<s:url value="/img/iconic/user_24x32.png"/>" />
                            </s:a>
                            <div class="divDerechaElc"> 
                                <div class="tcElcContainer">
                                    <s:a cssClass="tituloClienteElc linkUnderline" href="%{urlCliente}">Trujillo Empresa S.A.C</s:a>
                                    </div>
                                    <div class="detallesElc">
                                        <span class="lineaDetalleElc">
                                            <span class="bold">RUC: </span><span>20103065211</span>                                        
                                        </span>
                                        <span class="lineaDetalleElc">
                                            <span class="bold">Tipo de Régimen: </span><span>General</span>                                        
                                        </span>                                    
                                        <span class="linksElc">
                                        <s:url action="verLibros" var="urlLibros" >
                                            <s:param name="ruc">20103065211</s:param>
                                        </s:url>
                                        <s:a href="%{urlLibros}">Ver libros</s:a> 
                                        </span>                                    
                                    </div>
                                </div>
                            </div>
                            <!-- elemento de la lista -->
                            <div class="elementoListaClientes">
                                <a class="aImgElc lfloat" href="#">
                                    <img class="img" alt="" src="<s:url value="/img/iconic/user_24x32.png"/>" />
                            </a>
                            <div class="divDerechaElc"> 
                                <div class="tcElcContainer">
                                    <a class="tituloClienteElc linkUnderline" href="#">Comprando SRL</a>
                                </div>
                                <div class="detallesElc">
                                    <span class="lineaDetalleElc">
                                        <span class="bold">RUC: </span><span>20103065211</span>                                        
                                    </span>
                                    <span class="lineaDetalleElc">
                                        <span class="bold">Tipo de Régimen: </span><span>General</span>                                        
                                    </span>                                    
                                    <span class="linksElc">
                                        <s:a href="#">Ver libros</s:a>
                                        </span>                                    
                                    </div>
                                </div>
                            </div>
                            <!-- elemento de la lista -->
                            <div class="elementoListaClientes">
                                <a class="aImgElc lfloat" href="#">
                                    <img class="img" alt="" src="<s:url value="/img/iconic/user_24x32.png"/>" />
                            </a>
                            <div class="divDerechaElc"> 
                                <div class="tcElcContainer">
                                    <a class="tituloClienteElc linkUnderline" href="#">Perú trabaja SRL</a>
                                </div>
                                <div class="detallesElc">
                                    <span class="lineaDetalleElc">
                                        <span class="bold">RUC: </span><span>20103065211</span>                                        
                                    </span>
                                    <span class="lineaDetalleElc">
                                        <span class="bold">Tipo de Régimen: </span><span>General</span>                                        
                                    </span>                                    
                                    <span class="linksElc">
                                        <s:a href="#">Ver libros</s:a>
                                        </span>                                    
                                    </div>
                                </div>
                            </div>
                            <!-- elemento de la lista -->
                            <div class="elementoListaClientes">
                                <a class="aImgElc lfloat" href="#">
                                    <img class="img" alt="" src="<s:url value="/img/iconic/user_24x32.png"/>" />
                            </a>
                            <div class="divDerechaElc"> 
                                <div class="tcElcContainer">
                                    <a class="tituloClienteElc linkUnderline" href="#">Computer LC SAC</a>
                                </div>
                                <div class="detallesElc">
                                    <span class="lineaDetalleElc">
                                        <span class="bold">RUC: </span><span>20103065211</span>                                        
                                    </span>
                                    <span class="lineaDetalleElc">
                                        <span class="bold">Tipo de Régimen: </span><span>General</span>                                        
                                    </span>                                    
                                    <span class="linksElc">
                                        <s:a href="#">Ver libros</s:a>
                                        </span>                                    
                                    </div>
                                </div>
                            </div>      
                        </div>


                    </div>
                    <div class="spacer"></div>  

            </div>
        </div>

        <!-- footer -->                    
        <%@ include file="/WEB-INF/jspf/footer.jspf" %>        
    </body>
</html>
