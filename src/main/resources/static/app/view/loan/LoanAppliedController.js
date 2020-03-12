Ext.define('MyApp.view.loan.LoanAppliedController', {
	extend: 'Ext.app.ViewController',
	alias: 'controller.loanAppliedController',
	init: function () {},
	getViewForm: function () {
		return this.lookupReference('bankForm');
	},
	onAdd: function () {
		var grid = this.lookupReference('loanGrid');
	},
	showDetails: function (me) {
		var URL = me.findParentByType('fieldcontainer').down('image').getSrc();
		 
		var docType = me.findParentByType('fieldcontainer').config.docType;
		var docsubType = me.findParentByType('fieldcontainer').down('image').config.docsubType
			var userId = this.getViewModel().data.selection.get('id');
		const documents = '/users/' + userId + '/documents/';
		var URI;
		URI = documents + docType + '/subDocument/' + docsubType
			//console.log(URI);
		Ext.Ajax.request({
			url: Ext.manifest.api_url + URI,
			method: 'GET',
			success: function (conn, response, options, eOpts) {
				//console.log(conn.request.url);
				window.location.href = conn.request.url;
			},
			failure: function (response) {
				console.log("Failed " + response);
			}
		});
	},
	renderSocial: function () {
	//	console.log('controller');
	//	console.log(this.lookupReference('docDetail'));
		var docDetail = this.lookupReference('docDetail');
		var userId = this.getViewModel().data.selection.get('id');
		Ext.Ajax.request({
			url: Ext.manifest.api_url + "/users/"+userId+"/document",
			//url: Ext.manifest.api_url + "/users/9/document",
			method: 'GET',
			success: function (result, request) {
				var res = JSON.parse(result.responseText);
			//	console.log(res);
				//var form = Ext.getCmp('form');
				//var docDetail = this.lookupReference('docDetail');
				Ext.Array.each(res.documentList, function (record) {
				//	console.log(record.docSubName)
				//	console.log(record.source)
					if (record.id == 1) {
						docDetail.add({
							xtype: 'fieldset',
							title: record.name,
							listeners: {
								beforerender: function (me) {
									console.log(record.documentSubTypeBean);
									Ext.Array.each(record.documentSubTypeBean, function (rec) {
										me.add({
											xtype: 'displayfield',
											fieldLabel: rec.name,
											value: rec.value,
											hidden: rec.value == null ? true : false
										})
									});
								}
							}
						})
					}
					if (record.id == 2) {
						docDetail.add({
							xtype: 'fieldset',
							title: record.name,
							layout: 'hbox',
							listeners: {
								beforerender: function (me) {
								//	console.log(record.documentSubTypeBean);
									Ext.Array.each(record.documentSubTypeBean, function (rec) {
										me.add({
											xtype: 'fieldcontainer',
											fieldLabel: rec.name,
											labelAlign: 'top',
											labelSeparator: '',
											labelStyle: 'text-align:center',
											hidden: rec.value == null ? true : false,
											layout: 'vbox',
											height: 275,
											config: {
												docType: record.id
											},
											items: [{
													xtype: 'image',
													config: {
														docsubType: rec.id
													},
													height: 200,
													width: 175,
													fieldLabel: rec.name,
													labelAlign: 'center',
													src: rec.value,
													style: 'padding:5px;',
													hidden: rec.value == null ? true : false
												}, {
													xtype: 'button',
													text: 'Download',
													style: ' margin-left: 10px;',
													handler: 'showDetails',
												}
											]
										})
									});
								}
							}
						})
					}
					if (record.id == 3) {
						docDetail.add({
							xtype: 'fieldset',
							title: record.name,
							layout: 'hbox',
							autoHeight: true,
							listeners: {
								beforerender: function (me) {
							//		console.log(record.documentSubTypeBean);
									Ext.Array.each(record.documentSubTypeBean, function (rec) {
										me.add({
											xtype: 'fieldcontainer',
											fieldLabel: rec.name,
											labelAlign: 'top',
											labelSeparator: '',
											labelStyle: 'text-align:center',
											hidden: rec.value == null ? true : false,
											layout: 'vbox',
											height: 275,
											items: [{
													xtype: 'image',
													height: 200,
													width: 175,
													fieldLabel: rec.name,
													labelAlign: 'center',
													src: rec.value,
													style: 'padding:5px;',
													hidden: rec.value == null ? true : false
												}, {
													xtype: 'button',
													text: 'Download',
													style: ' margin-left: 10px;',
													handler: 'showDetails',
												}
											]
										})
									});
								}
							}
						})
					}
				});
			},
			failure: function (result, request) {}
		});
	},
	submit: function (me) {
		//var userId = this.lookupReference('bankForm').getView().down('form').getForm().findField('userId').getValue()
		//console.log(this.lookupReference('message').down('textareafield'));
		console.log(me.up('bankForm'));
		var messageText  = this.lookupReference('message').down('textareafield').getValue();
		var loanstatus  = this.lookupReference('message').down('combo').getValue();
		var docDetail = this.lookupReference('docDetail');
		var userId = this.getViewModel().data.selection.get('id');
		var loanId  = this.getViewModel().data.record.get('loanId')
		console.log(this.getViewModel().data.record.get('loanId'));
		Ext.Ajax.request({
			url: Ext.manifest.api_url + "/users/"+userId+"/loan/"+loanId+"/status",
			method: 'POST',
			jsonData:{
				messageText:messageText,
				loanstatus:loanstatus
			},
			success: function (result, request) {
				var res = JSON.parse(result.responseText);
				me.up('bankForm').close()
			},
			failure: function (result, request) {
				
				
			}
		});
		
	},
});
