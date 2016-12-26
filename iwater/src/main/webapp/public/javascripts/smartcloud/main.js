/*
* 主文件
* 加载引用的文件
*/
requirejs.config({
    baseUrl: 'public/javascripts',
    paths: {
        "jquery":'import/jquery-1.8.3',
        "jquery.validform":'import/jquery.validform',
        "bootstrap":'import/bootstrap.min',
        "hashchange":'import/jquery.ba-hashchange',
        "ace":'import/ace.min',
        "pageslide":'import/jquery.pageslide.min',
        "util":'common/util',
        "page":'common/page',
        "datatables":'import/jquery.dataTables.min',
        "datatables.bootstrap":'import/jquery.dataTables.bootstrap',
        "ztree":'import/jquery.ztree.all-3.5.min'
    },
    shim: {
        'hashchange': { 
            deps: ['jquery'],
            exports: 'hashchange'
        },
        'ace':{
            deps: ['jquery'],
            exports: 'ace'
        },
        "jquery.validform":{
            deps:['jquery']
        },
        "bootstrap":{
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
        },
        "ztree":{
            deps:["jquery"]
        }
    }
});

// Start the main app logic.
requirejs(['jquery','page','smartcloud/indexController','hashchange','bootstrap','ace'],function   ($,page,index) {

    index.initMenus();//初始化菜单
    $(window).hashchange( function(){
        var hashStr = "";//页面定位
        hashStr = location.hash.replace("#","");
        page.parsePage(hashStr,"public/smartcloud/config/app.json");
    
    });
    $(window).hashchange();//触发事件
});