Ext.define('MyApp.view.main.List', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.mainlist',
	requires: ['Ext.grid.filters.Filters', 'MyApp.view.bank.InterestRateViewModel'],
	title: 'Latest Bank Interest Rate ',
	layout: 'fit',
	autoScroll: true,
	viewModel: {
		type: 'interestRate'
	},
	selType: 'rowmodel',
	bind: {
				store: '{interestRatesList}'
			},
	/*store: {
		type: 'interestRates'
	},*/
	
	 initComponent: function () {
        Ext.apply(this,
        {
	columns: [{
			text: 'Bank Name',
			dataIndex: 'bankName',
			autoSizeColumn: true,
			flex: .125,
			filter: {
				"type": "string"
			}
		}, {
			text: 'Country',
			dataIndex: 'countryName',
			autoSizeColumn: true,
			flex: .125,
			filter: {
				"type": "string"
			}
		}, {
			text: 'Interest Rate',
			dataIndex: 'rate',
			autoSizeColumn: true,
			flex: .1,
			filter: {
				"type": "string"
			}
		}, {
			text: 'Loan Type',
			dataIndex: 'loanTypeName',
			autoSizeColumn: true,
			flex: .1,
			filter: {
				"type": "string"
			}
		}, {
			text: 'Processing Fees',
			dataIndex: 'processingFee',
			autoSizeColumn: true,
			flex: .15,
		}, {
			text: 'Updated Date',
			dataIndex: 'createdDate',
			autoSizeColumn: true,
			flex: .15,
		}, ],
	/*plugins: [{
			ptype: 'gridfilters'
		}, {
			ptype: 'rowexpander',
			rowBodyTpl: ['<table>', '<tpl foreach=".">', '<tr><td>{$}</td><td>{.}</td></tr>', '</tpl>', '</table>']
		}
	],*/
	viewConfig: {
		enableTextSelection: true
	},
	dockedItems: [{
			xtype: 'pagingtoolbar',
			bind: {
				store: '{interestRatesList}'
			},
			//store: 'rate',
			//pageSize: 2,
			dock: 'bottom',
			displayInfo: true,
			// displayInfo: true,
                displayMsg: 'Displaying {0} to {1} of {2} &nbsp;records ',
                emptyMsg: "No records to display&nbsp;"
		}
	],

	  });

        this.callParent(arguments);
	 }
});