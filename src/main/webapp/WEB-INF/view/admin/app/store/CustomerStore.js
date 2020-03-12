Ext.define('MyApp.store.CustomerStore', {
	extend: 'Ext.data.Store',

	alias: 'store.customer',

	constructor: function (cfg) {
		var me = this;
		cfg = cfg || {};
		me.callParent([Ext.apply({
					storeId: 'customer',

					pageSize: 15,
					remoteSort: true,
					autoLoad: true,
					proxy: {

						type: 'rest',
						url: 'http://localhost:8087/all',
						useDefaultXhrHeader: false,
						actionMethods: {
							//  create: 'POST',
							read: 'GET',
							//  update: 'PUT',
							// destroy: 'DELETE'
						},

						simpleSortMode: true,
						reader: {
							type: 'json',
							rootProperty: 'customers',
							successProperty: 'status',
						}
					},

					fields: [

						'id', 'custName', 'vechName', 'vechNumber', 'date', 'estimatePrice', 'ready', 'mobile', 'bill', 'discount', 'payment', 'due', 'technician'
					]

				}, cfg)]);
	}
});