Ext.define('MyApp.view.main.MainMenu', {
	extend: 'Ext.menu.Menu',
	xtype: 'app-menu',
	requires: ['Ext.menu.Menu'],
	// border:false,
	 
	 controller: 'menucontroller',

	initComponent: function () {
		Ext.apply(this, {
			width: 235,
			plain: true,
			floating: false,
			 border:false,

			items: [{
					text: 'Home',
					iconCls: 'fa fa-home ',
					//bind:this,
					reference:"home",
					listeners:{
						click:'hometab'
					}
					
				}, {
					text: 'User',
					iconCls: 'x-fa fa-user',
					listeners:{
						click:'usertab'
					}
				},
				{
					text: 'Applied Loan',
					iconCls: 'x-fa fa-bank',
					listeners:{
						click:'loanAppliedtab'
					}
				},


				{
					text: 'Setting',
					iconCls: 'x-fa fa-cog',
				}
			]

		});
		this.callParent(arguments);
	}

});
