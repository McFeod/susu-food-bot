package pojos;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "products_not_in_stock")
public class ProductNotInStock implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(targetEntity = Product.class, cascade = CascadeType.ALL, optional = false)
    private Product product;

    @ManyToOne(targetEntity = Buffet.class, cascade = CascadeType.ALL, optional = false)
    private Buffet buffet;
    
    //@Column(name = "date")
    //private Date date;
    @Column
    private Calendar date;

    public ProductNotInStock(Long id, Product product, Buffet buffet) {
        this.id      = id;
        this.product = product;
        this.buffet  = buffet;
    }

    public ProductNotInStock(Product product, Buffet buffet) {
        this.product = product;
        this.buffet  = buffet;
    }

    public ProductNotInStock(Product product, Buffet buffet, Calendar date) {
        this.product = product;
        this.buffet  = buffet;
        this.date    = date;
    }
    
    public ProductNotInStock() {
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
    
    
    public void setDate(Calendar date)
    {
        this.date = date;
    }
    
    public Calendar getDate()
    {
        return date;
    }
}
