package com.codedecode.order.service;

import com.codedecode.order.dto.OrderDTO;
import com.codedecode.order.dto.OrderDTOFromFE;
import com.codedecode.order.dto.UserDTO;
import com.codedecode.order.entity.Order;
import com.codedecode.order.mapper.OrderMapper;
import com.codedecode.order.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class OrderService {


    @Value("${user-service.base-url}")
    private String ORDER_SERVICE_BASE_URL;

    private final OrderRepo orderRepo;

    private final SequenceGenerator sequenceGenerator;


    private final RestClient.Builder restClientBuilder;

    public OrderService(OrderRepo orderRepo, SequenceGenerator sequenceGenerator, RestClient.Builder restClientBuilder) {
        this.orderRepo = orderRepo;
        this.sequenceGenerator = sequenceGenerator;
        this.restClientBuilder = restClientBuilder;
    }


    public OrderDTO createOrderInMongo(OrderDTOFromFE orderDetails) {
        Integer newOrderID = sequenceGenerator.generateNextOrderId();
        UserDTO userDTO = fetchUserDetailsFromUserId(orderDetails.getUserId());
        Order orderToBeSaved = new Order(newOrderID, orderDetails.getFoodItemsList(), orderDetails.getRestaurant(), userDTO);
        orderRepo.save(orderToBeSaved);
        return OrderMapper.INSTANCE.mapOrderToOrderDTO(orderToBeSaved);
    }

    private UserDTO fetchUserDetailsFromUserId(Integer userId) {
        return restClientBuilder.baseUrl(ORDER_SERVICE_BASE_URL).build()
                .get()
                .uri("/users/" + userId)
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError,((request, response) -> {
                    System.out.println("Error response from user service: " + response.getHeaders() + " " + request.getURI());
                })).body(UserDTO.class);
    }
}
