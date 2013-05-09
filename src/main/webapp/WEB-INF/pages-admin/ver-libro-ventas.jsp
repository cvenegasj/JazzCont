<%-- 
    Document   : ver-libro-registro-ventas
    Created on : 24/12/2012, 03:45:09 PM
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
                        <h1 class="medium">Libro de Registro de Ventas e Ingresos: <s:property value="periodo" /></h1>
                        <div class="right">
                            <a id="btnPdf" href="#" title="Ver en pdf"><img width="25" src="<s:url value="/img/pdf_icon.png" />" /></a>
                            <a id="btnExcel" href="#" title="Ver en excel"><img width="25" src="<s:url value="/img/excel_2010_icon.png" />" /></a>                        
                        </div>
                    </div>                    

                    <div id="libroContableWrapper">
                        <table id="libroVentasTable" class="libroContableTable">
                            <thead>
                                <tr>
                                    <th rowspan="3" style="width: 90px">Número correlativo del registro o código único de la operación</th>
                                    <th rowspan="3" style="width: 90px">Fecha de emisión del comprobante de pago o documento</th>
                                    <th rowspan="3" style="width: 90px">Fecha de vencimiento y/o pago</th>
                                    <th colspan="3">Comprobante de pago o documento</th>                                    
                                    <th colspan="3">Información del cliente</th>
                                    <th rowspan="3" style="width: 85px">Valor facturado de la exportación</th>
                                    <th rowspan="3" style="width: 85px">Base imponible de la operación gravada</th>
                                    <th colspan="2">Importe total de la operación exonerada o inafecta</th>
                                    <th rowspan="3" style="width: 75px">ISC</th>
                                    <th rowspan="3" style="width: 75px">IGV y/o IPM</th>                                    
                                    <th rowspan="3" style="width: 75px">Otros tributos y cargos que no forman parte de la base imponible</th>                                    
                                    <th rowspan="3" style="width: 85px">Importe total del comprobante de pago</th>                                    
                                    <th rowspan="3" style="width: 60px">Tipo de cambio</th>
                                    <th colspan="4">Referenca del comprobante de pago o documento original que se modifica</th>
                                </tr>
                                <tr>
                                    <th rowspan="2" style="width: 40px">Tipo (Tabla 10)</th>
                                    <th rowspan="2" style="width: 90px">N° serie o N° de serie de la máquina registradora</th>
                                    <th rowspan="2" style="width: 85px">Número</th>                                    
                                    <th colspan="2">Documento de identidad</th>
                                    <th rowspan="2" style="width: 180px">Apellidos y nombres, denominación o Razón Social</th> 
                                    <th rowspan="2" style="width: 75px">Exonerada</th>
                                    <th rowspan="2" style="width: 75px">Inafecta</th> 
                                    <th rowspan="2" style="width: 90px">Fecha</th>
                                    <th rowspan="2" style="width: 40px">Tipo (Tabla 10)</th>
                                    <th rowspan="2" style="width: 90px">Serie</th>
                                    <th rowspan="2" style="width: 90px">N° del comprobante de pago o documento</th>
                                </tr>
                                <tr>
                                    <th style="width: 45px">Tipo (Tabla 2)</th>
                                    <th style="width: 85px">Número</th>                                    
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>01</td> 
                                    <td>01/12/2012</td>    
                                    <td></td>    
                                    <td>02</td>    
                                    <td>0300</td>    
                                    <td>30021</td>    
                                    <td>05</td>    
                                    <td>25253625111</td>    
                                    <td>Empresa Prueba SRL</td>    
                                    <td class="right"></td>    
                                    <td class="right">1000.00</td>    
                                    <td class="right"></td>    
                                    <td class="right"></td>    
                                    <td class="right"></td> 
                                    <td class="right">190.00</td> 
                                    <td class="right"></td>    
                                    <td class="right">1190.00</td>    
                                    <td class="right"></td>    
                                    <td></td>    
                                    <td></td>    
                                    <td></td>    
                                    <td></td>                         
                                </tr>
                                <tr>
                                    <td>01</td> 
                                    <td>01/12/2012</td>    
                                    <td></td>    
                                    <td>02</td>    
                                    <td>0300</td>    
                                    <td>30021</td>    
                                    <td>05</td>    
                                    <td>25253625111</td>    
                                    <td>Empresa Prueba SRL</td>    
                                    <td class="right"></td>    
                                    <td class="right">1000.00</td>    
                                    <td class="right"></td>    
                                    <td class="right"></td>    
                                    <td class="right"></td> 
                                    <td class="right">190.00</td> 
                                    <td class="right"></td>    
                                    <td class="right">1190.00</td>    
                                    <td class="right"></td>    
                                    <td></td>    
                                    <td></td>    
                                    <td></td>    
                                    <td></td>                         
                                </tr>
                                <tr>
                                    <td>01</td> 
                                    <td>01/12/2012</td>    
                                    <td></td>    
                                    <td>02</td>    
                                    <td>0300</td>    
                                    <td>30021</td>    
                                    <td>05</td>    
                                    <td>25253625111</td>    
                                    <td>Empresa Prueba SRL</td>    
                                    <td class="right"></td>    
                                    <td class="right">1000.00</td>    
                                    <td class="right"></td>    
                                    <td class="right"></td>    
                                    <td class="right"></td> 
                                    <td class="right">190.00</td> 
                                    <td class="right"></td>    
                                    <td class="right">1190.00</td>    
                                    <td class="right"></td>    
                                    <td></td>    
                                    <td></td>    
                                    <td></td>    
                                    <td></td>                         
                                </tr>
                                <tr>
                                    <td>01</td> 
                                    <td>01/12/2012</td>    
                                    <td></td>    
                                    <td>02</td>    
                                    <td>0300</td>    
                                    <td>30021</td>    
                                    <td>05</td>    
                                    <td>25253625111</td>    
                                    <td>Empresa Prueba SRL</td>    
                                    <td class="right"></td>    
                                    <td class="right">1000.00</td>    
                                    <td class="right"></td>    
                                    <td class="right"></td>    
                                    <td class="right"></td> 
                                    <td class="right">190.00</td> 
                                    <td class="right"></td>    
                                    <td class="right">1190.00</td>    
                                    <td class="right"></td>    
                                    <td></td>    
                                    <td></td>    
                                    <td></td>    
                                    <td></td>                         
                                </tr>
                                <tr>
                                    <td>01</td> 
                                    <td>01/12/2012</td>    
                                    <td></td>    
                                    <td>02</td>    
                                    <td>0300</td>    
                                    <td>30021</td>    
                                    <td>05</td>    
                                    <td>25253625111</td>    
                                    <td>Empresa Prueba SRL</td>    
                                    <td class="right"></td>    
                                    <td class="right">1000.00</td>    
                                    <td class="right"></td>    
                                    <td class="right"></td>    
                                    <td class="right"></td> 
                                    <td class="right">190.00</td> 
                                    <td class="right"></td>    
                                    <td class="right">1190.00</td>    
                                    <td class="right"></td>    
                                    <td></td>    
                                    <td></td>    
                                    <td></td>    
                                    <td></td>                         
                                </tr>
                                <tr>
                                    <td>01</td> 
                                    <td>01/12/2012</td>    
                                    <td></td>    
                                    <td>02</td>    
                                    <td>0300</td>    
                                    <td>30021</td>    
                                    <td>05</td>    
                                    <td>25253625111</td>    
                                    <td>Empresa Prueba SRL</td>    
                                    <td class="right"></td>    
                                    <td class="right">1000.00</td>    
                                    <td class="right"></td>    
                                    <td class="right"></td>    
                                    <td class="right"></td> 
                                    <td class="right">190.00</td> 
                                    <td class="right"></td>    
                                    <td class="right">1190.00</td>    
                                    <td class="right"></td>    
                                    <td></td>    
                                    <td></td>    
                                    <td></td>    
                                    <td></td>                         
                                </tr>
                                <tr>
                                    <td>01</td> 
                                    <td>01/12/2012</td>    
                                    <td></td>    
                                    <td>02</td>    
                                    <td>0300</td>    
                                    <td>30021</td>    
                                    <td>05</td>    
                                    <td>25253625111</td>    
                                    <td>Empresa Prueba SRL</td>    
                                    <td class="right"></td>    
                                    <td class="right">1000.00</td>    
                                    <td class="right"></td>    
                                    <td class="right"></td>    
                                    <td class="right"></td> 
                                    <td class="right">190.00</td> 
                                    <td class="right"></td>    
                                    <td class="right">1190.00</td>    
                                    <td class="right"></td>    
                                    <td></td>    
                                    <td></td>    
                                    <td></td>    
                                    <td></td>                         
                                </tr>
                                <tr>
                                    <td>01</td> 
                                    <td>01/12/2012</td>    
                                    <td></td>    
                                    <td>02</td>    
                                    <td>0300</td>    
                                    <td>30021</td>    
                                    <td>05</td>    
                                    <td>25253625111</td>    
                                    <td>Empresa Prueba SRL</td>    
                                    <td class="right"></td>    
                                    <td class="right">1000.00</td>    
                                    <td class="right"></td>    
                                    <td class="right"></td>    
                                    <td class="right"></td> 
                                    <td class="right">190.00</td> 
                                    <td class="right"></td>    
                                    <td class="right">1190.00</td>    
                                    <td class="right"></td>    
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

                <%@ include file="/WEB-INF/jspf/dialog_select_cliente.jspf" %> 

            </div>
        </div>

        <!-- footer -->                    
        <%@ include file="/WEB-INF/jspf/footer.jspf" %>        
    </body>
</html>
