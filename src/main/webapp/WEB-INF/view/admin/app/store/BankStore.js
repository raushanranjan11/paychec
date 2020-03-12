Ext.define('MyApp.store.BankStore', {
	extend: 'Ext.data.Store',

	alias: 'store.bank',

	constructor: function (cfg) {
		var me = this;
		cfg = cfg || {};
		me.callParent([Ext.apply({
					storeId: 'bank',

					pageSize: 15,
					//remoteSort: true,
					autoLoad: true,
					proxy: {

						type: 'rest',
						url: 'http://localhost:8090/paychec/bank',
						method: 'GET',
						useDefaultXhrHeader: false,

						//	simpleSortMode: true,
						reader: {
							type: 'json',
							rootProperty: 'bank',
							successProperty: 'status',
						}
					},

					fields: [
						'id', 'countryName', 'isoCode', 'iso3Code'
					]

				}, cfg)]);
	}
});