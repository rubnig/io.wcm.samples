package io.wcm.samples.core.example;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.caconfig.ConfigurationBuilder;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

import javax.inject.Inject;

@Model(adaptables = SlingHttpServletRequest.class)
public class NestedConfigModel {

    private final NestedConfig nestedConfig;

    @Inject
    public NestedConfigModel(@Self final SlingHttpServletRequest request) {
        this.nestedConfig = request.getResource().adaptTo(ConfigurationBuilder.class).as(NestedConfig.class);
    }

    public NestedConfig getNestedConfig() {
        return this.nestedConfig;
    }
}
