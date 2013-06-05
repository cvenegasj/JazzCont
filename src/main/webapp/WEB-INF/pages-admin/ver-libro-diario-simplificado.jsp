<%-- 
    Document   : ver-libro-diario-simplificado
    Created on : 24/12/2012, 03:44:35 PM
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
                        <h1 class="medium">Libro Diario Simplificado: <s:property value="periodo" /></h1>
                        <div class="right">
                            <a id="btnPdf" href="#" title="Ver en pdf"><img width="25" src="<s:url value="/img/pdf_icon.png" />" /></a>
                            <a id="btnExcel" href="#" title="Ver en excel"><img width="25" src="<s:url value="/img/excel_2010_icon.png" />" /></a>                        
                        </div>
                    </div>                    

                    <div id="libroContableWrapper">
                        <table id="libroDiarioSimplificadoTable" class="libroContableTable">
                            <thead>
                                <tr>
                                    <th rowspan="2" style="width: 90px">Número correlativo o código único de la operación</th>
                                    <th rowspan="2" style="width: 90px">Fecha o período de la operación</th>
                                    <th rowspan="2" style="width: 230px">Glosa o descripción de la operación</th>
                                    <th colspan="4">Activos</th>
                                    <th colspan="4">Pasivos</th>
                                    <th colspan="4">Patrimonio</th>
                                    <th colspan="4">Gastos</th>
                                    <th colspan="4">Ingresos</th>
                                    <th colspan="4">Saldos intermediarios de gestión</th>
                                    <th colspan="4">Cuentas de función del gasto</th>
                                    <th colspan="4">Cuentas de orden</th>
                                </tr>
                                <tr>
                                    <th style="width: 60px">21</th>
                                    <th style="width: 60px">231</th>
                                    <th style="width: 60px">3</th>
                                    <th style="width: 60px">231</th>
                                    <th style="width: 60px">123</th>
                                    <th style="width: 60px">123</th>
                                    <th style="width: 60px">123</th>
                                    <th style="width: 60px">31</th>
                                    <th style="width: 60px">321</th>
                                    <th style="width: 60px">123</th>
                                    <th style="width: 60px">132</th>
                                    <th style="width: 60px">123</th>
                                    <th style="width: 60px">123</th>
                                    <th style="width: 60px">123</th>
                                    <th style="width: 60px">123</th>
                                    <th style="width: 60px">123</th>
                                    <th style="width: 60px">123</th>
                                    <th style="width: 60px">123</th>
                                    <th style="width: 60px">123</th>
                                    <th style="width: 60px">123</th>
                                    <th style="width: 60px">123</th>
                                    <th style="width: 60px">123</th>
                                    <th style="width: 60px">123</th>
                                    <th style="width: 60px">123</th>
                                    <th style="width: 60px">123</th>
                                    <th style="width: 60px">335</th>
                                    <th style="width: 60px">35</th>
                                    <th style="width: 60px">3558</th>
                                    <th style="width: 60px">58</th>
                                    <th style="width: 60px">58</th>
                                    <th style="width: 60px">252</th>
                                    <th style="width: 60px">25</th>                                    
                                </tr>                               
                            </thead>
                            <tbody>
                                <tr>
                                    <td>001</td>
                                    <td>01/12/2012</td>
                                    <td>Asiento prueba</td>
                                    <td>9,000,000.00</td>
                                    <td>6,000.00</td>
                                    <td>5,500.00</td>
                                    <td>1,800.00</td>
                                    <td>1,100.00</td>
                                    <td>1,100.00</td>
                                    <td>1,100.00</td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td>002</td>
                                    <td>02/12/2012</td>
                                    <td>Asiento prueba</td>
                                    <td>9,000.00</td>
                                    <td>6,000.00</td>
                                    <td>5,500.00</td>                                    
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td>1,800.00</td>
                                    <td>1,100.00</td>
                                    <td>1,100.00</td>
                                    <td>1,100.00</td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td>003</td>
                                    <td>03/12/2012</td>
                                    <td>Asiento prueba</td>
                                    <td></td>                                    
                                    <td>1,100.00</td>
                                    <td>1,100.00</td>
                                    <td>1,100.00</td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td>6,000.00</td>
                                    <td>5,500.00</td>
                                    <td>1,800.00</td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td>004</td>
                                    <td>04/12/2012</td>
                                    <td>Asiento prueba</td>
                                    <td></td>
                                    <td></td>
                                    <td>5,500.00</td>
                                    <td>1,800.00</td>
                                    <td>1,100.00</td>                                    
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td>1,100.00</td>
                                    <td>1,100.00</td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>


                </div>
                <div class="spacer"></div>

            </div>
        </div>

        <!-- footer -->                    
        <%@ include file="/WEB-INF/jspf/footer.jspf" %>        
    </body>
</html>
