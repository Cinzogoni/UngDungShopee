package Shopee.services.businessservice;

import Shopee.models.Customer;
import Shopee.models.Product;
import Shopee.services.cartservice.CartServiceImpl;
import Shopee.util.readfile.ReadCustomerFile;
import Shopee.views.HomePageView;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CustomerServiceImpl {
    private static volatile CustomerServiceImpl customerService;
    public CustomerServiceImpl() {}
    private String customerName;
    private String customerPhoneNumber;
    private String customerAddress;
    public static CustomerServiceImpl getCustomerService(String customerName) {
        if (customerService == null){
            synchronized (CustomerServiceImpl.class){
                if (customerService == null){
                    customerService = new CustomerServiceImpl();
                    customerService.customerName = customerName;

                    Customer customerInfo = customerService.getCustomerInfoFromDatabase(customerName);
                    if (customerInfo != null) {
                        customerService.customerPhoneNumber = customerInfo.getCustomerPhoneNumber();
                        customerService.customerAddress = customerInfo.getCustomerAddress();
                    }
                }
            }
        }
        return customerService;
    }
    private Customer getCustomerInfoFromDatabase(String customerName) {
        String filePath = "src\\Shopee\\database\\CustomerUserList.txt";
        ArrayList<Customer> customers = ReadCustomerFile.readCustomersFile(filePath);
        if (customers != null) {
            for (Customer customer : customers) {
                if (customer.getCustomerName().equals(customerName)) {
                    return customer;
                }
            }
        }
        return null;
    }

    public Object displayMenuAndGetCustomerChoice() {
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Chào mừng đến trình quản lý của " +customerName+ ".");
            System.out.println("0. Thông tin người dùng " +customerName+ ".");
            System.out.println("1. Mời chọn shop");
            System.out.println("2. Thoát");

            System.out.print("Mời chọn chức năng: ");
            try {
                int choose = scanner.nextInt();
                scanner.nextLine();
                switch (choose) {
                    case 0:
                        System.out.println("1. Tiếp tục xem, 2. Quay lại");
                        int chooseinfo = scanner.nextInt();
                        scanner.nextLine();
                        switch (chooseinfo){
                            case 1:
                                CustomerServiceImpl cusInfo = CustomerServiceImpl.getCustomerService(customerName);
                                Customer info = cusInfo.getCustomerInfoFromDatabase(customerName);
                                assert info != null;
                                System.out.println("Tên người dùng: " +info.getCustomerName());
                                System.out.println("Số điện thoại người dùng: " +info.getCustomerPhoneNumber());
                                System.out.println("Địa chỉ người dùng: " +info.getCustomerAddress());
                                System.out.println("--------------------------------------------------");
                                break;
                            case 2:
                                displayMenuAndGetCustomerChoice();
                                break;
                            default:
                                break;
                        }
                        break;
                    case 1:
                        System.out.println("Mời Chọn shop:");
                        System.out.println("1. Kaiwai Shop");
                        System.out.println("2. Nima Shop");
                        System.out.println("3. Tisa Shop");

                        int shopChoice = scanner.nextInt();
                        scanner.nextLine();

                        String shopName = "";
                        CartServiceImpl cartService = CartServiceImpl.getCartService(shopName, customerName);
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
                    case 2:
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
