Ext.define('MyApp.view.main.Footer', {
    extend: 'Ext.container.Container',
    xtype: 'appfooter',

    cls: 'app-footer',

    height: 30,

    layout: 'center',

    items: [
        {
            xtype: 'component',
            width: 450,
            componentCls: 'app-footer-title',
			style:'text-align: center;',
            bind: {
                html: '{footer}'
			  // html: 'ThinkSS'
            }
        }
    ]
});