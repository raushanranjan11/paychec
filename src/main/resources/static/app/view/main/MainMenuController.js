/**
 * This class is the controller for the main view for the application. It is specified as
 * the "controller" of the Main view class.
 *
 * TODO - Replace this content of this view to suite the needs of your application.
 */
Ext.define('MyApp.view.main.MainMenuController', {
	extend: 'Ext.app.ViewController',

	alias: 'controller.menucontroller',
	hometab:function(me, record){
		var tabtext = me.text;
		var tabPanel = Ext.ComponentQuery.query('mainPanel')[0];
		tabPanel.setActiveTab(0);
		
	},

	usertab: function (me, record) {
		var tabtext = me.text;
		var tabPanel = Ext.ComponentQuery.query('mainPanel')[0];
		var reqtab = tabPanel.getComponent('user');
		var flag = false
		var index = 0;
		var tablength = tabPanel.items.length; 
		//debugger;
		for (var i = 0; i < tablength; i++) {
			if (tabPanel.items.items[i].getTitle() == tabtext) {
				flag = true;
				index = i;
				break;
			}
		}
		//debugger;
		if (flag) {
			tabPanel.setActiveTab(index);
		} else {
			tabPanel.add({
				title: 'User',
				closable: true,
				iconCls: 'x-fa fa-user',
				items:[{
				//xtype:'panel',
				//title:'User Details'
					xtype: 'userList',
					//autoHeight: true,
					height: 500,
					autoWidth: true,
				}
				]
			}).show();
		}
	},
	loanAppliedtab: function (me, record) {
		var tabtext = me.text;
		var tabPanel = Ext.ComponentQuery.query('mainPanel')[0];
	//	var reqtab = tabPanel.getComponent('user');
		var flag = false
		var index = 0;
		var tablength = tabPanel.items.length; 
		//debugger;
		for (var i = 0; i < tablength; i++) {
			if (tabPanel.items.items[i].getTitle() == tabtext) {
				flag = true;
				index = i;
				break;
			}
		}
		//debugger;
		if (flag) {
			tabPanel.setActiveTab(index);
		} else {
			tabPanel.add({
				title: 'Applied Loan',
				closable: true,
				iconCls: 'x-fa fa-bank',
				items:[{
				//xtype:'panel',
				//title:'User Details'
					xtype: 'applyLoan',
					//xtype: 'userList',
					//autoHeight: true,
					height: 500,
					autoWidth: true,
				}
				]
			}).show();
		}
	},

	onConfirm: function (choice) {
		if (choice === 'yes') {
			//
		}
	}
});