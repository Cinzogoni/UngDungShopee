package Shopee.services.businessservice;

import Shopee.models.Product;
import Shopee.views.HomePageView;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class OwnerServiceImpl implements BusinessService, ProductService {
    private static volatile OwnerServiceImpl ownerService;
    public OwnerServiceImpl() {}
    public static OwnerServiceImpl getOwnerService() {
        if (ownerService == null){
            synchronized (CustomerServiceImpl.class){
                if (ownerService == null){
                    ownerService = new OwnerServiceImpl();
                }
            }
        }
        return ownerService;
    }
    ArrayList<Product> getOwnerList = new ArrayList<>();

    public Object displayMenuAndOwnerChoice() {
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Chào mừng đến trình quản lý của Owner:");
            System.out.println("0. Xem danh sách sản phẩm trong giỏ hàng");
            System.out.println("1. Thêm sản phẩm vào giỏ hàng");
            System.out.println("2. Xoá sản phẩm khỏi giỏ hàng");
            System.out.println("3. Sửa thông tin sản phẩm");
            System.out.println("4. Thanh toán sản phẩm");
            System.out.println("5. Thoát");

            System.out.print("Mời chọn chức năng: ");
            try {
                int choose = scanner.nextInt();
                scanner.nextLine();
                switch (choose) {
                    case 0:
                        System.out.println("Xem tất cả sản phẩm trong giỏ hàng");
                        if (getOwnerList != null && !getOwnerList.isEmpty()){
                            getOwnerList.forEach(System.out::println);
                        }
                        else {
                            System.out.println("Danh sách sản phẩm trống.");
                            System.out.println("----------------------------------------");
                        }
                        break;
                    case 1:
                        System.out.println("Thêm sản phẩm vào giỏ hàng");
                        ProductServiceImpl addOwnerInService = ProductServiceImpl.getInstanceProduct();
                        ArrayList<Product> addProductList = addOwnerInService.getProductList();
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
                            if (getOwnerList == null)
                                getOwnerList = new ArrayList<>();
                            getOwnerList.add(productToAdd);
                            System.out.println(productToAdd + " đã được thêm vào giỏ hàng.");
                            //Danh sách không in ra sản phẩm đã thêm như yêu cầu
                            System.out.println("Danh sách sản phẩm sau khi thêm:");
                            getOwnerList.forEach(System.out::println);
                        }
                        else
                            System.out.println("Lựa chọn không hợp lệ.");
                        break;
                    case 2:
                        System.out.println("Xoá sản phẩm khỏi giỏ hàng");
                        ProductServiceImpl removeOwnerService = ProductServiceImpl.getInstanceProduct();
                        ArrayList<Product> removeProductList = removeOwnerService.getProductList();
                        if (removeProductList == null || removeProductList.isEmpty())
                            System.out.println("Không có sản phẩm để xoá");
                        System.out.println("Danh sách sản phẩm trong giỏ hàng:");
                        for (int i = 0; i < getOwnerList.size(); i++)
                            System.out.println(i + 1 + ". " + getOwnerList.get(i));
                        System.out.print("Nhập số thứ tự của sản phẩm cần xoá khỏi giỏ hàng: ");
                        int removeIndex = scanner.nextInt();
                        if (removeIndex >= 1 && removeIndex <= getOwnerList.size()){
                            Product productToRemove = getOwnerList.get(removeIndex - 1);
                            getOwnerList.remove(productToRemove);
                            System.out.println(productToRemove + " đã được xoá khỏi giỏ hàng.");
                            System.out.println("Danh sách sản phẩm sau khi xoá:");
                            getOwnerList.forEach(System.out::println);
                        }
                        else
                            System.out.println("Lựa chọn không hợp lệ.");
                        break;
                    case 3:
                        System.out.println("Sửa thông tin sản phẩm");
                        editProduct();
                        break;
                    case 4:
                        System.out.println("Thanh toán sản phẩm");
                        Payment();
                        break;
                    case 5:
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
            getOwnerList.clear();
            return total;
        }
        else {
            System.out.println("Thanh toán đã bị huỷ.");
            return null;
        }
    }

    @Override
    public Object add(Product product) {
        if (!getOwnerList.contains(product)) {
            getOwnerList.add(product);
        }
        return product + " dã được thêm";
    }

    @Override
    public Object remove(Product product) {
        boolean removed = getOwnerList.removeIf(p -> p.getProductID() == product.getProductID());
        if (removed) {
            System.out.println("Đã xóa sản phẩm:");
            System.out.println(product);
        } else {
            System.out.println("Không tìm thấy sản phẩm để xóa.");
        }
        return getOwnerList;
    }

    @Override
    public ArrayList<Product> getProductList() {
        return getOwnerList;
    }

    @Override
    public  ArrayList<Product> editProduct() {
        ProductServiceImpl editOwmerService = ProductServiceImpl.getInstanceProduct();
        ArrayList<Product> editProductList = editOwmerService.getProductList();
        Scanner scanner = new Scanner(System.in);
        if (editProductList.isEmpty()) {
            System.out.println("Không có sản phẩm nào để sửa.");
            return null;
        }
        System.out.println("Danh sách sản phẩm:");
        for (int i = 0; i < getOwnerList.size(); i++) {
            System.out.println((i + 1) + ". " + getOwnerList.get(i));
        }

        System.out.print("Nhập số thứ tự của sản phẩm muốn sửa: ");
        int choiceIndex = scanner.nextInt();
        scanner.nextLine();

        if (choiceIndex >= 1 && choiceIndex <= getOwnerList.size()) {
            Product productToEdit = getOwnerList.get(choiceIndex - 1);

            System.out.println("Thông tin sản phẩm cũ:");
            System.out.println(productToEdit);

            System.out.print("Nhập tên mới của sản phẩm: ");
            String newName = scanner.nextLine();
            System.out.print("Nhập giá mới của sản phẩm: ");
            double newPrice = scanner.nextDouble();
            System.out.print("Nhập số lượng mới của sản phẩm: ");
            int newAmount = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Nhập mô tả mới của sản phẩm: ");
            String newDescription = scanner.nextLine();
            System.out.println("---------------------------------------");

            productToEdit.setProductName(newName);
            productToEdit.setProducrPrice(newPrice);
            productToEdit.setProductAmount(newAmount);
            productToEdit.setDescribe(newDescription);

            System.out.println("Thông tin sản phẩm sau khi sửa đổi:");
            System.out.println(productToEdit);
        }
        else
            System.out.println("Lựa chọn không hợp lệ.");
        return getOwnerList;
    }
}
