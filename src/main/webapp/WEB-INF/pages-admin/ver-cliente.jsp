<%-- 
    Document   : ver-cliente
    Created on : 26/12/2012, 11:37:23 AM
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
                
                $("button.btnEditar").on("click", function(event) {                    
                    var actual = $(event.target).prev("span").text();
                    alert(actual);
                    $(event.target).parent().next().children(":first-child").val(actual);
                    $(event.target).parent().addClass("hide").next("div").removeClass("hide");
                });
                
                // evento del botón guardar
                $(".detalleDatosCliente div.lfloat:nth-child(2) div:nth-child(2)").on("click", "button.btnGuardar", function(event) {
                    // val() funciona para input, select y textarea
                    var nuevoValor = $(event.target).prev().val();
                    // ruc del cliente a modificar
                    var ruc = "<s:property value="empresaCliente.ruc" />";                                       
                                       
                    
                    $(event.target).parent().addClass("hide");
                    // ajax-load.gif
                    $(event.target).parent().parent().append("<span class=\"loadingGif\"></span>");
                    
                    // url de la peticion ajax
                    var url = $(event.target).parent().parent().prev().children("input[type='hidden']").prop("value");
                                    
                    // ajax
                    $.ajax({
                        url: url,
                        type: "POST",
                        data: {
                            ruc: ruc, 
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
                                $(event.target).parent().prev().children("span:first-child").text(data.paramRetorno);
                                $(event.target).parent().addClass("hide").prev().removeClass("hide");
                                $(event.target).parent().parent().children("span.loadingGif").remove();
                                
                                if (url == "ClienteAjaxAction_editRuc") {
                                    // actualizar la página para que se actualice la cabecera también
                                    var urlDestino = "EmpresaClienteAction_show.action?ruc=" + nuevoValor;
                                    window.location = urlDestino;
                                } else if (url == "ClienteAjaxAction_editRazonSocial") {
                                    location.reload();
                                } 
                                
                            } else {
                                alert("No se actualizó correctamente el campo.");
                                $(event.target).parent().addClass("hide").prev().removeClass("hide");
                                $(event.target).parent().parent().children("span.loadingGif").remove();
                            }                           
                        }                    
                    });    
                    
                });
                
                $("#btnGuardarLibros").on("click", function(event) {
                
                    var ruc = "<s:property value="empresaCliente.ruc" />"; 
                    
                    var booleanRC = $("#checkboxRC").is(":checked");
                    var booleanRV = $("#checkboxRV").is(":checked");
                    var booleanDS = $("#checkboxDS").is(":checked");
                    
                    $(event.target).parent().addClass("hide");
                    // ajax-load.gif
                    $(event.target).parent().parent().append("<span class=\"loadingGif\"></span>");
                    
                    // url de la peticion ajax
                    var url = $(event.target).parent().parent().prev().children("input[type='hidden']").prop("value");
                                    
                    // ajax
                    $.ajax({
                        url: url,
                        type: "POST",
                        data: {
                            ruc: ruc, 
                            param: booleanRC + "-" + booleanRV + "-" + booleanDS
                        },
                        dataType: "json",
                        error: function(XMLHttpRequest, textStatus, errorThrown) {
                            alert(textStatus + "\nOcurrió un error y no se pudo realizar la operación");
                            alert(errorThrown);
                            alert(XMLHttpRequest.responseText);
                        },
                        success: function(data) { 
                            if (data.mensaje == "ok") {
                                //$(event.target).parent().prev().children("span:first-child").text(data.paramRetorno);
                                //$(event.target).parent().addClass("hide").prev().removeClass("hide");
                                //$(event.target).parent().parent().children("span.loadingGif").remove();
                                //alert(data.paramRetorno);
                                location.reload();
                                
                            } else {
                                alert("No se actualizaron correctamente los campos.");
                                $(event.target).parent().addClass("hide").prev().removeClass("hide");
                                $(event.target).parent().parent().children("span.loadingGif").remove();
                            }                           
                        }                    
                    });                          
                    
                });
                
                $(".detalleDatosCliente div").on("click", "button.btnCancelar", function(event) {
                    $(event.target).parent().addClass("hide").prev("div").removeClass("hide");
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
                        <h1 class="medium">Datos de empresa</h1>
                    </div>                    

                    <div id="datosWrapper">
                        <!-- detalle -->
                        <div class="detalleDatosCliente">
                            <div class="lfloat">
                                <span>RUC</span>
                                <input type="hidden" value="ClienteAjaxAction_editRuc" />
                            </div>
                            <div class="lfloat">
                                <div>
                                    <span><s:property value="empresaCliente.ruc" /></span>
                                    <button title="Editar" class="btnEditar smallButton mediumOpacity"><span class="editButtonImage"></span></button>                                                                  
                                </div>
                                <div class="hide">
                                    <input type="text" value="<s:property value="empresaCliente.ruc" />" />
                                    <button title="Guardar" class="btnGuardar button"><span>Guardar</span></button>
                                    <button title="Cancelar" class="btnCancelar button"><span>Cancelar</span></button>
                                </div>
                            </div> 
                            <div class="spacer"></div>
                        </div>                        
                        <!-- detalle -->
                        <div class="detalleDatosCliente">
                            <div class="lfloat">
                                <span>Razón Social</span>
                                <input type="hidden" value="ClienteAjaxAction_editRazonSocial" />
                            </div>
                            <div class="lfloat">
                                <div>
                                    <span><s:property value="empresaCliente.razonSocial" /></span>
                                    <button title="Editar" class="btnEditar smallButton mediumOpacity"><span class="editButtonImage"></span></button>                                                                  
                                </div>
                                <div class="hide">
                                    <input type="text" value="<s:property value="empresaCliente.razonSocial" />" />
                                    <button title="Guardar" class="btnGuardar button"><span>Guardar</span></button>
                                    <button title="Cancelar" class="btnCancelar button"><span>Cancelar</span></button>
                                </div>
                            </div> 
                            <div class="spacer"></div>
                        </div>                        
                        <!-- detalle -->
                        <div class="detalleDatosCliente">
                            <div class="lfloat">
                                <span>Nombre de empresa</span>
                                <input type="hidden" value="ClienteAjaxAction_editNombreEmpresa" />
                            </div>
                            <div class="lfloat">
                                <div>
                                    <span><s:property value="empresaCliente.nombreEmpresa" /></span>
                                    <button title="Editar" class="btnEditar smallButton mediumOpacity"><span class="editButtonImage"></span></button>                                                                  
                                </div>
                                <div class="hide">
                                    <input type="text" value="<s:property value="empresaCliente.nombreEmpresa" />" />
                                    <button title="Guardar" class="btnGuardar button"><span>Guardar</span></button>
                                    <button title="Cancelar" class="btnCancelar button"><span>Cancelar</span></button>
                                </div>
                            </div> 
                            <div class="spacer"></div>
                        </div>
                        <!-- detalle -->
                        <div class="detalleDatosCliente">
                            <div class="lfloat">
                                <span>Tipo de régimen</span>
                                <input type="hidden" value="ClienteAjaxAction_editTipoRegimen" />
                            </div>
                            <div class="lfloat">
                                <div>
                                    <span><s:property value="empresaCliente.tipoRegimen" /></span>
                                    <button title="Editar" class="btnEditar smallButton mediumOpacity"><span class="editButtonImage"></span></button>                                                                  
                                </div>
                                <div class="hide">
                                    <select>
                                        <option value="RG">RG</option>
                                        <option value="RER">RER</option>
                                        <option value="NRUS">NRUS</option>
                                    </select>
                                    <button title="Guardar" class="btnGuardar button"><span>Guardar</span></button>
                                    <button title="Cancelar" class="btnCancelar button"><span>Cancelar</span></button>
                                </div>
                            </div>
                            <div class="spacer"></div>
                        </div>
                        <!-- detalle -->
                        <div class="detalleDatosCliente">
                            <div class="lfloat">
                                <span>Libros contables habilitados</span>
                                <input type="hidden" value="ClienteAjaxAction_editLibrosContablesHabilitados" />
                            </div>
                            <div class="lfloat">
                                <div>
                                    <ul class="ul1">
                                        <s:if test="%{empresaCliente.isRegistroComprasHabilitado()}">
                                            <li>Registro de Compras</li>
                                        </s:if>
                                        <s:if test="%{empresaCliente.isRegistroVentasHabilitado()}">
                                            <li>Registro de Ventas e Ingresos</li>
                                        </s:if>
                                        <s:if test="%{empresaCliente.isDiarioSimplificadoHabilitado()}">
                                            <li>Libro Diario Simplificado</li>
                                        </s:if>
                                    </ul>                                    
                                    <button title="Editar" class="btnEditar smallButton mediumOpacity"><span class="editButtonImage"></span></button>                                                                  
                                </div>
                                <div class="hide">
                                    <s:if test="%{empresaCliente.isRegistroComprasHabilitado()}">
                                        <input id="checkboxRC" type="checkbox" checked /><label for="checkboxRC">Registro de compras</label><br />
                                    </s:if>
                                    <s:else>
                                        <input id="checkboxRC" type="checkbox" /><label for="checkboxRC">Registro de compras</label><br />
                                    </s:else>
                                    <s:if test="%{empresaCliente.isRegistroVentasHabilitado()}">
                                        <input id="checkboxRV" type="checkbox" checked /><label for="checkboxRV">Registro de ventas e ingresos</label><br />
                                    </s:if>
                                    <s:else>
                                        <input id="checkboxRV" type="checkbox" /><label for="checkboxRV">Registro de ventas e ingresos</label><br />
                                    </s:else>
                                    <s:if test="%{empresaCliente.isDiarioSimplificadoHabilitado()}">
                                        <input id="checkboxDS" type="checkbox" checked /><label for="checkboxDS">Diario simplificado</label><br />
                                    </s:if>
                                    <s:else>
                                        <input id="checkboxDS" type="checkbox" /><label for="checkboxDS">Diario simplificado</label><br />
                                    </s:else>
                                    <button title="Guardar" class="button" id="btnGuardarLibros"><span>Guardar</span></button>
                                    <button title="Cancelar" class="btnCancelar button"><span>Cancelar</span></button>
                                </div>
                            </div>
                            <div class="spacer"></div>
                        </div>
                        <!-- detalle -->
                        <div class="detalleDatosCliente">
                            <div class="lfloat">
                                <span>Estado en el sistema</span>
                                <input type="hidden" value="ClienteAjaxAction_editEstado" />
                            </div>
                            <div class="lfloat">
                                <div>
                                    <span><s:property value="empresaCliente.estado" /></span>
                                    <button title="Editar" class="btnEditar smallButton mediumOpacity"><span class="editButtonImage"></span></button>                                                                  
                                </div>
                                <div class="hide">
                                    <select>
                                        <option value="habilitado">habilitado</option>
                                        <option value="deshabilitado">deshabilitado</option>                                        
                                    </select>
                                    <button title="Guardar" class="btnGuardar button"><span>Guardar</span></button>
                                    <button title="Cancelar" class="btnCancelar button"><span>Cancelar</span></button>
                                </div>
                            </div>
                            <div class="spacer"></div>
                        </div>
                        <!-- detalle -->
                        <div class="detalleDatosCliente">
                            <div class="lfloat">
                                <span>Descripción del negocio</span>
                                <input type="hidden" value="ClienteAjaxAction_editDescripcion" />
                            </div>
                            <div class="lfloat">
                                <div>
                                    <span><s:property value="empresaCliente.descripcion" /></span>
                                    <button title="Editar" class="btnEditar smallButton mediumOpacity"><span class="editButtonImage"></span></button>                                                                  
                                </div>
                                <div class="hide">
                                    <textarea cols="45" rows="4"><s:property value="empresaCliente.descripcion" /></textarea>
                                    <button title="Guardar" class="btnGuardar button"><span>Guardar</span></button>
                                    <button title="Cancelar" class="btnCancelar button"><span>Cancelar</span></button>
                                </div>
                            </div>
                            <div class="spacer"></div>
                        </div>                        
                        <!-- detalle -->
                        <div class="detalleDatosCliente">
                            <div class="lfloat">
                                <span>Dirección</span>
                                <input type="hidden" value="ClienteAjaxAction_editDireccion" />
                            </div>
                            <div class="lfloat">
                                <div>
                                    <span><s:property value="empresaCliente.direccion" /></span>
                                    <button title="Editar" class="btnEditar smallButton mediumOpacity"><span class="editButtonImage"></span></button>                                                                  
                                </div>
                                <div class="hide">
                                    <input type="text" value="Av. Prueba 123 Calle 222" />
                                    <button title="Guardar" class="btnGuardar button"><span>Guardar</span></button>
                                    <button title="Cancelar" class="btnCancelar button"><span>Cancelar</span></button>
                                </div>
                            </div>
                            <div class="spacer"></div>
                        </div>
                        <!-- detalle -->
                        <div class="detalleDatosCliente">
                            <div class="lfloat">
                                <span>Teléfono 1</span>
                                <input type="hidden" value="ClienteAjaxAction_editTelefono1" />
                            </div>
                            <div class="lfloat">
                                <div>
                                    <span><s:property value="empresaCliente.telefono1" /></span>
                                    <button title="Editar" class="btnEditar smallButton mediumOpacity"><span class="editButtonImage"></span></button>                                                                  
                                </div>
                                <div class="hide">
                                    <input type="text" value="<s:property value="empresaCliente.telefono1" />" />
                                    <button title="Guardar" class="btnGuardar button"><span>Guardar</span></button>
                                    <button title="Cancelar" class="btnCancelar button"><span>Cancelar</span></button>
                                </div>
                            </div>
                            <div class="spacer"></div>
                        </div>
                        <!-- detalle -->
                        <div class="detalleDatosCliente">
                            <div class="lfloat">
                                <span>Teléfono 2</span>
                                <input type="hidden" value="ClienteAjaxAction_editTelefono2" />
                            </div>
                            <div class="lfloat">
                                <div>
                                    <span><s:property value="empresaCliente.telefono2" /></span>
                                    <button title="Editar" class="btnEditar smallButton mediumOpacity"><span class="editButtonImage"></span></button>                                                                  
                                </div>
                                <div class="hide">
                                    <input type="text" value="<s:property value="empresaCliente.telefono2" />" />
                                    <button title="Guardar" class="btnGuardar button"><span>Guardar</span></button>
                                    <button title="Cancelar" class="btnCancelar button"><span>Cancelar</span></button>
                                </div>
                            </div>
                            <div class="spacer"></div>
                        </div>
                        <!-- detalle -->
                        <div class="detalleDatosCliente">
                            <div class="lfloat">
                                <span>Fecha frontera</span>
                                <input type="hidden" value="ClienteAjaxAction_editFechaFrontera" />
                            </div>
                            <div class="lfloat">
                                <div>
                                    <span><s:property value="empresaCliente.fechaFrontera" /></span>
                                    <button title="Editar" class="btnEditar smallButton mediumOpacity"><span class="editButtonImage"></span></button>                                                                  
                                </div>
                                <div class="hide">
                                    <input type="text" value="<s:property value="empresaCliente.fechaFrontera" />" />
                                    <button title="Guardar" class="btnGuardar button"><span>Guardar</span></button>
                                    <button title="Cancelar" class="btnCancelar button"><span>Cancelar</span></button>
                                </div>
                            </div>
                            <div class="spacer"></div>
                        </div>
                    </div>

                    <div id="headerContentArea">
                        <h1 class="medium">Datos de contacto</h1>
                    </div>

                    <div id="datosWrapper">
                        <!-- detalle -->
                        <div class="detalleDatosCliente">
                            <div class="lfloat">
                                <span>Nombres</span>
                                <input type="hidden" value="ClienteAjaxAction_editNombresContacto" />
                            </div>
                            <div class="lfloat">
                                <div>
                                    <span><s:property value="empresaCliente.contacto.nombres" /></span>
                                    <button title="Editar" class="btnEditar smallButton mediumOpacity"><span class="editButtonImage"></span></button>                                                                  
                                </div>
                                <div class="hide">
                                    <input type="text" value="<s:property value="empresaCliente.contacto.nombres" />" />
                                    <button title="Guardar" class="btnGuardar button"><span>Guardar</span></button>
                                    <button title="Cancelar" class="btnCancelar button"><span>Cancelar</span></button>
                                </div>
                            </div> 
                            <div class="spacer"></div>
                        </div> 
                        <!-- detalle -->
                        <div class="detalleDatosCliente">
                            <div class="lfloat">
                                <span>Apellidos</span>
                                <input type="hidden" value="ClienteAjaxAction_editApellidosContacto" />
                            </div>
                            <div class="lfloat">
                                <div>
                                    <span><s:property value="empresaCliente.contacto.apellidos" /></span>
                                    <button title="Editar" class="btnEditar smallButton mediumOpacity"><span class="editButtonImage"></span></button>                                                                  
                                </div>
                                <div class="hide">
                                    <input type="text" value="<s:property value="empresaCliente.contacto.apellidos" />" />
                                    <button title="Guardar" class="btnGuardar button"><span>Guardar</span></button>
                                    <button title="Cancelar" class="btnCancelar button"><span>Cancelar</span></button>
                                </div>                                
                            </div> 
                            <div class="spacer"></div>
                        </div> 
                        <!-- detalle -->
                        <div class="detalleDatosCliente">
                            <div class="lfloat">
                                <span>Dirección</span>
                                <input type="hidden" value="ClienteAjaxAction_editDireccionContacto" />
                            </div>
                            <div class="lfloat">
                                <div>
                                    <span><s:property value="empresaCliente.contacto.direccion" /></span>
                                    <button title="Editar" class="btnEditar smallButton mediumOpacity"><span class="editButtonImage"></span></button>                                                                  
                                </div>
                                <div class="hide">
                                    <input type="text" value="<s:property value="empresaCliente.contacto.direccion" />" />
                                    <button title="Guardar" class="btnGuardar button"><span>Guardar</span></button>
                                    <button title="Cancelar" class="btnCancelar button"><span>Cancelar</span></button>
                                </div>
                            </div> 
                            <div class="spacer"></div>
                        </div> 
                        <!-- detalle -->
                        <div class="detalleDatosCliente">
                            <div class="lfloat">
                                <span>Celular</span>
                                <input type="hidden" value="ClienteAjaxAction_editCelularContacto" />
                            </div>
                            <div class="lfloat">
                                <div>
                                    <span><s:property value="empresaCliente.contacto.celular" /></span>
                                    <button title="Editar" class="btnEditar smallButton mediumOpacity"><span class="editButtonImage"></span></button>                                                                  
                                </div>
                                <div class="hide">
                                    <input type="text" value="<s:property value="empresaCliente.contacto.celular" />" />
                                    <button title="Guardar" class="btnGuardar button"><span>Guardar</span></button>
                                    <button title="Cancelar" class="btnCancelar button"><span>Cancelar</span></button>
                                </div>
                            </div> 
                            <div class="spacer"></div>
                        </div> 
                        <!-- detalle -->
                        <div class="detalleDatosCliente">
                            <div class="lfloat">
                                <span>Email Principal <br />(email de acceso al sistema)</span>
                                <input type="hidden" value="ClienteAjaxAction_editEmailPrincipalContacto" />
                            </div>
                            <div class="lfloat">
                                <div>
                                    <span><s:property value="empresaCliente.contacto.emailPrincipal" /></span>
                                    <button title="Editar" class="btnEditar smallButton mediumOpacity"><span class="editButtonImage"></span></button>                                                                  
                                </div>
                                <div class="hide">
                                    <input type="text" value="<s:property value="empresaCliente.contacto.emailPrincipal" />" />
                                    <button title="Guardar" class="btnGuardar button"><span>Guardar</span></button>
                                    <button title="Cancelar" class="btnCancelar button"><span>Cancelar</span></button>
                                </div>
                            </div> 
                            <div class="spacer"></div>
                        </div> 
                        <!-- detalle -->
                        <div class="detalleDatosCliente">
                            <div class="lfloat">
                                <span>Contraseña</span>
                                <input type="hidden" value="ClienteAjaxAction_editContraseñaContacto" />
                            </div>
                            <div class="lfloat">
                                <div>
                                    <span>(encriptada)</span>
                                    <button title="Editar" class="btnEditar smallButton mediumOpacity"><span class="editButtonImage"></span></button>                                                                  
                                </div>
                                <div class="hide">
                                    <input type="text" value="<s:property value="empresaCliente.contacto.password" />" />
                                    <button title="Guardar" class="btnGuardar button"><span>Guardar</span></button>
                                    <button title="Cancelar" class="btnCancelar button"><span>Cancelar</span></button>
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
                                    <span><s:property value="empresaCliente.contacto.emailSecundario" /></span>
                                    <button title="Editar" class="btnEditar smallButton mediumOpacity"><span class="editButtonImage"></span></button>                                                                  
                                </div>
                                <div class="hide">
                                    <input type="text" value="<s:property value="empresaCliente.contacto.emailSecundario" />" />
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
                                    <span><s:property value="empresaCliente.contacto.emailFacebook" /></span>
                                    <button title="Editar" class="btnEditar smallButton mediumOpacity"><span class="editButtonImage"></span></button>                                                                  
                                </div>
                                <div class="hide">
                                    <input type="text" value="<s:property value="empresaCliente.contacto.emailFacebook" />" />
                                    <button title="Guardar" class="btnGuardar button"><span>Guardar</span></button>
                                    <button title="Cancelar" class="btnCancelar button"><span>Cancelar</span></button>
                                </div>
                            </div> 
                            <div class="spacer"></div>
                        </div> 
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
