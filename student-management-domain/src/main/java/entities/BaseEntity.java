package entities;


import jakarta.persistence.*;

@Entity
@DiscriminatorValue("BaseNameEntity")
public abstract class BaseEntity extends BaseIDEntity {
    @Column(length = 50, nullable = false)
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
