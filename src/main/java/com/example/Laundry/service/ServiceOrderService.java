// Service: com.example.Laundry.service.ServiceOrderService.java
package com.example.Laundry.service;

import com.example.Laundry.domain.*;
import com.example.Laundry.dto.ServiceOrderCreateDto;
import com.example.Laundry.dto.ServiceOrderResponseDto;
import com.example.Laundry.mapper.ServiceOrderMapper;
import com.example.Laundry.repository.ItemsRepository;
import com.example.Laundry.repository.OrderItemRepository;
import com.example.Laundry.repository.PaymentLogRepository;
import com.example.Laundry.repository.ServiceOrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
public class ServiceOrderService {
    private final ServiceOrderRepository repo;
    private final OrderItemRepository orderrepo;
    private final ItemsRepository itemsRepository;
    private final ServiceOrderMapper mapper = ServiceOrderMapper.INSTANCE;
    private final IamportService iamportService;
    private final PaymentLogRepository paymentLogRepository;


    public ServiceOrderService(ServiceOrderRepository repo, OrderItemRepository orderrepo, ItemsRepository itemsRepository, IamportService iamportService, PaymentLogRepository paymentLogRepository) {
        this.repo = repo;
        this.orderrepo = orderrepo;
        this.itemsRepository = itemsRepository;
        this.iamportService = iamportService;
        this.paymentLogRepository = paymentLogRepository;
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
     */
    public ServiceOrder saveOrder(
            String orderer,
            String product,
            String inum,
            String count,
            String reservationDate,  // "yyyy-MM-dd" 형식
            String orderAddr,
            String request,
            String email,
            String category,
            String productcount,
            BigDecimal order_price,
            String merchant_uid,
            String imp_uid
    ) {
        ServiceOrder o = new ServiceOrder();
        o.setOrderer(orderer);
        o.setOrderAddr(orderAddr);
        o.setRequest(request);
        o.setCategory(category);
        o.setOrderPrice(order_price);
        o.setState("결제완료");
        o.setRegdate(LocalDate.now());
        o.setReservationDate(reservationDate);
        o.setMerchantUid(merchant_uid);
        o.setImpUid(imp_uid);


        ServiceOrder savedOrder = repo.save(o);

        List<OrderItem> orderItems = new ArrayList<>();
        String[] itemNames = productcount.split(",");
        String[] counts = count.split("/");

        for (int i = 0; i < itemNames.length; i++) {
            if (itemNames[i].isBlank() || counts[i].isBlank()) continue;

            OrderItem item = new OrderItem();
            item.setCode(savedOrder.getCode());  // 연관관계 주입

            String raw = itemNames[i].trim();
            String itemName = raw.replaceAll("\\s*\\d+개$", "").trim();
            Items productItem = itemsRepository.findByItem(itemName)
                    .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));

            item.setInum(productItem.getInum());
            item.setCount(Integer.parseInt(counts[i + 1].trim()));

            orderItems.add(item);
        }

        orderrepo.saveAll(orderItems);

        return savedOrder;
    }

    /**
     * 주문 목록
     */
    public Page<ServiceOrder> getPagedOrders(String orderer, String condition, String keyword, String state, int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);

        boolean hasKeyword = keyword != null && !keyword.trim().isEmpty();
        boolean hasState = state != null && !state.trim().isEmpty() && !"상태".equals(state);

        if (orderer == null || orderer.trim().isEmpty()) {
            // 관리자 조회
            if (hasKeyword && hasState) {
                return repo.findByConditionAndState(condition, keyword, state, pageable);
            } else if (hasKeyword) {
                return repo.findByCondition(condition, keyword, pageable);
            } else if (hasState) {
                return repo.findByState(state, pageable);
            } else {
                return repo.findAll(pageable);
            }
        } else {
            // 일반 사용자: 본인 것만 조회
            if (state == null || state.trim().isEmpty()) {
                return repo.findByOrderer(orderer, pageable);
            } else {
                return repo.findByOrdererAndState(orderer, state, pageable);
            }
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

    /**
     * 환불
     */
    public boolean refundOrder(String merchantUid, String impUid) {
        Optional<ServiceOrder> optionalOrder = repo.findByMerchantUid(merchantUid);
        if (optionalOrder.isPresent()) {
            ServiceOrder order = optionalOrder.get();

            if ("결제완료".equals(order.getState())) {
                boolean cancelSuccess = iamportService.cancelPayment(impUid, "사용자 환불 요청");
                if (cancelSuccess) {
                    order.setState("환불완료");
                    repo.save(order);

                    // 로그 남기기
                    PaymentLog log = new PaymentLog();
                    log.setMerchantUid(merchantUid);
                    log.setImpUid(impUid);
                    log.setStatus("cancelled");
                    log.setMessage("사용자 환불 요청으로 취소됨");
                    log.setCreatedAt(LocalDateTime.now());
                    paymentLogRepository.save(log);

                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 송장번호 발급 및 택배사 저장
     */
    @Transactional
    public int insertCourierAndInvoice(List<Integer> codes, String courier) {
        int count = 0;

        for (Integer code : codes) {
            ServiceOrder order = repo.findById(code)
                    .orElseThrow(() -> new IllegalArgumentException("주문 없음: " + code));

            String invoice = generateRandomInvoice();

            order.setGetCourier(courier);
            order.setGetInvoiceNum(invoice);

            count++;
        }

        return count;
    }

    private String generateRandomInvoice() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 11; i++) {
            sb.append(rand.nextInt(10)); // 0~9 무작위 숫자
        }

        return sb.toString();
    }

    @Transactional
    public int insertReturnCourierAndInvoice(List<Integer> codes, String courier) {
        int count = 0;

        for (Integer code : codes) {
            ServiceOrder order = repo.findById(code)
                    .orElseThrow(() -> new IllegalArgumentException("주문 없음: " + code));

            String invoice = generateRandomInvoice();

            order.setSendCourier(courier);
            order.setSendInvoiceNum(invoice);

            count++;
        }

        return count;
    }

    /**
     * 주문상태 변경
     */
    @Transactional
    public int updateOrderStates(List<Integer> codes, String newState) {
        List<ServiceOrder> orders = repo.findAllById(codes);
        for (ServiceOrder order : orders) {
            order.setState(newState);
        }
        repo.saveAll(orders);
        return orders.size();
    }
}