Ext.define('MyApp.view.bank.UserForm', {
	extend: 'Ext.window.Window',
	xtype: 'bankForm',
	autoDestroy: false,
	style: 'margin-top:10px;',
	layout: 'anchor',
	labelAlign: 'top',
	modal: true,
	closeToolText: null,
	reference: 'bankForm',
	defaults: {
		labelWidth: 120,
		style: 'margin:10px;',
	},
	autoScroll: true,
	height: 600,
	controller: 'loanAppliedController',
	initComponent: function () {
		Ext.apply(this, {
			closable: true,
			autoShow: true,
			resizable: false,
			monitorResize: true,
			title: 'User Details',
			items: [{
					xtype: 'tabpanel',
					activeTab: 0,
					width: 750,
					items: [{
							title: 'User Profile',
							bodyPadding: 5,
							items: [{
									xtype: 'form',
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
											name: 'userId',
											reference: 'userId',
											id:'kk',
											hidden: true,
											//bind: '{selection.id}',
											bind:{
												value:'{selection.id}',
											}
										}, {
											xtype: 'displayfield',
											name: 'firstName',
											fieldLabel: 'First Name',
											reference: 'firstName',
											msgTarget: 'side',
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
											fieldLabel: 'Current Loan Status',
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
											readOnly: true,
											listeners: {
												select: function (me) {
												//	console.log(me.findParentByType('form').down('textareafield'));
												//	me.findParentByType('form').down('textareafield').hide(false);
													
												}
											}
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
										}, {
											xtype: 'textareafield',
											id:'text',
											grow: true,
											name: 'message',
											fieldLabel: 'Message',
											anchor: '100%',
											hidden: true
										}
									],
									
									
									
									
								},
								{
											xtype: 'fieldcontainer',
										//	fieldLabel: 'Status',
											layout:'vbox',
											reference:'message',
											items: [
									{
											xtype: 'textareafield',
										//	id:'text',
											grow: true,
											width:450,
											name: 'message',
											fieldLabel: 'Message',
											anchor: '100%',
											//hidden: true
										},
										{
											xtype: 'combo',
											name: 'loanStatus',
											fieldLabel:'Loan Status',
											reference: 'loanStatus',
											store: 'loanstatus',
											displayField: 'loanStaus',
											valueField: 'id',
											forceSelection: false,
											editable: false,
											queryMode: 'local',
											triggerAction: 'all',
											queryMode: 'local',
										},
										{
											xtype: 'button',
											text:'Submit',
											listeners: {
														click: 'submit'
															
															
															
														//}
													}
											
										}
										]
									}
									




								]
						}, {
							title: 'Document Details',
							bodyPadding: 2,
							items: [{
									xtype: 'panel',
									autoscroll: true,
									bodyPadding: 2,
									items: [{
											xtype: 'panel',
											reference: 'docDetail',
											layout: {
												type: 'vbox',
											},
											autoscroll: true,
										}, ],
									listeners: {
										beforerender: 'renderSocial'
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
