package com.dartech.azalymanagementserver.service;

import com.dartech.azalymanagementserver.controller.Request.OrderAddRequest;
import com.dartech.azalymanagementserver.controller.Request.OrderUpdateRequest;
import com.dartech.azalymanagementserver.dto.OrderDto;
import com.dartech.azalymanagementserver.entity.Order;
import com.dartech.azalymanagementserver.entity.OrderLogInfo;
import com.dartech.azalymanagementserver.entity.Product;
import com.dartech.azalymanagementserver.exceptioHandler.ApplicationException;
import com.dartech.azalymanagementserver.exceptioHandler.CustomError;
import com.dartech.azalymanagementserver.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public OrderDto save(OrderAddRequest orderAddRequest) {
        Optional<Order> testValue = orderRepository.findByRef(orderAddRequest.getRef());
        if(testValue.isPresent()){
            throw new ApplicationException( new CustomError(400, "Order reference number already exist", "ref_already_exist"));
        }
        Order order = modelMapper.map(orderAddRequest, Order.class);
        OrderLogInfo orderLogInfo = new OrderLogInfo();
        orderLogInfo.setState(orderAddRequest.getState());
        orderLogInfo.setDate(orderAddRequest.getRegisteredDate());
        List<OrderLogInfo> orderLogInfos = new ArrayList<>();
        orderLogInfos.add(orderLogInfo);
        order.setOrderLog(orderLogInfos);
        order.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        order.setId(null);
        order = orderRepository.save(order);
        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
        return orderDto;
    }

    @Override
    public OrderDto update(OrderUpdateRequest orderUpdateRequest) {
        Optional<Order> testValue = orderRepository.findByRef(orderUpdateRequest.getRef());
        if(testValue.isPresent() && !testValue.get().getId().equals(orderUpdateRequest.getId())){
            throw new ApplicationException( new CustomError(400, "Order reference number already exist", "ref_already_exist"));
        }

        Order order = modelMapper.map(orderUpdateRequest, Order.class);
        /* if(orderUpdateRequest.getState().equals("delivered") && orderUpdateRequest.getDeliveryDate() == null) {
            order.setDeliveryDate(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(new Date()));
        } */
        order.setLastModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        order.setLastModifiedDate(LocalDateTime.now().toString());
        order = orderRepository.save(order);
        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
        return orderDto;
    }

    @Override
    public OrderDto updateState(String state, String orderId) {
        Order order = orderRepository.findById(orderId).get();
        order.setState(state);
        OrderLogInfo orderLogInfo = new OrderLogInfo();
        orderLogInfo.setState(state);
        orderLogInfo.setDate(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(new Date()));
        List<OrderLogInfo> orderLogInfos = order.getOrderLog();
        orderLogInfos.add(orderLogInfo);
        order.setOrderLog(orderLogInfos);
        order.setLastModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        order.setLastModifiedDate(LocalDateTime.now().toString());
        order = orderRepository.save(order);
        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
        return orderDto;
    }

    @Override
    public List<OrderDto> getAll() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDto> orderDtos = new ArrayList<>();
        for(Order order : orders) {
            orderDtos.add(modelMapper.map(order, OrderDto.class));
        };
        return orderDtos;
    }

    @Override
    public void delete(String id) {
        Optional<Order> order = orderRepository.findById(id);

        if(order.isPresent()){
            System.out.println(order.get());
        }
        orderRepository.deleteById(id);
    }

    @Override
    public long countAllOrders() {
        return orderRepository.count();
    }

    @Override
    public long countDeliveredOrders() {
        return orderRepository.countDistinctByStateEquals("delivered");
    }

    @Override
    public float getThisMonthGains() {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.withDayOfMonth(1);
        LocalDate endDate = today.withDayOfMonth(today.lengthOfMonth());
        System.out.println("First day: " + today.withDayOfMonth(1));
        System.out.println("Last day: " + today.withDayOfMonth(today.lengthOfMonth()));
        List<Order> orders = orderRepository.findAll();
        float total = 0;
        for(Order order : orders) {
            String deliveredDateString = order.getDeliveredDate();
            if(!deliveredDateString.equals("")) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
                LocalDate deliveredDate = LocalDate.parse(deliveredDateString, formatter);
                System.out.println("formatted date: " + deliveredDate);
                if(!(deliveredDate.isBefore(startDate) || deliveredDate.isAfter(endDate))) {
                    total = total + order.getTotalAmount();
                }
            }
        };
        return total;
    }

    @Override
    public float getThisWeekGains() {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today;
        while (startDate.getDayOfWeek() != DayOfWeek.MONDAY)
        {
            startDate = startDate.minusDays(1);
        }

        // Go forward to get Sunday
        LocalDate endDate = today;
        while (endDate.getDayOfWeek() != DayOfWeek.SUNDAY)
        {
            endDate = endDate.plusDays(1);
        }
        System.out.println("First day: " + startDate);
        System.out.println("Last day: " + endDate);
        List<Order> orders = orderRepository.findAll();
        float total = 0;
        for(Order order : orders) {
            String deliveredDateString = order.getDeliveredDate();
            if(!deliveredDateString.equals("")) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
                LocalDate deliveredDate = LocalDate.parse(deliveredDateString, formatter);
                System.out.println("formatted date: " + deliveredDate);
                if(!(deliveredDate.isBefore(startDate) || deliveredDate.isAfter(endDate))) {
                    total = total + order.getTotalAmount();
                }
            }
        };
        return total;
    }

    @Override
    public List<Float> getThisWeekGainsStatistics() {

        List<Float> statistics= new ArrayList<>();
        LocalDate today = LocalDate.now();
        Month month = today.getMonth();

        // LocalDate startDate = LocalDate.of(2019, 12, 12).with(DayOfWeek.MONDAY);
        // LocalDate endDate = LocalDate.of(2020, 12, 12);
        LocalDate startDate = today;
        while (startDate.getDayOfWeek() != DayOfWeek.MONDAY)
        {
            startDate = startDate.minusDays(1);
        }

        // Go forward to get Sunday
        LocalDate endDate = today;
        while (endDate.getDayOfWeek() != DayOfWeek.SUNDAY)
        {
            endDate = endDate.plusDays(1);
        }

        List<LocalDate> totalDates = new ArrayList<>();
        while (!startDate.isAfter(endDate)) {
            totalDates.add(startDate);
            startDate = startDate.plusDays(1);
        }

        List<Order> orders = orderRepository.findAll();
        for (LocalDate dayDate : totalDates) {
            float total = 0;
            for(Order order : orders) {
                String deliveredDateString = order.getDeliveredDate();
                if(!deliveredDateString.equals("")) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
                    LocalDate deliveredDate = LocalDate.parse(deliveredDateString, formatter);
                    System.out.println("formatted date: " + deliveredDate);
                    if(deliveredDate.isEqual(dayDate)){
                        total = total + order.getTotalAmount();
                    }
                }
            };
            statistics.add(total);
        }

        return statistics;
    }

    @Override
    public List<Float> getThisMonthGainsStatistics() {

        List<Float> statistics= new ArrayList<>();
        LocalDate today = LocalDate.now();

        // LocalDate startDate = LocalDate.of(2019, 12, 12).with(DayOfWeek.MONDAY);
        // LocalDate endDate = LocalDate.of(2020, 12, 12);
        LocalDate startDate = today.withDayOfMonth(1);
        LocalDate endDate = today.withDayOfMonth(today.lengthOfMonth());

        long weekNumber = ChronoUnit.WEEKS.between(startDate, endDate);
        List<Order> orders = orderRepository.findAll();
        Map<LocalDate, LocalDate> weeks = new LinkedHashMap<>();

        for (int i = 0; i < weekNumber; i++) {
            LocalDate endOfWeek = startDate.plusDays(6);
            weeks.put(startDate, endOfWeek);
            startDate = endOfWeek.plusDays(1);
        }

        for (LocalDate startOfWeek : weeks.keySet()) {
            LocalDate endOfWeek = weeks.get(startOfWeek);
            float total = 0;
            for(Order order : orders) {
                String deliveredDateString = order.getDeliveredDate();
                if(!deliveredDateString.equals("")) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
                    LocalDate deliveredDate = LocalDate.parse(deliveredDateString, formatter);
                    if(!(deliveredDate.isBefore(startOfWeek) || deliveredDate.isAfter(endOfWeek))) {
                        total = total + order.getTotalAmount();
                    }
                }
            };
            statistics.add(total);
        }

        return statistics;
    }

    @Override
    public List<Float> getThisYearGainsStatistics() {

        List<Float> statistics= new ArrayList<>();
        LocalDate today = LocalDate.now();

        List<Order> orders = orderRepository.findAll();
        for (int i = 1; i <= 12; i++) {
            float total = 0;
            LocalDate startDate = LocalDate.of(today.getYear(), i, 1).with(DayOfWeek.MONDAY);
            LocalDate endDate = today.withDayOfMonth(LocalDate.of(today.getYear(), i, 12).lengthOfMonth());
            for(Order order : orders) {
                String deliveredDateString = order.getDeliveredDate();
                if(!deliveredDateString.equals("")) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
                    LocalDate deliveredDate = LocalDate.parse(deliveredDateString, formatter);
                    if(!(deliveredDate.isBefore(startDate) || deliveredDate.isAfter(endDate))) {
                        total = total + order.getTotalAmount();
                    }
                }
            };
            statistics.add(total);
        }

        return statistics;
    }
}
