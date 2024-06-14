package Shopee.models;

import java.text.DecimalFormat;
import java.util.Objects;

public class Product {
    private int productID;
    private String productName;
    private double productPrice;
    private int productAmount;
    private String productDescribe;

    public Product() {
    }

    public Product(int productID, String productName, double producrPrice, int productAmount, String productDescribe) {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = producrPrice;
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

    public double getProductPrice() {
        return productPrice;
    }

    public void setProducrPrice(double productPrice) {
        this.productPrice = productPrice;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productID == product.productID &&
                Double.compare(product.productPrice, productPrice) == 0 &&
                productAmount == product.productAmount &&
                Objects.equals(productName, product.productName) &&
                Objects.equals(productDescribe, product.productDescribe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productID, productName, productPrice, productAmount, productDescribe);
    }

    @Override
    public String toString() {
        return String.format("%d - %s - %,.0f - %d - %s", productID, productName, productPrice, productAmount, productDescribe);
    }
}
