/**
 * The main application class. An instance of this class is created by app.js when it
 * calls Ext.application(). This is the ideal place to handle application launch and
 * initialization details.
 */
Ext.define('MyApp.Application', {
    extend: 'Ext.app.Application',

    name: 'MyApp',

    quickTips: false,
    platformConfig: {
        desktop: {
            quickTips: true
        }
    },

    stores: [
        // TODO: add global / shared stores here
		'MyApp.store.ManagementStore',
		//'MyApp.store.CustomerStore'
		'MyApp.store.CountryStore',
		'MyApp.store.BankStore'
    ],
	
	 views: [
        'MyApp.view.login.Login',
		'MyApp.view.main.Main',
		'MyApp.view.tree.ManagementTree',
		 'MyApp.view.util.LocalUrl'
		
		],

    launch: function () {
        // TODO - Launch the application
		
		// Ext.widget('login');
    },

    onAppUpdate: function () {
        Ext.Msg.confirm('Application Update', 'This application has an update, reload?',
            function (choice) {
                if (choice === 'yes') {
                    window.location.reload();
                }
            }
        );
    }
});
