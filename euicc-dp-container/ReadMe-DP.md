一、服务启用
1、上传zip压缩包到主机，解压euicc-dp-container-0.0.1-bin.zip
unzip euicc-dp-container-0.0.1-bin.zip
2、查看dp,sr进程 
ps -ef | grep euicc
3、杀掉进程
kill -9 进程号
4、启动进程 
cd euicc-dp-container-0.0.1
sh startup.sh

二、配置文件说明：
1、cache.cfg(redis配置)
host:redisIP
port:redis端口

2、config.properties(与环境无关配置)
sr_port_https:sr平台https端口
sr_port_http:sr平台http端口
tls_port:sr平台tls端口

3、euicc.properties(与环境有关配置)
sr_url:sr平台地址

tool_ip:连卡工具IP
tool_port:连卡工具端口

ecc_call_type:：ecc函数调用方式(0:调用DLL,1:调用服务，暂未提供)
ecc_tool_ip:ECC服务IP
ecc_tool_port:ECC服务端口

4、jdbc.properties(数据库连接)
jdbc.url:数据库连接地址
jdbc.username:数据库连接用户名
jdbc.password:数据库连接密码