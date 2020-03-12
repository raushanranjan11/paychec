Ext.define('MyApp.model.DocumentType', {
	extend: 'Ext.data.Model',
	fields: [
	
	'id','name','location'
		
	
		],
		idProperty: 'id',
	 	 associations : [
      /*  {
            type           : 'belongsTo',
            model          : 'MyApp.model.DocumentSubType',
            getterName     : 'getDocumentSubType',
            associationKey : 'DocumentSubType'
        },*/
		 
         {
            type           : 'hasOne',
            model          : 'MyApp.model.DocumentSubType',
            getterName     : 'getDocumentSubType',
            associatedName : 'subType',
            associationKey : 'subType'
        },
    ] 
	
});
