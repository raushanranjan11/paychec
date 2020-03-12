Ext.define('MyApp.view.loan.ApplyLoan', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.applyLoan',
	requires: ['Ext.grid.filters.Filters', 'MyApp.view.loan.ApplyLoanViewModel', 'MyApp.view.bank.UserForm', 'MyApp.view.loan.LoanAppliedController'],
	title: 'Applied Loan Details',
	layout: 'fit',
	autoScroll: true,
	viewModel: {
		type: 'appliedLoanUser'
	},
	selType: 'rowmodel',
	bind: {
		store: '{appliedLoanUser}'
	},
	reference: 'loanGrid',
	initComponent: function () {
		var bankStore = new MyApp.store.BankStore({});
		Ext.apply(this, {
			columns: [{
					xtype: 'rownumberer'
				}, {
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
					text: 'Loan Amount',
					dataIndex: 'loanAmount',
					autoSizeColumn: true,
					flex: .125,
					filter: {
						"type": "string"
					}
				}, {
					xtype: 'datecolumn',
					text: 'Applied on',
					dataIndex: 'createdDate',
					autoSizeColumn: true,
					flex: .1,
					format: 'M d, Y',
					filter: {}
				}, {
					text: 'Rate',
					dataIndex: 'rate',
					autoSizeColumn: true,
					flex: .1,
					filter: {
						"type": "string"
					},
				}, {
					text: 'Bank',
					dataIndex: 'bankId',
					autoSizeColumn: true,
					flex: .15,
					xtype: 'gridcolumn',
					editor: {
						xtype: 'combo',
						store: bankStore,
						displayField: 'name',
						valueField: 'id',
						forceSelection: false,
						editable: false,
						readOnly: true,
						queryMode: 'local',
						triggerAction: 'all',
					},
					renderer: function (value) {
						if (Ext.isNumber(value)) {
							var combo = this.columns[6].getEditor();
							var store = combo.getStore();
							return store.findRecord('id', value).get('name');
						}
						return value;
					},
				}, {
					text: 'Country',
					dataIndex: 'countryId',
					autoSizeColumn: true,
					flex: .15,
					xtype: 'gridcolumn',
					editor: {
						xtype: 'combo',
						store: 'country',
						displayField: 'countryName',
						valueField: 'id',
						forceSelection: false,
						editable: false,
						readOnly: true,
						queryMode: 'local',
						triggerAction: 'all',
					},
					renderer: function (value) {
						if (Ext.isNumber(value)) {
							var combo = this.columns[7].getEditor();
							var store = combo.getStore();
							return store.findRecord('id', value).get('countryName');
						}
						return value;
					},
				}, {
					text: 'Loan Refrence',
					dataIndex: 'loanRef',
					autoSizeColumn: true,
					flex: .15,
				}, {
					text: 'Loan status',
					dataIndex: 'loanStatus',
					autoSizeColumn: true,
					flex: .15,
					xtype: 'gridcolumn',
					editor: {
						xtype: 'combo',
						store: 'loanstatus',
						displayField: 'loanStaus',
						valueField: 'id',
						forceSelection: false,
						editable: false,
						readOnly: true,
						queryMode: 'local',
						triggerAction: 'all',
					},
					renderer: function (value) {
						if (Ext.isNumber(value)) {
							var combo = this.columns[9].getEditor();
							var store = combo.getStore();
							return store.findRecord('id', value).get('loanStaus');
						}
						return value;
					},
				}, ],
			viewConfig: {
				stripeRows: false,
				getRowClass: function (record, rowIndex, rowParams, store) {
					if (record.get('loanStatus') == 3) {
						return 'yellow-row';
					}
					if (record.get('loanStatus') == 4) {
						return 'green-row';
					}
					if (record.get('loanStatus') == 5) {
						return 'red-row';
					}
				}
			},
			selModel: 'cellmodel',
			plugins: {
				ptype: 'cellediting',
			},
			dockedItems: [{
					xtype: 'pagingtoolbar',
					bind: {
						store: '{appliedLoanUser}'
					},
					dock: 'bottom',
					displayInfo: true,
					displayMsg: 'Displaying {0} to {1} of {2} &nbsp;records ',
					emptyMsg: "No records to display&nbsp;"
				}
			],
			listeners: {
				itemdblclick: function (gridview, record) {
					Ext.create('MyApp.view.bank.UserForm', {
						viewModel: {
							data: {
								selection: record.getUser(),
								record: record,
								userDoc: record.getDocument()
							}
						}
					}).show(); ;
				}
			}
		});
		this.callParent(arguments);
	}
});
