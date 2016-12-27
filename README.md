# LightWork Web框架
作者：xuzhengchao  联系方式：QQ群591747485

LightWork是一款轻量级JavaEE Web框架，基于Spring4+SpringMVC4+MyBatis3实现，运用了前后端分离的设计思想，前端和服务端实现了松耦合，通过RESTful服务接口交互数据。

一、框架特点

1、轻量级JavaEE Web框架，运用了前后端分离的设计思想，前端和服务端实现了松耦合，通过RESTful服务接口交互数据。

2、基于开源组件实现，保证了框架的通用性、稳定性和扩展性。

3、前端通过HTML5+CSS+JavaScript技术实现，能够与多种平台（Java, .NET, NodeJS等）的服务端集成。

4、前端借鉴了AngularJS的设计思路，实现了MVC模式。可通过类似java package方式调用js方法和页面（如system.user.add形式），提供Handlebars.js模版引擎，逻辑清晰，代码简洁。

5、前端提供了多种web界面组件，如侧滑显示、下拉框、弹出框、模式对话框、分页列表、文件上传、树、CheckBox、RadioBox等。

6、服务端基于Spring4+SpringMVC4+MyBatis3实现，能够支持多种方式（Web浏览器、移动应用、桌面应用等）的前端。

7、服务端集成了Spring Security组件，实现了用户验证和访问控制功能，可自定义用户验证方式和资源访问控制。

8、服务端支持RESTful服务接口便捷开发，支持json模式校验。

9、服务端集成Thymeleaf模板，替代jsp页面。

10、框架易于使用和学习，可充分利用第三方组件和网络资源。

二、下载与使用

1、学习研究或基于本框架开发项目，可以直接下载zip文件。

2、参与本开源框架开发，贡献代码，请使用git工具下载项目进行开发。

3、推荐使用Eclipse neon2进行开发，已内置Maven、git等插件。选择导入项目，在“import”弹出框中选择“Git”，然后再选择“Projects from Git”。

下载地址：http://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/neon2

4、创建Maven web项目，Maven会自动构建按照pom.xml中的配置下载依赖的jar包。

5、运行init.sql在MySQL数据库中创建数据库表，然后在jdbc.properties中配置好数据库连接。

6、在Eclipse中配置好运行Server，如Tomcat8，将项目添加到server中即可发布运行。
