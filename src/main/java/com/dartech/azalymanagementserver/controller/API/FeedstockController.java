package com.dartech.azalymanagementserver.controller.API;

import com.dartech.azalymanagementserver.controller.Request.FeedstockAddRequest;
import com.dartech.azalymanagementserver.controller.Request.FeedstockUpdateRequest;
import com.dartech.azalymanagementserver.dto.FeedstockDto;
import com.dartech.azalymanagementserver.service.FeedstockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/feedstock")
@CrossOrigin("*")
public class FeedstockController {

    @Autowired
    FeedstockService feedstockService;

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody @Valid FeedstockAddRequest feedstockAddRequest) {

        FeedstockDto feedstock = feedstockService.save(feedstockAddRequest);
        return new ResponseEntity<>(feedstock, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity upate(@RequestBody @Valid FeedstockUpdateRequest feedstockUpdateRequest) {

        FeedstockDto feedstock = feedstockService.update(feedstockUpdateRequest);
        return new ResponseEntity<>(feedstock, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity getAll() {

        List<FeedstockDto> feedstocks = feedstockService.getAll();
        return new ResponseEntity<>(feedstocks, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable String id) {

        feedstockService.delete(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
    
}
