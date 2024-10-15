package com.dartech.azalymanagementserver.service;

import com.dartech.azalymanagementserver.controller.Request.PurchaseAddRequest;
import com.dartech.azalymanagementserver.controller.Request.PurchaseUpdateRequest;
import com.dartech.azalymanagementserver.dto.PurchaseDto;
import com.dartech.azalymanagementserver.entity.Order;
import com.dartech.azalymanagementserver.entity.Purchase;
import com.dartech.azalymanagementserver.entity.Purchase;

import com.dartech.azalymanagementserver.exceptioHandler.ApplicationException;
import com.dartech.azalymanagementserver.exceptioHandler.CustomError;
import com.dartech.azalymanagementserver.repository.PurchaseRepository;
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
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public PurchaseDto save(PurchaseAddRequest purchaseAddRequest) {
        Optional<Purchase> testValue = purchaseRepository.findByRef(purchaseAddRequest.getRef());
        if(testValue.isPresent()){
            throw new ApplicationException( new CustomError(400, "Purchase reference number already exist", "ref_already_exist"));
        }
        Purchase purchase = modelMapper.map(purchaseAddRequest, Purchase.class);
        purchase.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        purchase.setId(null);
        purchase = purchaseRepository.save(purchase);
        PurchaseDto purchaseDto = modelMapper.map(purchase, PurchaseDto.class);
        return purchaseDto;
    }

    @Override
    public PurchaseDto update(PurchaseUpdateRequest purchaseUpdateRequest) {
        Optional<Purchase> testValue = purchaseRepository.findByRef(purchaseUpdateRequest.getRef());
        if(testValue.isPresent() && !testValue.get().getId().equals(purchaseUpdateRequest.getId())){
            throw new ApplicationException( new CustomError(400, "Purchase reference number already exist", "ref_already_exist"));
        }

        Purchase purchase = modelMapper.map(purchaseUpdateRequest, Purchase.class);
        /* if(purchaseUpdateRequest.getState().equals("delivered") && purchaseUpdateRequest.getDeliveryDate() == null) {
            purchase.setDeliveryDate(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(new Date()));
        } */
        purchase.setLastModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        purchase.setLastModifiedDate(LocalDateTime.now().toString());
        purchase = purchaseRepository.save(purchase);
        PurchaseDto purchaseDto = modelMapper.map(purchase, PurchaseDto.class);
        return purchaseDto;
    }

    @Override
    public List<PurchaseDto> getAll() {
        List<Purchase> purchases = purchaseRepository.findAll();
        List<PurchaseDto> purchaseDtos = new ArrayList<>();
        for(Purchase purchase : purchases) {
            purchaseDtos.add(modelMapper.map(purchase, PurchaseDto.class));
        };
        return purchaseDtos;
    }

    @Override
    public void delete(String id) {
        purchaseRepository.deleteById(id);
    }

    @Override
    public float getThisMonthCosts() {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.withDayOfMonth(1);
        LocalDate endDate = today.withDayOfMonth(today.lengthOfMonth());
        System.out.println("First day: " + today.withDayOfMonth(1));
        System.out.println("Last day: " + today.withDayOfMonth(today.lengthOfMonth()));
        List<Purchase> purchases  = purchaseRepository.findAll();
        float total = 0;
        for(Purchase purchase : purchases) {
            String dateString = purchase.getDate();
            if(!dateString.equals("")) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
                LocalDate date = LocalDate.parse(dateString, formatter);
                System.out.println("formatted date: " + date);
                if(!(date.isBefore(startDate) || date.isAfter(endDate))) {
                    total = total + purchase.getTotalAmount();
                }
            }
        };
        return total;
    }

    @Override
    public float getThisWeekCosts() {
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
        List<Purchase> purchases  = purchaseRepository.findAll();
        float total = 0;
        for(Purchase purchase : purchases) {
            String dateString = purchase.getDate();
            if(!dateString.equals("")) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
                LocalDate date = LocalDate.parse(dateString, formatter);
                System.out.println("formatted date: " + date);
                if(!(date.isBefore(startDate) || date.isAfter(endDate))) {
                    total = total + purchase.getTotalAmount();
                }
            }
        };
        return total;
    }

    @Override
    public List<Float> getThisWeekCostsStatistics() {

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

        List<Purchase> purchases = purchaseRepository.findAll();
        for (LocalDate dayDate : totalDates) {
            float total = 0;
            for(Purchase purchase : purchases) {
                String dateString = purchase.getDate();
                if(!dateString.equals("")) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
                    LocalDate date = LocalDate.parse(dateString, formatter);
                    System.out.println("formatted date: " + date);
                    if(date.isEqual(dayDate)){
                        total = total + purchase.getTotalAmount();
                    }
                }
            };
            statistics.add(total);
        }

        return statistics;
    }

    @Override
    public List<Float> getThisMonthCostsStatistics() {

        List<Float> statistics= new ArrayList<>();
        LocalDate today = LocalDate.now();

        // LocalDate startDate = LocalDate.of(2019, 12, 12).with(DayOfWeek.MONDAY);
        // LocalDate endDate = LocalDate.of(2020, 12, 12);
        LocalDate startDate = today.withDayOfMonth(1);
        LocalDate endDate = today.withDayOfMonth(today.lengthOfMonth());

        long weekNumber = ChronoUnit.WEEKS.between(startDate, endDate);
        List<Purchase> purchases = purchaseRepository.findAll();
        Map<LocalDate, LocalDate> weeks = new LinkedHashMap<>();

        for (int i = 0; i < weekNumber; i++) {
            LocalDate endOfWeek = startDate.plusDays(6);
            weeks.put(startDate, endOfWeek);
            startDate = endOfWeek.plusDays(1);
        }

        for (LocalDate startOfWeek : weeks.keySet()) {
            LocalDate endOfWeek = weeks.get(startOfWeek);
            float total = 0;
            for(Purchase purchase : purchases) {
                String dateString = purchase.getDate();
                if(!dateString.equals("")) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
                    LocalDate date = LocalDate.parse(dateString, formatter);
                    if(!(date.isBefore(startDate) || date.isAfter(endDate))) {
                        total = total + purchase.getTotalAmount();
                    }
                }
            };
            statistics.add(total);
        }

        return statistics;
    }

    @Override
    public List<Float> getThisYearCostsStatistics() {

        List<Float> statistics= new ArrayList<>();
        LocalDate today = LocalDate.now();

        List<Purchase> purchases = purchaseRepository.findAll();
        for (int i = 1; i <= 12; i++) {
            float total = 0;
            LocalDate startDate = LocalDate.of(today.getYear(), i, 1).with(DayOfWeek.MONDAY);
            LocalDate endDate = today.withDayOfMonth(LocalDate.of(today.getYear(), i, 12).lengthOfMonth());
            for(Purchase purchase : purchases) {
                String dateString = purchase.getDate();
                if(!dateString.equals("")) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
                    LocalDate date = LocalDate.parse(dateString, formatter);
                    if(!(date.isBefore(startDate) || date.isAfter(endDate))) {
                        total = total + purchase.getTotalAmount();
                    }
                }
            };
            statistics.add(total);
        }

        return statistics;
    }
}
