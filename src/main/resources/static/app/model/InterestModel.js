Ext.define('MyApp.model.InterestModel', {
	extend: 'Ext.data.Model',
	fields: [{
			name: 'loanTypeName',
			type: 'string'
		}, {
			name: 'bankName',
			type: 'string'
		}, {
			name: 'countryName',
			type: 'string'
		}, {
			name: 'createdDate',
		//	type: 'date'
		}, {
			name: 'rate',
			type: 'string'
		}, {
			name: 'processingFee',
			type: 'string'
		}, 
	
		]
});
