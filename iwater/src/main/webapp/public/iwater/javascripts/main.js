/*
* 主文件
* 加载引用的文件
*/
requirejs.config({
    baseUrl: 'public',
    paths: {
        "jquery":'javascripts/import/jquery-1.8.3',
        "jquery.validform":'javascripts/import/jquery.validform',
        "bootstrap":'javascripts/import/bootstrap.min',
        "hashchange":'javascripts/import/jquery.ba-hashchange',
        "ace":'javascripts/import/ace.min',
        "pageslide":'javascripts/import/jquery.pageslide.min',
        "util":'javascripts/core/lightwork.ui',
        "page":'javascripts/core/lightwork.page',
        "datatables":'javascripts/import/jquery.dataTables.min',
        "datatables.bootstrap":'javascripts/import/jquery.dataTables.bootstrap',
        "ztree":'javascripts/import/jquery.ztree.all-3.5.min',
        "bootbox":'javascripts/import/bootbox.min'
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


function ApplicationContext(){
	this.name = "iwater";
    this.title = "智慧水务平台";	
    this.appPath = "public/"+this.name; //设置应用的访问路径
    this.appConfigPath = this.appPath+"/config/app.json";
    this.appMenuPath = this.appPath+"/config/menu.json";
    this.appComponentPath = this.appPath+"/config/component.json";
    this.appSelectPath = this.appPath+"/config/select.json";
    this.appJsPath = this.name+"/javascripts"; //注意和上面的路径不同，在应用中会和requirejs.config中的baseurl一起组成完整的相对路径
}

//全局变量.
var APP_CONTEXT = new ApplicationContext();

// Start the main app logic.
requirejs(['jquery','page',APP_CONTEXT.appJsPath+'/system/indexController','hashchange','bootstrap','ace'],function   ($,page,index) {
	
	

    index.initMenus();//初始化菜单
    $(window).hashchange( function(){
        var hashStr = "";//页面定位
        hashStr = location.hash.replace("#","");
        page.parsePage(hashStr);
    
    });
    $(window).hashchange();//触发事件
});