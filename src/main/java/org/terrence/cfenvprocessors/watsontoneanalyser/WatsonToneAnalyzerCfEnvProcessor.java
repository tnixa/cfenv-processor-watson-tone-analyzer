package org.terrence.cfenvprocessors.watsontoneanalyser;

import java.util.Map;

import io.pivotal.cfenv.core.CfCredentials;
import io.pivotal.cfenv.core.CfService;
import io.pivotal.cfenv.spring.boot.CfEnvProcessor;
import io.pivotal.cfenv.spring.boot.CfEnvProcessorProperties;

public class WatsonToneAnalyzerCfEnvProcessor implements CfEnvProcessor {

    public WatsonToneAnalyzerCfEnvProcessor() {
        System.out.println("WatsonToneAnalyzerCfEnvProcessor built");
    }

    @Override
    public boolean accept(CfService service) {
        boolean match = service.existsByLabelStartsWith("tone_analyzer");
        System.out.println("Match [" + match + "] to service " + service.toString());
        return match;
    }

    @Override
    public CfEnvProcessorProperties getProperties() {
        return CfEnvProcessorProperties.builder().propertyPrefixes("tone_analyzer").serviceName("Tone_Analyzer")
                .build();
    }

    @Override
    public void process(CfCredentials cfCredentials, Map<String, Object> properties) {
        // set watsonVersion to date of the released watson spring boot starter
        // version 1.0.0 was released on 2019-05-07
        String watsonVersion = "2019-05-07";
        properties.put("watson.tone-analyzer.url", cfCredentials.getUri("http"));
        properties.put("watson.tone-analyzer.iam-api-key", cfCredentials.getString("apikey"));
        properties.put("watson.tone-analyzer.versionDate", watsonVersion);

    }
}