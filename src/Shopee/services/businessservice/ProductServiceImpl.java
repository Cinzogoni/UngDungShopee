package Shopee.services.businessservice;

import Shopee.util.readfile.ReadFileUtil;
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
    private static ArrayList<Product> KaiwaiCartShop;
    private static ArrayList<Product> NimaCartShop;
    private static ArrayList<Product> TisaCartShop;

    static {
        ProductList = ReadFileUtil.readProductsFile("D:\\06-Java\\00-UngDungShopee\\src\\Shopee\\database\\ProductList.txt");
        KaiwaiCartShop = ReadFileUtil.readProductsFile("D:\\06-Java\\00-UngDungShopee\\src\\Shopee\\database\\cartshop\\KaiwaiCartShop.txt");
        NimaCartShop = ReadFileUtil.readProductsFile("D:\\06-Java\\00-UngDungShopee\\src\\Shopee\\database\\cartshop\\NimaCartShop.txt");
        TisaCartShop = ReadFileUtil.readProductsFile("D:\\06-Java\\00-UngDungShopee\\src\\Shopee\\database\\cartshop\\TisaCartShop");
    }

    private  ArrayList<Product> getCartShop(String shopName) {
        switch (shopName) {
            case "Kaiwai Shop":
                return KaiwaiCartShop;
            case "Nima Shop":
                return NimaCartShop;
            case "Tisa Shop":
                return TisaCartShop;
            default:
                return new ArrayList<>();
        }
    }

    @Override
    public ArrayList<Product> getProductList() {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<Product> getCartProductList(String shopName) {
        return getCartShop(shopName);
    }

    @Override
    public ArrayList<Product> editCartProduct() {
        return new ArrayList<>();
    }
}
