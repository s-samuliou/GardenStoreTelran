package org.garden.com.entity;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotBlank(message = "Name is required")
    @Length(max = 255, message = "Name must be less than 255 characters")
    @Column(name = "name")
    private String name;

    public Category(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
