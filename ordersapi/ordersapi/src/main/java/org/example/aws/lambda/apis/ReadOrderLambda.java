package org.example.aws.lambda.apis;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.aws.lambda.apis.dto.Order;

public class ReadOrderLambda {
    public APIGatewayProxyResponseEvent getOrders(APIGatewayProxyRequestEvent request) throws JsonProcessingException {
        ObjectMapper mapper  = new ObjectMapper();
        Order order = new Order(123, "Mac Book Air", 200);
        String jsonOutput = mapper.writeValueAsString(order);
        return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(jsonOutput);
    }
}
