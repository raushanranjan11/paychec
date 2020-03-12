/**
 * This class is the view model for the Main view of the application.
 */
Ext.define('MyApp.model.main.MainModel', {
    extend: 'Ext.app.ViewModel',

    alias: 'mainmodel',

    data: {
        name: 'app',
        appName: 'Administracion',
        appHeaderIcon: '<span class="fa fa-desktop fa-lg app-header-logo">',
        footer: 'MJ Professional Services - Moises Fernandez - http://www.mjproservices.com'
    }
});