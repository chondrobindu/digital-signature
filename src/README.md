keytool -genkey -alias signature -keystore apiProvider.jks
password:changeme

keytool -export -keystore apiProvider.jks -alias signature -file apiProvider.cer

keytool -import -alias signature -file apiProvider.cer -keystore apiConsumer.jks
