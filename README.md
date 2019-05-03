## Sanastosovellus

Sovellus on tehty *Ohjelmistotekniikka*-kurssia varten.


Sovelluksen avulla käyttäjät pystyvät opettelemaan haluamiaan sanoja kahden kielen välillä. Käyttäjät pystyvät sekä lisäämään että poistamaan sanapareja tarpeen mukaan, ja vaihtamaan sanojen opettelusuuntaa. Sovellukseen kirjaudutaan käyttäjätunnuksen ja salasanan avulla. 

### Releaset
[Loppupalautus](https://github.com/riinaalisah/sanastosovellus/releases/tag/Loppupalautus)

Sovelluksen saa suoritettua komennolla
```
java -jar sanastosovellus.jar
```



### Dokumentaatio

[Käyttöohje](https://github.com/riinaalisah/ot-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md)

[Vaatimusmäärittely](https://github.com/riinaalisah/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Arkkitehtuuri](https://github.com/riinaalisah/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

[Testaus](https://github.com/riinaalisah/sanastosovellus/blob/master/dokumentaatio/testaus.md)

[Työaikakirjanpito](https://github.com/riinaalisah/ot-harjoitustyo/blob/master/dokumentaatio/tyoaikakirjanpito.md)


### Komentorivitoiminnot

#### Jarin generointi

Suoritettavan jar.tiedoston saa luotua komennolla
```
mvn package
```

Kansioon *target* luodaan tällöin jar-tiedosto *sanastosovellus-1.0-SNAPSHOT.jar*

#### Testaus

Testit voidaan suorittaa komennolla 
```
mvn test
```

Testikattavuusraportti luodaan komennolla
```
mvn test jacoco:report
```

Raporttia pääsee tarkastelemaan avaamalla selaimessa tiedoston *target/site/jacoco/index.html*

#### Checkstyle
Checkstylen tarkistukset voidaan suorittaa komennolla
```
mvn jxr:jxr checkstyle:checkstyle
```

Mahdolliset virheet löytää avaamalla selaimessa tiedoston *target/site/checkstyle.html*


#### Javadoc
Javadoc voidaan luoda komennolla
```
mvn javadoc:javadoc
```

JavaDocia pääsee tarkastelemaan avaamalla selaimessa tiedoston *target/site/apidocs/index.html*


