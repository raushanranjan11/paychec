Ext.define('MyApp.view.main.ActionMenu',{
    extend: 'Ext.panel.Panel',
		xtype: 'actionmenu',


		items: [{
                text: 'Settings',
                iconCls: 'settings'
            }, {
                text: 'New Item',
                iconCls: 'compose'
            }, {
                text: 'Star',
                iconCls: 'star'
            }]



});