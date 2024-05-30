package Shopee.services.businessservice;

import Shopee.models.Product;

import java.util.ArrayList;

public interface ProductService {
    ArrayList<Product> getCartProductList(String shopName);
    ArrayList<Product> editCartProduct();
}
