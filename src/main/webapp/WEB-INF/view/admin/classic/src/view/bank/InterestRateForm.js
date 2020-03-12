Ext.define('MyApp.view.bank.InterestRateForm', {
	extend: 'Ext.window.Window',
	xtype: 'rateForm',
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
			title : 'Add Interest Rate',
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
				blankText: 'Bank  is Mandatory!',
				listeners: {}
			}, {
				xtype: 'textfield',
				name: 'loanType',
				fieldLabel: 'Loan Type',
				reference: 'loanType',
				msgTarget: 'side',
				blankText: 'Loan Type  is Mandatory!',
			},
			{
				xtype: 'textfield',
				name: 'rate',
				fieldLabel: 'Interest Rate',
				reference: 'rate',
				msgTarget: 'side',
				blankText: 'Interest Rate  is Mandatory!',
			},
			{
				xtype: 'textfield',
				name: 'processingFees',
				fieldLabel: 'Processing Fees',
				reference: 'rate',
				msgTarget: 'side',
				blankText: 'Processing Fees  is Mandatory!',
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