Ext.define('MyApp.store.CountryStore', {
	extend: 'Ext.data.Store',

	alias: 'store.country',

	constructor: function (cfg) {
		var me = this;
		cfg = cfg || {};
		me.callParent([Ext.apply({
					storeId: 'country',

					pageSize: 15,
					//remoteSort: true,
					autoLoad: true,
					proxy: {

						type: 'ajax',
						url: Ext.manifest.api_url+'/country/',
						method: 'GET',
						useDefaultXhrHeader: false,

						//	simpleSortMode: true,
						reader: {
							type: 'json',
							rootProperty: 'country',
							successProperty: 'status',
						}
					},

					fields: [
						'id', 'countryName', 'isoCode', 'iso3Code'
					]

				}, cfg)]);
	}
});