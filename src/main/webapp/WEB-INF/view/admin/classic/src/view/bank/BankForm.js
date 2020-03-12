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
	initComponent: function () {
		Ext.apply(this, {
			bodyPadding : 10,
			title : 'Add Bank',
			closable : true,
			autoShow : true,
		
		
		
		items : [{
				xtype: 'form',
				items: [
					{
					/*	name: 'country',
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
						listeners: {}*/
						xtype: 'textfield',
						name: 'countryName',
						fieldLabel: 'Country',
						reference: 'countryName',
						msgTarget: 'side',
						blankText: 'Bank Name  is Mandatory!',
					}, {
						xtype: 'textfield',
						name: 'bankName',
						fieldLabel: 'Bank Name',
						reference: 'bankName',
						msgTarget: 'side',
						blankText: 'Bank Name  is Mandatory!',
					}, {
						//xtype: 'textfield',
						name: 'bankLogo',
						fieldLabel: 'Bank Logo',
						reference: 'bankLogo',
						msgTarget: 'side',
						blankText: 'Bank Logo  is Mandatory!',

						xtype: 'filefield',
						// name: 'photo',
						// fieldLabel: 'Photo',
						// labelWidth: 50,
						// msgTarget: 'side',
						//allowBlank: false,
						anchor: '100%',
						buttonText: 'Select Image...',
						listeners: {
							change: function (me, value, eOpts) {
								console.log('trigger upload of file:', value);

								// var form = this.up('form').getForm();
								// if (form.isValid()) {
								//     form.submit({
								//         url: 'photo-upload.php',
								//         waitMsg: 'Uploading your photo...',
								//         success: function (fp, o) {
								//             Ext.Msg.alert('Success', 'Your photo "' + o.result.file + '" has been uploaded.');
								//         }
								//     });
								// }
							}
						}
					},
				],
				 url: 'http://localhost:8090/paychec/country/saveBank',
				buttons: [{
						text: 'Save',
						scope: this,
						//formBind: true,
						action: 'save',
						/*listeners: {
							click: 'submit'
						}*/
						handler:function(){
							
					//		var form = this.down('form'), // get the form panel
                   //  record = form.getForm().getRecord();
					//console.log(record);
							this.down('form').getForm().submit({
                /*   url: 'http://localhost:8090/paychec/country/saveBank',
                   method: 'POST',
                   params: {
                   //  response: hex_md5(Ext.getCmp('challenge').getValue()+hex_md5(Ext.getCmp('password').getValue()))
                   },*/
                   success: function(){
					   console.log('88888888888888888888888888888888888');
                   //  window.location = 'tabs-adv.html';
                   },
                   failure: function(form, action){
					    console.log('failure-----------------------');
                     Ext.MessageBox.show({
                       title: 'Error',
                     //  msg: action.result.message,
                       buttons: Ext.Msg.OK,
                       icon: Ext.MessageBox.ERROR
                     });
                   }
                 });
							
							
						}
					}, '->', {
						text: 'Reset',
						action: 'cancel',
						scope: this,id:'cancle',
						//handler: this.close,
						handler:function(){
						//	this.up('form').getForm().reset();
							//this.up('window').down('form').getForm().reset();
							this.down('form').getForm().reset();
						}
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