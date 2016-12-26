/*
* 主文件
* 加载引用的文件
*/
requirejs.config({
    baseUrl: 'public',  //注意不要随便修改
    paths: {
        "jquery":'core/javascripts/import/jquery-1.8.3',
        "jquery.validform":'core/javascripts/import/jquery.validform',
        "bootstrap":'core/javascripts/import/bootstrap.min',
        "hashchange":'core/javascripts/import/jquery.ba-hashchange',
        "ace":'core/javascripts/import/ace.min',
        "pageslide":'core/javascripts/import/jquery.pageslide.min',
        "util":'core/javascripts/core/lightwork.ui',
        "page":'core/javascripts/core/lightwork.page',
        "datatables":'core/javascripts/import/jquery.dataTables.min',
        "datatables.bootstrap":'core/javascripts/import/jquery.dataTables.bootstrap',
        "ztree":'core/javascripts/import/jquery.ztree.all-3.5.min',
        "bootbox":'core/javascripts/import/bootbox.min'
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