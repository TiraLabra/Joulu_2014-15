Joulu_2014 TIRA HARJOITUSTYÖ
============================

Tämän harjoitustyön aiheena on salausalgoritmi, tarkemmin RSA.

Ohjelma suoritetaan komentoriviltä komennolla: 
java -jar Encryption.jar <optional command> <optional.key> <optional.file>

Avainten luonti tapahtuu komennolla:
java -jar Encryption.jar -generate_keys
Tämä luo avainparin tiedostoihin public.key ja private.key.

Tiedoston sisällön salaus:
java -jar Encryption.jar -encrypt <public.key> <file to encrypt>
Tämä luo tiedoston encrypted.txt.

Salauksen purku:
java -jar Encryption.jar -encrypt <private.key> <file to decrypt>
Tämä luo tiedoston decrypted.txt.