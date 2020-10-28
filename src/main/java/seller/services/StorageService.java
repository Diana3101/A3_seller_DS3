package seller.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seller.entities.Storage;
import seller.entities.Thing;
import seller.repo.StorageRepository;

@Service
public class StorageService {
    private final StorageRepository storageRepository;

    @Autowired
    public StorageService(StorageRepository storageRepository) {
        this.storageRepository = storageRepository;
    }

    @Transactional
    public Storage findByThing(Thing thing){
        return storageRepository.findByThing(thing);
    }

    @Transactional
    public void saveStorageThing(Storage storage){
        storageRepository.save(storage);
    }
}
