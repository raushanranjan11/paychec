
Ext.define('MyApp.store.ManagementStore', {
    extend: 'Ext.data.TreeStore',
     root : {
        text: 'Management',iconCls: 'fa-cog',
        expanded : true, // Expands true node on initialization
        children :[{ // Specifies child nodes
            text:'User Management',
            leaf : true // Specifies node as leaf
        },{
            text:'Parts Management',iconCls: 'fa-cog',
            leaf : true
        },{
            text : 'Child 3',
            children: [{
                text : 'Grand Child 1',
                children : [{
                    text: 'etc.',
                    leaf : true
                }]
            }]
        }]
    },
	 listeners: {
            append: function( thisNode, newChildNode, index, eOpts ) {
               // newChildNode.set('icon', '../images/grid/g_1_blue_16.png');
            }
        }
         
        
        
    
});