package Shopee.Util;

import Shopee.models.Admin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadAdminFile {
    public static ArrayList<Admin> readAdminsFile(String filePath){
        ArrayList<Admin> admins = new ArrayList<>();
        try (FileReader fr = new FileReader(filePath);
             BufferedReader br = new BufferedReader(fr)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" - ");
                String adID = parts[0].trim();
                String adPassWord = parts[1].trim();
                admins.add(new Admin (adID, adPassWord));
            }
            return admins;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
