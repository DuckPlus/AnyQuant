package service.impl;

import org.springframework.stereotype.Service;
import service.StrategyService;
import util.MyDate;
import vo.FactorJudgmentVO;
import vo.ReportVO;

import java.util.List;
import java.util.Map;

/**
 * @author Qiang
 * @date 6/5/16
 */
@Service
public class StrategyServiceImpl implements StrategyService {
    @Override
    public List<FactorJudgmentVO> getStocksFactorJudgment(List<String> codes) {
        return null;
    }

    @Override
    public ReportVO analyseWithFactor(List<String> codes, MyDate start, MyDate end, Map<String, Double> factorWeight) {
        return null;
    }
}
