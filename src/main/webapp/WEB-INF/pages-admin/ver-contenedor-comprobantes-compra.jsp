<%-- 
    Document   : ver-contenedor-comprobantes-compra
    Created on : 29/12/2012, 03:55:33 PM
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
                
                $(".btnDetalles").on("click", function() {
                    $("#dialogDetallesComprobante").dialog("open");  
                    
                    //$("#dialogDetallesComprobante").html("<span class=\"loadingGif\"></span>");
                    
                    // obtener el id del comprobante a extraer de la BD
                    
                    
                    // llamada ajax para obtener los datos
                    
                    
                    
                    
                });
                
                $("#dialogDetallesComprobante").dialog({
                    autoOpen: false,
                    modal: true,
                    resizable: false,                    
                    width: 455,   
                    height: 455,
                    buttons: {
                        "Aceptar": function() {
                            $(this).dialog("close");                                                        
                        }
                    },                    
                    position: "center"
                }); 
                
                $(".btnEliminar").on("click", function() {
                    $("#dialogConfirmDelete").dialog("open");                                                        
                });
                
                $("#dialogConfirmDelete").dialog({
                    autoOpen: false,
                    modal: true,
                    resizable: false,                    
                    width: 405,   
                    height: 150,
                    buttons: {
                        "Aceptar": function() {
                            // hacer algo
                                
                                
                            $(this).dialog("close");                                                        
                        },
                        "Cancelar": function() {                                
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
                <%@ include file="/WEB-INF/jspf/barra_sesion_admin.jspf" %>                
            </div>           

            <div id="widthArea">

                <%@ include file="/WEB-INF/jspf/lateral_izq_admin.jspf" %>

                <div id="contentArea">

                    <%@ include file="/WEB-INF/jspf/header_cliente_sesion_admin.jspf" %>

                    <div id="headerContentArea">
                        <h1 class="medium">Contenedor de Comprobantes de Compra: <strong><s:property value="periodo" /></strong></h1>
                        <div class="right">
                            <a id="btnPdf" href="#" title="Ver en pdf"><img width="25" src="<s:url value="/img/pdf_icon.png" />" /></a>
                            <a id="btnExcel" href="#" title="Ver en excel"><img width="25" src="<s:url value="/img/excel_2010_icon.png" />" /></a>                        
                        </div>
                    </div>                    

                    <div>
                        <table id="contenedorComprasTable" class="contenedorComprobantesTable">
                            <thead>
                                <tr>
                                    <th rowspan="2" style="width: 80px">Fecha</th>
                                    <th rowspan="2" style="width: 45px">Código</th>
                                    <th colspan="2">Comprobante</th>
                                    <th rowspan="2" style="width: 180px">Razón Social</th>
                                    <th rowspan="2" style="width: 65px">Base</th>
                                    <th rowspan="2" style="width: 65px">Igv</th>
                                    <th rowspan="2" style="width: 65px">Total</th>
                                    <th rowspan="2"></th>
                                </tr>
                                <tr>
                                    <th style="width: 90px">Serie</th>
                                    <th style="width: 90px">Número</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>01/12/2012</td>
                                    <td>10</td>
                                    <td>200</td>
                                    <td>2001</td>
                                    <td>Empresa Prueba SRL</td>
                                    <td class="right">1000.00</td>
                                    <td class="right">180.00</td>
                                    <td class="right">1180.00</td>
                                    <td>
                                        <!-- id en BD del comprobante -->
                                        <input type="hidden" value="10" /> 
                                        <a class="btnDetalles" href="#" title="Detalles"><img src="<s:url value="/img/iconic/list_12x11.png" />" /></a>
                                        <a class="btnEliminar" href="#" title="Eliminar"><img src="<s:url value="/img/iconic/trash_stroke_12x12.png" />" /></a>
                                    </td>
                                </tr>  
                                <tr>
                                    <td>01/12/2012</td>
                                    <td>10</td>
                                    <td>200</td>
                                    <td>2001</td>
                                    <td>Empresa Prueba SRL</td>
                                    <td class="right">1000.00</td>
                                    <td class="right">180.00</td>
                                    <td class="right">1180.00</td>
                                    <td>
                                        <!-- id en BD del comprobante -->
                                        <input type="hidden" value="10" /> 
                                        <a class="btnDetalles" href="#" title="Detalles"><img src="<s:url value="/img/iconic/list_12x11.png" />" /></a>
                                        <a class="btnEliminar" href="#" title="Eliminar"><img src="<s:url value="/img/iconic/trash_stroke_12x12.png" />" /></a>
                                    </td>
                                </tr> 
                                <tr>
                                    <td>01/12/2012</td>
                                    <td>10</td>
                                    <td>200</td>
                                    <td>2001</td>
                                    <td>Empresa Prueba SRL</td>
                                    <td class="right">1000.00</td>
                                    <td class="right">180.00</td>
                                    <td class="right">1180.00</td>
                                    <td>
                                        <!-- id en BD del comprobante -->
                                        <input type="hidden" value="10" /> 
                                        <a class="btnDetalles" href="#" title="Detalles"><img src="<s:url value="/img/iconic/list_12x11.png" />" /></a>
                                        <a class="btnEliminar" href="#" title="Eliminar"><img src="<s:url value="/img/iconic/trash_stroke_12x12.png" />" /></a>
                                    </td>
                                </tr> 
                                <tr>
                                    <td>01/12/2012</td>
                                    <td>10</td>
                                    <td>200</td>
                                    <td>2001</td>
                                    <td>Empresa Prueba SRL</td>
                                    <td class="right">1000.00</td>
                                    <td class="right">180.00</td>
                                    <td class="right">1180.00</td>
                                    <td>
                                        <!-- id en BD del comprobante -->
                                        <input type="hidden" value="10" /> 
                                        <a class="btnDetalles" href="#" title="Detalles"><img src="<s:url value="/img/iconic/list_12x11.png" />" /></a>
                                        <a class="btnEliminar" href="#" title="Eliminar"><img src="<s:url value="/img/iconic/trash_stroke_12x12.png" />" /></a>
                                    </td>
                                </tr> 
                                <tr>
                                    <td>01/12/2012</td>
                                    <td>10</td>
                                    <td>200</td>
                                    <td>2001</td>
                                    <td>Empresa Prueba SRL</td>
                                    <td class="right">1000.00</td>
                                    <td class="right">180.00</td>
                                    <td class="right">1180.00</td>
                                    <td>
                                        <!-- id en BD del comprobante -->
                                        <input type="hidden" value="10" /> 
                                        <a class="btnDetalles" href="#" title="Detalles"><img src="<s:url value="/img/iconic/list_12x11.png" />" /></a>
                                        <a class="btnEliminar" href="#" title="Eliminar"><img src="<s:url value="/img/iconic/trash_stroke_12x12.png" />" /></a>
                                    </td>
                                </tr> 
                                <tr>
                                    <td>01/12/2012</td>
                                    <td>10</td>
                                    <td>200</td>
                                    <td>2001</td>
                                    <td>Empresa Prueba SRL</td>
                                    <td class="right">1000.00</td>
                                    <td class="right">180.00</td>
                                    <td class="right">1180.00</td>
                                    <td>
                                        <!-- id en BD del comprobante -->
                                        <input type="hidden" value="10" /> 
                                        <a class="btnDetalles" href="#" title="Detalles"><img src="<s:url value="/img/iconic/list_12x11.png" />" /></a>
                                        <a class="btnEliminar" href="#" title="Eliminar"><img src="<s:url value="/img/iconic/trash_stroke_12x12.png" />" /></a>
                                    </td>
                                </tr> 
                                <tr>
                                    <td>01/12/2012</td>
                                    <td>10</td>
                                    <td>200</td>
                                    <td>2001</td>
                                    <td>Empresa Prueba SRL</td>
                                    <td class="right">1000.00</td>
                                    <td class="right">180.00</td>
                                    <td class="right">1180.00</td>
                                    <td>
                                        <!-- id en BD del comprobante -->
                                        <input type="hidden" value="10" /> 
                                        <a class="btnDetalles" href="#" title="Detalles"><img src="<s:url value="/img/iconic/list_12x11.png" />" /></a>
                                        <a class="btnEliminar" href="#" title="Eliminar"><img src="<s:url value="/img/iconic/trash_stroke_12x12.png" />" /></a>
                                    </td>
                                </tr> 
                                <tr>
                                    <td>01/12/2012</td>
                                    <td>10</td>
                                    <td>200</td>
                                    <td>2001</td>
                                    <td>Empresa Prueba SRL</td>
                                    <td class="right">1000.00</td>
                                    <td class="right">180.00</td>
                                    <td class="right">1180.00</td>
                                    <td>
                                        <!-- id en BD del comprobante -->
                                        <input type="hidden" value="10" /> 
                                        <a class="btnDetalles" href="#" title="Detalles"><img src="<s:url value="/img/iconic/list_12x11.png" />" /></a>
                                        <a class="btnEliminar" href="#" title="Eliminar"><img src="<s:url value="/img/iconic/trash_stroke_12x12.png" />" /></a>
                                    </td>
                                </tr> 
                                <tr>
                                    <td>01/12/2012</td>
                                    <td>10</td>
                                    <td>200</td>
                                    <td>2001</td>
                                    <td>Empresa Prueba SRL</td>
                                    <td class="right">1000.00</td>
                                    <td class="right">180.00</td>
                                    <td class="right">1180.00</td>
                                    <td>
                                        <!-- id en BD del comprobante -->
                                        <input type="hidden" value="10" /> 
                                        <a class="btnDetalles" href="#" title="Detalles"><img src="<s:url value="/img/iconic/list_12x11.png" />" /></a>
                                        <a class="btnEliminar" href="#" title="Eliminar"><img src="<s:url value="/img/iconic/trash_stroke_12x12.png" />" /></a>
                                    </td>
                                </tr> 
                            </tbody>
                            <tfoot>
                                <tr>
                                    <th colspan="5" class="right">Total</th>
                                    <th class="right">1500.00</th>
                                    <th class="right">1500.00</th>
                                    <th class="right">1500.00</th>                                    
                                    <th></th>
                                </tr>
                            </tfoot>
                        </table>                                           
                    </div>


                </div>
                <div class="spacer"></div>

                <!-- dialogo1 -->
                <div id="dialogDetallesComprobante" title="Detalles del comprobante">
                    <div>
                        <div class="lineaDetalleDialog1">
                            <div class="lfloat">
                                <span>Fecha</span>
                            </div>
                            <div class="lfloat">
                                <span>20/12/2012</span>                                
                            </div>
                            <div class="spacer"></div>
                        </div>
                        <div class="lineaDetalleDialog1">
                            <div class="lfloat">
                                <span>Tipo de comprobante</span>
                            </div>
                            <div class="lfloat">
                                <span>(01) Factura</span>                                
                            </div>
                            <div class="spacer"></div>
                        </div>
                        <div class="lineaDetalleDialog1">
                            <div class="lfloat">
                                <span>Serie</span>
                            </div>
                            <div class="lfloat">
                                <span>200</span>                                
                            </div>
                            <div class="spacer"></div>
                        </div>
                        <div class="lineaDetalleDialog1">
                            <div class="lfloat">
                                <span>Número</span>
                            </div>
                            <div class="lfloat">
                                <span>2001</span>                                
                            </div>
                            <div class="spacer"></div>
                        </div>
                        <div class="lineaDetalleDialog1">
                            <div class="lfloat">
                                <span>Razón Social</span>
                            </div>
                            <div class="lfloat">
                                <span>Empresa Prueba SRL</span>                                
                            </div>
                            <div class="spacer"></div>
                        </div>                        
                    </div>
                    <div>
                        <table class="verDetallesComprobanteTable">
                            <thead>
                                <tr>
                                    <th style="width: 35px">Cantidad</th>
                                    <th style="width: 195px">Descripción</th>
                                    <th style="width: 55px">P. Unitario</th>
                                    <th style="width: 55px">Importe</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>3</td>
                                    <td>Kent HD</td>
                                    <td class="right">1.00</td>
                                    <td class="right">3.00</td>
                                </tr>
                                <tr>
                                    <td>3</td>
                                    <td>Marlboro</td>
                                    <td class="right">1.00</td>
                                    <td class="right">3.00</td>
                                </tr>
                                <tr>
                                    <td>3</td>
                                    <td>Yves Saint Laurent</td>
                                    <td class="right">1.00</td>
                                    <td class="right">3.00</td>
                                </tr>
                                <tr>
                                    <td>3</td>
                                    <td>Lucky Strike</td>
                                    <td class="right">1.00</td>
                                    <td class="right">3.00</td>
                                </tr>                                                              
                            </tbody>
                            <tfoot>
                                <tr>
                                    <th colspan="3" class="right">Total</th>
                                    <th class="right">3.00</th>
                                </tr>
                                <tr>
                                    <th colspan="3" class="right">Base</th>
                                    <th class="right">2.00</th>
                                </tr>
                                <tr>
                                    <th colspan="3" class="right">Igv</th>
                                    <th class="right">1.00</th>
                                </tr>
                            </tfoot>                            
                        </table>
                    </div>
                </div>
                <!-- ********** -->

                <!-- dialog 2 -->
                <div id="dialogConfirmDelete" title="Confimación">
                    ¿Está seguro que desea eliminar el comprobante?
                </div>



                <%@ include file="/WEB-INF/jspf/dialog_select_cliente.jspf" %> 

            </div>
        </div>

        <!-- footer -->                    
        <%@ include file="/WEB-INF/jspf/footer.jspf" %>        
    </body>
</html>
