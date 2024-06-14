package Shopee.services.cartservice;

import Shopee.models.Product;
import Shopee.services.businessservice.CustomerServiceImpl;
import Shopee.services.businessservice.ProductServiceImpl;

import java.text.DecimalFormat;
import java.util.*;

public class CartServiceImpl implements CartService {
    ArrayList<Product> getCartShopList = new ArrayList<>();
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
                    cartService.getCartShopList = ProductServiceImpl.readTempCartFromFile(shopName);
                }
            }
        }
        return cartService;
    }

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
                            products.sort(Comparator.comparing(Product::getProductName));

                            int offset1 = 0;
                            int limit1 = 5;
                            int totalProducts1 = products.size();
                            int totalPages1 = (int) Math.ceil((double) totalProducts1 / limit1);
                            boolean continuePaging1 = true;

                            while (continuePaging1) {
                                totalProducts1 = products.size();
                                int pageStart = offset1 * limit1;
                                int pageEnd = Math.min(pageStart + limit1, totalProducts1);
                                System.out.println("Hiển thị sản phẩm từ " + (pageStart + 1) + " đến " + pageEnd + " trong tổng số " + totalProducts1);
                                for (int i = pageStart; i < pageEnd; i++) {
                                    System.out.println((i + 1) + ". " + products.get(i));
                                }
                                System.out.println("Chọn trang: (B: Trang trước, N: Trang sau, S: Tìm kiếm, Q: Quay lại)");
                                String pageChoice = scanner.next();

                                switch (pageChoice.toUpperCase()) {
                                    case "B":
                                        if (offset1 > 0) offset1--;
                                        else System.out.println("Đây là trang đầu tiên.");
                                        break;
                                    case "N":
                                        if (offset1 < totalPages1 - 1) offset1++;
                                        else System.out.println("Đây là trang cuối cùng.");
                                        break;
                                    case "S":
                                        System.out.println("Tìm kiếm sản phẩm, 1: Theo tên, 2: Mô tả, 3: Theo giá , 4: Quay lại");
                                        searchType = scanner.nextInt();
                                        Set<Product> searchedProducts = new HashSet<>(getCartShopList);
                                        handleSearchType(products, searchType, searchedProducts, shopName);
                                        System.out.println("Thêm sản phẩm theo tìm kiếm vào giỏ hàng: 1. Có, 2. Không");
                                        int addChoiceSearch = scanner.nextInt();
                                        switch (addChoiceSearch) {
                                            case 1:
                                                System.out.println("Danh sách sản phẩm từ tìm kiếm:");
                                                int index = 1;
                                                for (Product product : searchedProducts) {
                                                    System.out.println(index + ". " + product);
                                                    index++;
                                                }

                                                System.out.println("Nhập số thứ tự trong danh sách tìm kiếm để thêm vào giỏ hàng:");
                                                int choiceIndex = scanner.nextInt();

                                                if (choiceIndex >= 1 && choiceIndex <= searchedProducts.size()) {
                                                    // Find the product corresponding to the chosen index
                                                    Iterator<Product> iterator = searchedProducts.iterator();
                                                    Product productToAdd = null;
                                                    for (int i = 0; i < choiceIndex; i++) {
                                                        productToAdd = iterator.next();
                                                    }

                                                    if (productToAdd != null) {
                                                        addProduct(new Product(productToAdd.getProductID(), productToAdd.getProductName(), productToAdd.getProductPrice(), productToAdd.getProductAmount(), productToAdd.getProductDescribe()));
                                                        System.out.println(productToAdd + " đã được thêm vào giỏ hàng.");
                                                        System.out.println("Danh sách sản phẩm sau khi thêm:");
                                                        getCartShopList.forEach(System.out::println);
                                                    } else {
                                                        System.out.println("Không tìm thấy sản phẩm với số thứ tự đã nhập.");
                                                    }
                                                } else
                                                    System.out.println("Lựa chọn không hợp lệ.");
                                                break;
                                            case 2:
                                                displayMenuAndGetCartChoice(shopName);
                                                break;
                                            default:
                                                System.out.println("Mời chọn đúng lại");
                                        }
                                        break;
                                    case "Q":
                                        continuePaging1 = false;
                                        break;
                                    default:
                                        System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
                                }
                            }
                        }
                        else
                            System.out.println("Danh sách sản phẩm trống.");
                        break;
                    case 1:
                        if (getCartShopList != null && !getCartShopList.isEmpty()) {
                            getCartShopList.sort(Comparator.comparing(Product::getProductName));
                            int offset2 = 0;
                            int limit2 = 5;
                            int totalProducts2 = getCartShopList.size();
                            int totalPages2 = (int) Math.ceil((double) totalProducts2 / limit2);
                            boolean continuePaging2 = true;

                            while (continuePaging2) {
                                totalProducts2 = getCartShopList.size();
                                int pageStart = offset2 * limit2;
                                int pageEnd = Math.min(pageStart + limit2, totalProducts2);
                                System.out.println("Danh sách sản phẩm từ " + (pageStart + 1) + " đến " + pageEnd + " trong tổng số " + totalProducts2);
                                for (int i = pageStart; i < pageEnd; i++) {
                                    System.out.println((i + 1) + ". " + getCartShopList.get(i));
                                }

                                System.out.println("Chọn trang: (B: Trang trước, N: Trang sau, S: Tìm kiếm, R: Xóa sản phẩm, Q: Quay lại)");
                                String pageChoice = scanner.next();

                                switch (pageChoice.toUpperCase()) {
                                    case "P":
                                        if (offset2 > 0) offset2--;
                                        else System.out.println("Đây là trang đầu tiên.");
                                        break;
                                    case "N":
                                        if (offset2 < totalPages2 - 1) offset2++;
                                        else System.out.println("Đây là trang cuối cùng.");
                                        break;
                                    case "S":
                                        System.out.println("Tìm kiếm sản phẩm, 1: Theo tên, 2: Mô tả, 3: Theo giá , 4: Quay lại");
                                        searchType = scanner.nextInt();
                                        Set<Product> searchedProducts = new HashSet<>(getCartShopList);
                                        handleSearchType(getCartShopList, searchType, searchedProducts, shopName);
                                        System.out.println("Thêm sản phẩm theo tìm kiếm vào giỏ hàng: 1. Có, 2. Không");
                                        int addChoiceSearch = scanner.nextInt();
                                        switch (addChoiceSearch) {
                                            case 1:
                                                System.out.println("Danh sách sản phẩm từ tìm kiếm:");
                                                int index = 1;
                                                for (Product product : searchedProducts) {
                                                    System.out.println(index + ". " + product);
                                                    index++;
                                                }

                                                System.out.println("Nhập số thứ tự trong danh sách tìm kiếm để thêm vào giỏ hàng:");
                                                int choiceIndex = scanner.nextInt();
                                                scanner.nextLine();

                                                if (choiceIndex >= 1 && choiceIndex <= searchedProducts.size()) {
                                                    Iterator<Product> iterator = searchedProducts.iterator();
                                                    Product productToAdd = null;
                                                    for (int i = 0; i < choiceIndex; i++) {
                                                        productToAdd = iterator.next();
                                                    }

                                                    if (productToAdd != null) {
                                                        addProduct(new Product(productToAdd.getProductID(), productToAdd.getProductName(), productToAdd.getProductPrice(), productToAdd.getProductAmount(), productToAdd.getProductDescribe()));
                                                        System.out.println(productToAdd + " đã được thêm vào giỏ hàng.");
                                                        System.out.println("Danh sách sản phẩm sau khi thêm:");
                                                        getCartShopList.forEach(System.out::println);
                                                    }
                                                    else
                                                        System.out.println("Không tìm thấy sản phẩm với số thứ tự đã nhập.");
                                                }
                                                else
                                                    System.out.println("Lựa chọn không hợp lệ.");

                                                break;
                                            case 2:
                                                break;
                                            default:
                                                System.out.println("Mời chọn đúng lại");
                                        }
                                        break;
                                    case "R":
                                        System.out.println("Danh sách sản phẩm trong giỏ hàng của " + customerName + ".");
                                        for (int i = 0; i < getCartShopList.size(); i++)
                                            System.out.println(i + 1 + ". " + getCartShopList.get(i));

                                        System.out.print("Nhập số thứ tự của sản phẩm cần xóa khỏi giỏ hàng: ");
                                        int removeIndex = scanner.nextInt();
                                        if (removeIndex >= 1 && removeIndex <= getCartShopList.size()) {
                                            Product productToRemove = getCartShopList.get(removeIndex - 1);
                                            removeProduct(productToRemove);
                                            System.out.println("Danh sách sản phẩm sau khi xóa:");
                                            getCartShopList.forEach(System.out::println);
                                        }
                                        else
                                            System.out.println("Lựa chọn không hợp lệ.");
                                        break;
                                    case "Q":
                                        continuePaging2 = false;
                                        break;
                                    default:
                                        System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
                                }
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
                        ProductServiceImpl.updateCart(shopName, getCartShopList);
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
        if (getCartShopList  == null || getCartShopList.isEmpty()) {
            System.out.println("Giỏ hàng trống. Không có gì để thanh toán.");
            return null;
        }
        Scanner scanner = new Scanner(System.in);
        double total = 0;

        // Bản sao của giỏ hàng để lưu trữ tạm thời các thay đổi
        ArrayList<Product> tempCartList = new ArrayList<>(getCartShopList);

        // Lưu trữ tạm thời số lượng sản phẩm đã chọn
        HashMap<Product, Integer> tempCart = new HashMap<>();

        HashMap<Integer, Double> originalPrices = new HashMap<>();
        for (Product product : tempCartList) {
            if (!originalPrices.containsKey(product.getProductID())) {
                originalPrices.put(product.getProductID(), product.getProductPrice());
            }
        }

        DecimalFormat decimalFormat = new DecimalFormat("#,###");

        for (Product product : tempCartList) {
            System.out.println("Sản phẩm: " + product.getProductName() +
                    "\nGiá: " + decimalFormat.format(product.getProductPrice()) + " VND" +
                    "\nSố lượng có sẵn: " + product.getProductAmount());
            System.out.print("Nhập số lượng muốn mua: ");
            int quantity = scanner.nextInt();
            while (quantity <= 0 || quantity > product.getProductAmount()) {
                System.out.println("Số lượng không hợp lệ. Vui lòng nhập lại.");
                System.out.println("Nhập số lượng muốn mua: ");
                quantity = scanner.nextInt();
            }
            total += product.getProductPrice() * quantity;
            // Lưu trữ số lượng đã chọn tạm thời
            tempCart.put(product, quantity);
            System.out.println("---------------------------------------");
        }

        System.out.println("Tổng số tiền cần thanh toán: " + decimalFormat.format(total) + " VND");
        System.out.println("Bạn chắc chắn muốn thanh toán? (1: Có, 2: Không)");
        int choice = new Scanner(System.in).nextInt();
        if (choice == 1) {
            // Cập nhật số lượng sản phẩm chỉ khi thanh toán được xác nhận
            for (Map.Entry<Product, Integer> entry : tempCart.entrySet()) {
                Product product = entry.getKey();
                int quantity = entry.getValue();

                // Cập nhật số lượng sản phẩm trong giỏ hàng thực tế
                getCartShopList.stream()
                        .filter(p -> p.getProductID() == product.getProductID())
                        .findFirst()
                        .ifPresent(p -> p.setProductAmount(p.getProductAmount() - quantity));
            }
            System.out.println("Thanh toán thành công!");
            getCartShopList.clear();
            ProductServiceImpl.deleteTempCart(shopName);
            return total;
        }
        else {
            // Khôi phục giá sản phẩm ban đầu nếu thanh toán bị hủy
            for (Product product : tempCartList) {
                product.setProducrPrice(originalPrices.get(product.getProductID()));
            }
            System.out.println("Thanh toán đã bị huỷ.");
            return null;
        }
    }

    @Override
    public Object addProduct(Product product) {
        boolean compares = getCartShopList.stream()
                .anyMatch(p -> Objects.equals(p.getProductID(), product.getProductID()));

        if (!compares) {
            getCartShopList.add(product);
            return product + " đã được thêm";
        }
        else
            return "Sản phẩm đã tồn tại trong giỏ hàng.";
    }

    @Override
    public ArrayList<Product> removeProduct(Product product) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Có muốn xoá sản phẩm không: 1. Có, 2. Không");
        int removeChoice = scanner.nextInt();
        scanner.nextLine();
        switch (removeChoice){
            case 1:
                boolean removed = getCartShopList.removeIf(p -> p.getProductID() == product.getProductID());
                if (removed) {
                    System.out.println(product + " đã xoá sản phẩm khỏi giỏ hàng");
                }
                else
                    System.out.println("Không tìm thấy sản phẩm để xóa.");
                break;
            case 2:
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ");
        }
        return getCartShopList;
    }

    public void handleSearchType(ArrayList<Product> products, int searchType, Set<Product> searchedProducts, String shopName){
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
                                if (product.getProductPrice() < 100000.0){
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
                                if (product.getProductPrice() > 100000.0 && product.getProductPrice() <= 500000.0){
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
                                if (product.getProductPrice() > 500000.0){
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
