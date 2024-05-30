package Shopee.models;

public class CartShop {
    private String shopName;
    private String shopAddress;
    private String shopPhoneNumber;
    private String shopEmail;

    public CartShop() {
    }

    public CartShop(String shopName, String shopAddress, String shopPhoneNumber, String shopEmail) {
        this.shopName = shopName;
        this.shopAddress = shopAddress;
        this.shopPhoneNumber = shopPhoneNumber;
        this.shopEmail = shopEmail;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopPhoneNumber() {
        return shopPhoneNumber;
    }

    public void setShopPhoneNumber(String shopPhoneNumber) {
        this.shopPhoneNumber = shopPhoneNumber;
    }

    public String getShopEmail() {
        return shopEmail;
    }

    public void setShopEmail(String shopEmail) {
        this.shopEmail = shopEmail;
    }

    @Override
    public String toString() {
        return shopName +" - "+ shopAddress +" - "+ shopPhoneNumber +" - "+ shopEmail;
    }
}
