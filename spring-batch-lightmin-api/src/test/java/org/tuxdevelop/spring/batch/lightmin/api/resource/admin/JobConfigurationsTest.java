package org.tuxdevelop.spring.batch.lightmin.api.resource.admin;

import org.tuxdevelop.spring.batch.lightmin.PojoTestBase;


public class JobConfigurationsTest extends PojoTestBase {

    @Override
    public void performPojoTest() {
        testStructureAndBehavior(JobConfigurations.class);
        testEquals(JobConfigurations.class);
    }
}
