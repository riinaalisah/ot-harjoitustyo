package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import javax.swing.text.MaskFormatter;

public class KassapaateTest {

    Kassapaate kassa;

    @Before
    public void setUp() {
        kassa = new Kassapaate();
    }

    @Test
    public void luotuKassapaateOlemassa() {
        assertTrue(kassa != null);
    }

    @Test
    public void rahaaAlussaOikeaMaara() {
        assertEquals(100000, kassa.kassassaRahaa());
    }

    @Test
    public void myytyjaLounaitaAlussaNolla() {
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }


    // syo edullisesti -testit
    @Test
    public void syoEdullisestiKassanRahamaaraKasvaaKunMaksuRiittava() {
        kassa.syoEdullisesti(260);
        assertEquals(100240, kassa.kassassaRahaa());
    }

    @Test
    public void syoEdullisestiVaihtorahaOikein() {
        assertEquals(60, kassa.syoEdullisesti(300));
    }

    @Test
    public void syoEdullisestiPalauttaaMaksunKunRahaaLiianVahan() {
        assertEquals(210, kassa.syoEdullisesti(210));
    }

    @Test
    public void syoEdullisestiKassanRahamaaraEiMuutuKunMaksuLiianVahan() {
        kassa.syoEdullisesti(210);
        assertEquals(100000, kassa.kassassaRahaa());
    }

    @Test
    public void edullistenLounaidenMyyntiKasvattaaMyytyjenLounaidenMaaraa() {
        kassa.syoEdullisesti(240);
        kassa.syoEdullisesti(230);
        kassa.syoEdullisesti(240);
        assertEquals(2, kassa.edullisiaLounaitaMyyty());
    }

    @Test
    public void syoEdullisestiMyytyjenLounaidenMaaraEiKasvaKunmaksuLiianVahan() {
        kassa.syoEdullisesti(210);
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }



    // syo maukkaasti -testit
    @Test
    public void syoMaukkaastiKassanRahamaaraKasvaaKunMaksuRiittava() {
        kassa.syoMaukkaasti(410);
        assertEquals(100400, kassa.kassassaRahaa());
    }

    @Test
    public void syoMaukkaastiVaihtorahaOikein() {
        assertEquals(30, kassa.syoMaukkaasti(430));
    }

    @Test
    public void syoMaukkaastiPalauttaaMaksunKunRahaaLiianVahan() {
        assertEquals(350, kassa.syoMaukkaasti(350));
    }

    @Test
    public void syoMaukkaastiKassanRahamaaraEiMuutuKunMaksuLiianVahan() {
        kassa.syoMaukkaasti(350);
        assertEquals(100000, kassa.kassassaRahaa());
    }

    @Test
    public void maukkaidenLounaidenMyyntiKasvattaaMyytyjenLounaidenMaaraa() {
        kassa.syoMaukkaasti(400);
        kassa.syoMaukkaasti(400);
        kassa.syoMaukkaasti(400);
        assertEquals(3, kassa.maukkaitaLounaitaMyyty());
    }

    @Test
    public void syoMaukkaastiMyytyjenLounaidenMaaraEiKasvaKunmaksuLiianVahan() {
        kassa.syoMaukkaasti(350);
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }




    // korttitestit
    @Test
    public void syoEdullisestiVahentaaKortinSaldoaOikein() {
        Maksukortti kortti = new Maksukortti(300);
        kassa.syoEdullisesti(kortti);
        assertEquals(60, kortti.saldo());
    }

    @Test
    public void syoEdullisestiPalauttaaTrueKunKortillaTarpeeksiSaldoa() {
        Maksukortti kortti = new Maksukortti(260);
        boolean tr = kassa.syoEdullisesti(kortti);
        assertTrue(tr);
        boolean fal = kassa.syoEdullisesti(kortti);
        assertTrue(!fal);
    }

    @Test
    public void syoMaukkaastiVahentaaKortinSaldoaOikein() {
        Maksukortti kortti = new Maksukortti(450);
        kassa.syoMaukkaasti(kortti);
        assertEquals(50, kortti.saldo());
    }


    @Test
    public void syoMaukkaastiPalauttaaTrueKunKortillaTarpeeksiSaldoa() {
        Maksukortti kortti = new Maksukortti(500);
        boolean tr = kassa.syoMaukkaasti(kortti);
        assertTrue(tr);
        boolean fal = kassa.syoMaukkaasti(kortti);
        assertTrue(!fal);
    }

    @Test
    public void syoEdullisestiKortillaMyytyjenLounaidenMaaraKasvaa() {
        Maksukortti kortti = new Maksukortti(300);
        kassa.syoEdullisesti(kortti);
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
    }

    @Test
    public void syoMaukkaastiKortillaMyytyjenLounaidenMaaraKasvaa() {
        Maksukortti kortti = new Maksukortti(450);
        kassa.syoMaukkaasti(kortti);
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
    }

    @Test
    public void syoEdullisestiKortinSaldoEiMuutuJosRahaaLiianVahan() {
        Maksukortti kortti = new Maksukortti(200);
        kassa.syoEdullisesti(kortti);
        assertEquals(200, kortti.saldo());
    }

    @Test
    public void syoEdullisestiMyytyjenLounaidenMaaraEiMuutuKunKortinSaldoLiianVahan() {
        Maksukortti kortti = new Maksukortti(200);
        kassa.syoEdullisesti(kortti);
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }

    @Test
    public void syoEdullisestiKassanRahamaaraEiMuutuKortilla() {
        Maksukortti kortti = new Maksukortti(300);
        kassa.syoEdullisesti(kortti);
        assertEquals(100000, kassa.kassassaRahaa());
    }

    @Test
    public void syoMaukkaastiKortinSaldoEiMuutuJosRahaaLiianVahan() {
        Maksukortti kortti = new Maksukortti(350);
        kassa.syoMaukkaasti(kortti);
        assertEquals(350, kortti.saldo());
    }

    @Test
    public void syoMaukkaastiMyytyjenLounaidenMaaraEiKasvaKunKortinSaldoLiianVahan() {
        Maksukortti kortti = new Maksukortti(350);
        kassa.syoMaukkaasti(kortti);
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }

    @Test
    public void syoMaukkaastiKassanRahamaaraEiMuutuKortilla() {
        Maksukortti kortti = new Maksukortti(450);
        kassa.syoMaukkaasti(kortti);
        assertEquals(100000, kassa.kassassaRahaa());
    }



    // rahan lataaminen
    @Test
    public void kortilleLataaminenKasvattaaSaldoaOikein() {
        Maksukortti kortti = new Maksukortti(1000);
        kassa.lataaRahaaKortille(kortti, 240);
        assertEquals("saldo: 12.40", kortti.toString());
    }

    @Test
    public void kortilleLataaminenKasvattaaKassanRahamaaraa() {
        Maksukortti kortti = new Maksukortti(100);
        kassa.lataaRahaaKortille(kortti, 200);
        assertEquals(100200, kassa.kassassaRahaa());
    }

    @Test
    public void kortinSaldoEiMuutuJosLadataanNegatiivinenSumma() {
        Maksukortti kortti = new Maksukortti(1000);
        kassa.lataaRahaaKortille(kortti, -200);
        assertEquals("saldo: 10.0", kortti.toString());
    }
}
