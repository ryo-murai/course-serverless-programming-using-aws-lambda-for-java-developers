package org.example.patientcheckout;

import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClaimManagementLambda {
    private static final Logger logger = LoggerFactory.getLogger(ClaimManagementLambda.class);

    public void handler(SQSEvent event) {
        event.getRecords().forEach(sqsMessage -> {
            logger.info(sqsMessage.getBody());
        });
    }
}
