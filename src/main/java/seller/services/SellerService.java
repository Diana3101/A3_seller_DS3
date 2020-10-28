package seller.services;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seller.entities.Seller;
import seller.entities.Storage;
import seller.entities.Thing;
import seller.repo.SellerRepository;
import seller.repo.StorageRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SellerService {
    private final SellerRepository sellerRepository;
    private final StorageRepository storageRepository;

    @Autowired
    public SellerService(SellerRepository sellerRepository, StorageRepository storageRepository){
        this.sellerRepository = sellerRepository;
        this.storageRepository = storageRepository;
    }

    @Transactional
    public Seller findSellerByName(String firstName, String lastName){
        return sellerRepository.findSellerByName(firstName, lastName);
    }


    @Transactional
    public void addThingsToStorage(Seller seller, List<Thing> toStorage, List<Integer> thingQuantities){
        sellerRepository.save(seller);

        for(int i = 0; i < toStorage.size(); i++){
            Thing thingTemp = toStorage.get(i);
            int thingQTemp = thingQuantities.get(i);
            Storage existsStorageThing = storageRepository.findByThing(thingTemp);

            if(existsStorageThing == null){
                Storage storageItem = new Storage();
                storageItem.setId(UUID.randomUUID());
                storageItem.setThing(thingTemp);
                storageItem.setQuantity(thingQTemp);
                storageRepository.save(storageItem);
            }else {
                int thingQuantity = existsStorageThing.getQuantity();
                int newThingQuantity = thingQuantity + thingQTemp;
                existsStorageThing.setQuantity(newThingQuantity);
                storageRepository.save(existsStorageThing);
            }

        }
    }

    @Transactional
    public List<Seller> getSellers(){
        return sellerRepository.findAll();
    }

    @Transactional
    public Seller getSellerById(UUID id) throws NotFoundException {
        Optional<Seller> tempSeller = sellerRepository.findById(id);
        if (tempSeller.isPresent())
            return tempSeller.get();
        else
            throw new NotFoundException(String.format("Seller with id %s was not found", id));
    }
}
