package Shopee.services.businessservice;

import Shopee.models.Product;

import java.util.ArrayList;

public interface ProductService {
    ArrayList<Product> getProductList();
    ArrayList<Product> editProduct();

}
