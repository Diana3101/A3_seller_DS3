package com.example.seller.services;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.seller.entities.Thing;
import com.example.seller.repo.ThingRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ThingService {
    private final ThingRepository thingRepository;

    @Autowired
    public ThingService(ThingRepository thingRepository) {
        this.thingRepository = thingRepository;
    }

    @Transactional
    public Thing findThingByName(String name){
        return thingRepository.findThingByName(name);
    }


    @Transactional
    public void addThing(Thing thing){
        if(thingRepository.findThingByName(thing.getName()) == null){
            thingRepository.save(thing);
        }
    }

    @Transactional
    public List<Thing> getThings(){
        return thingRepository.getThingsInStock();
    }

    @Transactional
    public Thing getThingById(UUID id)throws NotFoundException {
        Optional<Thing> tempThing = thingRepository.findById(id);
        if (tempThing.isPresent())
            return tempThing.get();
        else
            throw new NotFoundException(String.format("Thing with id %s was not found", id));
    }

}
