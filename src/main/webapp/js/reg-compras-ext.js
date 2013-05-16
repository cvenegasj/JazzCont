Ext.require([
    'Ext.grid.*',
    'Ext.data.*',
    'Ext.util.*',
    'Ext.state.*',
    'Ext.toolbar.Paging',
    'Ext.ModelManager'    
    ]);

Ext.onReady(function() {
       
    Ext.define('DetalleLibroRegistroCompras', {
        extend: 'Ext.data.Model',
        fields: [
        {
            name: 'idDetalleLibroRegistroCompras', 
            type: 'auto'
        },
        {
            name: 'numeroCorrelativo', 
            type: 'auto'
        },
        {
            name: 'tipoAdquisicionGravada', 
            type: 'auto'
        },
        {
            name: 'baseImponible1', 
            type: 'auto'
        },
        {
            name: 'igv1', 
            type: 'auto'
        },
        {
            name: 'baseImponible2', 
            type: 'auto'
        },
        {
            name: 'igv2', 
            type: 'auto'
        },
        {
            name: 'baseImponible3', 
            type: 'auto'
        },
        {
            name: 'igv3', 
            type: 'auto'
        },
        {
            name: 'valorAdquisicionesNoGravadas', 
            type: 'auto'
        },
        {
            name: 'isc', 
            type: 'auto'
        },
        {
            name: 'otrosTributosYcargos', 
            type: 'auto'
        },
        {
            name: 'importeTotal', 
            type: 'auto'
        },
        {
            name: 'tipoCambio', 
            type: 'auto'
        },{
            name: 'numeroCompPagoSujNoDom', 
            type: 'auto'
        },
        {
            name: 'numeroConstDepDetraccion', 
            type: 'auto'
        },
        {
            name: 'fechaEmisionConstDepDetraccion', 
            type: 'auto'
        },{
            name: 'numeroFinalOperDiariasSinCredFiscal', 
            type: 'auto'
        },{
            name: 'marcaComprobanteSujetoAretencion', 
            type: 'auto'
        },{
            name: 'fechaHoraRegistro', 
            type: 'auto'
        },          
        // comprobante
        {
            name: 'idComprobanteCompra', 
            type: 'auto'
        },
        {
            name: 'numeroComprobante', 
            type: 'auto'
        },
        {
            name: 'serieComprobante', 
            type: 'auto'
        },
        {
            name: 'anioEmisionDuaOdsiComprobante', 
            type: 'auto'
        },
        {
            name: 'fechaEmisionComprobante', 
            type: 'auto'
        },
        {
            name: 'fechaVencimientoOpagoComprobante', 
            type: 'auto'
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
        // proveedor
        {
            name: 'numeroTipoDocIdentidadProveedor', 
            type: 'auto'
        },
        {
            name: 'numeroDocIdentidadProveedor', 
            type: 'auto'
        },
        {
            name: 'razonSocialProveedor', 
            type: 'auto'
        },        
        // comprobante referenciado
        {
            name: 'idComprobanteCompraReferenciado', 
            type: 'auto'
        },
        {
            name: 'fechaComprobanteCompraReferenciado', 
            type: 'auto'
        },
        {
            name: 'numeroTipoComprobanteCompraReferenciado', 
            type: 'auto'
        },
        {
            name: 'serieComprobanteCompraReferenciado', 
            type: 'auto'
        },
        {
            name: 'numeroComprobanteCompraReferenciado', 
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
        pageSize: 10,
        model: 'DetalleLibroRegistroCompras',
        autoLoad: true,
        proxy: {
            type: 'ajax',
            url : 'LibrosAjaxAction_listDetallesRC?ruc=' + QueryString.ruc + '&idLibro=' + QueryString.idLibro,
            reader: {
                type: 'json'                
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
            text     : 'Fecha de vencimiento o fecha de pago',
            width    : 105,
            sortable : true,
            renderer : Ext.util.Format.dateRenderer('d/m/Y'),
            dataIndex: 'fechaVencimientoOpagoComprobante'
        }, {
            text: 'Comprobante de pago o documento',
            columns: [{
                text     : 'Tipo (Tabla 10)',
                width    : 75,
                sortable : true,                
                dataIndex: 'numeroTipoComprobante'
            }, {
                text     : 'Serie o código de la dependencia aduanera (Tabla 11)',
                width    : 85,
                sortable : true,                
                dataIndex: 'serieComprobante'
            }, {
                text     : 'Año de emisión de la DUA o DSI',
                width    : 75,
                sortable : true,                
                dataIndex: 'anioEmisionDuaOdsiComprobante'
            }]
        }, {
            text: 'N° del comprobante de pago, documento, N° de orden del formulario físico o virtual, N° de DUA, DSI \n\
                     o liquidación de cobranza u otros documetos emitidos por SUNAT para acreditar el crédito fiscal en la importación',
            width: 200,
            sortable: true,
            dataIndex: 'numeroComprobante'
        }, {
            text: 'Información del proveedor',
            columns: [{
                text     : 'Documento de identidad',
                columns: [{
                    text     : 'Tipo (Tabla 2)',
                    width    : 85,
                    sortable : true,                    
                    dataIndex: 'numeroTipoDocIdentidadProveedor'
                }, {
                    text     : 'Número',
                    width    : 125,
                    sortable : true,                    
                    dataIndex: 'numeroDocIdentidadProveedor'
                }]
            }, {
                text     : 'Apellidos y nombres, denominación o Razón Social',
                width    : 295,
                sortable : true,                
                dataIndex: 'razonSocialProveedor'
            }]
        }, {
            text     : 'Adquisiciones gravadas destinadas a operaciones gravadas y/o de exportación',
            columns: [{
                text     : 'Base imponible',
                width    : 105,
                sortable : true,  
                tdCls: 'align-right-td',
                renderer: Ext.util.Format.numberRenderer('0.00'),
                dataIndex: 'baseImponible1'
            }, {
                text     : 'IGV',
                width    : 105,
                sortable : true,   
                tdCls: 'align-right-td',
                renderer: Ext.util.Format.numberRenderer('0.00'),
                dataIndex: 'igv1'
            }]
        }, {
            text     : 'Adquisiciones gravadas destinadas a operaciones gravadas y/o de exportación y a operaciones no gravadas',
            columns: [{
                text     : 'Base imponible',
                width    : 105,
                sortable : true,   
                tdCls: 'align-right-td',
                renderer: Ext.util.Format.numberRenderer('0.00'),
                dataIndex: 'baseImponible2'
            }, {
                text     : 'IGV',
                width    : 105,
                sortable : true,  
                tdCls: 'align-right-td',
                renderer: Ext.util.Format.numberRenderer('0.00'),
                dataIndex: 'igv2'
            }]
        }, {
            text     : 'Adquisiciones gravadas destinadas a operaciones no gravadas',
            columns: [{
                text     : 'Base imponible',
                width    : 105,
                sortable : true,  
                tdCls: 'align-right-td',
                renderer: Ext.util.Format.numberRenderer('0.00'),
                dataIndex: 'baseImponible3'
            }, {
                text     : 'IGV',
                width    : 105,
                sortable : true,  
                tdCls: 'align-right-td',
                renderer: Ext.util.Format.numberRenderer('0.00'),
                dataIndex: 'igv3'
            }]
        },{
            text     : 'Valor de las adquisiciones no gravadas',
            width    : 105,
            sortable : true,  
            tdCls: 'align-right-td',
            renderer: Ext.util.Format.numberRenderer('0.00'),
            dataIndex: 'valorAdquisicionesNoGravadas'
        }, {
            text     : 'ISC',
            width    : 105,
            sortable : true,  
            tdCls: 'align-right-td',
            renderer: Ext.util.Format.numberRenderer('0.00'),
            dataIndex: 'isc'
        }, {
            text     : 'Otros tributos y cargos',
            width    : 105,
            sortable : true,   
            tdCls: 'align-right-td',
            renderer: Ext.util.Format.numberRenderer('0.00'),
            dataIndex: 'otrosTributosYcargos'
        }, {
            text     : 'Importe total',
            width    : 105,
            sortable : true, 
            tdCls: 'align-right-td',
            renderer: Ext.util.Format.numberRenderer('0.00'),
            dataIndex: 'importeTotal'
        }, {
            text     : 'N° de comprobante de pago emitido por sujeto no domiciliado',
            width    : 105,
            sortable : true,
            dataIndex: 'numeroCompPagoSujNoDom'
        }, {
            text     : 'Constancia de depósito de detracción',
            columns: [{
                text     : 'Número',
                width    : 105,
                sortable : true,                
                dataIndex: 'numeroConstDepDetraccion'
            }, {
                text     : 'Fecha de emisión',
                width    : 105,
                sortable : true,
                renderer : Ext.util.Format.dateRenderer('d/m/Y'),
                dataIndex: 'fechaEmisionConstDepDetraccion'
            }]
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
                dataIndex: 'fechaComprobanteCompraReferenciado'
            }, {
                text     : 'Tipo (Tabla 10)',
                width    : 75,
                sortable : true,                
                dataIndex: 'numeroTipoComprobanteCompraReferenciado'
            }, {
                text     : 'Serie',
                width    : 85,
                sortable : true,                
                dataIndex: 'serieComprobanteCompraReferenciado'
            }, {
                text     : 'N° del comprobante de pago o documento',
                width    : 105,
                sortable : true,                
                dataIndex: 'numeroComprobanteCompraReferenciado'
            }]
        }],
        height: 450,
        width: 840,         
        // paging bar on the bottom
        bbar: Ext.create('Ext.PagingToolbar', {
            store: store,
            displayInfo: true,
            displayMsg: 'Mostrando registros {0} - {1} de {2}',
            emptyMsg: "No hay registros que mostrar"            
        }),
        title: 'Registro Compras ' + QueryString.prd,        
        renderTo: 'libroComprasExt'
    });   
});