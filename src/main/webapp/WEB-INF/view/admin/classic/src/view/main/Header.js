Ext.define('MyApp.view.main.Header', {
    extend: 'Ext.toolbar.Toolbar',
    xtype: 'appheader',

    requires: [
     //   'MyApp.view.locale.Translation'
      //  'MyApp.view.main.ResponsiveMenuButton'
    ],

    ui: 'footer',

    items: [{
            xtype: 'component',
            bind: {
                html: '{appHeaderIcon}'
            }
    },
    {
            xtype: 'component',
            componentCls: 'app-header-title',
            bind: {
                html: '{name}'
				//html:"PayChec"
            }
        },{
            xtype: 'tbfill'
        },{
            xtype: 'tbseparator'
        },{
            xtype: 'button',
            itemId: 'logout',
            text: "logout",
            reference: 'logout',
            iconCls: 'fa fa-sign-out fa-lg buttonIcon',
            listeners: {
                click: 'onLogout'
            }
        }
    ]
});