Ext.define('MyApp.store.KYCDocument', {
	extend: 'Ext.data.Store',

	alias: 'store.kycstore',

	constructor: function (cfg) {
		var me = this;
		cfg = cfg || {};
		me.callParent([Ext.apply({
					storeId: 'kycstore',

					//pageSize: 15,
					//remoteSort: true,
					autoLoad: true,
					proxy: {

						type: 'rest',
						url: Ext.manifest.api_url+ '/kyc',
						method: 'GET',
						useDefaultXhrHeader: false,

						//	simpleSortMode: true,
						reader: {
							type: 'json',
							rootProperty: 'kycList',
							successProperty: 'status',
						}
					},

					fields: [
						'id', 'name',  
					]

				}, cfg)]);
	}
});