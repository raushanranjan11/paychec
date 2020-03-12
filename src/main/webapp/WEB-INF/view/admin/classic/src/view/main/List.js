/**
 * This view is an example list of people.
 */
Ext.define('MyApp.view.main.List', {
    extend: 'Ext.grid.Panel',
    xtype: 'mainlist',

    requires: [
        'MyApp.store.Personnel',
		'MyApp.store.CustomerStore'
    ],

    title: 'Bank Interest Rate ',
	layout:'fit',
	autoScroll:true,

   /* store: {
        type: 'customer'
    },*/
	//store:'customer',//'customer',

    columns: [
	{ text: 'Id',  dataIndex: 'id' },
	{ text: 'Bank Name',  dataIndex: 'bankName' },
	{ text: 'Country',  dataIndex: 'country' },
	{ text: 'Interest Rate',  dataIndex: 'rate' },
	{ text: 'Processing Fees',  dataIndex: 'fees' },
	{ text: 'Updated Date',  dataIndex: 'date' },
	
    ],
	
	plugins: [
            {
                ptype: 'rowexpander',
                rowBodyTpl: [
                    '<table>',
                    '<tpl foreach=".">',
                    '<tr><td>{$}</td><td>{.}</td></tr>',
                    '</tpl>',
                    '</table>'
                ]
            }
        ],
    viewConfig: {
        enableTextSelection: true
    },
	dockedItems: [{
                xtype: 'pagingtoolbar',
                bind: {
                 //   store:Ext.create('MyApp.store.CustomerStore'),// '{personnel}'
                },
                dock: 'bottom',
                displayInfo: true
            }],

    listeners: {
        select: 'onItemSelected'
    }
});
