package seller.controllers;


import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seller.entities.Storage;
import seller.entities.Thing;
import seller.entities.dto.ThingDTO;
import seller.entities.dto.ThingsDTO;
import seller.services.StorageService;
import seller.services.ThingService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("things")
public class ThingController {
    private final ThingService thingService;
    private final StorageService storageService;

    @Autowired
    public ThingController(ThingService thingService, StorageService storageService) {
        this.thingService = thingService;
        this.storageService = storageService;
    }

    @GetMapping
    public ResponseEntity<ThingsDTO> show() {
        ThingsDTO thingsDTO = new ThingsDTO();
        thingsDTO.setThings(thingService.getThings());
        System.out.println(ResponseEntity.ok(thingsDTO));
        return ResponseEntity.ok(thingsDTO);
    }

    @GetMapping("{id}")
    public ResponseEntity<Thing> showById(@PathVariable UUID id) throws NotFoundException {
        return ResponseEntity.ok(thingService.getThingById(id));
    }

    @PutMapping(value = "/newQuantity")
    public void setNewQuantity(@RequestBody ThingDTO thingDTO){
        Thing thing = thingService.findThingByName(thingDTO.getName());
        Storage storageThing = storageService.findByThing(thing);
        storageThing.setQuantity(thingDTO.getQuantity());
        storageService.saveStorageThing(storageThing);
    }

    @GetMapping(value = "/name={name}")
    public @ResponseBody ThingDTO getThingByName(@PathVariable String name){
        Thing thing = thingService.findThingByName(name);
        Storage storageThing = storageService.findByThing(thing);

        return new ThingDTO(thing.getId(), thing.getName(), thing.getSize(),
                thing.getCondition(), thing.getPrice(), storageThing.getQuantity());
    }


}
