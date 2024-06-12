import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ShopService shopService = new ShopService(new ProductRepo(), new OrderMapRepo(), new IdService());

        shopService.getProductRepo().addProduct(new Product("1", "Säge"));
        shopService.getProductRepo().addProduct(new Product("2", "Hammer"));
        shopService.getProductRepo().addProduct(new Product("3", "Nagel"));

        List<String> orderList = new ArrayList<>();
        orderList.add("1");
        orderList.add("2");
        orderList.add("3");

        try {
            shopService.addOrder(orderList);
        } catch (NoProductExistException e) {
            throw new RuntimeException(e);
        }

        System.out.println(shopService.getListFilteredByOrderstatus(Orderstatus.PROCESSING).toString());
        shopService.updateOrder(shopService.getOrderIDList().getFirst(), Orderstatus.IN_DELIVERY);
        System.out.println(shopService.getListFilteredByOrderstatus(Orderstatus.IN_DELIVERY).toString());


    }
}
