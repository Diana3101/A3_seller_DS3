package seller.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import java.util.UUID;

@EnableAutoConfiguration
@Entity
@Data
public class Thing {
    @Id
    private UUID id;
    private String name;
    private Size size;
    private ConditionOfThing condition;
    private double price;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller addedBy;

    public Thing() {

    }

    public Thing(String name, Size size, ConditionOfThing condition, double price, Seller addedBy) {
        this.name = name;
        this.size = size;
        this.condition = condition;
        this.price = price;
        this.addedBy = addedBy;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public ConditionOfThing getCondition() {
        return condition;
    }

    public void setCondition(ConditionOfThing condition) {
        this.condition = condition;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Seller getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(Seller addedBy) {
        this.addedBy = addedBy;
    }
}
