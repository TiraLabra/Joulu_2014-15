IF ("%1" == "") goto :END
IF ("%2" == "") goto :END
IF ("%3" == "") goto :END
IF ("%1" == "-generate_keys") java -jar .\dist\Encryption.jar -generate_keys
IF ("%1" == "-encrypt") java -jar .\dist\Encryption.jar -encrypt %2 %3
IF ("%1" == "-decrypt") java -jar .dist\Encryption.jar -decrypt %2 %3 
:END 
java -jar .\dist\Encryption.jar