package Shopee.services.cartservice;

import Shopee.models.Product;

public interface CartService {
    public Object Payment();
    public Object add(Product product);
    public Object remove(Product product);

}
