## Sanastosovellus

Sovellus on tehty *Ohjelmistotekniikka*-kurssia varten.


Sovelluksen avulla käyttäjät pystyvät opettelemaan haluamiaan sanoja kahden kielen välillä. Käyttäjät pystyvät sekä lisäämään että poistamaan sanapareja tarpeen mukaan, ja vaihtamaan sanojen opettelusuuntaa. Sovellukseen kirjaudutaan käyttäjätunnuksen ja salasanan avulla. 

### Releaset
[Viikko 6 release](https://github.com/riinaalisah/ot-harjoitustyo/releases/tag/viikko6)

Releasen saa suoritettua komennolla
```
java -jar sanastosovellus2.jar
```



### Dokumentaatio
[Vaatimusmäärittely](https://github.com/riinaalisah/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Arkkitehtuuri](https://github.com/riinaalisah/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

[Työaikakirjanpito](https://github.com/riinaalisah/ot-harjoitustyo/blob/master/dokumentaatio/tyoaikakirjanpito.md)


### Komentorivitoiminnot

#### Testaus

Testit voidaan suorittaa komennolla 
```
mvn test
```

Testikattavuusraportti luodaan komennolla
```
mvn test jacoco:report
```

#### Checkstyle
Checkstyle voidaan suorittaa komennolla
```
mvn jxr:jxr checkstyle:checkstyle
```





