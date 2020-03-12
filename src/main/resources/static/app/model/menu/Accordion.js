Ext.define('MyApp.model.menu.Accordion',{
    extend: 'Ext.data.Model',
    idProperty: 'id',
    require: [
        'MyApp.model.menu.TreeNode'
    ],

    fields: [
        { name: 'id', type: 'int'},
        { name: 'text'},
        { name: 'iconCls'}
    ],

    hasMany: {
        model: 'MyApp.model.menu.TreeNode',
    //    foreignKey: 'parent_id',
        associationKey: 'menus',
        name: 'menus'
    }

});