# Arkkitehtuurikuvaus

### Rakenne
Koodin pakkausrakenne on seuraava:

![](https://github.com/riinaalisah/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/pakkauskaavio.png)


Pakkaus *sanastosovellus.ui* sisältää käyttöliittymän, joka on toteutettu JavaFX:llä, *sanastosovellus.domain* sisältää sovelluslogiikan ja *sanastosovellus.dao* vastaa tietojen tallennuksesta.

### Käyttöliittymä

Käyttöliittymään sisältyy neljä erilaista näkymää:
- sisäänkirjautuminen
- uuden käyttäjän luominen
- sanaparien listaus
- sanojen harjoittelu

Jokainen näkymä on toteutettu omana Scene-olionaan. Käyttöliittymä on rakennettu luokassa [sanastosovellus.ui.SanastosovellusUI](https://github.com/riinaalisah/ot-harjoitustyo/blob/master/src/main/java/sanastosovellus/ui/SanastosovellusUI.java).

### Sovelluslogiikka

Sovelluksen datamallin muodostavat luokat [User](https://github.com/riinaalisah/ot-harjoitustyo/blob/master/src/main/java/sanastosovellus/domain/User.java)
ja [WordPair](https://github.com/riinaalisah/ot-harjoitustyo/blob/master/src/main/java/sanastosovellus/domain/WordPair.java), jotka kuvaavat käyttäjiä ja heidän lisäämiä sanapareja:

![](https://github.com/riinaalisah/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/userJaWordpair.png)

Toiminnallisuuksista vastaa luokka [AppService](https://github.com/riinaalisah/ot-harjoitustyo/blob/master/src/main/java/sanastosovellus/domain/AppService.java) tarjoamalla mm. seruaavat metodit:
- boolean login(String username, String pwd)
- boolean createUser(String username, String pwd)
- List<WordPair> getPairs()
- boolean addWordPair(String word, String translation)

*AppService* pääsee käsiksi käyttäjien ja sanaparien tietoihin pakkauksessa *sanastosovellus.dao* sijaitsevien rajapintojen *UserDao* ja *WordPairDao* toteuttavien luokkien avulla. 

Sovelluksen eri osien suhteita kuvaava luokka-/pakkauskaavio:

![](https://github.com/riinaalisah/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/luokka_pakkauskaavio.png)

### Tietojen pysyväistallennus

Pakkauksen *sanastosovellus.dao* luokat *FileUserDao* ja *FileWordPairDao* huolehtivat tietojen tallentamisesta tiedoistoihin. Luokat noudattavat Data Access Object -mallia ja ne on eristetty rajapintojen *UserDao* ja *WordPairDao* taakse, eikä sovelluslogiikka käytä niitä suoraan. 

#### Tiedostot

Sovellus tallentaa käyttäjien ja sanaparien tiedot erillisiin tiedostoihin. Tiedostojen nimet määritellään sovelluksen juuren konfiguraatiotiedostossa [config.properties](https://github.com/riinaalisah/ot-harjoitustyo/blob/master/config.properties).

Käyttäjien tiedot tallennetaan seuraavassa muodossa:

```
myUsername;myPwd
alisa;alisasPwd
```

eli käyttäjätunnus ennen ensimmäistä puoltapistettä, ja salasana sen jälkeen.

Sanaparien tiedot tallennetaan seuraavasti:

```
1;kissa;cat;myUsername
2;koira;dog;alisa
3;kukka;flower;myUsername
```

Ensimmäisessä kentässä on sanaparin *id*, toisessa itse sana, kolmannessa sanan käännös ja neljännessä sanaparin luoneen käyttäjän käyttäjänimi.

### Päätoiminnallisuudet

#### Sisäänkirjautuminen

Kun käyttäjä on syöttänyt kirjautumisnäkymässä käyttäjänimensä ja salasanansa, sekä klikannut sisäänkirjautumispainiketta, etenee sovelluksen kontrolli seuraavasti:

![](https://github.com/riinaalisah/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/sisaankirjautuminen.png)

Painikkeen painamiseen reagoiva [tapahtumakäsittelijä](https://github.com/riinaalisah/ot-harjoitustyo/blob/c5093c3ca444398ed1920844d07f2bb72f1bde7f/src/main/java/sanastosovellus/ui/SanastosovellusUI.java#L124) kutsuu sovelluslogiikan *appService* metogia [login](https://github.com/riinaalisah/ot-harjoitustyo/blob/c5093c3ca444398ed1920844d07f2bb72f1bde7f/src/main/java/sanastosovellus/domain/AppService.java#L63) antaen parametreiksi syötetyn käyttäjänimen ja salasanan.
Sovelluslogiikka selvittää *userDao*n avulla onko kyseistä käyttäjää olemassa, ja jos on, se varmistaa vielä että salasana täsmää *User*-luokan metodin *getPassword()* avulla. Jos sekin täsmää, kirjautuminen onnistuu ja näkymäski vaihdetaan päänäkymä, eli *appScene* ja sivulle listtaan kirjautuneen käyttäjän lisäämät sanaparit metodilla *redrawWordPairList*.


#### Uuden käyttäjän luominen

Kun käyttäjä on syöttänyt uuden käyttäjän luominen -näkymässä sellaisen nimimerkin, joka ei ole vielä käytössä, sekä salasanan, ja on klikannut *createNewUser*-painiketta, etenee sovelluksen kontrolli seuraavasti:

![](https://github.com/riinaalisah/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/uudenKayttajanLuominen.png)

[Tapahtumakäsittelijä](https://github.com/riinaalisah/ot-harjoitustyo/blob/c5093c3ca444398ed1920844d07f2bb72f1bde7f/src/main/java/sanastosovellus/ui/SanastosovellusUI.java#L124) 
kutsuu sovelluslogiikan metodia [createUser](https://github.com/riinaalisah/ot-harjoitustyo/blob/c5093c3ca444398ed1920844d07f2bb72f1bde7f/src/main/java/sanastosovellus/domain/AppService.java#L94) 
antaen parametreiksi käyttäjän syöttämät tiedot (nimierkki ja salasana). Sovelluslogiikka selvittää *userDao*n avulla onko kyseistä käyttäjää olemassa, ja jos ei ole, luo sovelluslogiikka uuden *User*-olion ja tallentaa sen kutsumalla *UserDao*n metodia *create*. Tämän jälkeen näkymäksi vaihdetaan sisäänkirjautumisnäkymä.

#### Sanaparin lisääminen

Kun käyttäjä on klikannut *addWordPair*-painiketta, etenee sovelluksen kontrolli seuraavasti:

![](https://github.com/riinaalisah/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/sanaparinLuominen.png)

[Tapahtumakäsittelijä](https://github.com/riinaalisah/ot-harjoitustyo/blob/c5093c3ca444398ed1920844d07f2bb72f1bde7f/src/main/java/sanastosovellus/ui/SanastosovellusUI.java#L124) kutsuu
sovelluslogiikan metodia *addWordPair* antaen parametreina luotavan sanaparin osat (sana ja sen käännös). Sovelluslogiikka luo uuden *WordPair*-olion ja tallentaa sen kutsumalla *WordPairDao*n metodia *create*. Tämän jälkeen päänäkymän sanaparien listaus päivitetään kutsumalla käyttöliittymän metodia *redrawWordPairList*
