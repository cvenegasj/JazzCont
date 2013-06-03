<%-- 
    Document   : ver-libro-registro-compras
    Created on : 24/12/2012, 03:44:59 PM
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
        <script type="text/javascript" src="<s:url value="/js/reg-compras-ext.js"/>"></script>

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
                $(document).on("click", "span.verDetallesComprobanteIcon", function(e) {                    
                    $("#dialogDetallesComprobante").dialog("open");  
                    
                    // cargando
                    $("#dialogDetallesComprobante").html("<span class=\"loadingGif\"></span>");
                    
                    // obtener el id del comprobante a extraer de la BD
                    var idComp = $(e.target).attr("data-idcomp");
                    //alert(idComp);
                    
                    // llamada ajax para obtener los datos
                    $.ajax({
                        type: "GET",
                        url: "ComprobantesAjaxAction_showComprobanteCompra",
                        cache: false,
                        dataType: "json",
                        data: {idComp: idComp},
                        error: function(XMLHttpRequest, textStatus, errorThrown){
                            alert('Error ' + textStatus);
                            alert(errorThrown);
                            alert(XMLHttpRequest.responseText);
                        },
                        success: function(data){         
                            //alert('SUCCESS' + '\nDATA: ' + data);
                            var fechaVencimientoOpago = "";
                            if (data.fechaVencimientoOpago != null) {
                                fechaVencimientoOpago = data.fechaVencimientoOpago;
                            } 
                            
                            var numeroCodigoAduana = "";
                            if (data.numeroCodigoAduana != null) {
                                numeroCodigoAduana = "(" + data.numeroCodigoAduana + ") " + data.descripcionCodigoAduana;  
                            }
                            
                            var tbody = "<tbody>";
                            $.each(data.detallesComprobanteCompra, function(i, item) {
                                tbody += "<tr>\n\
                                            <td>" + item.cantidad + "</td>\n\
                                            <td>" + item.nombreProducto + "</td>\n\
                                            <td class=\"right\">" + $.number(item.precioUnitario, 2) + "</td>\n\
                                            <td class=\"right\">" + $.number(item.subtotal, 2) + "</td>\n\
                                         </tr>"
                            })
                            tbody += "</tbody>";
                            
                            var tfoot = "<tfoot>\n\
                                            <tr>\n\
                                                <th colspan=\"3\" class=\"right\">Total</th>\n\
                                                <th class=\"right\">" + $.number(data.importeTotal, 2) + "</th>\n\
                                            </tr>\n\
                                            <tr>\n\
                                                <th colspan=\"3\" class=\"right\">Base</th>\n\
                                                <th class=\"right\">" + $.number(data.base, 2) + "</th>\n\
                                            </tr>\n\
                                            <tr>\n\
                                                <th colspan=\"3\" class=\"right\">Igv</th>\n\
                                                <th class=\"right\">" + $.number(data.igv, 2) + "</th>\n\
                                            </tr>\n\
                                        </tfoot>";
                            
                            
                            // tabla completa
                            var html = "<div>\n\
                                            <div class=\"lineaDetalleDialog1\">\n\
                                                <div class=\"lfloat\"><span>Fecha</span></div>\n\
                                                <div class=\"lfloat\"><span>" + data.fechaEmision + "</span></div>\n\
                                                <div class=\"spacer\"></div> \n\
                                            </div>\n\
                                        </div>\n\
                                        <div>\n\
                                            <div class=\"lineaDetalleDialog1\">\n\
                                                <div class=\"lfloat\"><span>Tipo de comprobante</span></div>\n\
                                                <div class=\"lfloat\"><span>(" + data.numeroTipoCompPago + ") " + data.descTipoCompPago + "</span></div>\n\
                                                <div class=\"spacer\"></div> \n\
                                            </div>\n\
                                        </div>\n\
                                        <div>\n\
                                            <div class=\"lineaDetalleDialog1\">\n\
                                                <div class=\"lfloat\"><span>Serie</span></div>\n\
                                                <div class=\"lfloat\"><span>" + data.serie + "</span></div>\n\
                                                <div class=\"spacer\"></div> \n\
                                            </div>\n\
                                        </div>\n\
                                        <div>\n\
                                            <div class=\"lineaDetalleDialog1\">\n\
                                                <div class=\"lfloat\"><span>Número</span></div>\n\
                                                <div class=\"lfloat\"><span>" + data.numero + "</span></div>\n\
                                                <div class=\"spacer\"></div> \n\
                                            </div>\n\
                                        </div>\n\
                                        <div>\n\
                                            <div class=\"lineaDetalleDialog1\">\n\
                                                <div class=\"lfloat\"><span>Año de emisión de la DUA o DSI</span></div>\n\
                                                <div class=\"lfloat\"><span>" + data.anioEmisionDuaOdsi + "</span></div>\n\
                                                <div class=\"spacer\"></div> \n\
                                            </div>\n\
                                        </div>\n\
                                        <div>\n\
                                            <div class=\"lineaDetalleDialog1\">\n\
                                                <div class=\"lfloat\"><span>Fecha de vencimiento o fecha de pago</span></div>\n\
                                                <div class=\"lfloat\"><span>" + fechaVencimientoOpago + "</span></div>\n\
                                                <div class=\"spacer\"></div> \n\
                                            </div>\n\
                                        </div>\n\
                                        <div>\n\
                                            <div class=\"lineaDetalleDialog1\">\n\
                                                <div class=\"lfloat\"><span>Código aduana</span></div>\n\
                                                <div class=\"lfloat\"><span>" + numeroCodigoAduana + "</span></div>\n\
                                                <div class=\"spacer\"></div> \n\
                                            </div>\n\
                                        </div>\n\
                                        <div>\n\
                                            <div class=\"lineaDetalleDialog1\">\n\
                                                <div class=\"lfloat\"><span>Razón social de proveedor</span></div>\n\
                                                <div class=\"lfloat\"><span>" + data.razonSocialProveedor + "</span></div>\n\
                                                <div class=\"spacer\"></div> \n\
                                            </div>\n\
                                        </div>\n\
                                        <div>\n\
                                            <div class=\"lineaDetalleDialog1\">\n\
                                                <div class=\"lfloat\"><span>Documento de identidad de proveedor</span></div>\n\
                                                <div class=\"lfloat\"><span>(" + data.numeroTipoDocIdentidadProveedor + ") " + data.numeroDocIdentidadProveedor + "</span></div>\n\
                                                <div class=\"spacer\"></div> \n\
                                            </div>\n\
                                        </div>\n\
                                        <div>\n\
                                            <table class=\"verDetallesComprobanteTable\">\n\
                                                <thead>\n\
                                                    <tr>\n\
                                                    <th style=\"width: 35px\">Cantidad</th>\n\
                                                    <th style=\"width: 195px\">Descripción</th>\n\
                                                    <th style=\"width: 55px\">P. Unitario</th>\n\
                                                    <th style=\"width: 55px\">Importe</th>\n\
                                                    </tr>\n\
                                                </thead>\n" + tbody + "\n" + tfoot +
                                            "</table>";
                            
                            
                            $("#dialogDetallesComprobante").html(html);
                        }                         
                })                       
        });
                
        $("#dialogDetallesComprobante").dialog({
            autoOpen: false,
            modal: true,
            resizable: false,                    
            width: 515,   
            height: 465,
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
                <%@ include file="/WEB-INF/jspf/barra_sesion_admin.jspf" %>                
            </div>           

            <div id="widthArea">

                <%@ include file="/WEB-INF/jspf/lateral_izq_admin.jspf" %>

                <div id="contentArea">

                    <%@ include file="/WEB-INF/jspf/header_cliente_sesion_admin.jspf" %>

                    <div id="headerContentArea">
                        <h1 class="medium">Libro de Registro de Compras: <strong><s:property value="periodo" /></strong></h1><br />
                        <div class="right">
                            <a id="btnPdf" href="#" title="Ver en pdf"><img width="25" src="<s:url value="/img/pdf_icon.png" />" /></a>
                            <a id="btnExcel" href="#" title="Ver en excel"><img width="25" src="<s:url value="/img/excel_2010_icon.png" />" /></a>                        
                        </div>
                    </div>                    

                    <div id="libroContableWrapper">
                        <div id="libroComprasExt"></div>                  
                    </div>                    
                </div>                        
                <div class="spacer"></div>

                <!-- dialogDetallesComprobante -->
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

                <%@ include file="/WEB-INF/jspf/dialog_select_cliente.jspf" %> 

            </div>
        </div>

        <!-- footer -->                    
        <%@ include file="/WEB-INF/jspf/footer.jspf" %>   

    </body>
</html>
