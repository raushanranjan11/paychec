Ext.define('MyApp.view.bank.LoanTypeForm', {
	extend: 'Ext.window.Window',
	xtype: 'loanTypeForm',
	autoDestroy: false,
	initComponent: function () {
		//var bankStore = new MyApp.store.BankStore({});
		Ext.apply(this, {
			bodyPadding: 10,
			title: 'Add Loan Type',
			closable: true,
			autoShow: true,
			modal: true,
			// closeAction: 'hide',
			 closeToolText: null,
			items: [{
					xtype: 'form',
					defaults: {
						labelWidth: 160,
					},
					items: [{
							xtype: 'textfield',
							name: 'loanName',
							fieldLabel: 'Loan Type',
							reference: 'loanType',
							msgTarget: 'side',
							allowBlank: false,
							blankText: 'Loan Type  is Mandatory!',
						},/* {
							xtype: 'textfield',
							name: 'loanMinAmount',
							fieldLabel: 'Loan Min. Amount',
							reference: 'loanType',
							msgTarget: 'side',
							allowBlank: false,
							blankText: 'Loan Min.  is Mandatory!',
						}, */{
							xtype: 'numberfield',
							name: 'loanAmountLimit',
							fieldLabel: 'Loan Max. Amount',
							reference: 'loanAmountLimit',
							msgTarget: 'side',
							allowBlank: false,
							blankText: 'Loan Max.  is Mandatory!',
						}, {
							xtype: 'textarea',
							name: 'loanDescription',
							allowBlank: false,
							fieldLabel: 'Loan Description',
							reference: 'loanDesc',
							msgTarget: 'side',
							blankText: 'Loan Description  is Mandatory!',
						},
						{
							name: 'loanImages',
							fieldLabel: 'Loan Logo',
							reference: 'bankLogo',
							msgTarget: 'side',
							blankText: 'Loan Logo  is Mandatory!',
							xtype: 'filefield',
							anchor: '100%',
							allowBlank: false,
							buttonText: 'Select Image...',
							listeners: {
								change: function (me, value, eOpts) {
									console.log('trigger upload of file:', value);
								}
							}
						}, 



						],
					url: Ext.manifest.api_url+'/country/saveLoanType',
					buttons: [{
							text: 'Save',
							scope: this,
							formBind: true,
							action: 'save',
							handler: function () {
								var formRecord = this.down('form').getForm();
								var me = this;
								if (formRecord.isValid()) {
									formRecord.submit({
										waitMsg: 'Saving Loan Detail...',
										success: function (form, o) {
											me.close();
											Ext.Msg.show({
												title: 'Result',
												msg: Ext.decode(o.response.responseText).message,
												buttons: Ext.Msg.OK,
												icon: Ext.Msg.INFO,
												fn: function (btn) {
													btn.close();
												}
											});
										},
										failure: function (form, action) {
											console.log('failure-----------------------');
											console.log(action.response.responseText);
											Ext.MessageBox.show({
												title: 'Error',
												msg: Ext.decode(action.response.responseText).message,
												buttons: Ext.Msg.OK,
												icon: Ext.MessageBox.ERROR
											});
										}
									});
								}
							}
						}, '->', {
							text: 'Reset',
							action: 'cancel',
							scope: this,
							handler: function () {
								this.down('form').getForm().reset();
							}
						}
					]
				}
			]
		});
		this.callParent();
	}
});