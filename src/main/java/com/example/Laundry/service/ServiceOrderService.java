// Service: com.example.Laundry.service.ServiceOrderService.java
package com.example.Laundry.service;

import com.example.Laundry.domain.OrderItem;
import com.example.Laundry.domain.QnaBoard;
import com.example.Laundry.domain.ServiceOrder;
import com.example.Laundry.dto.ServiceOrderCreateDto;
import com.example.Laundry.dto.ServiceOrderResponseDto;
import com.example.Laundry.mapper.ServiceOrderMapper;
import com.example.Laundry.repository.OrderItemRepository;
import com.example.Laundry.repository.ServiceOrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
public class ServiceOrderService {
    private final ServiceOrderRepository repo;
    private final OrderItemRepository orderrepo;
    private final ServiceOrderMapper mapper = ServiceOrderMapper.INSTANCE;

    public ServiceOrderService(ServiceOrderRepository repo, OrderItemRepository orderrepo) {
        this.repo = repo;
        this.orderrepo = orderrepo;
    }

    /**
     * category 값만 바꿔서 조회
     */
    public List<ServiceOrderResponseDto> getItemsByCategory(String category) {
        return repo.findAllByCategory(category).stream()
                .map(ServiceOrderResponseDto::new)
                .toList();
    }

    /**
     * 주문 저장
     * @param dto 컨트롤러에서 받은 주문 정보 DTO
     * @return 저장된 엔티티
     */
    public ServiceOrder saveOrder(
            String orderer,
            String product,
            String inum,
            String count,
            String reservationDate,  // "yyyy-MM-dd" 형식
            String orderAddr,
            String request,
            String payment,
            String email,
            String category,
            BigDecimal order_price
    ) {
        ServiceOrder o = new ServiceOrder();
        o.setOrderer(orderer);
        o.setOrderAddr(orderAddr);
        o.setRequest(request);
        o.setCategory(category);
        o.setOrderPrice(order_price);
        o.setRegdate(LocalDate.now());
        o.setReservationDate(reservationDate);


        return repo.save(o);
    }

    /**
     * 주문 목록
     */
    public Page<ServiceOrder> getPagedOrders(String orderer, String keyword, String state, int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        if (state == null || state.trim().isEmpty()) {
            return repo.findByOrderer(orderer, pageable);
        } else {
            return repo.findByOrdererAndState(orderer, state, pageable);
        }
    }

    // 주문 상세 조회
    public ServiceOrder findOrderByCode(Integer code) {
        return repo.findByCode(code)
                .orElse(null);
    }

    // 주문 품목 리스트 조회
    public List<OrderItem> findOrderItems(Integer orderCode) {
        return orderrepo.findByCode(orderCode);
    }
}