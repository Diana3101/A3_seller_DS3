package seller.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.UUID;

@Entity
public class Seller {
    @Id
    private UUID id;
    private String firstName;
    private String lastName;
    @JsonBackReference
    @OneToMany(mappedBy = "addedBy")
    private List<Thing> addedClothing;

    public Seller(){

    }

    public Seller(UUID id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddedClothing(List<Thing> addedClothing) {
        this.addedClothing = addedClothing;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public List<Thing> getAddedClothing(){
        return addedClothing;
    }

}
