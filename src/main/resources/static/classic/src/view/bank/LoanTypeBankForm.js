Ext.define('MyApp.view.bank.LoanTypeBankForm', {
	extend: 'Ext.window.Window',
	xtype: 'loanTypeBankForm',
	autoDestroy: false,
	initComponent: function () {
		var loanTypeStore = new MyApp.store.LoanTypeStore({});
		var bankStore = new MyApp.store.BankStore({});
		Ext.apply(this, {
			bodyPadding: 10,
			title: 'Link Loan Type To Bank',
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
							store: 'country',
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
									//console.log('sssssss666sssssss7777777777777777777777777777');
									var selVal = this.getValue();
									console.log(selVal);
									bankStore.load({
										url: '/paychec/country/' + selVal + "/bank",
										method: 'POST',
										params: {
											'id': selVal
										}
									});
								}
							}
						}, {
							name: 'bank',
							fieldLabel: 'Bank',
							xtype: 'combobox',
							maxHeight: 150,
							emptyText: "Select Bank",
							store: bankStore,
							displayField: 'name',
							valueField: 'id',
							forceSelection: false,
							editable: false,
							queryMode: 'local',
							triggerAction: 'all',
							allowBlank: false,
							reference: 'bank',
							msgTarget: 'side',
							blankText: 'Bank  is Mandatory!',
							listeners: {
								beforequery: function (queryPlan, eOpt) {
									/*this.store.getProxy().extraParams = {
										status: 2
									};*/
								}
							}
						},

					/*	{
							xtype: 'textfield',
							name: 'loanType',
							fieldLabel: 'Loan Type',
							reference: 'loanType',
							msgTarget: 'side',
							blankText: 'Loan Type  is Mandatory!',

						}, */
						
						{
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
							store: loanTypeStore,
							//store:'loanType',
							//store:  Ext.create('MyApp.store.BankStore'),
							displayField: 'loanName',
							valueField: 'id',
							forceSelection: false,
							editable: false,
							queryMode: 'local',
							triggerAction: 'all',
							allowBlank: false, 
							listeners: {
								beforequery: function (queryPlan, eOpt) {
									/*this.store.getProxy().extraParams = {
										status: 2
									};*/
								}
							}
						},
						
						],
							url: Ext.manifest.api_url+'/country/linkToBank',
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
									  waitMsg:'Saving Loan Detail...',
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
