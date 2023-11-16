package io.wcm.samples.core.example;

public @interface NestedConfigSub {

    String subStringParam();

    int intParam();

    String[] stringArrayParam() default {
            "value1", "value2"
    };

}
