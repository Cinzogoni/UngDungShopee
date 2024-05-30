package Shopee.services.cartservice;

import Shopee.models.Product;
import Shopee.services.businessservice.CustomerServiceImpl;
import Shopee.services.businessservice.ProductServiceImpl;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CartServiceImpl implements CartService {
    private static volatile CartServiceImpl cartService;
    public CartServiceImpl() {}
    public static CartServiceImpl getCartService() {
        if (cartService == null){
            synchronized (CustomerServiceImpl.class){
                if (cartService == null){
                    cartService = new CartServiceImpl();
                }
            }
        }
        return cartService;
    }
    ArrayList<Product> getCartShopList = new ArrayList<>();

    public Object displayMenuAndGetCartChoice(String shopName) {
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Chào mừng đến shop của tôi:");
            System.out.println("0. Danh sách sản phẩm trong giỏ hàng của " +shopName+ ".");
            System.out.println("1. Danh sách sản phẩm trong giỏ hàng của bạn");
            System.out.println("2. Thêm sản phẩm vào giỏ hàng của bạn");
            System.out.println("3. Xoá sản phẩm khỏi giỏ hàng của bạn");
            System.out.println("4. Thanh toán sản phẩm bạn đã chọn");
            System.out.println("5. Quay lại");

            System.out.print("Mời chọn chức năng: ");
            try {
                int choose = scanner.nextInt();
                scanner.nextLine();
                switch (choose) {
                    case 0:
                        ProductServiceImpl shopProductList = ProductServiceImpl.getInstanceProduct();
                        ArrayList<Product> products = shopProductList.getCartProductList(shopName);
                        if (products != null && !products.isEmpty()) {
                            products.forEach(System.out::println);
                        }
                        else
                            System.out.println("Danh sách sản phẩm trống.");
                        break;
                    case 1:
                        if (getCartShopList != null && !getCartShopList.isEmpty()){
                            getCartShopList.forEach(System.out::println);
                        }
                        else {
                            System.out.println("Danh sách sản phẩm trống.");
                            System.out.println("----------------------------------------");
                        }
                        break;
                    case 2:
                        ProductServiceImpl addCustomerInService = ProductServiceImpl.getInstanceProduct();
                        ArrayList<Product> addProductList = addCustomerInService.getCartProductList(shopName);
                        if (addProductList != null){
                            System.out.println("Danh sách sản phẩm:");
                            for (int i = 0; i < addProductList.size(); i++) {
                                System.out.println(i + 1 + ". " + addProductList.get(i));
                            }
                        }
                        System.out.print("Nhập số thứ tự của sản phẩm cần thêm vào giỏ hàng: ");
                        int choiceIndex = scanner.nextInt();
                        if (choiceIndex >= 1 && choiceIndex <= addProductList.size()) {
                            Product productToAdd = addProductList.get(choiceIndex - 1);
                            getCartShopList.add(productToAdd);
                            System.out.println(productToAdd + " đã được thêm vào giỏ hàng.");
                            System.out.println("Danh sách sản phẩm sau khi thêm:");
                            getCartShopList.forEach(System.out::println);
                        }
                        else
                            System.out.println("Lựa chọn không hợp lệ.");
                        break;
                    case 3:
                        ProductServiceImpl removeCustomerService = ProductServiceImpl.getInstanceProduct();
                        ArrayList<Product> removeProductList = removeCustomerService.getCartProductList(shopName);
                        if (removeProductList == null || removeProductList.isEmpty())
                            System.out.println("Không có sản phẩm để xoá");
                        System.out.println("Danh sách sản phẩm trong giỏ hàng:");
                        for (int i = 0; i < getCartShopList.size(); i++)
                            System.out.println(i + 1 + ". " + getCartShopList.get(i));
                        System.out.print("Nhập số thứ tự của sản phẩm cần xoá khỏi giỏ hàng: ");
                        int removeIndex = scanner.nextInt();
                        if (removeIndex >= 1 && removeIndex <= getCartShopList.size()){
                            Product productToRemove = getCartShopList.get(removeIndex - 1);
                            getCartShopList.remove(productToRemove);
                            System.out.println(productToRemove + " đã được xoá khỏi giỏ hàng.");
                            System.out.println("Danh sách sản phẩm sau khi xoá:");
                            getCartShopList.forEach(System.out::println);
                        }
                        else
                            System.out.println("Lựa chọn không hợp lệ.");
                        break;
                    case 4:
                        Payment();
                        break;
                    case 5:
                        CustomerServiceImpl customerService = CustomerServiceImpl.getCustomerService();
                        customerService.displayMenuAndGetCustomerChoice();
                        break;
                    default:
                        System.out.println("Mời chọn lại");
                }
            }
            catch (InputMismatchException e) {
                System.out.println("Hãy chọn các số có trong danh sách");
                scanner.nextLine();
            }
            catch (NoSuchElementException e) {
                System.out.println("Không có dữ liệu đầu vào.");
            }
        } while (true);
    }

    @Override
    public Object Payment() {
        ProductServiceImpl productService = ProductServiceImpl.getInstanceProduct();
        ArrayList<Product> paymentList = productService.getProductList();
        if (paymentList == null || paymentList.isEmpty()) {
            System.out.println("Giỏ hàng trống. Không có gì để thanh toán.");
            return null;
        }
        double total = 0;
        for (Product product : paymentList) {
            total += product.getProducrPrice();
            product.setProductAmount(product.getProductAmount() - 1);
        }
        System.out.println("Tổng số tiền cần thanh toán: " + total + " VND");
        System.out.println("Bạn chắc chắn muốn thanh toán? (1: Có, 2: Không)");
        int choice = new Scanner(System.in).nextInt();
        if (choice == 1) {
            System.out.println("Thanh toán thành công!");
            getCartShopList.clear();
            return total;
        }
        else {
            System.out.println("Thanh toán đã bị huỷ.");
            return null;
        }
    }

    @Override
    public Object add(Product product) {
        if (!getCartShopList.contains(product)) {
            getCartShopList.add(product);
        }
        return product + " dã được thêm";
    }

    @Override
    public Object remove(Product product) {
        boolean removed = getCartShopList.removeIf(p -> p.getProductID() == product.getProductID());
        if (removed) {
            System.out.println("Đã xóa sản phẩm:");
            System.out.println(product);
        } else {
            System.out.println("Không tìm thấy sản phẩm để xóa.");
        }
        return getCartShopList;
    }
}
