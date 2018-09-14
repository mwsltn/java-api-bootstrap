package net.tretin.api.core;

import static junit.framework.TestCase.assertNotNull;

public class ApiServletTest extends AbstractTestCase<ApiServlet> {
    public ApiServletTest() {
        super(ApiServlet.class);
    }

    @Override
    public void testInstantiatedObject() {
        assertNotNull(getT());
    }

}