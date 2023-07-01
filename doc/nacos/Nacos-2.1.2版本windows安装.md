# 1.安装包下载

下载地址：

https://github.com/alibaba/nacos/releases/download/2.1.2/nacos-server-2.1.2.zip

我这边是使用迅雷下载的

![image-20221107205339559](http://mxchen-figure-bed.oss-cn-hangzhou.aliyuncs.com/img/2022/11/07/20221107215645.png)

解压到 D:\software\env\nacos-server-2.1.2 下（这里换成自己的安装地址）

解压目录：

![image-20221107205608075](http://mxchen-figure-bed.oss-cn-hangzhou.aliyuncs.com/img/2022/11/07/20221107215650.png)

![image-20221107205617217](http://mxchen-figure-bed.oss-cn-hangzhou.aliyuncs.com/img/2022/11/07/20221107215657.png)

nacos支持自带内存数据库，外置数据源

这里就直接使用外置数据源 要使用mysql5.7+

mysql的安装这里就不详细罗列了，就放出我参考的链接吧

[(239条消息) MySQL 5.7详细下载安装配置教程_乞力马扎罗の黎明的博客-CSDN博客_mysql5.7](https://blog.csdn.net/qq_39715000/article/details/123534326)

也许在安装的过程中会遇到“由于找不到MSVCR120.dll，无法继续执行代码。重新安装程序可能会解决此问题”

或者“由于找不到VCRUNTIME140_1.dll，无法继续执行代码。重新安装程序可能会解决此问题”

这种错误是由于电脑系统缺少部分配置文件引起的，安装 vcredist 下载相关配置文件即可。

解决办法
1、官网地址 (vcredist) ：https://www.microsoft.com/zh-CN/download/details.aspx?id=40784

也可以直接在网盘下载安装包

链接: https://pan.baidu.com/s/1mMBtyr1loInC5DfCPsrSQw

提取码: wyn6

2、安装刚才下载的那个软件，位置默认即可直接点修复，根据软件提示操作即可



**好了回归正文**

# 2.设置外置数据源

进入nacos/conf下找到对应的mysql的sql文件

![image-20221107211027547](http://mxchen-figure-bed.oss-cn-hangzhou.aliyuncs.com/img/2022/11/07/20221107215703.png)

创建数据库nacos2.1.2 (名字自己取，与下方的mysql链接对应)

![image-20221107212756793](http://mxchen-figure-bed.oss-cn-hangzhou.aliyuncs.com/img/2022/11/07/20221107215707.png)

修改application.properties

![image-20221107211134413](http://mxchen-figure-bed.oss-cn-hangzhou.aliyuncs.com/img/2022/11/07/20221107215710.png)

修改内容：

![image-20221107211406158](http://mxchen-figure-bed.oss-cn-hangzhou.aliyuncs.com/img/2022/11/07/20221107215714.png)

```yaml
#*************** Config Module Related Configurations ***************#
### If use MySQL as datasource:
spring.datasource.platform=mysql

### Count of DB:
db.num=1

### Connect URL of DB:
db.url.0=jdbc:mysql://127.0.0.1:3306/nacos2.1.2?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC
db.user.0=root
db.password.0=123456
```

# 3.设置单机模式并启动

进入 nacos/bin下找到startup.cmd

![image-20221107212134274](http://mxchen-figure-bed.oss-cn-hangzhou.aliyuncs.com/img/2022/11/07/20221107215719.png)

找到mode 修改为 单机模式 standalone

修改前：

![image-20221107212231293](http://mxchen-figure-bed.oss-cn-hangzhou.aliyuncs.com/img/2022/11/07/20221107215723.png)

修改后

![image-20221107212313501](http://mxchen-figure-bed.oss-cn-hangzhou.aliyuncs.com/img/2022/11/07/20221107215728.png)

保存后点击启动startup.cmd

![image-20221107212403262](http://mxchen-figure-bed.oss-cn-hangzhou.aliyuncs.com/img/2022/11/07/20221107215731.png)

启动后出现运行窗口

![image-20221107212938768](http://mxchen-figure-bed.oss-cn-hangzhou.aliyuncs.com/img/2022/11/07/20221107215735.png)

当出现

![image-20221107213010177](http://mxchen-figure-bed.oss-cn-hangzhou.aliyuncs.com/img/2022/11/07/20221107215739.png)

代表运行成功

访问刚刚出现的地址

![image-20221107213102185](http://mxchen-figure-bed.oss-cn-hangzhou.aliyuncs.com/img/2022/11/07/20221107215627.png)

输入账号密码：(默认的)

账号：nacos

密码：nacos

![image-20221107213203872](http://mxchen-figure-bed.oss-cn-hangzhou.aliyuncs.com/img/2022/11/07/20221107215746.png)

nacos启动就到此为止了

# 4.注册windows服务（扩展）

**接下来扩展内容**

有时候我不想手动点击，我想让它开机自启动，后台运行

那么这时候我们就借助一个工具将其注册成windows服务

## 下载 Windows Service Wrapper 工具

下载地址：[https://github.com/winsw/winsw/releases/tag/v2.11.0](https://github.com/winsw/winsw/releases/tag/v2.11.0)

默认下载后的名字为WinSW.NET4.exe  
![](https://mxchen-figure-bed.oss-cn-hangzhou.aliyuncs.com/picBed/d0cc66861f1a4a35bd251dc0de858985.png)  

## 下载该工具后

* 将WinSW.NET4.exe重命名为nacos-service.exe并放在nacos的bin目录下

* 创建配置文件nacos-service.xml  

  ![image-20221107213827246](http://mxchen-figure-bed.oss-cn-hangzhou.aliyuncs.com/img/2022/11/07/20221107215752.png)

**nacos-service.xml 内容如下**

```
<service>
  <!-- 唯一服务ID-->
  <id>nacos</id>
  <!-- 显示服务的名称 -->
  <name>Nacos Service</name>
  <!-- 服务描述 -->
  <description>Nacos服务</description>
  <!-- 日志路径 -->
  <logpath>D:\software\env\nacos-server-2.1.2\nacos\bin\logs\</logpath>
  <!-- 日志模式 -->
  <logmode>roll</logmode>
  <!-- 可执行文件的命令 -->
  <executable>D:\software\env\nacos-server-2.1.2\nacos\bin\startup.cmd</executable>
  <!-- 停止可执行文件的命令 -->
  <stopexecutable>D:\software\env\nacos-server-2.1.2\nacos\bin\shutdown.cmd</stopexecutable>
</service>
```

**注意将里面的路径改为你`nacos`的路径，这里标注一下：**  
![image-20221107215140895](http://mxchen-figure-bed.oss-cn-hangzhou.aliyuncs.com/img/2022/11/07/20221107215757.png)

cmd进入到nacos的bin目录  

![image-20221107214259378](http://mxchen-figure-bed.oss-cn-hangzhou.aliyuncs.com/img/2022/11/07/20221107215801.png)


将Nacos注册到Windows服务

```
nacos-service.exe install

```

注册成功  
  ![image-20221107214547685](http://mxchen-figure-bed.oss-cn-hangzhou.aliyuncs.com/img/2022/11/07/20221107215805.png)

如果需要卸载，进入到nacos的bin目录下，cmd 打开窗口，执行此命令：

```
nacos-service.exe uninstall

```

![image-20221107215251539](http://mxchen-figure-bed.oss-cn-hangzhou.aliyuncs.com/img/2022/11/07/20221107215809.png)



查看nacos服务  （找到电脑右键管理)

![image-20221107214658113](http://mxchen-figure-bed.oss-cn-hangzhou.aliyuncs.com/img/2022/11/07/20221107215812.png)  找到注册的服务，右键启动

![image-20221107215424155](http://mxchen-figure-bed.oss-cn-hangzhou.aliyuncs.com/img/2022/11/07/20221107215615.png)

启动成功之后，浏览器测试[http://localhost:8848/nacos/index.html](http://localhost:8848/nacos/index.html)  
 ![image-20221107215555545](http://mxchen-figure-bed.oss-cn-hangzhou.aliyuncs.com/img/2022/11/07/20221107215818.png)

 有关nacos安装就真的到此为止！！！
 希望能够帮助到大家，在此，与君共勉。	**记：2022年11月07日**
