package org.example.patientcheckout;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

public class PatientCheckoutLamdba {
    private static final String PATIENT_CHECKOUT_TOPIC = System.getenv("PATIENT_CHECKOUT_TOPIC");
    private final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AmazonSNS sns = AmazonSNSClientBuilder.defaultClient();

    public void handler(S3Event event, Context context) {
        var logger = context.getLogger();
        event.getRecords().forEach(record -> {
            var s3Entry = record.getS3();
            try (var s3inputStream =
                    s3.getObject(s3Entry.getBucket().getName(), s3Entry.getObject().getKey())
                    .getObjectContent() ) {
                var patientCheckoutEvents =
                        Arrays.asList(objectMapper.readValue(s3inputStream, PatientCheckoutEvent[].class));
                logger.log(patientCheckoutEvents.toString());

                patientCheckoutEvents.forEach(checkoutEvent -> {
                    try {
                        sns.publish(PATIENT_CHECKOUT_TOPIC, objectMapper.writeValueAsString(checkoutEvent));
                    } catch (JsonProcessingException e) {
                        var writer = new StringWriter();
                        e.printStackTrace(new PrintWriter(writer));
                        logger.log(writer.toString());
                    }
                });
            } catch (IOException e) {
                var writer = new StringWriter();
                e.printStackTrace(new PrintWriter(writer));
                logger.log(writer.toString());
            }
        });
    }
}
