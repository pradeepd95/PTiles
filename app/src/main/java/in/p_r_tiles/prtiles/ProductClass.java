package in.p_r_tiles.prtiles;

public class ProductClass {

    Integer productId;
    String imageUrl;
    String name;
    String description;
    String stock;
    String price;
    String views;

    public ProductClass() {
        super();
    }

    public ProductClass(Integer productId, String imageUrl,String name, String description, String stock, String price, String views) {
        super();
        this.productId = productId;
        this.imageUrl = imageUrl;
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.price = price;
        this.views = views;
    }
}
