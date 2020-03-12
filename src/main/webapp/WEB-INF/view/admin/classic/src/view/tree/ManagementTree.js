//ManagementTree
Ext.define('MyApp.view.tree.ManagementTree', {
   
   extend: 'Ext.panel.Panel',
    alias: 'widget.managementTree',
	
	 requires: [
        'MyApp.store.ManagementStore'
    ],
	
	items:[{
	xtype:'treepanel',
    title: 'Management',
   // store: 'managementStore'  ,
  //  width: 100,
   // height: 50,
    //store: 'managementStore',
    useArrows: true,
   // rootVisible: true,
	border : false,
        store : new Ext.create('MyApp.store.ManagementStore',{}), // Store created above
        rootVisible : true
		
	}]
	
});
	
	
	