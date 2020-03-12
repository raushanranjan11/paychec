Ext.define('MyApp.model.DocumentDetails', {
	extend: 'Ext.data.Model',
	fields: [{
			name: 'id',
			//type: 'int',
			//mapping:'id'
		}, {
			name: 'imageSource',
			type: 'string'//,mapping:''
		}
	
		],
		idProperty: 'id',
		 associations : [
        {
            type           : 'belongsTo',
            model          : 'MyApp.model.LoanAppliedUser',
            getterName     : 'getLoanApply1',
            associationKey : 'LoanAppliedUser'
        },
        
    ]
});
