package io.wcm.samples.core.example;

import com.google.common.collect.ImmutableList;
import io.wcm.caconfig.extensions.persistence.impl.PagePersistenceStrategy;
import io.wcm.samples.core.testcontext.AppAemContext;
import io.wcm.sling.commons.adapter.AdaptTo;
import io.wcm.sling.commons.resource.ImmutableValueMap;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.testing.mock.caconfig.MockContextAwareConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(AemContextExtension.class)
class NestedConfigModelTest {

    private final AemContext context = AppAemContext.newAemContext();

    @BeforeEach
    void setUp() {
        context.registerInjectActivateService(PagePersistenceStrategy.class, Collections.singletonMap("enabled", true));
    }

    @Test
    void testNestedConfig() {
        // given
        MockContextAwareConfig.registerAnnotationClasses(context, NestedConfig.class);
        MockContextAwareConfig.writeConfiguration(context, context.currentPage().getPath(), NestedConfig.class,
    "stringParam", "value1",
            "sub", ImmutableList.of(
                    ImmutableValueMap.of("subStringParam", "v1", "intParam", 5,
                            "stringArrayParam", new String[] {"v1a","v1b"}),
                    ImmutableValueMap.of("subStringParam", "v2")
            )
        );
        // when
        NestedConfigModel underTest = AdaptTo.notNull(context.request(), NestedConfigModel.class);
        // then
        NestedConfig config = underTest.getNestedConfig();
        assertEquals("value1", config.stringParam());
        NestedConfigSub[] sub = config.sub();
        assertEquals(2, sub.length);
        assertEquals("v1", sub[0].subStringParam());
        assertEquals(5, sub[0].intParam());
        assertArrayEquals(new String[] {"v1a","v1b"}, sub[0].stringArrayParam());
        assertEquals("v2", sub[1].subStringParam());
        assertEquals(0, sub[1].intParam());
        assertArrayEquals(new String[] {"value1", "value2"}, sub[1].stringArrayParam());
    }
}
