package Shopee.services.businessservice;

import Shopee.util.readfile.ReadFileUtil;
import Shopee.models.Product;
import Shopee.util.writefile.WriteProductFile;

import java.util.ArrayList;

public class ProductServiceImpl implements ProductService {
    //Singleton
    private static volatile ProductServiceImpl productService;

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

    private static ArrayList<Product> KawaiCartShop;
    private static ArrayList<Product> NimaCartShop;
    private static ArrayList<Product> TisaCartShop;

    static {
        KawaiCartShop = ReadFileUtil.readProductsFile("src/Shopee/database/cartshop/KawaiCartShop.txt");
        NimaCartShop = ReadFileUtil.readProductsFile("src/Shopee/database/cartshop/NimaCartShop.txt");
        TisaCartShop = ReadFileUtil.readProductsFile("src/Shopee/database/cartshop/TisaCartShop.txt");
    }


    private ArrayList<Product> getCartShop(String shopName) {
        switch (shopName) {
            case "Kawai Shop":
                return KawaiCartShop;
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
                filePath = "src/Shopee/database/cartshop/KaiwaiCartShop.txt";
                break;
            case "Nima Shop":
                filePath = "src/Shopee/database/cartshop/NimaCartShop.txt";
                break;
            case "Tisa Shop":
                filePath = "src/Shopee/database/cartshop/TisaCartShop.txt";
                break;
            default:
                break;
        }
        WriteProductFile.writeProductsFile(filePath, cart);
    }

    @Override
    public ArrayList<Product> getCartProductList(String shopName) {
        ArrayList<Product> cartShop = getCartShop(shopName);
        if (cartShop == null) {
            return new ArrayList<>();
        }
        return cartShop;
    }

    @Override
    public ArrayList<Product> editCartProduct() {
        return new ArrayList<>();
    }
}
