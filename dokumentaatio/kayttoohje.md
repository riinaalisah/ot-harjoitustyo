# Käyttöohje

### Konfigurointi

Ohjelman suoritushakemiston tulee sisältää konfiguraatiotiedoston *config.properties*, jonka sisällön tulee olla 
```
userFile=users.txt
wordPairFile=wordpairs.txt
```

### Käynnistäminen

Ohjelma käynnistetään komennolla 
```
java -jar sanastosovellus.jar
```

### Kirjautuminen

Kirjautuakseen sisään käyttäjän tulee syöttää oma käyttäjänimi ja salasana, ja painaa *Kirjaudu sisään*.

### Uuden käyttäjän luominen

Uuden käyttäjän luominen -näkymään pääsee kirjautumisnäkymästä painamalla nappia *Luo uusi käyttäjä*. 

Uudelle käyttäjälle tulee syöttää käyttäjänimi ja salasana.
Molempien tulee olla vähitnää 5 merkkiä pitkät ja käyttäjänimen tulee olla uniikki.
Käyttäjä lisätään painamalla *Luo uusi käyttäjä* -painiketta. Jos uusi käyttäjä luodaan onnistuneesti,
siirtyy näkymä takaisin kirjautumisnäkymään.

### Sanaparin lisääminen

Uuden sanaparin pystyy lisäämään päänäkymän alareunassa. Kenttään *Sana* syötetään sana ja kenttään *Käännös* sanan käännös haluamalla kielellä.
Sanapari lisätään painamalla *Lisää sanapari* -painiketta. Luotu sanapari ilmestyy sanaparien listaan.

### Sanaparin poistaminen

Sanapareja pystyy poistamaan yksitellen tai useamman kerrallaan. Sanaparin saa poistettua valitsemalla
sanaparin vieressä olevan checkboksin ja painamalla sivupalkin *Poista valitut sanaparit* -painiketta.
Tällöin valitut sanaparit poistuvat listasta.

### Sanojen harjoittelu

Sanojen harjoittelunäkymään pääsee painamalla päänäkymän jompaa kumpaa *Harjoittele*-painiketta,
riippuen siitä, kumpaan suuntaan sanoja haluaa harjoitella.

Harjoittelunäkymässä näytetään käyttäjän lisäämiä sanaparien sanoja/käännöksiä satunnaisessa järjestyksessä. 
Käyttäjän tulee syöttää sanaa/käännöstä vastaava käännös/sana tekstikenttään.
Kun käyttäjä painaa *Vastaa*-painiketta, käyttäjälle kerrotaan oliko vastaus oikein vai väärin.
Jos vastaus on oikein, käyttäjälle näytetään seuraava sana/käännös. Jos taas vastaus on väärin, käyttäjä saa yrittää uudelleen. 
Painikkeesta *Ohita* käyttäjä voi ohittaa sanan, jolloin hänelle näytetään oikea vastaus ja annetaan uusi sana. 
Painikkeesta *Lopeta* pystyy palaamaan päänäkymään.



