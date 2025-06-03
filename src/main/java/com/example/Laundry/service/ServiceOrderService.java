// Service: com.example.Laundry.service.ServiceOrderService.java
package com.example.Laundry.service;

import com.example.Laundry.domain.QnaBoard;
import com.example.Laundry.domain.ServiceOrder;
import com.example.Laundry.dto.ServiceOrderCreateDto;
import com.example.Laundry.dto.ServiceOrderResponseDto;
import com.example.Laundry.mapper.ServiceOrderMapper;
import com.example.Laundry.repository.ServiceOrderRepository;
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
    private final ServiceOrderMapper mapper = ServiceOrderMapper.INSTANCE;

    public ServiceOrderService(ServiceOrderRepository repo) {
        this.repo = repo;
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
     * @return 저장된 엔티티 (필요 없으면 void 로 해도 괜찮습니다)
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
}