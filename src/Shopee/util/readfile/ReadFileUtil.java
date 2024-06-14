package Shopee.util.readfile;

import Shopee.models.Product;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;

public class ReadFileUtil {
    public static ArrayList<Product> readProductsFile(String filePath){
        ArrayList<Product> products = new ArrayList<>();
        try (FileReader fr = new FileReader(filePath);
             BufferedReader br = new BufferedReader(fr)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" - ");
                int productID = Integer.parseInt(parts[0].trim());
                String productName = parts[1].trim();
                double productPrice = Double.parseDouble(parts[2].trim());
                int productAmount = Integer.parseInt(parts[3].trim());
                String productDescribe = parts[4].trim();
                products.add(new Product(productID, productName, productPrice, productAmount, productDescribe));
            }
        }
        catch (FileNotFoundException e){
            System.err.println("Không thể đọc tập tin: " +filePath);
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return products;
    }
}
