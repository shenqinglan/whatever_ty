一、、服务启用
1、上传war至tomcat/webapp
2、查看ms进程 
ps -ef | grep tomcat
3、杀掉进程
kill -9 进程号
4、启动进程 
cd tomcat/bin
nohup ./startup.sh &

二、配置文件说明：
1、cache.cfg(redis配置，给cache使用)
host:redisIP
port:redis端口

2、config.properties(与环境有关配置)
is_call_server:是否调用服务操作数据库(1:调用服务操作数据库  0:本地操作数据库)

3、euicc.properties(与环境无关配置)
dp_url:dp平台http端口
sr_url:sr平台http端口

4、jdbc.properties(数据库连接)
jdbc.url:数据库连接地址
jdbc.username:数据库连接用户名
jdbc.password:数据库连接密码

5、redis。properties(redis配置)
redis.ip:redisIP
redis.port:redis端口