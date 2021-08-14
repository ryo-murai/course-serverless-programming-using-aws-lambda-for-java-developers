package org.example.aws.lambda.apis;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.aws.lambda.apis.dto.Order;

public class CreateOrderLambda {

    public APIGatewayProxyResponseEvent createOrder(APIGatewayProxyRequestEvent request) throws JsonProcessingException {
        ObjectMapper mapper  = new ObjectMapper();
        Order order = mapper.readValue(request.getBody(), Order.class);
        return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody("Order ID:" + order.id);
    }
}
