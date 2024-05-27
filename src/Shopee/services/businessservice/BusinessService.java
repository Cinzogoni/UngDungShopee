package Shopee.services.businessservice;

import Shopee.models.Product;

import java.util.ArrayList;

public interface BusinessService<T> {
//    public T CashPayment();
//    public T BankPayment();
//    public T EwalletPaymen();

    public T Payment();

    public T add(Product product);
    public T remove(Product product);
}
