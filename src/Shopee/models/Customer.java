package Shopee.models;

public class Customer {
    private String customerName;
    private String customerPhoneNumber;
    private String customerAddress;
    private String customerPassword;

    public Customer() {
    }

    public Customer(String customerName, String customerPhoneNumber, String customerAddress, String customerPassword) {
        this.customerName = customerName;
        this.customerPhoneNumber = customerPhoneNumber;
        this.customerAddress = customerAddress;
        this.customerPassword = customerPassword;
    }

    public Customer(String customerName, String customerPhoneNumber, String customerAddress) {
        this.customerName = customerName;
        this.customerPhoneNumber = customerPhoneNumber;
        this.customerAddress = customerAddress;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerPassword() {
        return customerPassword;
    }

    public void setCustomerPassword(String customerPassword) {
        this.customerPassword = customerPassword;
    }

    @Override
    public String toString() {
        return customerName +" - "+ customerPhoneNumber +" - "+ customerAddress +" - "+ customerPassword;
    }
}
