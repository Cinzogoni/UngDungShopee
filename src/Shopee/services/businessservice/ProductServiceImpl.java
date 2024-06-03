package Shopee.services.businessservice;

import Shopee.util.readfile.ReadFileUtil;
import Shopee.models.Product;
import Shopee.util.writefile.WriteProductFile;

import java.util.ArrayList;

public class ProductServiceImpl implements ProductService {
    //Singleton
    private static volatile ProductServiceImpl productService;
    public ProductServiceImpl(){
    }
    public static ProductServiceImpl getInstanceProduct() {
        if (productService == null) {
            synchronized (ProductServiceImpl.class) {
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
        TisaCartShop = ReadFileUtil.readProductsFile("D:\\06-Java\\00-UngDungShopee\\src\\Shopee\\database\\cartshop\\TisaCartShop.txt");
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
    public static void updateCartFile(String shopName, ArrayList<Product> cart) {
        String filePath = "";
        switch (shopName) {
            case "Kaiwai Shop":
                filePath = "D:\\06-Java\\00-UngDungShopee\\src\\Shopee\\database\\cartshop\\KaiwaiCartShop.txt";
                break;
            case "Nima Shop":
                filePath = "D:\\06-Java\\00-UngDungShopee\\src\\Shopee\\database\\cartshop\\NimaCartShop.txt";
                break;
            case "Tisa Shop":
                filePath = "D:\\06-Java\\00-UngDungShopee\\src\\Shopee\\database\\cartshop\\TisaCartShop.txt";
                break;
            default:
                break;
        }
        WriteProductFile.writeProductsFile(filePath, cart);
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
