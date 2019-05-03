# Testausdokumentti

Kaikille luokille lukuunottamatta käyttöliittymäluokkaa *SanastosovellusUI* on luotu JUnit-testit. Järjestelmätason testit on 
suoritettu manuaalisesti. 

### Yksikkö- ja integraatiotestaus

#### Testauskattavuus

Käyttöliittymäluokkaa ei ole testattu. Muiden luokkien tapauksessa testauksen rivikattavuus on 92% ja haaraumakattavuus 95%.

![](https://github.com/riinaalisah/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/jacoco.png)


#### Sovelluslogiikka

Sovelluslogiikkaluokan [AppService](https://github.com/riinaalisah/ot-harjoitustyo/blob/master/src/main/java/sanastosovellus/domain/AppService.java) testit 
on eritelty käyttäjä- ja sanaparitoimintoihin testausluokkiin [AppServiceUserTest](https://github.com/riinaalisah/ot-harjoitustyo/blob/master/src/test/java/domain/AppServiceUserTest.java) ja 
[AppServiceWordPairTest](https://github.com/riinaalisah/ot-harjoitustyo/blob/master/src/test/java/domain/AppServiceWordPairTest.java).
 Molemmissa luokissa testitapaukset simuloivat käyttöliittymäluokan toiminnallisuuksia. 
 
 Integraatiotesteissä datan pysyväistallennukseen käytetään DAO-rajapintojen toteutuksia [FakeUserDao](https://github.com/riinaalisah/ot-harjoitustyo/blob/master/src/test/java/dao/FakeUserDao.java)
  ja [FakeWordPairDao](https://github.com/riinaalisah/ot-harjoitustyo/blob/master/src/test/java/dao/FakeWordPairDao.java). 
  
  Luokille [User](https://github.com/riinaalisah/ot-harjoitustyo/blob/master/src/main/java/sanastosovellus/domain/User.java)
  ja [WordPair](https://github.com/riinaalisah/ot-harjoitustyo/blob/master/src/main/java/sanastosovellus/domain/WordPair.java)
  on luotu muutama testi sellaisille tapauksille, joita integraatiotestit eivät kata. 
  
 #### DAO-luokat
  
  Molempien DAO-luokkien testauksessa on hyödynnetty JUnitin TemporaryFolder-ruleja.
  
 
 ### Järjestelmätestaus
 
 Järjestelmätestaus on suoritettu manuaalisesti. 
 
 #### Asennus ja konfigurointi
 
 Sovellusta on testattu [käyttöohjeen](https://github.com/riinaalisah/ot-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md) kuvaamalla tavalla Linux-ympäristössä siten, että käynnistyshakemistoon on luotu käyttöohjeen
 kuvauksen mukainen *config.properties* -tiedosto.
 
Sovellusta on testattu kahdenlaisissa tilanteissa: tilanteessa, jossa käyttäjiä ja sanapareja tallentavia tiedostoja ei ole ollut olemassa
(eli ohjelma on luonut ne itse), sekä tilanteessa, jossa ne ovat olleet jo olemassa.
 
 #### Toiminnallisuudet
 
 Testauksessa on käyty läpi kaikki määrittelydokumentin ja käyttöohjeen listaamat toiminnallisuudet. Kenttiin on myös kokeiltu syöttää virheellisiä, kuten liian lyhyitä, arvoja.
 
