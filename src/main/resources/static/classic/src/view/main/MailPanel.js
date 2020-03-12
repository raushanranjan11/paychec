Ext.define('MyApp.view.main.MainPanel', {
	extend: 'Ext.tab.Panel',
	xtype: 'mainPanel',
	requires: ['Ext.panel.Panel', 'MyApp.view.bank.BankForm', 'MyApp.view.bank.LoanTypeForm', 'MyApp.view.bank.InterestRateForm', 
	'MyApp.view.bank.CountryForm'],
	layout: 'fit',
	items: [{
			xtype: 'panel',
			iconCls: 'fa fa-home fa-lg tabIcon',
			title: 'Home',
			defaults: {
				style: 'margin:10px;',
			},
			items: [{
					xtype: 'button',
					text: 'Country',
					iconCls: 'fa fa-plus',
					handler: function () {
						Ext.create('MyApp.view.bank.CountryForm');
					}
				}, {
					xtype: 'button',
					text: 'Bank',
					iconCls: 'fa fa-plus',
					action: 'btn-add-country',
					handler: function () {
						Ext.create('MyApp.view.bank.BankForm');
					}
				}, {
					xtype: 'button',
					text: 'Loan Type',
					iconCls: 'fa fa-plus',
					handler: function () {
						Ext.create('MyApp.view.bank.LoanTypeForm');
					}
				}, {
					xtype: 'button',
					text: 'Link Loan Type to Bank',
					iconCls: 'fa fa-plus',
					handler: function () {
						Ext.create('MyApp.view.bank.LoanTypeBankForm');
					}
				}, {
					xtype: 'button',
					text: 'Interest Rate',
					iconCls: 'fa fa-plus',
					handler: function () {
						Ext.create('MyApp.view.bank.InterestForm');
					}
				}, {
					xtype: 'mainlist',
					autoHeight: true,
					autoWidth: true,
					height: 475
				}
			]
		},


		]
});
