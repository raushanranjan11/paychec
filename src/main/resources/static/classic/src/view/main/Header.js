Ext.define('MyApp.view.main.Header', {
    extend: 'Ext.toolbar.Toolbar',
    xtype: 'appheader',

    requires: [
     //   'MyApp.view.locale.Translation'
      //  'MyApp.view.main.ResponsiveMenuButton'
    ],

    ui: 'footer',

    items: [{
            xtype: 'component',
            bind: {
                html: '{appHeaderIcon}'
            }
    },
    {
            xtype: 'image',
			height:'30px',
            componentCls: 'app-header-title',
			src:'img/paychec.png',
            bind: {
                html: '{name}'//"${userName}"
				//html:"PayChec"
            }
        },{
            xtype: 'tbfill'
        },{
            xtype: 'tbseparator'
        },
		{
            xtype: 'displayfield',
			value:'',
			  listeners:{
                render:function(){
					//console.log('&&&&&&&&&&&&&&&&&&&&&&&&777');
                    var me = this;
                    Ext.Ajax.request({
                        url: Ext.manifest.api_url+'/currentUser',
                        success: function(response, opts) {
                            var obj = Ext.decode(response.responseText);
							console.log(obj);
                            if(obj.success){
								console.log(obj);
                                var user = obj.user;
                                me.setValue(user.username);
                            }else{
                             //   window.location.href="/paychec/logout";
                            }
                        },
                        failure: function(response, opts) {
							// window.location.href="/paychec/logout";

                        }
                    });
                }
            }
        },
		
		
		
		
		{
            xtype: 'button',
            itemId: 'logout',
            text: "logout",
            reference: 'logout',
            iconCls: 'fa fa-sign-out fa-lg buttonIcon',
            listeners: {
                click: function(){
					
					window.location.href="/paychec/logout";
				}
            }
        }
    ]
});