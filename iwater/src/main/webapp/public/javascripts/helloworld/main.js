/*
* 主文件
* 加载引用的文件
*/
requirejs.config({
    baseUrl: '/javascripts',
    paths: {
        "jquery":'import/jquery-1.8.3',
        "jquery.validform":'import/jquery.validform',
        "hashchange":'import/jquery.ba-hashchange',
        "pageslide":'import/jquery.pageslide.min',
        "page":'common/page',
        "datatables":'import/jquery.dataTables.min',
        "datatables.bootstrap":'import/jquery.datatables.bootstrap'
    },
    shim: {
        'hashchange': { 
            deps: ['jquery'],
            exports: 'hashchange'
        },
        "jquery.validform":{
            deps:['jquery']
        },
        "pageslide":{
            deps:['jquery']
        },
        "datatables":{
            deps:['jquery']
        },
        "datatables.bootstrap":{
            deps:["datatables"]
        }
    }
});

// Start the main app logic.
requirejs(['jquery','page','hashchange'],function   ($,page) {

    $(window).hashchange( function(){
        var hashStr = "";//页面定位
        hashStr = location.hash.replace("#","");
        page.parsePage(hashStr,"/helloworld/config/app.json","#content");
    
    });
    $(window).hashchange();//触发事件
});