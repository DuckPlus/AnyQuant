package DAO.impl;

import DAO.BoardDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ss
 * @date 16/5/22
 */
@Repository
public class BoardDAOImpl implements BoardDAO {
    @Override
    public List<String> getAllStocks(String boardName) {
        return null;
    }
}
