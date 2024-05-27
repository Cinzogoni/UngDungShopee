package Shopee.models;

public class Owner {
    private String shopownerID;
    private String shopownerName;
    private String shopownerNumber;
    private String shopownerAddress;
    private String shopownerPassword;


    public Owner() {
    }

    public Owner(String shopownerID, String shopownerName, String shopownerNumber, String shopownerAddress, String shopownerPassword) {
        this.shopownerID = shopownerID;
        this.shopownerName = shopownerName;
        this.shopownerNumber = shopownerNumber;
        this.shopownerAddress = shopownerAddress;
        this.shopownerPassword = shopownerPassword;
    }

    public String getShopownerID() {
        return shopownerID;
    }

    public void setShopownerID(String shopownerID) {
        this.shopownerID = shopownerID;
    }

    public String getShopownerName() {
        return shopownerName;
    }

    public void setShopownerName(String shopownerName) {
        this.shopownerName = shopownerName;
    }

    public String getShopownerNumber() {
        return shopownerNumber;
    }

    public void setShopownerNumber(String shopownerNumber) {
        this.shopownerNumber = shopownerNumber;
    }

    public String getShopownerAddress() {
        return shopownerAddress;
    }

    public void setShopownerAddress(String shopownerAddress) {
        this.shopownerAddress = shopownerAddress;
    }

    public String getShopownerPassword() {
        return shopownerPassword;
    }

    public void setShopownerPassword(String shopownerPassword) {
        this.shopownerPassword = shopownerPassword;
    }

    @Override
    public String toString() {
        return shopownerID +" - "+ shopownerName +" - "+ shopownerNumber +" - "+ shopownerAddress +" - "+ shopownerPassword;
    }
}
