keytool -genkey -alias signature -keystore apiProvider.jks
password:changeme

keytool -export -keystore apiProvider.jks -alias signature -file apiProvider.cer

keytool -import -alias signature -file apiProvider.cer -keystore apiConsumer.jks


Reference
https://tools.ietf.org/id/draft-cavage-http-signatures-09.html
https://docs.oracle.com/javase/tutorial/security/apisign/index.html