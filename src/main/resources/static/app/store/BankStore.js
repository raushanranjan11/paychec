Ext.define('MyApp.store.BankStore', {
	extend: 'Ext.data.Store',

	alias: 'store.bank',

	constructor: function (cfg) {
		var me = this;
		cfg = cfg || {};
		me.callParent([Ext.apply({
					storeId: 'bank',

					//pageSize: 15,
					//remoteSort: true,
					autoLoad: true,
					proxy: {

						type: 'rest',
						url: Ext.manifest.api_url+ '/bank',
						method: 'GET',
						useDefaultXhrHeader: false,

						//	simpleSortMode: true,
						reader: {
							type: 'json',
							rootProperty: 'bankList',
							successProperty: 'status',
						}
					},

					fields: [
						'id', 'name', 'banklogo'
					]

				}, cfg)]);
	}
});