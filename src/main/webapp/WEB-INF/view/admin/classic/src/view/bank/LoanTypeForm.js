Ext.define('MyApp.view.bank.LoanTypeForm', {
	extend: 'Ext.window.Window',
	xtype: 'loanTypeForm',
	autoDestroy: false,
	/*style: 'margin-top:10px;',
	layout: 'hbox',
	labelAlign: 'top',
	reference: 'loanTypeForm',
	defaults: {
		labelWidth: 120,
		style: 'margin:10px;',
		
	},*/
	initComponent: function () {
		Ext.apply(this, {
			bodyPadding : 10,
			title : 'Add Loan Type',
			closable : true,
			autoShow : true,
		
		
		
		items : [{
				xtype: 'form',
			/*	style: 'margin-top:10px;',
	layout: 'hbox',
	labelAlign: 'top',
	reference: 'loanTypeForm',
	defaults: {
		labelWidth: 120,
		style: 'margin:10px;',
		
	},*/
				items: [
					{
				name: 'country',
				fieldLabel: 'Country',
				xtype: 'combobox',
				maxHeight: 150,
				emptyText: "Select Country",
				store: 'country',
				displayField: 'countryName',
				valueField: 'id',
				forceSelection: false,
				editable: false,
				queryMode: 'local',
				triggerAction: 'all',
				allowBlank: false,
				reference: 'country',
				msgTarget: 'side',
				blankText: 'Country  is Mandatory!',
				listeners: {}
			},/* {
				xtype: 'textfield',
				name: 'bankName',
				fieldLabel: 'Bank Name',
				reference: 'bankName',
				msgTarget: 'side',
				blankText: 'Bank Name  is Mandatory!',
			},*/
		
		
		
		{
				name: 'bank',
				fieldLabel: 'Bank',
				xtype: 'combobox',
				maxHeight: 150,
				emptyText: "Select Bank",
				store: 'bank',
				displayField: 'countryName',
				valueField: 'id',
				forceSelection: false,
				editable: false,
				queryMode: 'local',
				triggerAction: 'all',
				allowBlank: false,
				reference: 'country',
				msgTarget: 'side',
				blankText: 'Bank  is Mandatory!',
			/*	store: {
    fields: ['id', 'name'],
    proxy: {
      type: 'ajax',
      url: '/bank'
    },
    autoLoad: true,
    autoSync: true
  },*/
				listeners: {
					beforequery: function(queryPlan, eOpt) {
						console.log();
						//var country = this.lookupReference('country');
           //queryEvent.combo.onLoad();
           // prevent doQuery from firing and clearing out my filter.
          // return false; 
		  
		  this.store.getProxy().extraParams = {
  status: 2
};
     }
					
					
					
					
					
				}
			}, {
				xtype: 'textfield',
				name: 'loanType',
				fieldLabel: 'Loan Type',
				reference: 'loanType',
				msgTarget: 'side',
				blankText: 'Loan Type  is Mandatory!',
			},
				],
				buttons: [{
						text: 'Save',
						scope: this,
						formBind: true,
						action: 'save',
						listeners: {
							click: 'submit'
						}
					}, '->', {
						text: 'Cancel',
						action: 'cancel',
						scope: this,
						handler: this.close,
					}
				]
			}

		]
	});
		/*	this.buttons = [{
		text: 'Save',
		scope: this,formBind : true,
		action: 'save',
		listeners: {
		click: 'submit'
		}
		}, '->', {
		text: 'Cancel',
		action: 'cancel',
		scope: this,
		handler: this.close,
		}
		];*/
		this.callParent();
	}
});