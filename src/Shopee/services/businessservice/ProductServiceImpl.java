package Shopee.services.businessservice;

import Shopee.util.readfile.ReadFileUtil;
import Shopee.models.Product;
import Shopee.util.writefile.WriteProductFile;


import java.io.File;
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
            case "Kawai Shop":
                filePath = "src/Shopee/database/cartshop/KawaiCartShop.txt";
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

    public static final String TEMP_CART_FILE_DIRECTORY = "src/Shopee/database/tempcart";

    public static void saveTempCartToFile(String shopName, ArrayList<Product> cart) {
        String filePath = TEMP_CART_FILE_DIRECTORY + "/" +shopName+ ".txt";
        WriteProductFile.writeProductsFile(filePath, cart);
    }

    public static ArrayList<Product> readTempCartFromFile(String shopName) {
        ArrayList<Product> cart = new ArrayList<>();
        try {
            String filePath = TEMP_CART_FILE_DIRECTORY + "/" + shopName+ ".txt";
//            System.out.println(filePath);
            cart = ReadFileUtil.readProductsFile(filePath);
//            System.out.println("Giỏ hàng lưu tạm: " +cart.size());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return cart;
    }

    public static void deleteTempCartFile(String shopName) {
        String filePath = TEMP_CART_FILE_DIRECTORY + "/" + shopName+ ".txt";
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
//            System.out.println("Đã xóa giỏ hàng tạm thời cho " +shopName+ ".txt");
        }
        else {
            System.out.println("Không tìm thấy giỏ hàng tạm thời cho " + shopName + ".txt");
        }
    }

    public static void updateCart(String shopName, ArrayList<Product> cart) {
        saveTempCartToFile(shopName, cart);
    }

    public static void deleteTempCart(String shopName) {
        deleteTempCartFile(shopName);
    }
}
