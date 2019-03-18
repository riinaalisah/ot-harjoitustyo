package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }

    @Test
    public void kortinSaldoOikeinAlussa() {
        assertEquals("saldo: 0.10", kortti.toString());
    }

    @Test
    public void rahanLisaaminenKasvattaaSaldoaOikein() {
        kortti.lataaRahaa(200);
        assertEquals("saldo: 2.10", kortti.toString());
    }

    @Test
    public void saldoVaheneeOikeinKunRahaaTarpeeksi() {
        kortti.otaRahaa(10);
        assertEquals("saldo: 0.0", kortti.toString());
    }

    @Test
    public void saldoEiMuutuJosRahaaEiTarpeeksi() {
        kortti.otaRahaa(20);
        assertEquals("saldo: 0.10", kortti.toString());
    }

    @Test
    public void palauttaaTrueJosRahatRiittävät() {
        boolean isTrue = kortti.otaRahaa(10);
        assertTrue(isTrue);
        boolean isFalse = kortti.otaRahaa(20);
        assertTrue(!isFalse);
    }
}
