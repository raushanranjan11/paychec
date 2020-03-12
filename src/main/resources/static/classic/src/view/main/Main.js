Ext.define('MyApp.view.main.Main', {
	extend: 'Ext.panel.Panel',
	xtype: 'app-main',
	requires: ['Ext.plugin.Viewport', 'Ext.window.MessageBox', 'Ext.grid.filters.Filters', 'MyApp.view.main.MainController',
	'MyApp.view.main.MainModel', 'MyApp.view.main.List', 'MyApp.view.tree.ManagementTree', 'MyApp.view.main.Footer',
	'MyApp.view.main.Header','MyApp.view.main.MainMenu','MyApp.view.main.MainMenuController',
	'MyApp.view.main.ResponsiveMenuButton'],
	plugins: 'viewport',
	controller: 'main',
	viewModel: 'main',
	tabBarHeaderPosition: 1,
	titleRotation: 0,
	tabRotation: 0,
	tabBar: {
		flex: 1,
		layout: {
			align: 'stretch',
			overflowHandler: 'none'
		}
	},
	responsiveConfig: {
		tall: {
			headerPosition: 'top'
		},
		wide: {
			headerPosition: 'left'
		}
	},
	defaults: {
		bodyPadding: 20,
		tabConfig: {
			plugins: 'responsive',
			responsiveConfig: {
				wide: {
					iconAlign: 'left',
					textAlign: 'left'
				},
				tall: {
					iconAlign: 'top',
					textAlign: 'center',
					width: 120
				}
			}
		}
	},
	layout: 'border',
	bodyBorder: false,
	defaults: {
		bodyPadding: 10
	},
	items: [{
			region: 'center',
			xtype: 'mainPanel',
		}, {
			region: 'north',
			xtype: 'appheader'
		}, {
			region: 'south',
			xtype: 'appfooter'
		}, {
			xtype: 'panel',
			region: 'west',
			title: 'Menu',
			height: 200,
			width: 250,
			split: true,
			collapsible: true,
			collapsed: false,
			/*items: [{
					xtype: 'segmentedbutton',
					width: 200,
					style: 'margin-top:10px;',
					vertical: true,
					items: [{
							text: 'Home',
							iconCls: 'fa fa-home ',
						}, ]
				}
			]*/
			
			items:[
			{
						 xtype: 'app-menu',
						
        
		}
					],
					
					
					
		}
	]
});
