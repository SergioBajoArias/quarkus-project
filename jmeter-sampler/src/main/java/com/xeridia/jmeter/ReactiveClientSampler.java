package com.xeridia.jmeter;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import java.util.concurrent.ExecutionException;

public class ReactiveClientSampler implements JavaSamplerClient {
    @Override
    public void setupTest(JavaSamplerContext javaSamplerContext) {

    }

    @Override
    public SampleResult runTest(JavaSamplerContext javaSamplerContext) {
        Client client = ClientBuilder.newClient();
        WebTarget employeeService = client.target("http://localhost:8081/messages/1");

        SampleResult sampleResult = new SampleResult();
        sampleResult.sampleStart();

        String response = null;
        try {
            response = employeeService.request()
                    .accept(MediaType.APPLICATION_JSON)
                    .rx()
                    .get(String.class)
                    .toCompletableFuture()
                    .get();

            sampleResult.setSuccessful(true);
            sampleResult.setResponseData(response, "UTF-8");
            sampleResult.setResponseCodeOK();
        } catch (InterruptedException | ExecutionException e) {
            sampleResult.setSuccessful(false);
            sampleResult.setResponseMessage(e.getMessage());
            throw new RuntimeException(e);
        }


        sampleResult.sampleEnd();

        return sampleResult;
    }

    @Override
    public void teardownTest(JavaSamplerContext javaSamplerContext) {

    }

    @Override
    public Arguments getDefaultParameters() {
        return null;
    }

    public static void main(String[] args) {
        ReactiveClientSampler sampler = new ReactiveClientSampler();
        sampler.runTest(null);
    }
}
