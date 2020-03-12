Ext.define('MyApp.view.bank.BankForm', {
	extend: 'Ext.window.Window',
	xtype: 'bankForm',
	autoDestroy: false,
	style: 'margin-top:10px;',
	layout: 'hbox',
	labelAlign: 'top',
	reference: 'bankForm',
	defaults: {
		labelWidth: 120,
		style: 'margin:10px;',
	},
	modal: true,
	closeToolText: null,
	initComponent: function () {
		Ext.apply(this, {
			bodyPadding: 10,
			title: 'Add Bank',
			closable: true,
			autoShow: true,
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
									console.log('kkkkkkkkkkkkkkkkkkkk');
								}
							}
						}, {
							xtype: 'textfield',
							name: 'bankName',
							fieldLabel: 'Bank Name',
							reference: 'bankName',
							msgTarget: 'side',
							blankText: 'Bank Name  is Mandatory!',
						}, {
							name: 'bankLogo',
							fieldLabel: 'Bank Logo',
							reference: 'bankLogo',
							msgTarget: 'side',
							blankText: 'Bank Logo  is Mandatory!',
							xtype: 'filefield',
							anchor: '100%',
							buttonText: 'Select Image...',
							listeners: {
								change: function (me, value, eOpts) {
									console.log('trigger upload of file:', value);
								}
							}
						}, ],
					url: Ext.manifest.api_url+'/country/saveBank',
					buttons: [{
							text: 'Save',
							scope: this,
							action: 'save',
							formBind: true,
							handler: function () {
								var formRecord  = this.down('form').getForm();
								var me  = this;
								if(formRecord.isValid()){
								formRecord.submit({
								//	submitEmptyText: false,
                                   //   standardSubmit: false,
                                      //fileUpload: false,
									  waitMsg:'Saving Bank Detail...',
									success: function (form, o) {
										//Ext.Msg.alert('Success', " Bank Details Successfully saved.");
										console.log('88888888888888888888888888888888888');
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
							id: 'cancle',
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
