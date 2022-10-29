package hello.app.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV0 {

    private final OrderRepositoryV0 orderRepository0;

    public void orderItem(String itemId) {
        orderRepository0.save(itemId);
    }
}
