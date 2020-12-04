package com.example.seller.controllers.grpc;

import com.example.seller.*;
import com.example.seller.entities.Seller;
import com.example.seller.entities.Thing;
import com.example.seller.entities.dto.ServeDTO;
import com.example.seller.services.SellerService;
import com.example.seller.services.ThingService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@GrpcService
public class SellerServiceImpl extends sellerServiceGrpc.sellerServiceImplBase {
    private final SellerService sellerService;
    private final ThingService thingService;

    @Autowired
    public SellerServiceImpl(SellerService sellerService, ThingService thingService) {
        this.sellerService = sellerService;
        this.thingService = thingService;
    }


    @Override
    public void getSellers(GetRequestSeller request, StreamObserver<GetResponseSeller> responseStreamObserver) {
        List<Seller> sellers = sellerService.getSellers();

        List<ProtoSeller> protoSellers = new ArrayList<>();
        for (Seller seller: sellers) {
            ProtoSeller protoSeller = ProtoSeller.newBuilder()
                    .setFirstName(seller.getFirstName())
                    .setLastName(seller.getLastName())
                    .build();
            protoSellers.add(protoSeller);
        }
        GetResponseSeller response = GetResponseSeller.newBuilder().addAllSellers(protoSellers).build();
        responseStreamObserver.onNext(response);
        responseStreamObserver.onCompleted();
    }

    @Override
    public void getThings(GetRequestThing request, StreamObserver<GetResponseThing> responseStreamObserver) {
        List<Thing> things = thingService.getThings();

        List<ProtoThing> protoThings = new ArrayList<>();
        for (Thing thing: things) {
            ProtoThing protoThing = ProtoThing.newBuilder()
                    .setName(thing.getName())
                    .setSize(thing.getSize())
                    .setCondition(thing.getCondition())
                    .setPrice(thing.getPrice())
                    .build();
            protoThings.add(protoThing);
        }
        GetResponseThing response = GetResponseThing.newBuilder().addAllThings(protoThings).build();
        responseStreamObserver.onNext(response);
        responseStreamObserver.onCompleted();
    }

    @Override
    public void create(CreateRequest request, StreamObserver<CreateResponse> responseStreamObserver){
        String serveJson = request.getServeJson();
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

        CreateResponse response = CreateResponse.newBuilder()
                .build();
        responseStreamObserver.onNext(response);
        responseStreamObserver.onCompleted();
    }

}

