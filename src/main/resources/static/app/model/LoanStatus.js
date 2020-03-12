Ext.define('MyApp.model.LoanStatus', {
	extend: 'Ext.data.Model',
	fields: [{
			name: 'id',
			//type: 'int',
			//mapping:'id'
		}, {
			name: 'loanStaus',
			type: 'string'//,mapping:''
		}
	
		],
		//idProperty: 'id',
});
