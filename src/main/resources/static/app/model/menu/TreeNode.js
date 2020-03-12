Ext.define('MyApp.model.menu.TreeNode',{
    extend: 'Ext.data.Model',
   idProperty: 'id',
    fields: [
        { name: 'id', type: 'int'},
        { name: 'text'},
        { name: 'iconCls'},
        { name: 'className'},
        { name: 'parent_id', mapping: 'menu_id'}
    ]

});