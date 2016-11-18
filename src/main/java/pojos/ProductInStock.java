package pojos;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "products_in_stock")
public class ProductInStock implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(targetEntity = Product.class, cascade = CascadeType.ALL, optional = false)
    private Product product;

    @ManyToOne(targetEntity = Buffet.class, cascade = CascadeType.ALL, optional = false)
    private Buffet buffet;

    public ProductInStock(Long id, Product product, Buffet buffet) {
        this.id      = id;
        this.product = product;
        this.buffet  = buffet;
    }

    public ProductInStock(Product product, Buffet buffet) {
        this.product = product;
        this.buffet  = buffet;
    }

    public ProductInStock() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Buffet getBuffet() {
        return this.buffet;
    }

    public void setBuffet(Buffet buffet) {
        this.buffet = buffet;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
