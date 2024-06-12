import java.util.List;

public record Order(
        String id,
        OrderStatus orderStatus,
        List<Product> products
) {
}
