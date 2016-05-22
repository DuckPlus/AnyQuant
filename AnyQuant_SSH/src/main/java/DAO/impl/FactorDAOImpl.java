package DAO.impl;

import DAO.FactorDAO;
import entity.FactorEntity;
import util.MyDate;
import util.enumration.AnalysisFactor;
import vo.Factor_VO;

import java.util.List;

/**
 * @author ss
 * @date 16/5/20
 */
public class FactorDAOImpl implements FactorDAO {
    @Override
    public List<Factor_VO> getFactorVO(String stockCode, AnalysisFactor factor, MyDate start, MyDate end) {
        return null;
    }

    @Override
    public List<FactorEntity> getFactorByDate(String stockCode, MyDate start, MyDate end) {
        return null;
    }
}
