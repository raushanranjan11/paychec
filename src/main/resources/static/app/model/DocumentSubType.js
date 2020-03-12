Ext.define('MyApp.model.DocumentSubType', {
	extend: 'Ext.data.Model',
	fields: [
	
	'id','name','location'
		
	
		],
		idProperty: 'id',
	 	 associations : [
        {
            type           : 'belongsTo',
            model          : 'MyApp.model.DocumentType',
            getterName     : 'getDocumentType',
            associationKey : 'subType'
        },
		 
        
    ] 
});
