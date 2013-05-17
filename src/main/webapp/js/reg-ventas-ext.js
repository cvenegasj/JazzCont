Ext.require([
    'Ext.grid.*',
    'Ext.data.*',
    'Ext.util.*',
    'Ext.state.*',
    'Ext.toolbar.Paging',
    'Ext.ModelManager'    
    ]);

Ext.onReady(function() {
       
    Ext.define('DetalleLibroRegistroVentas', {
        extend: 'Ext.data.Model',
        fields: [
        {
            name: 'idDetalleLibroRegistroVentas', 
            type: 'auto'
        },
        {
            name: 'numeroCorrelativo', 
            type: 'auto'
        },
        {
            name: 'valorFacturadoExportacion', 
            type: 'auto'
        },
        {
            name: 'baseImponibleOpGravada', 
            type: 'auto'
        },
        {
            name: 'totalOperacionExonerada', 
            type: 'auto'
        },
        {
            name: 'totalOperacionInafecta', 
            type: 'auto'
        },
        {
            name: 'isc', 
            type: 'auto'
        },
        {
            name: 'igv_ipm', 
            type: 'auto'
        },
        {
            name: 'otrosTributos', 
            type: 'auto'
        },
        {
            name: 'importeTotal', 
            type: 'auto'
        },
        {
            name: 'numeroFinalTicketOcintaDelDia', 
            type: 'auto'
        },
        {
            name: 'tipoCambio', 
            type: 'auto'
        },
        {
            name: 'baseImponibleArrozPilado', 
            type: 'auto'
        },
        {
            name: 'impuestoVentasArrozPilado', 
            type: 'auto'
        },
        {
            name: 'estadoOportunidadDeAnotación', 
            type: 'auto'
        },
        {
            name: 'fechaHoraRegistro', 
            type: 'auto'
        }, 
        // comprobante
        {
            name: 'idComprobanteVenta', 
            type: 'auto'
        },
        {
            name: 'fechaEmisionComprobante', 
            type: 'date', dateFormat: 'd/m/Y'
        },
        {
            name: 'fechaVencimientoComprobante', 
            type: 'date', dateFormat: 'd/m/Y'
        },
        {
            name: 'baseComprobante', 
            type: 'auto'
        },
        {
            name: 'igvComprobante', 
            type: 'auto'
        },
        {
            name: 'importeTotalComprobante', 
            type: 'auto'
        },
        {
            name: 'numeroTipoComprobante', 
            type: 'auto'
        },
        {
            name: 'serieComprobante', 
            type: 'auto'
        },
        {
            name: 'numeroComprobante', 
            type: 'auto'
        },
        // comprador
        {
            name: 'numeroTipoDocIdentidadComprador', 
            type: 'auto'
        },
        {
            name: 'numeroDocIdentidadComprador', 
            type: 'auto'
        },
        {
            name: 'razonSocialONombresComprador', 
            type: 'auto'
        },
        // comprobante referenciado
        {
            name: 'idComprobanteVentaReferenciado', 
            type: 'auto'
        },
        {
            name: 'fechaComprobanteVentaReferenciado', 
            type: 'date', dateFormat: 'd/m/Y'
        },
        {
            name: 'numeroTipoComprobanteVentaReferenciado', 
            type: 'auto'
        },
        {
            name: 'serieComprobanteVentaReferenciado', 
            type: 'auto'
        },
        {
            name: 'numeroComprobanteVentaReferenciado', 
            type: 'auto'
        }        
        ]
    });

    var QueryString = function () {
        // This function is anonymous, is executed immediately and 
        // the return value is assigned to QueryString!
        var query_string = {};
        var query = window.location.search.substring(1);
        var vars = query.split("&");
        for (var i=0;i<vars.length;i++) {
            var pair = vars[i].split("=");
            // If first entry with this name
            if (typeof query_string[pair[0]] === "undefined") {
                query_string[pair[0]] = pair[1];
            // If second entry with this name
            } else if (typeof query_string[pair[0]] === "string") {
                var arr = [ query_string[pair[0]], pair[1] ];
                query_string[pair[0]] = arr;
            // If third or later entry with this name
            } else {
                query_string[pair[0]].push(pair[1]);
            }
        } 
        return query_string;
    } ();

    var store = Ext.create('Ext.data.Store', {
        pageSize: 20,
        model: 'DetalleLibroRegistroVentas',
        autoLoad: true,
        proxy: {
            type: 'ajax',
            url : 'LibrosAjaxAction_listDetallesRV?ruc=' + QueryString.ruc + '&idLibro=' + QueryString.idLibro,
            reader: {
                type: 'json',
                root: 'listaDetallesRVSrl',
                totalProperty: 'totalCount'                
            }
        }      
    });

    // create the Grid
    var grid = Ext.create('Ext.grid.Panel', {
        store: store,
        columnLines: true,
        columns: [{
            text     : 'Número correlativo del registro o código único de la operación',
            width    : 85,
            sortable : true,
            dataIndex: 'numeroCorrelativo'
        }, {
            text     : 'Fecha de emisión del comprobante de pago o documento',
            width    : 105,
            sortable : true,
            renderer : Ext.util.Format.dateRenderer('d/m/Y'),
            dataIndex: 'fechaEmisionComprobante'
        }, {
            text     : 'Fecha de vencimiento y/o pago',
            width    : 105,
            sortable : true,
            renderer : Ext.util.Format.dateRenderer('d/m/Y'),
            dataIndex: 'fechaVencimientoComprobante'
        }, {
            text: 'Comprobante de pago o documento',
            columns: [{
                text     : 'Tipo (Tabla 10)',
                width    : 75,
                sortable : true,                
                dataIndex: 'numeroTipoComprobante'
            }, {
                text     : 'N° serie o N° de serie de la máquina registradora',
                width    : 85,
                sortable : true,                
                dataIndex: 'serieComprobante'
            }, {
                text     : 'Número',
                width    : 105,
                sortable : true,                
                dataIndex: 'numeroComprobante'
            }]
        }, {
            text: 'Información del cliente',
            columns: [{
                text     : 'Documento de identidad',
                columns: [{
                    text     : 'Tipo (Tabla 2)',
                    width    : 85,
                    sortable : true,                    
                    dataIndex: 'numeroTipoDocIdentidadComprador'
                }, {
                    text     : 'Número',
                    width    : 125,
                    sortable : true,                    
                    dataIndex: 'numeroDocIdentidadComprador'
                }]
            }, {
                text     : 'Apellidos y nombres, denominación o Razón Social',
                width    : 295,
                sortable : true,                
                dataIndex: 'razonSocialONombresComprador'
            }]
        }, {
            text     : 'Valor facturado de la exportación',
            width    : 105,
            sortable : true,  
            tdCls: 'align-right-td',
            renderer: Ext.util.Format.numberRenderer('0.00'),
            dataIndex: 'valorFacturadoExportacion'
        }, {
            text     : 'Base imponible de la operación gravada',
            width    : 105,
            sortable : true,    
            tdCls: 'align-right-td',
            renderer: Ext.util.Format.numberRenderer('0.00'),
            dataIndex: 'baseImponibleOpGravada'
        }, {
            text     : 'Importe total de la operación exonerada o inafecta',
            columns: [{
                text     : 'Exonerada',
                width    : 105,
                sortable : true,
                tdCls: 'align-right-td',
                renderer: Ext.util.Format.numberRenderer('0.00'),
                dataIndex: 'totalOperacionExonerada'
            }, {
                text     : 'Inafecta',
                width    : 105,
                sortable : true,
                tdCls: 'align-right-td',
                renderer: Ext.util.Format.numberRenderer('0.00'),
                dataIndex: 'totalOperacionInafecta'
            }]
        }, {
            text     : 'ISC',
            width    : 105,
            sortable : true,  
            tdCls: 'align-right-td',
            renderer: Ext.util.Format.numberRenderer('0.00'),
            dataIndex: 'isc'
        }, {
            text     : 'IGV y/o IPM',
            width    : 105,
            sortable : true,   
            tdCls: 'align-right-td',
            renderer: Ext.util.Format.numberRenderer('0.00'),
            dataIndex: 'igv_ipm'
        }, {
            text     : 'Otros tributos y cargos que no forman parte de la base imponible',
            width    : 105,
            sortable : true, 
            tdCls: 'align-right-td',
            renderer: Ext.util.Format.numberRenderer('0.00'),
            dataIndex: 'otrosTributos'
        }, {
            text     : 'Importe total del comprobante de pago',
            width    : 105,
            sortable : true,   
            tdCls: 'align-right-td',
            renderer: Ext.util.Format.numberRenderer('0.00'),
            dataIndex: 'importeTotal'
        }, {
            text     : 'Tipo de cambio',
            width    : 105,
            sortable : true,  
            tdCls: 'align-right-td',
            renderer: Ext.util.Format.numberRenderer('0.00'),
            dataIndex: 'tipoCambio'
        }, {
            text: 'Referencia del comprobante de pago o documento original que se modifica',
            columns: [{
                text     : 'Fecha',
                width    : 105,
                sortable : true,
                renderer : Ext.util.Format.dateRenderer('d/m/Y'),
                dataIndex: 'fechaComprobanteVentaReferenciado'
            }, {
                text     : 'Tipo (Tabla 10)',
                width    : 75,
                sortable : true,                
                dataIndex: 'numeroTipoComprobanteVentaReferenciado'
            }, {
                text     : 'Serie',
                width    : 85,
                sortable : true,                
                dataIndex: 'serieComprobanteVentaReferenciado'
            }, {
                text     : 'N° del comprobante de pago o documento',
                width    : 105,
                sortable : true,                
                dataIndex: 'numeroComprobanteVentaReferenciado'
            }]
        }],
        height: 550,
        width: 840,         
        // paging bar on the bottom
        bbar: Ext.create('Ext.PagingToolbar', {
            store: store,
            displayInfo: true,
            displayMsg: 'Mostrando registros {0} - {1} de {2}',
            emptyMsg: "No hay registros que mostrar"            
        }),
        title: 'Registro Ventas ' + QueryString.prd,        
        renderTo: 'libroVentasExt'
    });   
});