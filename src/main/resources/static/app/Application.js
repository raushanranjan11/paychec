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
	models:[
	'MyApp.model.InterestModel',
	'MyApp.model.UserModel',
	'MyApp.model.LoanStatus',
	'MyApp.model.DocumentDetails',
	'MyApp.model.DocumentSubType',
	'MyApp.model.DocumentType'
	
	
	
	],

    stores: [
        // TODO: add global / shared stores here
		'MyApp.store.CountryStore',
		'MyApp.store.BankStore',
		'MyApp.store.LoanTypeStore',
		'MyApp.store.LoanStatus',
		'MyApp.store.KYCDocument',
		'MyApp.store.FinanceDocument',
		//'MyApp.store.UserListStore'
		
    ],
	
	 views: [
        'MyApp.view.login.Login',
		'MyApp.view.main.Main',
		'MyApp.util.LocalUrl',
		],

    launch: function () {
        // TODO - Launch the application
		Ext.tip.QuickTipManager.init();
         // initialising login screen during starting or boot of application
	   // Ext.widget('login');
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
