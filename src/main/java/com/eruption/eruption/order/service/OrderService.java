package com.eruption.eruption.order.service;

import com.eruption.eruption.global.exception.BusinessException;
import com.eruption.eruption.global.exception.ErrorCode;
import com.eruption.eruption.goods.entity.GoodsOption;
import com.eruption.eruption.goods.repository.GoodsOptionRepository;
import com.eruption.eruption.order.dto.CreateOrderRequest;
import com.eruption.eruption.order.dto.OrderResponse;
import com.eruption.eruption.order.entity.OrderItem;
import com.eruption.eruption.order.entity.Orders;
import com.eruption.eruption.order.repository.OrderItemRepository;
import com.eruption.eruption.order.repository.OrdersRepository;
import com.eruption.eruption.user.entity.User;
import com.eruption.eruption.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderItemRepository orderItemRepository;
    private final OrdersRepository ordersRepository;
    private final GoodsOptionRepository goodsOptionRepository;
    private final UserRepository userRepository;

    @Transactional
    public OrderResponse createOrder(Long userId, CreateOrderRequest createOrderRequest) {
        LocalDateTime now = LocalDateTime.now();
        User user = userRepository.findById(userId).orElseThrow(() -> new BusinessException(ErrorCode.UNAUTHORIZED));
        // 옵션 별 갯수 맵으로 변환
        Map<Long, Integer> qtyByOption = createOrderRequest.items().stream()
                .collect(Collectors.toMap(
                        CreateOrderRequest.OrderItemRequest::optionId,
                        CreateOrderRequest.OrderItemRequest::qty,
                        Integer::sum,
                        LinkedHashMap::new));
        // 옵션만 리스트화
        List<Long> optionIds = List.copyOf(qtyByOption.keySet());

        List<GoodsOption> options = goodsOptionRepository.findAllWithGoodsByIdIn(optionIds);
        // 요청 옵션 검증
        if (options.size() != optionIds.size()) {
            throw new BusinessException(ErrorCode.GOODS_OPTION_NOT_FOUND);
        }

        int totalAmt = 0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (GoodsOption goodsOption : options) {

            int qty = qtyByOption.get(goodsOption.getId());

            // 굿즈 유효 기간 검증
            goodsOption.getGoods().validateReservable(now);
            // 수량 검증 및 재고 차감
            goodsOption.decreaseStock(qty);
            // 주문 아이템 생성
            OrderItem orderItem = OrderItem.builder()
                    .goodsOption(goodsOption)
                    .price(goodsOption.getPrice())
                    .qty(qty)
                    .build();

            orderItems.add(orderItem);

            totalAmt += goodsOption.getPrice() * qty;

        }

        Orders order = Orders.builder()
                .orderNo(UUID.randomUUID().toString())
                .user(user)
                .totalAmt(totalAmt)
                .expiredAt(now.plusMinutes(30))
                .build();

        orderItems.forEach(orderItem -> orderItem.assignOrders(order));

        ordersRepository.save(order);
        orderItemRepository.saveAll(orderItems);

        return OrderResponse.of(order, orderItems);
    }
}
