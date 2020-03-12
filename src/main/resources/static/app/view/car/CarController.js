/**
 * This class is the controller for the main view for the application. It is specified as
 * the "controller" of the Main view class.
 *
 * TODO - Replace this content of this view to suite the needs of your application.
 */
Ext.define('MyApp.view.car.CarController', {
    extend: 'Ext.app.ViewController',

    alias: 'controller.carController',

    onItemSelected: function (sender, record) {
      //  Ext.Msg.confirm('Confirm', 'Are you sure?', 'onConfirm', this);
    },

    onConfirm: function (choice) {
        if (choice === 'yes') {
            //
        }
    },
	submit:function(){
		
		var form1 = this.getView();
		 var form = this.getView().getForm();
                    if(form.isValid())
                        form.submit({
                            waitMsg:'submitting...',
						//	type: 'rest',
						url: 'http://localhost:8087/carsDetails',
						useDefaultXhrHeader: false,
						actionMethods: {
						  create: 'POST',
							
						},
                           // url: 'http://localhost:8087/carsDetails',
                            success: function(form,action) {
								//alert(this.getView().findParentByType());
								form1.up('mainPanel').down('mainlist').getStore().load();
                              //  newForm.windowConfig.close();
                                //window.location = action.result.data.url;
                            },
                            failure: function(form,action){
                                Ext.MessageBox.alert('Erro',action.result.data.msg);
                            }
                        });
	}
});
