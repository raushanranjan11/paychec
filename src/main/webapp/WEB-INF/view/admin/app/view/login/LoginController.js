/**
 * 
 */

Ext.define('MyApp.view.login.LoginController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.login',

		init: function (view) {    },

// Validating username and password for a user
    onLoginClick: function() { 
		var me = this;
	  
	  var formObj = Ext.ComponentQuery.query('form')[0].getForm().getValues();
		if(('admin' == formObj.username) && ('adminPassword' == formObj.password) ){
		 // Remove Login Window
        this.getView().destroy();
        // Add the main view to the viewport
        Ext.widget('app-main');
	}
	 
    }
});