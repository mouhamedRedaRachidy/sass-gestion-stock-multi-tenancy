package com.rachidy.sassgestionstockapp.services.impl;

import com.rachidy.sassgestionstockapp.common.PageResponse;
import com.rachidy.sassgestionstockapp.entities.Product;
import com.rachidy.sassgestionstockapp.entities.StockMvt;
import com.rachidy.sassgestionstockapp.mappers.StockMvtMapper;
import com.rachidy.sassgestionstockapp.repositories.ProductRepository;
import com.rachidy.sassgestionstockapp.repositories.StockMvtRepository;
import com.rachidy.sassgestionstockapp.requests.StockMvtRequest;
import com.rachidy.sassgestionstockapp.responses.StockMvtResponse;
import com.rachidy.sassgestionstockapp.services.StockMvtService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockMvtServiceImpl implements StockMvtService {

    private final ProductRepository productRepository;
    private final StockMvtRepository stockMvtRepository;
    private final StockMvtMapper stockMvtMapper;

    @Override
    public void create(StockMvtRequest request) {
        checkIfProductExistById(request.getProductId());
        StockMvt stockMvt=this.stockMvtMapper.toEntity(request);
        this.stockMvtRepository.save(stockMvt);
    }

    @Override
    public void update(String id, StockMvtRequest request) {
        //check mvt if exist
        final Optional<StockMvt>existingStockMvt=this.stockMvtRepository.findById(id);
        if(existingStockMvt.isEmpty()){
            log.debug("Stock does not exist");
            throw new EntityNotFoundException("Stock does not exist ");
        }
        checkIfProductExistById(request.getProductId());

        final StockMvt stockMvtToUpdate=this.stockMvtMapper.toEntity(request);
        stockMvtToUpdate.setId(id);
        this.stockMvtRepository.save(stockMvtToUpdate);
    }

    @Override
    public PageResponse<StockMvtResponse> findAll(int page, int size) {
        return null;
    }

    @Override
    public StockMvtResponse findById(String id) {
        return this.stockMvtRepository.findById(id)
                .map(this.stockMvtMapper::toResponse)
                .orElseThrow(()->new EntityNotFoundException("Stock mvt dose not exist"));
    }

    @Override
    public void delete(String id) {
        final StockMvt stockMvt= this.stockMvtRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Stock mvt dose not exist"));
        this.stockMvtRepository.delete(stockMvt);
    }

    private void checkIfProductExistById(String id){
        final Optional<Product>product=this.productRepository.findById(id);
        if (product.isEmpty()){
            log.debug("Product does not exist");
            throw new EntityNotFoundException("Product does not exist");
        }
    }


}
