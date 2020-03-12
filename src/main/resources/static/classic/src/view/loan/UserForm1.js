Ext.define('MyApp.view.bank.UserForm1', {
	extend: 'Ext.window.Window',
	xtype: 'bankForm',
	autoDestroy: false,
	//layout: 'anchor',
	//labelAlign: 'top',
	modal: true,
	closeToolText: null,
	reference: 'bankForm',
	
	height: 500,
	width: 600,
	controller: 'loanAppliedController',
	initComponent: function () {
		Ext.apply(this, {
			closable: true,
			autoShow: true,
		//	resizable: true,
			//monitorResize: true,
			title: 'User Details',
			items: [{
					xtype: 'panel',
					bodyPadding: 2,
					items: [{
							xtype: 'form',
										//id: 'form',
							layout: {
										type: 'hbox',
										//columns: 3
									},
							items: [{
									xtype: 'fieldset',
									title: 'Details',
									collapsible: true,
									width: 300,
									items: [{
											xtype: 'fieldcontainer',
											layout: {
												type: 'vbox',
											},
											//width: 300,
											fieldDefaults: {},
											items: [{
													xtype: 'displayfield',
													//width: 60,
													//id: 'userId',
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
												}, 
												{
													xtype: 'displayfield',
													name: 'emailId',
													fieldLabel: 'Email',
													reference: 'email',
													msgTarget: 'side',
													bind: '{selection.emailId}',
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
													name: 'phoneNumber',
													fieldLabel: 'Phone No.',
													reference: 'phoneNumber',
													msgTarget: 'side',
													bind: '{selection.phoneNumber}',
													readOnly: true
												},  {
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
												}, ],
										}
									]
								}, 
								
								{
									xtype: 'fieldset',
									title: 'Social',
									collapsible: true,
									width: 300,
									items: [{
											xtype: 'fieldcontainer',
										//	title: 'Social',
										layout: {
												type: 'vbox',
											},
											items: [{
													xtype: 'displayfield',
													fieldLabel: 'Email',
													name: 'email',
													//id:'email',
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
									}]
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
