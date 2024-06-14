package Shopee.services.cartservice;

import Shopee.models.Product;

import java.util.ArrayList;

public interface CartService {
    public Object Payment();
    public Object addProduct(Product product);
    ArrayList<Product> removeProduct(Product product);
}
