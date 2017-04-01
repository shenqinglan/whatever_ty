一、广研院机房
服务器1（戴尔R920）
内网 192.168.100.200  网关：192.168.244.254
外网(纯) 121.32.89.211 Bcast：121.32.89.223  网关：121.32.89.209

服务器2（戴尔R720）:
内网 192.168.100.202  网关：192.168.244.254
外网 192.168.244.202  Bcast：192.168.244.255  网关：192.168.244.254

正确外网地址：121.33.232.98，121.32.89.211

二、部署以及服务启用
1、解压euicc-sr-container-0.0.1-bin.zip
unzip euicc-sr-container-0.0.1-bin.zip
2、ecc环境变量配置：
cp libEccArth.so /usr/lib
3、查看dp,sr进程 
ps -ef | grep euicc
4、杀掉进程
kill -9 进程号
5、启动进程 
cd euicc-sr-container-0.0.1
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
tool_ip:连卡工具IP
tool_port:连卡工具端口

sms_connectType：发送短信是连卡还是调用短信网关(0:tool,1:SMSAGENT)
sms_url:短信网关地址

ecc_call_type:：ecc函数调用方式(0:调用DLL,1:调用服务，暂未提供)
ecc_tool_ip:ECC服务IP
ecc_tool_port:ECC服务端口

sr_change_type:测试类型（1:single 2:two台主机）
sr_receive_url:SM-SR CHANGER接收地址
sr_send_url:SM-SR CHANGER发送地址

4、jdbc.properties(数据库连接)
jdbc.url:数据库连接地址
jdbc.username:数据库连接用户名
jdbc.password:数据库连接密码