Ext.define('MyApp.view.bank.UserForm', {
	extend: 'Ext.window.Window',
	xtype: 'bankForm',
	autoDestroy: false,
	style: 'margin-top:10px;',
	//layout: 'hbox',
	layout: 'anchor',
	// resizable: true,
	labelAlign: 'top',
	modal: true,
	closeToolText: null,
	reference: 'bankForm',
	defaults: {
		labelWidth: 120,
		style: 'margin:10px;',
	},
	height: 400,
	//width: 890,
	controller :'loanAppliedController',
	initComponent: function () {
		Ext.apply(this, {
			closable: true,
			autoShow: true,
			resizable: false,
			 monitorResize:true,
			title: 'User Details',
			items: [{
					xtype: 'tabpanel',
					activeTab: 0,
					width: 885,
					items: [{
							title: 'User Profile',
							bodyPadding: 5,
							items: [{
									xtype: 'form',
									id: 'form',
									layout: {
										type: 'table',
										columns: 2
									},
									bind: {},
									defaults: {
										style: 'padding: 0 0 0 5px;',
									},
									fieldDefaults: {},
									items: [{
											xtype: 'displayfield',
											id: 'userId',
											name: 'userId',
											reference: 'userId',
											hidden: true,
											bind: '{selection.id}',
										}, {
											xtype: 'displayfield',
											name: 'firstName',
											fieldLabel: 'First Name',
											reference: 'firstName',
											msgTarget: 'side',
											//blankText: 'Bank Name  is Mandatory!',
											bind: '{selection.firstName}',
											readOnly: true
										}, {
											xtype: 'displayfield',
											name: 'lastName',
											fieldLabel: 'Last Name',
											reference: 'lastName',
											msgTarget: 'side',
											bind: '{selection.lastName}',
											readOnly: true
										}, {
											xtype: 'displayfield',
											name: 'birthDate',
											fieldLabel: 'DOB',
											reference: 'birthDate',
											msgTarget: 'side',
											bind: '{selection.birthDate}',
											readOnly: true,
										}, {
											xtype: 'displayfield',
											name: 'emailId',
											fieldLabel: 'Email',
											reference: 'email',
											msgTarget: 'side',
											bind: '{selection.emailId}',
											readOnly: true
										}, {
											xtype: 'displayfield',
											name: 'phoneNumber',
											fieldLabel: 'Phone No.',
											reference: 'phoneNumber',
											msgTarget: 'side',
											bind: '{selection.phoneNumber}',
											readOnly: true
										}, {
											xtype: 'combo',
											name: 'loanStatus',
											fieldLabel: 'Loan Status',
											reference: 'loanStatus',
											store: 'loanstatus',
											displayField: 'loanStaus',
											valueField: 'id',
											forceSelection: false,
											editable: false,
											queryMode: 'local',
											triggerAction: 'all',
											queryMode: 'local',
											bind: {
												value: '{record.loanStatus}'
											},
										}, {
											xtype: 'fieldcontainer',
											fieldLabel: 'Profile Pic',
											items: [{
													fieldLabel: 'Profile Pic.',
													xtype: 'image',
													height: 200,
													width: 175,
													bind: {
														src: '{selection.profilePic}'
													},
													listeners: {
														render: function () {}
													}
												}
											]
										}, /*{
											xtype: 'combo',
											name: 'loanStatus',
											fieldLabel: 'Loan Status',
											reference: 'loanStatus',
											store: 'loanstatus',
											displayField: 'loanStaus',
											valueField: 'id',
											forceSelection: false,
											editable: false,
											queryMode: 'local',
											triggerAction: 'all',
											queryMode: 'local',
											bind: {
												value: '{record.loanStatus}'
											},
										}*/
									],
								}, ]
						}, {
							title: 'Document Details',
							bodyPadding: 5,
							items: [{
									xtype: 'tabpanel',
									height: 300,
									autoscroll: true,
									bodyPadding: 5,
									items: [{
											xtype: 'form',
											url: Ext.manifest.api_url + '/social/document/',
											title: 'Social Info.',
											layout: {
												type: 'vbox',
											},
											items: [{
													xtype: 'displayfield',
													fieldLabel: 'Email',
													name: 'email',
													id:'email',
													bind: {
														value: '{userDoc.imageSource}'
													}
												}, {
													xtype: 'displayfield',
													fieldLabel: 'LinkedIn',
													name: 'linkedIn',
												}, {
													xtype: 'displayfield',
													fieldLabel: 'Facebook',
													name: 'facebook'
												}
											]
										}, {
											xtype: 'panel',
											title: 'Document Info.',
											layout: {
												type: 'hbox',
											},
											items: [{
													xtype: 'container',
													layout: 'vbox',
													items: [{
															xtype: 'combo',
															name: 'kycDoc',
															fieldLabel: 'KYC Docu.',
															reference: 'kyc',
															store: 'kycstore',
															displayField: 'name',
															valueField: 'id',
															forceSelection: false,
															editable: false,
															reference: 'kycDoc',
															queryMode: 'local',
															triggerAction: 'all',
															id: 'kycdoc',
															listeners: {
																beforedeselect: function (combo, record, index, eOpts) {
																	combo.up('container').query('combo')[1].setRawValue();
																},
																select: function (combo, record, eOpts) {
																	combo.up('container').query('combo')[1].setRawValue();
																var userId  = combo.lookupReferenceHolder('bankForm').getView().getViewModel().getData().selection.get('id');
																	Ext.Ajax.request({
																		url: Ext.manifest.api_url + '/kyc/user/'+userId+'/document/' + combo.getValue(),
																		method: 'GET',
																		success: function (result, request) {
																			var res = JSON.parse(result.responseText);
																			var button = combo.up('panel').down('button');
																			button.setHidden(true);
																			combo.up('panel').down('image').setSrc('');
																			if(res.status){
																			combo.up('panel').down('image').setSrc(res.docSource);
																			button.setHidden(false);
																			}
																			
																			
																		},
																		failure: function (result, request) {
																			combo.up('panel').down('image').setSrc('');
																			var button = combo.up('panel').down('button');
																			button.setHidden(true);
																		}
																	});
																}
															}
														}, {
															xtype: 'combo',
															name: 'financeDoc',
															fieldLabel: 'Finance Docu.',
															reference: 'financeDoc',
															store: 'financestore',
															displayField: 'name',
															valueField: 'id',
															forceSelection: false,
															editable: false,
															reference: 'finDoc',
															queryMode: 'local',
															triggerAction: 'all',
															id: 'financeDoc',
															listeners: {
																beforedeselect: function (combo, record, index, eOpts) {
																	combo.up('container').query('combo')[0].setRawValue();
																},
																select: function (combo, record, eOpts) {
																	combo.up('container').query('combo')[0].setRawValue();
																	Ext.Ajax.request({
																		url: Ext.manifest.api_url + '/kyc/document/' + combo.getValue(),
																		method: 'GET',
																		success: function (result, request) {
																			var res = JSON.parse(result.responseText);
																			var button = combo.up('panel').down('button');
																			button.setHidden(true);
																			combo.up('panel').down('image').setSrc('');
																			if(res.status){
																			combo.up('panel').down('image').setSrc(res.docSource);
																			//var button = combo.up('panel').down('button');
																			button.setHidden(false);
																			}
																		},
																		failure: function (result, request) {
																			combo.up('panel').down('image').setSrc('');
																			var button = combo.up('panel').down('button');
																			button.setHidden(true);
																		}
																	});
																}
															}
														}
													]
												}, 
												{
													xtype: 'container',
													layout: 'vbox',
													defaults:{
														style: ' padding-left: 10px;',
													},
													items:[
												{
													xtype: 'fieldcontainer',
												//	style: ' padding-left: 10px;',
													height: 200,
													layout:'vbox',
													items: [{
															xtype: 'image',
															height: 200,
														},
														/*{
															xtype: 'button',
															text:'Download'
															//height: 200,
														}*/
													]
												},
												{
															xtype: 'button',
															text:'Download',
															hidden:true,
															id:'download',
															style: ' margin-left: 10px;',
															//height: 200,
															 handler: 'showDetails',
														/*	 function() {
																var finDoc =  Ext.getCmp('financeDoc').getValue();
																var kycDoc =  Ext.getCmp('kycdoc').getValue();
																const document  = 'document/';
																var URI ;
																if(!Ext.isEmpty(kycDoc)){
																	URI =document+2+'/subDocument/'+kycDoc
																}
																if(!Ext.isEmpty(finDoc)){
																	URI =document+3+'/subDocument/'+finDoc
																}
																console.log(URI);
																Ext.Ajax.request({
																	url: Ext.manifest.api_url +URI,//'/download'+ '/kyc/document/' + combo.getValue(),
																	method: 'GET',
																	contentType: "application/vnd.ms-excel",
																	success: function(response){
																	console.log("Success " + response);
																	},
																	failure: function(response) {
																	console.log("Failed " + response);
																	}
																	});
															}
															*/
														}
														]
												}
											]
										}, ],
								//	id: 'tab',
									listeners: {
										beforerender:'renderSocial'
										/*beforerender: function (me, eOpts) {
											Ext.Ajax.request({
												url: Ext.manifest.api_url + '/social',
												method: 'GET',
												success: function (result, request) {
													var res = JSON.parse(result.responseText);
													console.log(res);
													Ext.Array.each(res.socialList, function (record) {
														if (record.documentSubTypeId == 7) {
															me.down('form').getForm().findField('email').setValue(record.imageSource);
														}
														if (record.documentSubTypeId == 8) {
															me.down('form').getForm().findField('facebook').setValue(record.imageSource);
														}
														if (record.documentSubTypeId == 9) {
															me.down('form').getForm().findField('linkedIn').setValue(record.imageSource);
														}
														console.log(record);
													});
												},
												failure: function (result, request) {}
											});
										}*/
									}
								}
							]
						}
					]
				}
			]
		});
		this.callParent();
	}
});
