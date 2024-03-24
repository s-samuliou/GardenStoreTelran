package org.garden.com.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "favorites")
public class Favorites {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Favorites() {
    }

    public Favorites(Long id, Product product, User user) {
        this.id = id;
        this.product = product;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getProductId() {
        if (product != null) {
            return product.getId();
        }
        return null;
    }

    public void setProductId(Long productId) {
        this.product.setId(productId);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getUserId() {
        if (user != null) {
            return user.getId();
        }
        return null;
    }

    public void setUserId(Long userId) {
        this.user.setId(userId);
    }
}
