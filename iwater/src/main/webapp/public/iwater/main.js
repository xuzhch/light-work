/*
 * 主文件
 * 加载引用的文件
 */
requirejs
		.config({
			baseUrl : 'public', // 注意不要随便修改
			paths : {
				app : 'iwater/javascripts',
				"jquery" : 'core/javascripts/import/jquery-1.8.3',
				"jquery.validform" : 'core/javascripts/import/jquery.validform',
				"jquery.easyui" : 'core/javascripts/import/jquery.easyui.min',
				"bootstrap" : 'core/javascripts/import/bootstrap.min',
				"hashchange" : 'core/javascripts/import/jquery.ba-hashchange',
				"ace" : 'core/javascripts/import/ace.min',
				"pageslide" : 'core/javascripts/import/jquery.pageslide.min',
				"util" : 'core/javascripts/core/lightwork.ui',
				"page" : 'core/javascripts/core/lightwork.page',
				"datatables" : 'core/javascripts/import/jquery.dataTables.min',
				"datatables.bootstrap" : 'core/javascripts/import/jquery.dataTables.bootstrap',
				"ztree" : 'core/javascripts/import/jquery.ztree.all-3.5.min',
				"bootbox" : 'core/javascripts/import/bootbox.min'
			},
			shim : {
				'hashchange' : {
					deps : [ 'jquery' ],
					exports : 'hashchange'
				},
				'ace' : {
					deps : [ 'jquery' ],
					exports : 'ace'
				},
				"jquery.validform" : {
					deps : [ 'jquery' ]
				},
				"bootstrap" : {
					deps : [ 'jquery' ]
				},
				"pageslide" : {
					deps : [ 'jquery' ]
				},
				"datatables" : {
					deps : [ 'jquery' ]
				},
				"datatables.bootstrap" : {
					deps : [ "datatables" ]
				},
				"ztree" : {
					deps : [ "jquery" ]
				}
			}
		});

requirejs.onError = function(err) {
	
	if (err.requireType === 'timeout') {
		console.error('请求超时，请检查服务端配置，modules: ' + err.requireModules);
	}
	if (err.requireType === 'scripterror') {
		console.error('请检查服务端文件路径是否正确，modules:' + err.requireModules);
	}

	throw err;
};

/**
 * 设置应用的基本参数
 */
function ApplicationContext() {
	this.name = "iwater";
	this.title = "智慧水务平台";
	this.appPath = "public/iwater"; // 设置应用的访问路径
	this.indexPage = "system.users.list";

	this.code = "component_smartcloudServer";
	this.pCode = "component_root";

	this.htmlPath = "/html";
	this.javascriptsPath = "/javascripts";
	this.appMenuPath = this.appPath + "/config/menu.json";
	this.appComponentPath = this.appPath + "/config/component.json";
	this.appSelectPath = this.appPath + "/config/select.json";

}

// 全局变量.
var APP_CONTEXT = new ApplicationContext();

// Start the main app logic.
requirejs([ 'jquery', 'page', 'app/system/indexController', 'hashchange',
		'bootstrap', 'ace' ], function($, page, index) {

	index.initMenus();// 初始化菜单
	$(window).hashchange(function() {
		var hashStr = "";// 页面定位
		hashStr = location.hash.replace("#", "");
		page.parsePage(hashStr);

	});
	$(window).hashchange();// 触发事件
});