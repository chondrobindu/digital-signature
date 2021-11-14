keytool -genkey -alias signature -keystore apiProvider.jks
password:changeme

keytool -export -keystore apiProvider.jks -alias signature -file apiProvider.cer

keytool -import -alias signature -file apiProvider.cer -keystore apiConsumer.jks

https://stackoverflow.com/questions/56520582/digital-signature-different-everytime/56527077
https://stackoverflow.com/questions/16325057/why-does-rsa-encrypted-text-give-me-different-results-for-the-same-text?noredirect=1&lq=1
https://stackoverflow.com/questions/5883451/are-rsa-signatures-unique?noredirect=1&lq=1
https://stackoverflow.com/questions/18329526/same-rsa-keys-same-message-different-encrypts?noredirect=1&lq=1