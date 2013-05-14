Ext.require([
    'Ext.grid.*',
    'Ext.data.*',
    'Ext.util.*',
    'Ext.state.*',
    'Ext.toolbar.Paging',
    'Ext.ux.PreviewPlugin',
    'Ext.ModelManager'    
    ]);

Ext.onReady(function() {
       
    Ext.define('DetalleLibroRegistroVentas', {
        extend: 'Ext.data.Model',
        fields: [
        { name: 'idDetalleLibroRegistroVentas', type: 'int' },
        { name: 'numeroCorrelativo', type: 'int' },
        { name: 'valorFacturadoExportacion', type: 'auto' },
        { name: 'baseImponibleOpGravada', type: 'auto' },
        { name: 'totalOperacionExonerada', type: 'auto' },
        { name: 'totalOperacionInafecta', type: 'auto' },
        { name: 'isc', type: 'auto' },
        { name: 'igv_ipm', type: 'auto' },
        { name: 'otrosTributos', type: 'auto' },
        { name: 'importeTotal', type: 'auto' },
        { name: 'numeroFinalTicketOcintaDelDia', type: 'int' },
        { name: 'tipoCambio', type: 'auto' },
        { name: 'baseImponibleArrozPilado', type: 'auto' },
        { name: 'impuestoVentasArrozPilado', type: 'auto' },
        { name: 'estadoOportunidadDeAnotación', type: 'int' },
        { name: 'fechaHoraRegistro', type: 'date', dateFormat: 'timestamp'},
        { name: 'idComprobanteVenta', type: 'int' },
        { name: 'numeroTipoComprobante', type: 'auto' },
        { name: 'serieComprobante', type: 'auto' },
        { name: 'numeroComprobante', type: 'auto' },
        { name: 'numeroTipoDocIdentidadComprador', type: 'auto' },
        { name: 'numeroDocIdentidadComprador', type: 'auto' },
        { name: 'razonSocialONombresComprador', type: 'auto' },
        { name: 'idComprobanteVentaReferenciado', type: 'int' },
        { name: 'fechaComprobanteVentaReferenciado', type: 'date', dateFormat: 'Y-m-d' },
        { name: 'numeroTipoComprobanteVentaReferenciado', type: 'int' },
        { name: 'serieComprobanteVentaReferenciado', type: 'auto' },
        { name: 'numeroComprobanteVentaReferenciado', type: 'auto' },        
        ]
    });

    var store = Ext.create('Ext.data.Store', {
        model: 'DetalleLibroRegistroVentas',
        proxy: {
            type: 'ajax',
            url : '',
            reader: {
                type: 'json',
                root: 'detallesLibroRegistroVentas',
                successProperty: 'success'
            }
        },
        autoLoad: true
    });

    // create the Grid
    var grid = Ext.create('Ext.grid.Panel', {
        store: store,
        columnLines: true,
        columns: [{
            text     : 'Número \ncorrelativo del registro o código único de la operación',
            width    : 85,
            sortable : true,
            dataIndex: ''
        }, {
            text     : 'Fecha de emisión del comprobante de pago o documento',
            width    : 105,
            sortable : true,
            renderer : Ext.util.Format.dateRenderer('m/d/Y'),
            dataIndex: ''
        }, {
            text     : 'Fecha de vencimiento y/o pago',
            width    : 105,
            sortable : true,
            renderer : Ext.util.Format.dateRenderer('m/d/Y'),
            dataIndex: ''
        }, {
            text: 'Comprobante de pago o documento',
            columns: [{
                text     : 'Tipo (Tabla 10)',
                width    : 75,
                sortable : true,                
                dataIndex: ''
            }, {
                text     : 'N° serie o N° de serie de la máquina registradora',
                width    : 85,
                sortable : true,                
                dataIndex: ''
            }, {
                text     : 'Número',
                width    : 105,
                sortable : true,                
                dataIndex: ''
            }]
        }, {
            text: 'Información del cliente',
            columns: [{
                text     : 'Documento de identidad',
                columns: [{
                    text     : 'Tipo (Tabla 2)',
                    width    : 85,
                    sortable : true,                    
                    dataIndex: ''
                }, {
                    text     : 'Número',
                    width    : 125,
                    sortable : true,                    
                    dataIndex: ''
                }]
            }, {
                text     : 'Apellidos y nombres, denominación o Razón Social',
                width    : 295,
                sortable : true,
                renderer : change,
                dataIndex: ''
            }]
        }, {
            text     : 'Valor facturado de la exportación',
            width    : 105,
            sortable : true,            
            dataIndex: ''
        }, {
            text     : 'Base imponible de la operación gravada',
            width    : 105,
            sortable : true,            
            dataIndex: ''
        }, {
            text     : 'Importe total de la operación exonerada o inafecta',
            columns: [{
                text     : 'Exonerada',
                width    : 105,
                sortable : true,
                renderer : 'usMoney',
                dataIndex: ''
            }, {
                text     : 'Inafecta',
                width    : 105,
                sortable : true,
                renderer : 'usMoney',
                dataIndex: ''
            }]
        }, {
            text     : 'ISC',
            width    : 105,
            sortable : true,            
            dataIndex: ''
        }, {
            text     : 'IGV y/o IPM',
            width    : 105,
            sortable : true,            
            dataIndex: ''
        }, {
            text     : 'Otros tributos y cargos que no forman parte de la base imponible',
            width    : 105,
            sortable : true,            
            dataIndex: ''
        }, {
            text     : 'Importe total del comprobante de pago',
            width    : 105,
            sortable : true,            
            dataIndex: ''
        }, {
            text     : 'Tipo de cambio',
            width    : 105,
            sortable : true,            
            dataIndex: ''
        }, {
            text: 'Referencia del comprobante de pago o documento original que se modifica',
            columns: [{
                text     : 'Fecha',
                width    : 105,
                sortable : true,
                renderer : Ext.util.Format.dateRenderer('m/d/Y'),
                dataIndex: ''
            }, {
                text     : 'Tipo (Tabla 10)',
                width    : 75,
                sortable : true,                
                dataIndex: ''
            }, {
                text     : 'Serie',
                width    : 85,
                sortable : true,                
                dataIndex: ''
            }, {
                text     : 'N° del comprobante de pago o documento',
                width    : 105,
                sortable : true,                
                dataIndex: ''
            }]
        }],
        height: 450,
        width: 840,        
        title: 'Registro Ventas - 04/2013',
        renderTo: 'libroVentasExt'
    });
});