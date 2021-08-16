package org.example.patientcheckout.errorhandler;

import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import org.slf4j.LoggerFactory;

public class ErrorHandler {
    public void handler(SNSEvent event) {
        var logger = LoggerFactory.getLogger(ErrorHandler.class);
        event.getRecords().forEach(record -> logger.info("Dead Letter Queue Event:{}", record));
    }
}
