Ext.define('MyApp.view.main.ResponsiveMenuButton',{
    extend: 'Ext.button.Split',
    xtype: 'mainmenu',

    require: [
        'MyApp.model.main.MainModel'
    ],

    text: 'Menu',

  // plugins: 'responsive',
   /* responsiveConfig: {
        'width < 768 && tall': {
            visible: true
    },
       'width >= 768': {
            visible: false 
       }
    },
		*/

    menu: {
        xtype: 'menu',
       // items: [{
           // xtype: 'mainmenu'
       // }]
    }   
});


