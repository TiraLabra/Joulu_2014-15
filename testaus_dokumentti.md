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
Benchmarkit sisältävä tiedosto on
test/src/main/java/org/sample/PersistentDataStructuresBenchmarks.java

Seuraavassa joitain benchmarktuloksia omalla koneellani ajettuna.

data structure | operation | ops/ms
:---------------|:-----------|:-------
PersistentVector  | lookup | 79218.871 ±  224.499
ArrayList | lookup | 241406.231 ± 3389.042
PersistentHashMap | lookup | 32896.173 ±  201.312
HashMap | lookup | 113245.784 ±  401.141
