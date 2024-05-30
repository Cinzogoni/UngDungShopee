package Shopee.util.readfile;

import Shopee.models.Owner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadOwnerFile {
    public static ArrayList<Owner> readOwnersFile(String filePath){
        ArrayList<Owner> owners = new ArrayList<>();
        try (FileReader fr = new FileReader(filePath);
             BufferedReader br = new BufferedReader(fr)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" - ");
                String ownerID = parts[0].trim();
                String ownerNmae = parts[1].trim();
                String ownerPhoneNumber = parts[2].trim();
                String ownerAddress = parts[3].trim();
                String ownerPassword = parts[4].trim();
                owners.add(new Owner(ownerID, ownerNmae, ownerPhoneNumber, ownerAddress, ownerPassword));
            }
            return owners;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
