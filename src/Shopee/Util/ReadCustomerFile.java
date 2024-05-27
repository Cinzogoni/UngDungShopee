package Shopee.Util;

import Shopee.models.Customer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadCustomerFile {
    public static ArrayList<Customer> readCustomersFile(String filePath){
        ArrayList<Customer> customers = new ArrayList<>();
        try (FileReader fr = new FileReader(filePath);
             BufferedReader br = new BufferedReader(fr)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" - ");
                String cusName = parts[0].trim();
                String cusPhoneNumber = parts[1].trim();
                String cusAddress = parts[2].trim();
                String cusPassword = parts[3].trim();
                customers.add(new Customer(cusName, cusPhoneNumber, cusAddress, cusPassword));
            }
            return customers;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
