package net.tretin.api.core;

import com.google.inject.Stage;
import org.junit.Before;
import org.junit.Test;

public class ApiServletTest {

    @Before
    public void setUp() {
        new ApiGuice(
                Stage.DEVELOPMENT,
                ApiServletModule.builder().addPackage("net.tretin.api").build(),
                ApiServerModule.builder().build()
        ).injector().getInstance(ApiServlet.class);
    }

    @Test
    public void noop() {
    }


}