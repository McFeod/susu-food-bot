package logic;

public class ProductsNotInStock {
    private long id;
    private Product product;
    private Buffet buffet;

    public ProductsNotInStock() {
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setBuffet(Buffet buffet) {
        this.buffet = buffet;
    }

    public Buffet getBuffet() {
        return buffet;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }
}
