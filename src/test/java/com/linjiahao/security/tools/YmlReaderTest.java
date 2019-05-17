package com.linjiahao.security.tools;

import org.junit.Test;

import static org.junit.Assert.*;

public class YmlReaderTest {

    @Test
    public void load() {
        assertEquals("test", YmlReader.load("YmlReaderTest.string"));
        assertEquals("test", YmlReader.load("YmlReaderTest.list[0]"));
        assertEquals("test", YmlReader.load("YmlReaderTest.list[1].data.string"));
    }

    @Test
    public void load_old() {
        assertEquals("test", YmlReader.load_old("YmlReaderTest.string"));
    }
}