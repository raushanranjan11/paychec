Ext.define('MyApp.store.UserListStore', {
	extend: 'Ext.data.Store',

	alias: 'store.userList',

	constructor: function (cfg) {
		var me = this;
		cfg = cfg || {};
		me.callParent([Ext.apply({
					storeId: 'userList',

					//pageSize: 15,
					//remoteSort: true,
					autoLoad: false,
					proxy: {

						type: 'rest',
						url: Ext.manifest.api_url+ '/userList',
						method: 'GET',
						useDefaultXhrHeader: false,

						//	simpleSortMode: true,
						reader: {
							type: 'json',
							rootProperty: 'userList',
							successProperty: 'status',
						}
					},

					fields: [
					'id',
						'firstName',
						'lastName',
						'dob',
						 { name: 'dob', mapping: 'birthDate' },
						'country','phoneNumber','profilePic'
					]

				}, cfg)]);
	}
});