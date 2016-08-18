package com.gcplot.cassandra;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class BaseCassandra {
    protected static Server server;

    @BeforeClass
    public static void before() throws Exception {
        server = new Server();
        server.start();
    }

    @AfterClass
    public static void afterClass() {
        server.stop();
    }

    @After
    public void after() throws Exception {
        server.clean();
    }
}
