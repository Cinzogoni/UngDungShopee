package Shopee.util.writefile;

import Shopee.models.Product;

import java.io.*;
import java.util.ArrayList;

public class WriteProductFile {
    public static ArrayList<Product> writeProductsFile(String filePath, ArrayList<Product> cart){
        ArrayList<Product> products  = new ArrayList<>();
        try (Writer writer = new FileWriter(filePath, false);
             BufferedWriter bw = new BufferedWriter(writer)) {
            for (Product product : cart) {
                bw.write(product.toString());
                bw.newLine();
            }
            System.out.println("Ghi sản phẩm vào file thành công.");
            return products;
        }
        catch (IOException e) {
            System.out.println("Có lỗi xảy ra khi ghi file");
            return null;
        }
    }
}
