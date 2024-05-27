package Shopee.services.businessservice;

import Shopee.models.Product;
import Shopee.views.HomePageView;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CustomerServiceImpl implements BusinessService {
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
            System.out.println("0. Xem danh sách sản phẩm trong giỏ hàng");
            System.out.println("1. Thêm sản phẩm vào giỏ hàng");
            System.out.println("2. Xoá sản phẩm khỏi giỏ hàng");
            System.out.println("3. Thanh toán sản phẩm");
            System.out.println("4. Thoát");

            System.out.print("Mời chọn chức năng: ");
            try {
                int choose = scanner.nextInt();
                scanner.nextLine();
                switch (choose) {
                    case 0:
                        System.out.println("Xem tất cả sản phẩm trong giỏ hàng");
                        if (getCustomerList != null && !getCustomerList.isEmpty()){
                            getCustomerList.forEach(System.out::println);
                        }
                        else {
                            System.out.println("Danh sách sản phẩm trống.");
                            System.out.println("----------------------------------------");
                        }
                        break;
                    case 1:
                        System.out.println("Thêm sản phẩm vào giỏ hàng");
                        ProductServiceImpl addCustomerInService = ProductServiceImpl.getInstanceProduct();
                        ArrayList<Product> addProductList = addCustomerInService.getProductList();
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
                            if (getCustomerList == null)
                                getCustomerList = new ArrayList<>();
                            getCustomerList.add(productToAdd);
                            System.out.println(productToAdd + " đã được thêm vào giỏ hàng.");
                            //Danh sách không in ra sản phẩm đã thêm như yêu cầu
                            System.out.println("Danh sách sản phẩm sau khi thêm:");
                            getCustomerList.forEach(System.out::println);
                        }
                        else
                            System.out.println("Lựa chọn không hợp lệ.");
                        break;
                    case 2:
                        System.out.println("Xoá sản phẩm khỏi giỏ hàng");
                        ProductServiceImpl removeCustomerService = ProductServiceImpl.getInstanceProduct();
                        ArrayList<Product> removeProductList = removeCustomerService.getProductList();
                        if (removeProductList == null || removeProductList.isEmpty())
                            System.out.println("Không có sản phẩm để xoá");
                        System.out.println("Danh sách sản phẩm trong giỏ hàng:");
                        for (int i = 0; i < getCustomerList.size(); i++)
                            System.out.println(i + 1 + ". " + getCustomerList.get(i));
                        System.out.print("Nhập số thứ tự của sản phẩm cần xoá khỏi giỏ hàng: ");
                        int removeIndex = scanner.nextInt();
                        if (removeIndex >= 1 && removeIndex <= getCustomerList.size()){
                            Product productToRemove = getCustomerList.get(removeIndex - 1);
                            getCustomerList.remove(productToRemove);
                            System.out.println(productToRemove + " đã được xoá khỏi giỏ hàng.");
                            System.out.println("Danh sách sản phẩm sau khi xoá:");
                            getCustomerList.forEach(System.out::println);
                        }
                        else
                            System.out.println("Lựa chọn không hợp lệ.");
                        break;
                    case 3:
                        System.out.println("Thanh toán sản phẩm");
                        Payment();
                        break;
                    case 4:
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
            getCustomerList.clear();
            return total;
        }
        else {
            System.out.println("Thanh toán đã bị huỷ.");
            return null;
        }
    }

    @Override
    public Object add(Product product) {
        if (!getCustomerList.contains(product)) {
            getCustomerList.add(product);
        }
        return product + " dã được thêm";
    }

    @Override
    public Object remove(Product product) {
        boolean removed = getCustomerList.removeIf(p -> p.getProductID() == product.getProductID());
        if (removed) {
            System.out.println("Đã xóa sản phẩm:");
            System.out.println(product);
        } else {
            System.out.println("Không tìm thấy sản phẩm để xóa.");
        }
        return getCustomerList;
    }
}
