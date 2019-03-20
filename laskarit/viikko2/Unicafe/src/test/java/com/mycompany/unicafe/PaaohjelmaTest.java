package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class PaaohjelmaTest extends Paaohjelma {

    Paaohjelma paaohj;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        paaohj = new Paaohjelma();
    }

    @Before
    public void setUpStream() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testaaMain() {
        String[] args = {"one", "two", "three"};
        main(args);
        assertEquals("1\nsaldo: 97.60\n", outContent.toString());
    }

}
