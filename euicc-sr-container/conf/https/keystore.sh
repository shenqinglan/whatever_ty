#创建证书
keytool -genkey -alias mahaifeng -keypass mahaifeng -keyalg RSA -keysize 1024 -keystore https.keystore -storepass mahaifeng
#导出证书
keytool -export -keystore https.keystore -alias mahaifeng -file https.crt -storepass mahaifeng