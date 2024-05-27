package Shopee.services.businessservice;

import Shopee.Util.ReadFileUtil;
import Shopee.models.Product;

import java.util.ArrayList;

public class ProductServiceImpl implements ProductService {
    //Singleton
    private static volatile ProductServiceImpl productService;
    public ProductServiceImpl(){
    }
    public static ProductServiceImpl getInstanceProduct() {
        if (productService == null) {
            synchronized (CustomerServiceImpl.class) {
                if (productService == null) {
                    productService = new ProductServiceImpl();
                }
            }
        }
        return productService;
    }
    private static ArrayList<Product> ProductList;
    static {
        ProductList = ReadFileUtil.readProductsFile("D:\\06-Java\\00-UngDungShopee\\Shopee\\src\\Shopee\\database\\ProductList.txt");
    }

    @Override
    public ArrayList<Product> getProductList() {
        return ProductList;
    }

    @Override
    public ArrayList<Product> editProduct() {
        return ProductList;
    }
}
