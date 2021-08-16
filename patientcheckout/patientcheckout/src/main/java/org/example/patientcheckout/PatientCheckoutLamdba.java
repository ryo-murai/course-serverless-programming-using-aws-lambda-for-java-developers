package org.example.patientcheckout;


import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;

public class PatientCheckoutLamdba {
    private final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void handler(S3Event event) {
        event.getRecords().forEach(record -> {
            var s3Entry = record.getS3();
            var s3inputStream =
                    s3.getObject(s3Entry.getBucket().getName(), s3Entry.getObject().getKey())
                    .getObjectContent();
            try {
                var patientCheckoutEvents =
                        Arrays.asList(objectMapper.readValue(s3inputStream, PatientCheckoutEvent[].class));
                System.out.println(patientCheckoutEvents);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
