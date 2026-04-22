package com.rachidy.sassgestionstockapp.controller;

import com.rachidy.sassgestionstockapp.common.PageResponse;
import com.rachidy.sassgestionstockapp.requests.StockMvtRequest;
import com.rachidy.sassgestionstockapp.responses.StockMvtResponse;
import com.rachidy.sassgestionstockapp.services.StockMvtService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stockMvts")
public class StockMvtController {

    private final StockMvtService stockMvtService;

    @GetMapping
    public ResponseEntity<PageResponse<StockMvtResponse>>getAll(
            @RequestParam(name = "page")
            final int page,
            @RequestParam(name = "size")
            final int size
    ){
        PageResponse<StockMvtResponse>body=this.stockMvtService.findAll(page,size);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{stockMvtId}")
    public ResponseEntity<StockMvtResponse>findOne(
            @PathVariable
            @NotNull(message = "StockMvt Id cannot be null")
            final String stockMvtId
    ){
        StockMvtResponse stockMvtResponse=this.stockMvtService.findById(stockMvtId);
        return ResponseEntity.ok(stockMvtResponse);
    }

    @PostMapping
    public ResponseEntity<Void>create(
            @RequestBody
            @Valid
            final StockMvtRequest request
    ){
        this.stockMvtService.create(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{stockMvtId}")
    public ResponseEntity<Void>update(
            @PathVariable
            @NotNull(message = "StockMvt Id cannot be null")
            final String  stockMvtId,
            @RequestBody
            @Valid
            final StockMvtRequest request
    ){
         this.stockMvtService.update(stockMvtId,request);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/{stockMvtId}")
    public ResponseEntity<Void>delete(
            @PathVariable
            @NotNull(message = "StockMvt Id cannot be null")
            final String stockMvtId
    ){
        this.stockMvtService.delete(stockMvtId);
        return ResponseEntity.noContent().build();
    }
}
