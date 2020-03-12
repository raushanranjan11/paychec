Ext.define('MyApp.view.main.MainPanel', {
	extend: 'Ext.tab.Panel',
	xtype: 'mainPanel',
	requires: ['Ext.panel.Panel', 'MyApp.view.bank.BankForm',
		'MyApp.view.bank.LoanTypeForm', 'MyApp.view.bank.InterestRateForm'],
	layout: 'fit',
	items: [{
			xtype: 'panel',
			iconCls: 'fa fa-home fa-lg tabIcon',
			title: 'Dashboard',
			//layout: 'hbox',
			defaults: {
				//labelWidth: 120,
				style: 'margin:10px;',
			},
			items: [{
					//{
					xtype: 'button',
					// margin: '-32 0 0 380',
					// style: 'position:absolute;',
					// width: 78,
					text: 'Country',
					iconCls: 'fa fa-plus',
					// action: 'btn-add-country'
					//},
				}, {
					//{
					xtype: 'button',
					// margin: '-32 0 0 380',
					// style: 'position:absolute;',
					// width: 78,
					text: 'Bank',
					iconCls: 'fa fa-plus',
					action: 'btn-add-country',
					handler: function () {
						console.log('hi');
						Ext.create('MyApp.view.bank.BankForm');
					}
					//},loanTypeForm
				}, {
					//{
					xtype: 'button',
					// margin: '-32 0 0 380',
					// style: 'position:absolute;',
					// width: 78,
					text: 'Loan Type',
					iconCls: 'fa fa-plus',
					//  action: 'btn-add-country',
					handler: function () {
						console.log('Bye');
						Ext.create('MyApp.view.bank.LoanTypeForm');
					}
				}, {
					//{
					xtype: 'button',
					// margin: '-32 0 0 380',
					// style: 'position:absolute;',
					// width: 78,
					text: 'Interest Rate',
					iconCls: 'fa fa-plus',
					//  action: 'btn-add-country',
					handler: function () {
						console.log('Bye');
						Ext.create('MyApp.view.bank.InterestRateForm');
					}
					//},
				},
				/*	{
				title: 'Add Bank',
				xtype: 'fieldset',
				collapsible:true,
				autoHeight:true,  //ui: 'round',
				items:[{
				xtype: 'bankForm',
				}
				]
				//height:150
				},{
				title: 'Add Loan Type',
				xtype: 'fieldset',
				collapsible:true,
				autoHeight:true,  //ui: 'round',
				items:[{
				xtype: 'loanTypeForm',
				}
				]
				//height:150
				},*/
				{
				xtype: 'mainlist',
				autoHeight: true,
				height:340
				}
			]
		}, /*{
		xtype: 'mainlist',
		autoHeight: true,
		height:340
		}*/
	]
});