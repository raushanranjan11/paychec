Ext.define('MyApp.view.car.CarForm', {
    extend: 'Ext.form.Panel',
    requires: ['MyApp.view.car.CarController'],
    xtype: 'carForm',
    autoDestroy:false,
	style:'margin-top:10px;',
	
    controller: 'carController',
   layout:'column',
    labelAlign: 'top',
	reference: 'carForm',
   defaults: {
     // xtype: 'container',
      layout: 'form',
      //columnWidth: 0.5
	  labelWidth:120,
	  style:'margin:10px;',
   },
   
    initComponent: function() {
       
       // var comboStore = new MyApp.store.Category({});
	   

        this.items = [
				 
					{
						xtype : 'textfield',
						name : 'vechNumber', 
						//allowBlank : false,
 						msgTarget : 'side',
						//blankText : 'Movie Name  is Mandatory!',
						fieldLabel : 'Vechile Number',
						reference: 'vNumber',
            			// bind: '{theMovies.movieTitle}',
						listeners : {
							 
						}
					}, 
					{
						xtype : 'textfield',
						name : 'vechName', 
						//allowBlank : false,
 						msgTarget : 'side',
						//blankText : 'Movie Name  is Mandatory!',
						fieldLabel : 'Vechicle Name',
						reference: 'vName',
            		//	 bind: '{theMovies.movieTitle}',
						listeners : {
							 
						}
					}, 
					{//'custName', 'vechName', 'vechNumber', 
						xtype : 'textfield',
						name : 'mobile', 
					//	allowBlank : false,
 						msgTarget : 'side',
						//blankText : 'Movie Name  is Mandatory!',
						fieldLabel : 'Mobile Number',
						reference: 'movieTitle',
            		//	 bind: '{theMovies.movieTitle}',
						listeners : {
							 
						}
					}, 
					{
						xtype : 'textfield',
						name : 'custName', 
					//	allowBlank : false,
 						msgTarget : 'side',
						//blankText : 'Movie Name  is Mandatory!',
						fieldLabel : 'Customer Name',
						reference: 'cName',
            			// bind: '{theMovies.movieTitle}',
						listeners : {
							 
						}
					}, 
					{
						xtype : 'textfield',
						name : 'distance', 
					//	allowBlank : false,
 						msgTarget : 'side',
						//blankText : 'Movie Name  is Mandatory!',
						fieldLabel : 'Kilo Meter Driven',
						//reference: 'movieTitle',
            			// bind: '{theMovies.movieTitle}',
						listeners : {
							 
						}
					},
{
						xtype : 'textfield',
						name : 'technician', 
					//	allowBlank : false,
 						msgTarget : 'side',
						//blankText : 'Movie Name  is Mandatory!',
						fieldLabel : 'Technician',
						reference: 'technician',
            			// bind: '{theMovies.movieTitle}',
						listeners : {
							 
						}
					},					
				/*	{
						xtype : 'textfield',
						name : 'releaseYear',
						fieldLabel : 'Release year', 
						 reference: 'releaseYear',
            			 bind: '{theMovies.releaseYear}',
						listeners : {
							 
						}
					}, 
					
					{
 						name : 'category',
						fieldLabel : 'Category',
						xtype: 'combobox', 
						maxHeight: 150,        
	 					multiSelect: true,
						emptyText : "Select Category",
						// store: 'MyApp.store.Category',
						//store:comboStore,
						displayField: 'categoryName',
						valueField: 'cat_id', 
						forceSelection: false,
						editable: false,
						queryMode: 'local',
						value:'AC',
 						triggerAction: 'all',
						allowBlank : false,
						reference : 'category',
						msgTarget : 'side',
						blankText : 'Category  is Mandatory!',
						 reference: 'category',
            			 bind: '{theMovies.category}',
						 
						listeners : {
							 
						}
					},*/
					{
						xtype : 'textfield',
						name : 'supervisor',
						fieldLabel : 'Superviser',
						//allowBlank : false,
						reference : 'supervisor',
						msgTarget : 'side',
						blankText : 'Superviser Name  is Mandatory!',
						 reference: 'director',
            			//bind: '{theMovies.director}'
						
					},
					{
						xtype : 'textfield',
						name : 'email',
						fieldLabel : 'Email',
						 reference: 'email',
            		//	bind: '{theMovies.imdbLink}'
						
					}
					
				];
       this. buttons = [{
            text: 'Save',
            scope : this,
            action: 'save',
            //formBind : true,
			//handler : this.submit,
			listeners : {
							click : 'submit'
						}
             
        }, '->', {
            text: 'Cancel',
            action: 'cancel',
            scope : this,
            handler : this.close,
        }];
        this.callParent();
    }
});