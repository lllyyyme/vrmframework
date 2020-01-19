### do something interesting 做一些有趣的事
##### 更新 README
###### 这周上班摸鱼的时候，设计了一个将应用的数据通过XML转化为不同场景的VO的mini-framework吧，写的时候没什么感觉，写完感觉好像没什么卵用。。，记录一下思考过程吧。 大致如下：
###### 项目是一个渠道系统的外包，前后端分离，甲方希望通过后端来控制页面展示内容以及业务方面的逻辑。 
###### 由于是渠道，数据来源主要来自核心系统，也会存放在redis ，渠道本身也会在数据库中存放写过程数据，在一些场景下也会使用到，那么数据源来源就有缓存，数据库等，针对页面初始化这部分内容中，后端要做的事就是把这些数据根据场景的不同放在VO中 xxxVo.set(xxxbo.getXXX()) 大致这样吧，哈哈 自己想了想完全可以把这些这一类代码变为XML的配置,基本功能已经完成，对于日志，异常这些还没统一做处理，readme先写到这吧 2020-1-17 21:31:33 
##### 更新 README 关于项目和计划： 
###### 不知道怎么命名所以核心对象就命名为EngineCore,由EngineCoreFactory来创建这个对象，并记录EngineCore是否已经启动，目的是EngineCore必须为单例，故这里将EngineCore定义为EngineCoreFactory的内部类。
###### 在EngineCore启动时会把指定路径下的所有XML配置读取成为XmlConfig对象存放起来，xml结构以及解析目前认为是现在这个样子，能力有限，如果以后有想法可以再过头改改。
###### 提供给开发人员调用的是EngineCoreExecuter中的submitModelTask方法,这里还没有仔细考虑,初步先是由EngineCoreExecuter管理一个ThreadPool进行处理,后续再分析看看该选择怎样实现（在用threadPool之前，executer本身只是一个多例执行对象，需要去手动去建，感觉可能差不多） 
###### DTD文件是针对XML的约束定义，定义标签以及属性（这个比较简单） 
###### annotation包准备实现注解，以便简化配置 2020年1月18日21点07分 

##### 更新 readme 
###### 开始写的时候是没想那么多的，只是想实现一个可配置的BO转VO的功能，后来发现BO转化VO是需要业务规则支撑的，所以又加入了Rule这个概念。 
###### 现在写完主体功能后发现,实际上我单方面是这样理解业务功能的： 
- ViewObject 一个可以描述页面的简单javabean 
- Model 业务模型对象，可以描述一个功能的javabean，可以理解为BO。 
- Rule 制定业务规则 
---
将三部分结合起来：页面可以通过一个VO对象描述，VO对象是由BO转化而来，但有时需要制定rule来决定改如何转化 2020年1月19日22:33:44
