Ext.define('MyApp.store.LoanTypeStore', {
	extend: 'Ext.data.Store',

	alias: 'store.loanType',

	constructor: function (cfg) {
		var me = this;
		cfg = cfg || {};
		me.callParent([Ext.apply({
					storeId: 'loanType',

					//pageSize: 15,
					//remoteSort: true,
					autoLoad: true,
					proxy: {

						type: 'rest',
						url: Ext.manifest.api_url+'/country/loanType',
						method: 'GET',
						useDefaultXhrHeader: false,

						//	simpleSortMode: true,
						reader: {
							type: 'json',
							rootProperty: 'loneTypeList',
							successProperty: 'status',
						}
					},

					fields: [
						'id', 'loanName', 'loanDescription'
					]

				}, cfg)]);
	}
});