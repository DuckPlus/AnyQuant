package service.impl;

import DAO.StockDAO;
import entity.StockEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.StockService;

import java.util.List;

/**
 * @author Qiang
 * @date 16/5/6
 */
@Service
@Transactional
public class StockServiceImpl implements StockService {
    @Autowired
    StockDAO stockDAO;

    @Override
    public List<StockEntity> getAllStocks() {
        return stockDAO.findAllStocks();
    }

    /**
     * Get today's(or last trading day)data of the given stockCode
     *
     * @param stockCode
     * @return
     */
    @Override
    public StockEntity getTodayStockVO(String stockCode) {
        return null;
    }
}
