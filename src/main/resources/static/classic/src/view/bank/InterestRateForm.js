Ext.define('MyApp.view.bank.InterestRateForm', {
	extend: 'Ext.window.Window',
	xtype: 'rateForm',
	autoDestroy: false,
	initComponent: function () {
		//var bankStore = new MyApp.store.BankStore({});
		//var loanTypeStore = new MyApp.store.LoanTypeStore({});
		
		Ext.apply(this, {
			bodyPadding: 10,
			title: 'Add Interest Rate',
			closable: true,
			autoShow: true,
			modal: true,
			closeToolText: null,
			items: [{
					xtype: 'form',
					items: [{
							name: 'country',
							fieldLabel: 'Country',
							xtype: 'combobox',
							maxHeight: 150,
							emptyText: "Select Country",
							store: 'countryName',
							displayField: 'countryName',
							valueField: 'isoCode',
							forceSelection: false,
							editable: false,
							queryMode: 'local',
							triggerAction: 'all',
							allowBlank: false,
							reference: 'country',
							msgTarget: 'side',
							blankText: 'Country  is Mandatory!',
							listeners: {
								select: function (combo, record, eOpts) {
									var selVal = this.getValue();
									console.log(selVal);
									bankStore.load({
										url: Ext.manifest.api_url+'/country/' + selVal + "/bank",
										method: 'POST',
										params: {
											'id': selVal
										}
									});
								}
							}
						}, {
							name: 'bankId',
							fieldLabel: 'Bank',
							xtype: 'combobox',
							maxHeight: 150,
							emptyText: "Select Bank",
							//store: bankStore,
							//store:'bank',
							store:  Ext.create('MyApp.store.BankStore'),
							displayField: 'name',
							valueField: 'id',//id:'bank',
							forceSelection: false,
							editable: false,
							queryMode: 'local',
							triggerAction: 'all',
							allowBlank: false,
							reference: 'bank',
							msgTarget: 'side',
							blankText: 'Bank  is Mandatory!',
							listeners: {
								select: function (combo, record, eOpts) {
									var selVal = this.getValue();
									console.log(this.findParentByType('window').down('form').getForm().findField('country').getValue());
									var country = this.findParentByType('window').down('form').getForm().findField('country').getValue();
									loanTypeStore.load({
										url: Ext.manifest.api_url+'country/' + country + "/bank/"+selVal +"/loanType",
										method: 'POST',
										params: {
											'id': selVal
										}
									});
								}
							}
						}, {
							//xtype: 'textfield',
							name: 'loanType',
							fieldLabel: 'Loan Type',
							reference: 'loanType',
							msgTarget: 'side',
							blankText: 'Loan Type  is Mandatory!',
							//name: 'bank',
							//fieldLabel: 'Bank',
							xtype: 'combobox',
							maxHeight: 150,
							emptyText: "Select Loan Type",
							//store: loanTypeStore,
							//store:'loanType',
							store:  Ext.create('MyApp.store.BankStore'),
							displayField: 'loanName',
							valueField: 'id',
							forceSelection: false,
							editable: false,
							queryMode: 'local',
							triggerAction: 'all',
							allowBlank: false,
							//reference: 'bank',
							//msgTarget: 'side',
							//blankText: 'Bank  is Mandatory!',
							listeners: {
								beforequery: function (queryPlan, eOpt) {
									/*this.store.getProxy().extraParams = {
										status: 2
									};*/
								}
							}
						}, {
							xtype: 'textfield',
							name: 'rate',
							fieldLabel: 'Interest Rate',
							reference: 'rate',
							msgTarget: 'side',
							blankText: 'Interest Rate  is Mandatory!',
						}, {
							xtype: 'textfield',
							name: 'processingFee',
							fieldLabel: 'Processing Fees',
							reference: 'processingFee',
							msgTarget: 'side',
							blankText: 'Processing Fees  is Mandatory!',
						}, ],
						url: Ext.manifest.api_url+'/country/saveRate',
					buttons: [{
							text: 'Save',
							scope: this,
							formBind: true,
							action: 'save',
							/*listeners: {
								click: 'submit'
							}*/
							handler: function () {
								var formRecord  = this.down('form').getForm();
								var me  = this;
								if(formRecord.isValid()){
								formRecord.submit({
								//	submitEmptyText: false,
                                   //   standardSubmit: false,
                                      //fileUpload: false,
									  waitMsg:'Saving Interest Rate Detail...',
									success: function (form, o) {
										//Ext.Msg.alert('Success', " Bank Details Successfully saved.");
										//console.log('88888888888888888888888888888888888');
										me.close();
										
										Ext.Msg.show({
													title:'Result',
													msg: Ext.decode(o.response.responseText).message,//o.response.responseText,//message
													buttons:Ext.Msg.OK,
													icon:Ext.Msg.INFO,
													fn: function(btn) {
														
														btn.close();
													}
												});
									},
									failure: function (form, action) {
										console.log('failure-----------------------');
										Ext.MessageBox.show({
											title: 'Error',
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
							//handler: this.close,
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
