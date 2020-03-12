Ext.define('MyApp.model.UserModel', {
	extend: 'Ext.data.Model',
	fields: [{
			name: 'firstName',
			type: 'string'
		}, {
			name: 'lastName',
			type: 'string'
		}, {
			name: 'dob',
			type: 'date',mapping: 'birthDate'
		}, {
			name: 'phoneNumber',
		//	type: 'date'//country
		}, {
			name: 'profilePic',
			type: 'string'
		}, {
			name: 'processingFee',
			type: 'string'
		}, 
		{
			name: 'id',
			type: 'int'
		}, 
	
		],
		idProperty: 'id',
		 associations : [
        {
            type           : 'belongsTo',
            model          : 'MyApp.model.LoanAppliedUser',
            getterName     : 'getLoanApply',
            associationKey : 'LoanAppliedUser'
        },
        
    ]
});

 