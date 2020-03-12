Ext.define('MyApp.store.FinanceDocument', {
	extend: 'Ext.data.Store',

	alias: 'store.financestore',

	constructor: function (cfg) {
		var me = this;
		cfg = cfg || {};
		me.callParent([Ext.apply({
					storeId: 'financestore',

					//pageSize: 15,
					//remoteSort: true,
					autoLoad: true,
					proxy: {

						type: 'rest',
						url: Ext.manifest.api_url+ '/finance',
						method: 'GET',
						useDefaultXhrHeader: false,

						//	simpleSortMode: true,
						reader: {
							type: 'json',
							rootProperty: 'financeList',
							successProperty: 'status',
						}
					},

					fields: [
						'id', 'name',  
					]

				}, cfg)]);
	}
});