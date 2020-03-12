Ext.define('MyApp.model.LoanAppliedUser', {
	extend: 'Ext.data.Model',
	fields: [{
			name: 'firstName',
			type: 'string',mapping:'first_name'
		}, {
			name: 'lastName',
			type: 'string'
		}, {
			name: 'createdDate',
			type: 'date',mapping: 'createdDate'
		}, {
			name: 'loanRefrenceNumber',
		//	type: 'date'//country
		}, {
			name: 'bankId',
			type: 'number'
		}, {
			name: 'countryId',
			type: 'number'
		}, {
			name: 'loanRef',
			type: 'number'
		}, {
			name: 'loanId',
			type: 'number'
		},{
			name: 'loanStatus',
			type: 'number'
		},  
		
		//user
	
		],
		idProperty: 'loanId',
		// hasOne: [{ model: 'MyApp.model.UserModel', name: 'user' }],
		 associations : [
        {
            type           : 'hasOne',
            model          : 'MyApp.model.UserModel',
            getterName     : 'getUser',
            associatedName : 'user',
            associationKey : 'user'
        },
		{
            type           : 'hasOne',
            model          : 'MyApp.model.DocumentDetails',
            getterName     : 'getDocument',
            associatedName : 'doc',
            associationKey : 'doc'
        },
		]
});

 