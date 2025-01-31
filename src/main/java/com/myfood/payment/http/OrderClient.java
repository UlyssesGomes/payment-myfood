package com.myfood.payment.http;

import com.myfood.payment.model.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("order-microservice")
public interface OrderClient {

    @RequestMapping(method = RequestMethod.PUT, value = "/orders/{id}/paid")
    void updatePaidOrder(@PathVariable Long id);

    @RequestMapping(method = RequestMethod.GET, value = "/orders/{id}")
    Order getOrdersById(@PathVariable Long id);
}
