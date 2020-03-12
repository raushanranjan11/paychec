Ext.define('MyApp.store.Personnel', {
    extend: 'Ext.data.Store',

    alias: 'store.personnel',

    fields: [
        'id', 'cName', 'vName','vNumber','date','estimatePrice','ready','mobile','bill','discount','payment','due','technician'
    ],

    data: { items: [
        
       { id: '1',    cName: 'raushan', vName: "Tata Xenon", vNumber:'123456',date:'17/04/2018' , estimatePrice:'1000.00',  ready:'ready' ,  mobile: "555-444-4444",
		bill:'bill',discount:'discount',payment:'500.00',due:'500.00',technician:'ranjan' },
		{ id: '2',    cName: 'raushan', vName: "Tata Xenon", vNumber:'123456',date:'17/04/2018' , estimatePrice:'1000.00',  ready:'ready' ,  mobile: "555-444-4444",
		bill:'bill',discount:'discount',payment:'500.00',due:'500.00',technician:'ranjan' },
		{ id: '3',    cName: 'raushan', vName: "Tata Xenon", vNumber:'123456',date:'17/04/2018' , estimatePrice:'1000.00',  ready:'ready' ,  mobile: "555-444-4444",
		bill:'bill',discount:'discount',payment:'500.00',due:'500.00',technician:'ranjan' },
		{ id: '4',    cName: 'raushan', vName: "Tata Xenon", vNumber:'123456',date:'17/04/2018' , estimatePrice:'1000.00',  ready:'ready' ,  mobile: "555-444-4444",
		bill:'bill',discount:'discount',payment:'500.00',due:'500.00',technician:'ranjan' }
    ]},

    proxy: {
        type: 'memory',
        reader: {
            type: 'json',
            rootProperty: 'items'
        }
    }
});
