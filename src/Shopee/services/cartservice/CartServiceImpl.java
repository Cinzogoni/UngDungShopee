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
    private String shopName;
    private String customerName;


    public static CartServiceImpl getCartService(String shopName, String customerName) {
        if (cartService == null){
            synchronized (CustomerServiceImpl.class){
                if (cartService == null){
                    cartService = new CartServiceImpl();
                    cartService.shopName = shopName;
                    cartService.customerName = customerName;
                }
            }
        }
        return cartService;
    }
    ArrayList<Product> getCartShopList = new ArrayList<>();

    public void displayMenuAndGetCartChoice(String shopName) {
        Scanner scanner = new Scanner(System.in);
        int searchType;
        do {
            System.out.println("Chào mừng đến shop của tôi:");
            System.out.println("0. Danh sách sản phẩm trong giỏ hàng của " +shopName+ ".");
            System.out.println("1. Danh sách sản phẩm trong giỏ hàng của " +customerName+ ".");
            System.out.println("2. Thanh toán sản phẩm bạn đã chọn");
            System.out.println("3. Quay lại");

            System.out.print("Mời chọn chức năng: ");
            try {
                int choose = scanner.nextInt();
                scanner.nextLine();
                switch (choose) {
                    case 0:
                        ProductServiceImpl shopProductList = ProductServiceImpl.getInstanceProduct();
                        ArrayList<Product> products = shopProductList.getCartProductList(shopName);
                        if (products != null && !products.isEmpty()) {
                            System.out.println("Tìm kiếm sản phẩm, 1: Theo tên, 2: Mô tả, 3: Theo giá , 4: Quay lại");
                            products.forEach(System.out::println);

                            searchType = scanner.nextInt();
                            ArrayList<Product> searchedProducts = new ArrayList<>(getCartShopList);
                            handleSearchType(products, searchType, searchedProducts, shopName);
                            System.out.println("Thêm sản phẩm theo tìm kiếm vào giỏ hàng: 1. Có, 2. Không");
                            int addChoiceSearch = scanner.nextInt();
                            switch (addChoiceSearch){
                                case 1:
                                    System.out.println("Danh sách sản phẩm từ tìm kiếm:");
                                    for (int i = 0; i < searchedProducts.size(); i++)
                                        System.out.println(i + 1 + ". " + searchedProducts.get(i));

                                    System.out.print("Nhập số thứ tự trong giỏ hàng từ danh sách tìm kiếm: ");
                                    int choiceIndex = scanner.nextInt();
                                    if (choiceIndex >= 1 && choiceIndex <= searchedProducts.size()) {
                                        Product productToAdd = searchedProducts.get(choiceIndex - 1);
                                        addProduct(productToAdd);
                                        System.out.println(productToAdd + " đã được thêm vào giỏ hàng.");
                                        System.out.println("Danh sách sản phẩm sau khi thêm:");
                                        getCartShopList.forEach(System.out::println);
                                    } else
                                        System.out.println("Lựa chọn không hợp lệ.");
                                    break;
                                case 2:
                                    displayMenuAndGetCartChoice(shopName);
                                    break;
                                default:
                                    System.out.println("Mời chọn đúng lại");
                            }
                        }
                        else
                            System.out.println("Danh sách sản phẩm trống.");
                        break;
                    case 1:
                        if (getCartShopList != null && !getCartShopList.isEmpty()){
                            System.out.println("Danh sách sản phâm trong giỏ hàng của " +customerName+ ".");
                            System.out.println("Tìm kiếm sản phẩm, 1: Theo tên, 2: Mô tả, 3: Theo giá , 4: Quay lại");
                            getCartShopList.forEach(System.out::println);
                            searchType = scanner.nextInt();
                            ArrayList<Product> searchedProducts = new ArrayList<>(getCartShopList);
                            handleSearchType(getCartShopList, searchType, searchedProducts, shopName);

                            System.out.println("Xoá sản phẩm vào giỏ hàng: 1. Có, 2. Không");
                            int removeChoice = scanner.nextInt();
                            switch (removeChoice){
                                case 1:
                                    System.out.println("Danh sách sản phẩm trong giỏ hàng của " +customerName+ ".");
                                    for (int i = 0; i < getCartShopList.size(); i++)
                                        System.out.println(i + 1 + ". " + getCartShopList.get(i));
                                    System.out.print("Nhập số thứ tự của sản phẩm cần xoá khỏi giỏ hàng: ");
                                    int removeIndex = scanner.nextInt();
                                    if (removeIndex >= 1 && removeIndex <= getCartShopList.size()){
                                        Product productToRemove = getCartShopList.get(removeIndex - 1);
                                        removeProduct(productToRemove);



                                        System.out.println("Danh sách sản phẩm sau khi xoá:");
                                        getCartShopList.forEach(System.out::println);
                                    }
                                    else
                                        System.out.println("Lựa chọn không hợp lệ.");
                                    break;
                                case 2:
                                    break;
                                default:
                                    System.out.println("Mời chọn đúng lại");
                            }
                        }
                        else {
                            System.out.println("Danh sách sản phẩm trống.");
                            System.out.println("----------------------------------------");
                        }
                        break;
                    case 2:
                        Payment();
                        break;
                    case 3:
                        CustomerServiceImpl customerService = CustomerServiceImpl.getCustomerService(customerName);
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
        if (getCartShopList  == null || getCartShopList .isEmpty()) {
            System.out.println("Giỏ hàng trống. Không có gì để thanh toán.");
            return null;
        }
        double total = 0;
        for (Product product : getCartShopList ) {
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
    public Object addProduct(Product product) {
        if (!getCartShopList.contains(product)) {
            getCartShopList.add(product);
        }
        return product + " dã được thêm";
    }

    @Override
    public ArrayList<Product> removeProduct(Product product) {
        boolean removed = getCartShopList.removeIf(p -> p.getProductID() == product.getProductID());
        if (removed)
            System.out.println(product + "Đã xoá sản phẩm khỏi giỏ hàng");
         else
            System.out.println("Không tìm thấy sản phẩm để xóa.");
        return getCartShopList;
    }

    public void handleSearchType(ArrayList<Product> products, int searchType, ArrayList<Product> searchedProducts, String shopName){

        Scanner scanner = new Scanner(System.in);
        boolean continueSearching = true;
        while (continueSearching) {
            switch (searchType){
                case 1:
                    System.out.print("Nhập tên sản phẩm cần tìm: ");
                    String nameSearch = scanner.nextLine();
                    boolean foundByName = false;
                    for (Product product : products) {
                        if (product.getProductName().toLowerCase().contains(nameSearch.toLowerCase())) {
                            System.out.println(product);
                            foundByName = true;
                            searchedProducts.add(product);
                        }
                    }
                    if (!foundByName)
                        System.out.println("Không tìm thấy sản phẩm với tên: " + nameSearch);
                    break;
                case 2:
                    System.out.print("Nhập mô tả sản phẩm cần tìm: ");
                    String describeSearch = scanner.nextLine();
                    boolean foundByDescribe = false;
                    for (Product product : products) {
                        if (product.getProductDescribe().toLowerCase().contains(describeSearch.toLowerCase())) {
                            System.out.println(product);
                            foundByDescribe = true;
                            searchedProducts.add(product);
                        }
                    }
                    if (!foundByDescribe)
                        System.out.println("Không tìm thấy sản phẩm với mô tả: " + describeSearch);
                    break;
                case 3:
                    System.out.println("Nhập giá sản phẩm cần tìm:");
                    System.out.println("1. Dưới 100000, 2. 100000 ~ 500000, 3. Trên 500000, 4. Quay lại");
                    int priceChoice = scanner.nextInt();
                    scanner.nextLine();
                    switch (priceChoice){
                        case 1:
                            boolean foundUnder100000 = false;
                            System.out.println("Danh sách sản phẩm < 100.000:");
                            for (Product product: products){
                                if (product.getProducrPrice() < 100000.0){
                                    System.out.println(product);
                                    foundUnder100000 = true;
                                    searchedProducts.add(product);
                                }
                            }
                            if (!foundUnder100000)
                                System.out.println("Không tìm thấy sản phẩm với giá dưới 100000");
                            break;
                        case 2:
                            boolean foundBetween100000And500000 = false;
                            System.out.println("Danh sách sản phẩm 100.000 ~ 500.000:");
                            for (Product product: products){
                                if (product.getProducrPrice() > 100000.0 && product.getProducrPrice() <= 500000.0){
                                    System.out.println(product);
                                    foundBetween100000And500000 = true;
                                    searchedProducts.add(product);
                                }
                            }
                            if (!foundBetween100000And500000)
                                System.out.println("Không tìm thấy sản phẩm với giá 100000 ~ 500000");
                            break;
                        case 3:
                            boolean foundOver500000 = false;
                            System.out.println("Danh sách sản phẩm > 500.000:");
                            for (Product product: products){
                                if (product.getProducrPrice() > 500000.0){
                                    System.out.println(product);
                                    foundOver500000 = true;
                                    searchedProducts.add(product);
                                }
                            }
                            if (!foundOver500000)
                                System.out.println("Không tìm thấy sản phẩm với giá trên 500000");
                            break;
                        case 4:
                            displayMenuAndGetCartChoice(shopName);
                            break;
                        default:
                    }
                    break;
                case 4:
                    ProductServiceImpl shopList = ProductServiceImpl.getInstanceProduct();
                    ArrayList<Product> shopProductsList = shopList.getCartProductList(shopName);

                    System.out.println("Thêm sản phẩm vào giỏ hàng từ shop: 1. Có, 2. Không ");
                    int addChoiceAll = scanner.nextInt();
                    scanner.nextLine();
                    switch (addChoiceAll){
                        case 1:
                            System.out.println("Danh sách sản phẩm từ shop:");
                            for (int i = 0; i < shopProductsList.size(); i++)
                                System.out.println(i + 1 + ". " + shopProductsList.get(i));

                            System.out.print("Nhập số thứ tự trong giỏ hàng từ shop: ");
                            int choiceIndex = scanner.nextInt();
                            if (choiceIndex >= 1 && choiceIndex <= shopProductsList.size()) {
                                Product productToAdd = shopProductsList.get(choiceIndex - 1);
                                addProduct(productToAdd);
                                System.out.println(productToAdd + " đã được thêm vào giỏ hàng.");
                                System.out.println("Danh sách sản phẩm sau khi thêm:");
                                getCartShopList.forEach(System.out::println);
                            }
                            else
                                System.out.println("Lựa chọn không hợp lệ.");
                            break;
                        case 2:
                            displayMenuAndGetCartChoice(shopName);
                            break;
                        default:
                            System.out.println("Mời chọn đúng lại");
                    }
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
            if (continueSearching) {
                System.out.println("Tiếp tục tìm kiếm? (1: Có, 2: Không)");
                int choice = scanner.nextInt();
                scanner.nextLine();
                if (choice != 1) {
                    continueSearching = false;
                }
            }
        }
    }
}
