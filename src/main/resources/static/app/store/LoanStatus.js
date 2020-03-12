Ext.define('MyApp.store.LoanStatus', {
	extend: 'Ext.data.Store',

	alias: 'store.loanstatus',

	constructor: function (cfg) {
		var me = this;
		cfg = cfg || {};
		me.callParent([Ext.apply({
					storeId: 'loanstatus',

					//pageSize: 15,
					//remoteSort: true,
					autoLoad: true,
					proxy: {

						type: 'rest',
						url: Ext.manifest.api_url+ '/loanStatus',
						method: 'GET',
						useDefaultXhrHeader: false,

						//	simpleSortMode: true,
						reader: {
							type: 'json',
							rootProperty: 'loanStatusList',
							successProperty: 'status',
						}
					},

					  model: 'MyApp.model.LoanStatus',

				}, cfg)]);
	}
});