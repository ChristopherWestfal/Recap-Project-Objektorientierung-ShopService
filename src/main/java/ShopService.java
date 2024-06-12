import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@RequiredArgsConstructor
public class ShopService {
    private final ProductRepo productRepo;
    private final OrderRepo orderRepo;
    private final IdService idService;
    private List<String> orderIDList = new ArrayList<>();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd:MMMM:yyyy : HH:mm");


    public Order addOrder(List<String> productIds) throws NoProductExistException{
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Optional<Product> productToOrder = productRepo.getProductById(productId);
            products.add(productToOrder.orElseThrow(() -> new NoProductExistException("Product mit der Id: " + productId + " konnte nicht bestellt werden!")));
        }

        orderIDList.add(idService.generatedId());
        ZonedDateTime orderTimestamp = ZonedDateTime.now();

        Order newOrder = new Order(orderIDList.getLast(), Orderstatus.PROCESSING, products, orderTimestamp.format(formatter));

        return orderRepo.addOrder(newOrder);
    }

    public List<Order> getListFilteredByOrderstatus(Orderstatus orderstatus){
        return orderRepo.getOrders().stream()
                .filter(orderlist -> orderlist.orderStatus().equals(orderstatus))
                .collect(Collectors.toList());
    }

    public void updateOrder(String orderID, Orderstatus orderstatus){
        Order temp = orderRepo.getOrderById(orderID).withOrderStatus(orderstatus);
        orderRepo.removeOrder(orderID);
        orderRepo.addOrder(temp);
    }
}
