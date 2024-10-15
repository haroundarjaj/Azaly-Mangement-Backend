package com.dartech.azalymanagementserver.service;

import com.dartech.azalymanagementserver.controller.Request.FeedstockAddRequest;
import com.dartech.azalymanagementserver.controller.Request.FeedstockUpdateRequest;
import com.dartech.azalymanagementserver.dto.FeedstockDto;
import com.dartech.azalymanagementserver.entity.Feedstock;
import com.dartech.azalymanagementserver.exceptioHandler.ApplicationException;
import com.dartech.azalymanagementserver.exceptioHandler.CustomError;
import com.dartech.azalymanagementserver.repository.FeedstockRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FeedstockServiceImpl implements FeedstockService {

    @Autowired
    FeedstockRepository feedstockRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public FeedstockDto save(FeedstockAddRequest feedstockAddRequest) {
        Optional<Feedstock> testValue = feedstockRepository.findByRef(feedstockAddRequest.getRef());
        if(testValue.isPresent()){
            throw new ApplicationException( new CustomError(400, "Feedstock reference number already exist", "ref_already_exist"));
        }

        Feedstock feedstock = modelMapper.map(feedstockAddRequest, Feedstock.class);
        feedstock.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        feedstock = feedstockRepository.save(feedstock);
        FeedstockDto feedstockDto = modelMapper.map(feedstock, FeedstockDto.class);
        return feedstockDto;
    }

    @Override
    public FeedstockDto update(FeedstockUpdateRequest feedstockUpdateRequest) {
        Optional<Feedstock> testValue = feedstockRepository.findByRef(feedstockUpdateRequest.getRef());
        if(testValue.isPresent() && !testValue.get().getId().equals(feedstockUpdateRequest.getId())){
            throw new ApplicationException( new CustomError(400, "Feedstock reference number already exist", "ref_already_exist"));
        }

        Feedstock feedstock = modelMapper.map(feedstockUpdateRequest, Feedstock.class);
        System.out.println(feedstock);
        feedstock.setLastModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        feedstock.setLastModifiedDate(LocalDateTime.now().toString());
        feedstock = feedstockRepository.save(feedstock);
        FeedstockDto feedstockDto = modelMapper.map(feedstock, FeedstockDto.class);
        return feedstockDto;
    }

    @Override
    public List<FeedstockDto> getAll() {
        List<Feedstock> feedstocks = feedstockRepository.findAll();
        List<FeedstockDto> feedstockDtos = new ArrayList<>();
        for(Feedstock feedstock : feedstocks) {
            feedstockDtos.add(modelMapper.map(feedstock, FeedstockDto.class));
        };
        return feedstockDtos;
    }

    @Override
    public void delete(String id) {
        feedstockRepository.deleteById(id);
    }
}
