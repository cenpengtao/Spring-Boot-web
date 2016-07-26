# Spring-Boot-web
A simple website implemented with Spring Boot/MVC/Security + MySQL + Mybatis. Now,it is only a empty framwork with simple example. 
采用spring Boot/MVC/Security + MySQL + Mybatis实现的一个简单网站后台。现在还一个空的框架,包括简单示例。
#项目介绍
这是一个用Maven构建的项目。父项目是parent-module，为了方便以后扩展（手机端和PC端软件开发），共享后台代码，将其他部分分为四个子模块：web-module;security-module;mybatis-module;share-module，将来会加入redis-module。
web-module：web网站的入口。里面包括web应用的程序入口WebLauncher.java、配置文件和controller。
security-module：web网站的安全框架，用于用户登录授权，接口访问权限控制等。里面包括安全框架配置、用户验证逻辑、验证成功或失败处理、访问禁止处理和登出成功处理等操作类。
mybatis-module：web后台的数据框架，用于数据库逆向生成model和数据库访问操作。其中包括数据库逆向配置和数据库访问的model和mapper。
share-module：web后台共享代码（不仅仅限于web开发）。其中包括服务层和持久层。所用数据库操作异常在服务层捕获，方便在服务层使用事务操作；持久层将来用于集成redis，因此方法的返回值为将要缓存的对象。
# project introduction
This is a project built with Maven. The parent project is parent-module, in order to expansion of (end of the phone and the PC end software development) to facilitate future, sharing code for the background, the other is divided into four modules: web-module; security-module; mybatis-module; share-module will join the redis-module.
Web-module:web portal. Which includes the web application program entry WebLauncher.java, configuration files and controller.
Security-module:web website security framework for user login authorization, access control, etc.. Which includes security framework configuration, user authentication logic, validation success or failure processing, access forbidden processing and logout successful treatment etc. operation.
Mybatis-module:web background data framework for database reverse generation model and database access operations. Including database reverse configuration and database access model and mapper.
Share-module:web background sharing code (not limited to web development). Including the service layer and the persistence layer. The database operation exception is captured in the service layer, which is convenient to use the transaction operation in the service layer; the persistence layer is used to integrate the redis in the future, so the return value of the method is the object to be cached.
