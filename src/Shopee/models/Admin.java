package Shopee.models;

public class Admin {
    private String adminID;
    private String adminPassword;

    public Admin() {
    }

    public Admin(String adminID, String adminPassword) {
        this.adminID = adminID;
        this.adminPassword = adminPassword;
    }

    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    @Override
    public String toString() {
        return adminID +" - "+adminPassword;
    }
}
