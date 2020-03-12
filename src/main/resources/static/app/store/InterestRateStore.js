

Ext.define('MyApp.store.InterestRateStore', {
	extend: 'Ext.data.Store',

	alias: 'store.rate',

	constructor: function (cfg) {
		var me = this;
		cfg = cfg || {};
		me.callParent([Ext.apply({
					storeId: 'rate',
					autoSync: true,
					pageSize: 1,
					remoteSort: true, //For Remote Sorting
					remoteFilter: true,//For Remote Filtering 
					autoLoad: true,
					//buffered: true,
					// pageSize: 10,
   /*  autoLoad: {
        start: 0,
        limit: 1
    }, */
	// Parameter name to send filtering information in
            //filterParam: 'query',
			
					sorters: [
					{
							property : 'bankName',
							direction: 'asc'
					}
					],
			   filters: [
						{ property: 'createdDate', value: '' }
						],
					proxy: {

						type: 'ajax',
						url: Ext.manifest.api_url+'/country/allRates',
						useDefaultXhrHeader: false,
						actionMethods: {
							//  create: 'POST',
							read: 'GET',
							//  update: 'PUT',
							// destroy: 'DELETE'
						},

						simpleSortMode: true,
						enablePaging : true,
						reader: {
							type: 'json',
							rootProperty: 'rateList',
							successProperty: 'status',
							totalProperty: 'total'
						}
					},

					fields: [

						//'id', 
						'loanTypeName', 'bankName', 'countryName', 'createdDate', 'rate','processingFee',
					]

				}, cfg)]);
	}
});