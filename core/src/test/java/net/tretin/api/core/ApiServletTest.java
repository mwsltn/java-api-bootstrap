package net.tretin.api.core;

import com.google.inject.Guice;
import net.tretin.api.core.testrs.TestEndpoint;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;

public class ApiServletTest {
    @Test
    public void instantiate() {
        ApiServlet servlet = Guice.createInjector(
                ApiServletModule.builder()
                        .addClass(TestEndpoint.class)
                        .build()
        ).getInstance(ApiServlet.class);
        assertNotNull(servlet);
    }
}