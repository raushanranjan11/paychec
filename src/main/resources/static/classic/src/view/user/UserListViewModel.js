Ext.define('MyApp.view.bank.UserListViewModel', {
	extend: 'Ext.app.ViewModel',
	alias: 'viewmodel.userList',
	stores: {
		userList: {
			model: 'MyApp.model.UserModel',
			autoSync: true,
			pageSize: 10,
			remoteSort: true,
			remoteFilter: true,
			autoLoad: true,
		/*	 autoLoad: {
        start: 0,
        limit: 2
    },*/
			sorters: [{
					property: 'firstName',
					direction: 'ASC'
				}
			],
			filters: [{
					property: 'firstName',
					value: ''
				}
			],
			proxy: {
				type: 'rest',
				 
				url: Ext.manifest.api_url+ '/userList',
				enablePaging: true,
				simpleSortMode: true,
				reader: {
					type: 'json',
					rootProperty: 'userList',
					successProperty: 'status',
					totalProperty: 'total'
				}
			}
		}
	}
});
