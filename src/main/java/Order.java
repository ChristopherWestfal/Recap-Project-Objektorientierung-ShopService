import lombok.With;

import java.time.ZonedDateTime;
import java.util.List;

@With
public record Order(
        String id,
        Orderstatus orderStatus,
        List<Product> products,
        String timestamp
) {
}
