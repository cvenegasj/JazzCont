<%-- 
    Document   : ver-mi-info
    Created on : 06/06/2013, 01:45:57 PM
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
                
                $("button.btnEditar").on("click", function() {                     
                    var actual = $(this).prev("span").text();                    
                    //alert(actual);
                    $(this).parent().next().children(":first-child").val(actual);
                    $(this).parent().addClass("hide").next("div").removeClass("hide");
                });
                
                // evento del botón guardar
                $("button.btnGuardar").on("click", function() {
                    // val() funciona para input, select y textarea
                    var nuevoValor = $(this).prev().val();
                    //alert("nuevo valor: " + nuevoValor);                    
                    $(this).parent().addClass("hide");
                    // ajax-load.gif
                    $(this).parent().parent().append("<span class=\"loadingGif\"></span>");
                    
                    // url de la peticion ajax
                    var url = $(this).parent().parent().prev().children("input[type='hidden']").prop("value");
                    //alert(url);                
                    // ajax
                    $.ajax({
                        url: url,
                        type: "POST",
                        data: {                             
                            param: nuevoValor
                        },
                        dataType: "json",
                        error: function(XMLHttpRequest, textStatus, errorThrown) {
                            alert(textStatus + "\nOcurrió un error y no se pudo realizar la operación");
                            alert(errorThrown);
                            alert(XMLHttpRequest.responseText);
                        },
                        success: function(data) { 
                            if (data.mensaje == "ok") {
                                location.reload();  
                            } else {
                                alert("No se actualizó correctamente el campo.");
                                location.reload();                                  
                            }                           
                        }                    
                    }); 
                }); 
                
                $("button.btnCancelar").on("click", function() {
                    $(this).parent().addClass("hide").prev("div").removeClass("hide");
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
                        <h1 class="medium">Datos de empresa</h1>
                    </div>                    

                    <div id="divR_wrapper">
                        <!-- detalle -->
                        <div class="detalleDatosCliente">
                            <div class="lfloat">
                                <span>RUC</span>                                
                            </div>
                            <div class="lfloat">
                                <div>
                                    <span><s:property value="#session.sesionUsuario.empresaCliente.ruc" /></span>                                                                                                      
                                </div>                                
                            </div> 
                            <div class="spacer"></div>
                        </div>                        
                        <!-- detalle -->
                        <div class="detalleDatosCliente">
                            <div class="lfloat">
                                <span>Razón Social</span>                               
                            </div>
                            <div class="lfloat">
                                <div>
                                    <span><s:property value="#session.sesionUsuario.empresaCliente.razonSocial" /></span>                                                                                         
                                </div>                                
                            </div> 
                            <div class="spacer"></div>
                        </div>                        
                        <!-- detalle -->
                        <div class="detalleDatosCliente">
                            <div class="lfloat">
                                <span>Nombre de empresa</span>                                
                            </div>
                            <div class="lfloat">
                                <div>
                                    <span><s:property value="#session.sesionUsuario.empresaCliente.nombreEmpresa" /></span>                                                                                       
                                </div>                                
                            </div> 
                            <div class="spacer"></div>
                        </div>
                        <!-- detalle -->
                        <div class="detalleDatosCliente">
                            <div class="lfloat">
                                <span>Tipo de régimen</span>                                
                            </div>
                            <div class="lfloat">
                                <div>
                                    <span><s:property value="#session.sesionUsuario.empresaCliente.tipoRegimen" /></span>                                                                                                  
                                </div>                                
                            </div>
                            <div class="spacer"></div>
                        </div>
                        <!-- detalle -->
                        <div class="detalleDatosCliente">
                            <div class="lfloat">
                                <span>Libros contables habilitados</span>                                
                            </div>
                            <div class="lfloat">
                                <div>
                                    <ul class="ul1">
                                        <s:if test="%{#session.sesionUsuario.empresaCliente.isRegistroComprasHabilitado()}">
                                            <li>Registro de Compras</li>
                                        </s:if>
                                        <s:if test="%{#session.sesionUsuario.empresaCliente.isRegistroVentasHabilitado()}">
                                            <li>Registro de Ventas e Ingresos</li>
                                        </s:if>
                                        <s:if test="%{#session.sesionUsuario.empresaCliente.isDiarioSimplificadoHabilitado()}">
                                            <li>Libro Diario Simplificado</li>
                                        </s:if>
                                    </ul>                      
                                </div>                               
                            </div>
                            <div class="spacer"></div>
                        </div>
                        <!-- detalle -->
                        <div class="detalleDatosCliente">
                            <div class="lfloat">
                                <span>Estado en el sistema</span>                               
                            </div>
                            <div class="lfloat">
                                <div>
                                    <span><s:property value="#session.sesionUsuario.empresaCliente.estado" /></span>                                                         
                                </div>                               
                            </div>
                            <div class="spacer"></div>
                        </div>
                        <!-- detalle -->
                        <div class="detalleDatosCliente">
                            <div class="lfloat">
                                <span>Descripción del negocio</span>                                
                            </div>
                            <div class="lfloat">
                                <div>
                                    <span><s:property value="#session.sesionUsuario.empresaCliente.descripcion" /></span>                                                                        
                                </div>                                
                            </div>
                            <div class="spacer"></div>
                        </div>                        
                        <!-- detalle -->
                        <div class="detalleDatosCliente">
                            <div class="lfloat">
                                <span>Dirección</span>                              
                            </div>
                            <div class="lfloat">
                                <div>
                                    <span><s:property value="#session.sesionUsuario.empresaCliente.direccion" /></span>                                                                   
                                </div>                                
                            </div>
                            <div class="spacer"></div>
                        </div>
                        <!-- detalle -->
                        <div class="detalleDatosCliente">
                            <div class="lfloat">
                                <span>Teléfono 1</span>                                
                            </div>
                            <div class="lfloat">
                                <div>
                                    <span><s:property value="#session.sesionUsuario.empresaCliente.telefono1" /></span>                                                                
                                </div>                                
                            </div>
                            <div class="spacer"></div>
                        </div>
                        <!-- detalle -->
                        <div class="detalleDatosCliente">
                            <div class="lfloat">
                                <span>Teléfono 2</span>                                
                            </div>
                            <div class="lfloat">
                                <div>
                                    <span><s:property value="#session.sesionUsuario.empresaCliente.telefono2" /></span>                                               
                                </div>                                
                            </div>
                            <div class="spacer"></div>
                        </div>
                        <!-- detalle -->
                        <div class="detalleDatosCliente">
                            <div class="lfloat">
                                <span>Fecha frontera</span>                                
                            </div>
                            <div class="lfloat">
                                <div>
                                    <span><s:property value="#session.sesionUsuario.empresaCliente.fechaFrontera" /></span>                                                                          
                                </div>                                
                            </div>
                            <div class="spacer"></div>
                        </div>
                    </div>
                    <br />

                    <div id="headerContentArea">
                        <h1 class="medium">Datos de contacto</h1>
                    </div>
                    <div id="divR_wrapper">
                        <!-- detalle -->
                        <div class="detalleDatosCliente">
                            <div class="lfloat">
                                <span>Nombres</span>                                
                            </div>
                            <div class="lfloat">
                                <div>
                                    <span><s:property value="#session.sesionUsuario.nombres" /></span>                                                                          
                                </div>                                
                            </div> 
                            <div class="spacer"></div>
                        </div> 
                        <!-- detalle -->
                        <div class="detalleDatosCliente">
                            <div class="lfloat">
                                <span>Apellidos</span>                                
                            </div>
                            <div class="lfloat">
                                <div>
                                    <span><s:property value="#session.sesionUsuario.apellidos" /></span>                                                                                       
                                </div>                                                            
                            </div> 
                            <div class="spacer"></div>
                        </div> 
                        <!-- detalle -->
                        <div class="detalleDatosCliente">
                            <div class="lfloat">
                                <span>Dirección</span>                                
                            </div>
                            <div class="lfloat">
                                <div>
                                    <span><s:property value="#session.sesionUsuario.direccion" /></span>                                                                                                 
                                </div>                                
                            </div> 
                            <div class="spacer"></div>
                        </div> 
                        <!-- detalle -->
                        <div class="detalleDatosCliente">
                            <div class="lfloat">
                                <span>Celular</span>                               
                            </div>
                            <div class="lfloat">
                                <div>
                                    <span><s:property value="#session.sesionUsuario.celular" /></span>                                                                                                     
                                </div>                                
                            </div> 
                            <div class="spacer"></div>
                        </div> 
                        <!-- detalle -->
                        <div class="detalleDatosCliente">
                            <div class="lfloat">
                                <span>Email Principal <br />(email de acceso al sistema)</span>                               
                            </div>
                            <div class="lfloat">
                                <div>
                                    <span><s:property value="#session.sesionUsuario.emailPrincipal" /></span>                                        
                                </div>                                
                            </div> 
                            <div class="spacer"></div>
                        </div> 
                        <!-- detalle -->
                        <div class="detalleDatosCliente">
                            <div class="lfloat">
                                <span>Contraseña</span>                                
                            </div>
                            <div class="lfloat">
                                <div>
                                    <span>(encriptada)</span>                                    
                                </div>                                
                            </div> 
                            <div class="spacer"></div>
                        </div> 
                        <!-- detalle -->
                        <div class="detalleDatosCliente">
                            <div class="lfloat">
                                <span>Email Secundario</span>    
                                <input type="hidden" value="ClienteAjaxAction_editEmailSecundarioContacto" />
                            </div>
                            <div class="lfloat">
                                <div>
                                    <span><s:property value="#session.sesionUsuario.emailSecundario" /></span>                                    
                                    <button title="Editar" class="btnEditar smallButton mediumOpacity"><span class="editButtonImage"></span></button> 
                                </div>  
                                <div class="hide">
                                    <input type="text" value="<s:property value="#session.sesionUsuario.emailSecundario" />" />
                                    <button title="Guardar" class="btnGuardar button"><span>Guardar</span></button>
                                    <button title="Cancelar" class="btnCancelar button"><span>Cancelar</span></button>
                                </div>
                            </div> 
                            <div class="spacer"></div>
                        </div> 
                        <!-- detalle -->
                        <div class="detalleDatosCliente">
                            <div class="lfloat">
                                <span>Email Facebook</span>
                                <input type="hidden" value="ClienteAjaxAction_editEmailFacebookContacto" />
                            </div>
                            <div class="lfloat">
                                <div>
                                    <span><s:property value="#session.sesionUsuario.emailFacebook" /></span>
                                    <button title="Editar" class="btnEditar smallButton mediumOpacity"><span class="editButtonImage"></span></button>                                                                  
                                </div>
                                <div class="hide">
                                    <input type="text" value="<s:property value="#session.sesionUsuario.emailFacebook" />" />
                                    <button title="Guardar" class="btnGuardar button"><span>Guardar</span></button>
                                    <button title="Cancelar" class="btnCancelar button"><span>Cancelar</span></button>
                                </div>
                            </div> 
                            <div class="spacer"></div>
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

