# frame_mvp


代码库里架构和独特的MVP模式设计和代码解藕的方法，是借鉴了一位国际友人的构思。

    里面的一些积累都是来自于，后期一些项目中的应用，工具类的集合等等。这个代码库投入后来一些项目中使用，
    也积累了一些新的东西。
    
    当然，这个代码也是后面一些项目一点点的积累出来的。
    
    
为何要开源这个项目？
	
	因为，每次碰到一个新项目，不管是私活，还是公司的新项目，很多情况下私活的老板或者公司的产品狗特么的跟疯狗一样，
	今天需求还没确定就希望明天开始动手做项目，后天就上线，特么的你说你搭框架要一个星期，他们会容忍？
	怎么可能！
	
# 这个项目既然是个基础的框架那么包含了什么？
## 1.初始化目录
 app第一次启动根据FrameApplication中配置的project_name，在SD卡根目录创建的文件夹。然后文件夹内会自动创建，log，data,.img,db,其中img目录可以配置为隐藏的，也可以不配置为隐藏的，重要的在于img目录做了配置，里面创建了一个隐藏文件。当每次开机启动时，mediaservice服务搜索目录刷新相册时，就不会把img目录下的图片加入到相册的数据中去。
 
 	PS:为何是在SD卡创建这个文件？首先较老的android 系统是不建议往内部存储空间写入东西的，内部存储空间有限，
	就连imageloader都默认在sd卡的.android目录下写img缓存。所以这是一个好习惯。
	虽然新版android 对这个没有太多考虑了，但是这样子还是比较方便调试，和找bug。
	
# 
## 2.整体架构：*精简版的MVP*
为何是精简版？MVP本身带来的问题就是代码量的问题，代码量太大，所以这里精简下来。act，frag可以直接调用presenter 的public方法，不需要建立interface，加速开发。
设计模式的目的是什么？解藕。
只要达到了我们解藕的目的，何必在意自己在用什么设计模式，理解了设计模式的精髓。你完全可以自创设计模式。所以为了快速开发，我们尽量用一些android提供的方法去加速开发，或者通过精简的设计模式来加速团队的开发。

## 3.提供基础的组件和常用的控件：

>- 仿IOS效果的switch控件，轮播图控件，保持宽高比的自定义控件基于Layout，ImageView的都有实现。
>- 图片载入的控件，圆形头像，圆角等等的实现。fresco提供的圆角和原型方案是性能最高的，从网上看到的各种某某CSDN大神的博客，往年的CSDN博客之星，各种实现的方案，明显性能很差，有的还在用Xfmode的方案，卧槽！！！看我不打你。框架里同时也导入了imageloader和glide，当然，随你所愿。我自己还是比较喜欢用fresco，毕竟性能第一。
>- 下拉刷新，上拉加载更多的列表控件。 分别作两种一种是以listview为基础的，另外一种是以recclerview为基础的。recyclerview在一些列表刷新的性能上有很好的改善。同时横向列表的实现更加简单。
>- 对第三方网络请求框架的封装。把网络请求封装到Request 和 Response中，这样子，不需要非常过程化思想的暴露出，url params，header等等。Response自动返回java bean。整个过程非常的面相对象，开发者不需要知道内部的流程，想要发送一个网络请求时，基于BaseRequst建立一个子类，提供网络请求的action，往里面传递参数，网络请求回来时就是一个java bean，整个过程如行云流水。
>- 跨页面之间相互通信，使用本地广播类LocalBroadCast类来做这些工作，LocalBroadcast不同于另外一个广播，这个东西内部是handler实现，极高的性能和安全性。发送出去的广播不会被外部的应用接受到。
>- 一些基础的工具类，常用的，比如网络判断，键盘的隐藏。当然工具类后续还会更新。

# 这个开源项目中存在不足

由于项目中加入了各种人的代码，和一些找来的开源控件，每个人的代码质量良莠不齐，不同的人不同的代码风格。但是我自己的能做的就是：尽量能让代码的易读性完美到，不需要一行注释都能让人可以快速的阅读。









	