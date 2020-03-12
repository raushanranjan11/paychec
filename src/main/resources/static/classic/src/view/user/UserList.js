Ext.define('MyApp.view.user.UserList', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.userList',
	requires: ['Ext.grid.filters.Filters', 'MyApp.view.bank.InterestRateViewModel'],
	title: 'User Details',
	layout: 'fit',
	autoScroll: true,
	viewModel: {
		type: 'userList'
	},
	selType: 'rowmodel',
	bind: {
		store: '{userList}'
	},
	initComponent: function () {
		Ext.apply(this, {
			columns: [{
					text: 'First Name',
					dataIndex: 'firstName',
					autoSizeColumn: true,
					flex: .125,
					filter: {
						"type": "string"
					}
				}, {
					text: 'Last Name',
					dataIndex: 'lastName',
					autoSizeColumn: true,
					flex: .125,
					filter: {
						"type": "string"
					}
				}, {
					xtype: 'datecolumn',
					text: 'DOB',
					dataIndex: 'dob',
					autoSizeColumn: true,
					flex: .1,
					filter: {
						"type": "string"
					}
				}, {
					text: 'Country',
					dataIndex: 'country',
					autoSizeColumn: true,
					flex: .1,
					filter: {
						"type": "string"
					}
				}, {
					text: 'Phone Number',
					dataIndex: 'phoneNumber',
					autoSizeColumn: true,
					flex: .15,
				}, {
					text: 'Profile Pic',
					dataIndex: 'profilePic',
					autoSizeColumn: true,
					flex: .15,
					renderer: function (value) {
						//var val = "http://localhost:8080/paychec/resources/image/profile_pic/raushanranjan85@gmail.com.jpg";
						return '<img src=' + value + ' alt="Image" height="20" width="20">';
					}
				}, ],
			plugins: [{
					ptype: 'gridfilters'
				}, {
					ptype: 'rowexpander',
					rowBodyTpl: ['<div style="background-color:#CBDDF3;margin-left:5px;margin-bottom: 20px;border: 1px solid;" id="">', '<p><b>First Name:</b><br/>{firstName}<br/> </span> </p>', '<p><b>Last Name:</b><br/>{lastName}<br/> </span> </p>', ]
				}
			],
			viewConfig: {
				enableTextSelection: true
			},
			dockedItems: [{
					xtype: 'pagingtoolbar',
					bind: {
						store: '{userList}'
					},
					dock: 'bottom',
					displayInfo: true,
					displayMsg: 'Displaying {0} to {1} of {2} &nbsp;records ',
					emptyMsg: "No records to display&nbsp;"
				}
			],
		});
		this.callParent(arguments);
	}
});
