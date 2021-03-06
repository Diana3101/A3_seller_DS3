package com.example.seller.controllers.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.seller.entities.Seller;
import com.example.seller.entities.Thing;
import com.example.seller.entities.dto.ServeDTO;
import com.example.seller.repo.SellerRepository;
import com.example.seller.services.SellerService;
import com.example.seller.services.ThingService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("sellers")
public class SellerController {
    private final SellerService sellerService;
    private final ThingService thingService;

    @Autowired
    public SellerController(SellerService sellerService, ThingService thingService) {
        this.sellerService = sellerService;
        this.thingService = thingService;
    }

    @GetMapping
    public ResponseEntity<List<Seller>> show() {
        return ResponseEntity.ok(sellerService.getSellers());
    }

    @GetMapping("{id}")
    public ResponseEntity<Seller> showById(@PathVariable UUID id) throws NotFoundException {
        return ResponseEntity.ok(sellerService.getSellerById(id));
    }

    @PostMapping
    public ResponseEntity<Void> ThingsFromSeller(@RequestBody String serveJson){
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        ServeDTO serve = gson.fromJson(serveJson, ServeDTO.class);
        List<Thing> toStorage = serve.getThings();
        List<Integer> thingQuantities = serve.getThingQuantities();
        Seller jsonSeller = serve.getSeller();
        sellerService.save(jsonSeller);
        Seller seller = sellerService.findSellerByName(jsonSeller.getFirstName(), jsonSeller.getLastName());
        for(Thing th: toStorage){
            th.setAddedBy(seller);
            thingService.addThing(th);
        }
        sellerService.addThingsToStorage(seller, toStorage, thingQuantities);


        return ResponseEntity.ok().build();
    }

}
