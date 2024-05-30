package Shopee.services.businessservice;

import Shopee.models.Product;
import Shopee.services.cartservice.CartServiceImpl;
import Shopee.views.HomePageView;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CustomerServiceImpl {
    private static volatile CustomerServiceImpl customerService;
    public CustomerServiceImpl() {}
    public static CustomerServiceImpl getCustomerService() {
        if (customerService == null){
            synchronized (CustomerServiceImpl.class){
                if (customerService == null){
                    customerService = new CustomerServiceImpl();
                }
            }
        }
        return customerService;
    }
    ArrayList<Product> getCustomerList = new ArrayList<>();

    public Object displayMenuAndGetCustomerChoice() {
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Chào mừng đến shop của tôi:");
            System.out.println("0. Mời chọn shop");
            System.out.println("1. Thoát");

            System.out.print("Mời chọn chức năng: ");
            try {
                int choose = scanner.nextInt();
                scanner.nextLine();
                switch (choose) {
                    case 0:
                        System.out.println("Mời Chọn shop:");
                        System.out.println("1. Kaiwai Shop");
                        System.out.println("2. Nima Shop");
                        System.out.println("3. Tisa Shop");

                        int shopChoice = scanner.nextInt();
                        scanner.nextLine();

                        String shopName = "";
                        CartServiceImpl cartService = CartServiceImpl.getCartService();
                        switch (shopChoice) {
                            case 1:
                                shopName = "Kaiwai Shop";
                                cartService.displayMenuAndGetCartChoice(shopName);
                                break;
                            case 2:
                                shopName = "Nima Shop";
                                cartService.displayMenuAndGetCartChoice(shopName);
                                break;
                            case 3:
                                shopName = "Tisa Shop";
                                cartService.displayMenuAndGetCartChoice(shopName);
                                break;
                            default:
                                System.out.println("Lựa chọn không hợp lệ.");
                                continue;
                        }
                        ProductServiceImpl ChoiceCartShopInService = ProductServiceImpl.getInstanceProduct();
                        ArrayList<Product> cartShop = ChoiceCartShopInService.getCartProductList(shopName);
                        System.out.println("Danh sách sản phẩm của shop " + shopName + ":");
                        cartShop.forEach(System.out::println);
                    case 1:
                        HomePageView homePageView = HomePageView.gethomePageView();
                        homePageView.displayHomePage();
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
        }
        while (true);
    }
}
