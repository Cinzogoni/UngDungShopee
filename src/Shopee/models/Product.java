package Shopee.models;

public class Product {
    private int productID;
    private String productName;
    private double producrPrice;
    private int productAmount;
    private String productDescribe;

    public Product() {
    }

    public Product(int productID, String productName, double producrPrice, int productAmount, String productDescribe) {
        this.productID = productID;
        this.productName = productName;
        this.producrPrice = producrPrice;
        this.productAmount = productAmount;
        this.productDescribe = productDescribe;
    }



    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProducrPrice() {
        return producrPrice;
    }

    public void setProducrPrice(double producrPrice) {
        this.producrPrice = producrPrice;
    }

    public int getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }

    public String getProductDescribe() {
        return productDescribe;
    }

    public void setProductDescribe(String productDescribe) {
        this.productDescribe = productDescribe;
    }

    @Override
    public String toString() {
        return String.format(productID +" - "+ productName +" - "+ producrPrice +" - "+ productAmount +" - "+ productDescribe);
    }
}
