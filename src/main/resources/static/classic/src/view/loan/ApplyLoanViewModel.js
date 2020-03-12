Ext.define('MyApp.view.loan.ApplyLoanViewModel', {
	extend: 'Ext.app.ViewModel',
	alias: 'viewmodel.appliedLoanUser',
	stores: {
		appliedLoanUser: {
			model: 'MyApp.model.LoanAppliedUser',
			autoSync: true,
			pageSize: 10,
			remoteSort: true,
			remoteFilter: true,
			autoLoad: true,
		/*	 autoLoad: {
        start: 0,
        limit: 2
    },*/
			sorters: [{
					property: 'createdDate',
					direction: 'ASC'
				}
			],
			filters: [{
					property: 'createdDate',
					value: ''
				}
			],
			proxy: {
				type: 'rest',
				 
				//url: Ext.manifest.api_url+ '/loanAppliedUser',
				url: Ext.manifest.api_url+ '/appliedLoan',
				enablePaging: true,
				simpleSortMode: true,
				reader: {
					type: 'json',
					rootProperty: 'loanAppliedUser',
					successProperty: 'status',
					totalProperty: 'total'
				}
			}
		}
	}
});
