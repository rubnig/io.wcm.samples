package io.wcm.samples.core.example;

import org.apache.sling.caconfig.annotation.Configuration;

/**
 * Nested configuration example.
 */
@Configuration
public @interface NestedConfig {

    String stringParam();

    NestedConfigSub[] sub();
}
