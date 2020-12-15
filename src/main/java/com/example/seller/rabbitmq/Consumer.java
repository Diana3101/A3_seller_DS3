package com.example.seller.rabbitmq;

import com.example.seller.entities.Seller;
import com.example.seller.entities.Thing;
import com.example.seller.entities.dto.ServeDTO;
import com.example.seller.services.SellerService;
import com.example.seller.services.ThingService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Consumer {

    @Autowired
    SellerService sellerService;
    ThingService thingService;

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void consume(Seller seller) {
        sellerService.save(seller);
    }
}
