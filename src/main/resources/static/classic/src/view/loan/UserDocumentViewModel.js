Ext.define('MyApp.view.loan.UserDocumentViewModel', {
	extend: 'Ext.app.ViewModel',
	alias: 'viewmodel.userDocumentDetails',
	stores: {
		userDocumentDetails: {
			model: 'MyApp.model.DocumentType',
			autoLoad: true,
			 
			proxy: {
				type: 'rest',
				 
				//url: Ext.manifest.api_url+ '/loanAppliedUser',
				url: Ext.manifest.api_url+ "/users/30/document",
				//enablePaging: true,
				simpleSortMode: true,
				reader: {
					type: 'json',
					rootProperty: 'documentList',
					successProperty: 'status',
				//	totalProperty: 'total'
				}
			}
		}
	}
});
