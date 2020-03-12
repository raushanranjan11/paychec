Ext.define('MyApp.view.bank.InterestRateViewModel', {
	extend: 'Ext.app.ViewModel',
	alias: 'viewmodel.interestRate',
	stores: {
		interestRatesList: {
			model: 'MyApp.model.InterestModel',
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
					property: 'bankName',
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
				//url: '/paychec/country/allRate',
				url: Ext.manifest.api_url+'/country/allRate',
				enablePaging: true,
				simpleSortMode: true,
				reader: {
					type: 'json',
					rootProperty: 'rateList',
					successProperty: 'status',
					totalProperty: 'total'
				}
			}
		}
	}
});
