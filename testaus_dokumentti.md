Projektissa on kahdenlaisia testejä:

Tiedostot *PersistentHashMapTest.java*, *PersistentVectorTest.java* testaavat tietorakenteiden oikeellisuutta ja ovat nopeita ajaa.
Tiedostot *PersistentVectorSlow.java* *PersistentHashMapTestSlow.java* sisältävät aikarajoitettuja testejä isohkoilla syötteillä.

Projekti sisältää myös benchmarktestejä. Seuraavassa ohjeita niiden ajamiseen.

Suorita seuraavat kommennot projektin juuresta:
```
cd test
mvn clean install
java -jar target/benchmarks.jar
```
