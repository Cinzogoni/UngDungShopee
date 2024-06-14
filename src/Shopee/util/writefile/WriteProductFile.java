package Shopee.util.writefile;

import Shopee.models.Product;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class WriteProductFile {
    public static boolean writeProductsFile(String filePath, ArrayList<Product> cart){
        try (Writer writer = new FileWriter(filePath, false);
             BufferedWriter bw = new BufferedWriter(writer)) {
            // Format giá tiền thành 3 số phần ngàn trước khi ghi vào file
            for (Product product : cart) {
                bw.write(product.getProductID() + " - " +
                        product.getProductName() + " - " +
                        product.getProductPrice() + " - " +
                        product.getProductAmount() + " - " +
                        product.getProductDescribe());
                bw.newLine();
            }
            System.out.println("Ghi sản phẩm vào file thành công.");
            return true;
        }
        catch (IOException e) {
            System.out.println("Có lỗi xảy ra khi ghi file");
            return false;
        }
    }
}
