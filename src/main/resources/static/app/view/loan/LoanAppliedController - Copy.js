/**
 * 
 */

Ext.define('MyApp.view.loan.LoanAppliedController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.loanAppliedController',
	//alias: 'controller.menucontroller',

		  init: function() {
              //  var viewModel = this.getViewModel();
             //   this.getViewForm().loadRecord(viewModel.get('currentRecord'));
            },
            getViewForm: function() {
                return this.lookupReference('bankForm');
            },
			
			onAdd: function () {
					var grid = this.lookupReference('loanGrid');
				},
				
				showDetails:function(){
				//	console.log(this.lookupReference('userId'));
					console.log(this.getViewModel().data);
				//	console.log(this.getViewModel().data.selection.get('id'));
					var userId = this.getViewModel().data.selection.get('id');
					var finDoc =  Ext.getCmp('financeDoc').getValue();
																var kycDoc =  Ext.getCmp('kycdoc').getValue();
																const document  = '/users/'+userId+'/document/';
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
																	//contentType: "application/vnd.ms-excel",
																	success: function(conn, response, options, eOpts){
																		console.log(conn.request.url);
																	
																		window.location.href = conn.request.url;
																	 
							
							
				
																	},
																	failure: function(response) {
																	console.log("Failed " + response);
																	}
																	});
					
				},
				renderSocial:function(){
					console.log('controller');
					var me = this.getView().down('tabpanel').down('tabpanel');
					//console.log(me.down('tabpanel').down('tabpanel').down('form'));
					var userId = this.getViewModel().data.selection.get('id');
					Ext.Ajax.request({
												//url: Ext.manifest.api_url + '/users/'+30+'/social',
												url: Ext.manifest.api_url+ "/users/9/document",
												method: 'GET',
												success: function (result, request) {
													var res = JSON.parse(result.responseText);
													console.log(res);
												var form = 	Ext.getCmp('form');
													Ext.Array.each(res.documentList, function (record) {
														console.log(record.docSubName)
														console.log(record.source)
														if (record.id == 1) {
															console.log('******************88');
														form.add(
													 
															{
															 xtype:'fieldset',
															title:record.name,
															listeners:{
																beforerender :function(me){
																	 console.log(record.documentSubTypeBean);
																	Ext.Array.each(record.documentSubTypeBean, function (rec) {
																		console.log('***');
															 me.add({
																		xtype:'displayfield',
																		fieldLabel:rec.name,
																		value:rec.value,
																		hidden:rec.value ==null ? true:false
															 })
																	});
															}
																}
															}
															
															)
														}
														if (record.id == 2) {
															console.log('******************88');
														form.add(
													 
															{
															 xtype:'fieldset',
															title:record.name,
															layout:'hbox',
															listeners:{
																beforerender :function(me){
																	 console.log(record.documentSubTypeBean);
																	Ext.Array.each(record.documentSubTypeBean, function (rec) {
																		console.log('***');
															 me.add({
																 
																 xtype: 'fieldcontainer',
											fieldLabel: rec.name,
											labelAlign:'top',
											labelSeparator : '',
											labelStyle:'text-align:center',
											hidden:rec.value ==null ? true:false,
											layout:'vbox',
											items: [{
																		xtype:'image',
																		height: 200,
																		width: 175,
																		fieldLabel:rec.name,
																		labelAlign:'center',
																		src:rec.value,
																	//	bodyPadding:5,
																		style:'padding:5px;',
																		hidden:rec.value ==null ? true:false
															

											},
											{
															xtype: 'button',
															text: 'Download',
														//	hidden: true,
														//	id: 'download',
															style: ' margin-left: 10px;',
															handler: 'showDetails',
														}
											
											
											]
											})
																	});
															}
																}
															}
															
															)
														}
													
/*

													if (record.documentSubTypeId == 7) {
															console.log('^^^^^^^^^^^');
															console.log(me);
															console.log(me.down('form').getForm().findField('email'));
														//	me.down('form').getForm().findField('email').hide(false);
															me.down('form').getForm().findField('email').setValue(record.imageSource);
														}else{
														//	me.down('form').getForm().findField('email').hide();
														}
														if (record.documentSubTypeId == 8) {
															console.log('^^^^^^       ^^^^^');
														//	me.down('form').getForm().findField('facebook').hide(false);
															me.down('form').getForm().findField('facebook').setValue(record.imageSource);
															//hidden: '{!record.date_begin}'
														}else{
															//	me.down('form').getForm().findField('facebook').hide();
														}
														if (record.documentSubTypeId == 9) {
															console.log('^^^^^^       ^^^^^');
														//	me.down('form').getForm().findField('linkedIn').hide(false);
															me.down('form').getForm().findField('linkedIn').setValue(record.imageSource);
														}else{
															//me.down('form').getForm().findField('linkedIn').hide();
														}
														console.log(record.documentSubTypeId == 7);
														
														*/
													});
												},
												failure: function (result, request) {}
											});
									//	}
				}
});