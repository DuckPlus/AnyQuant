package data.update;

import entity.FactorEntity;
import util.MyDate;

import java.util.List;

/**
 * Created by 67534 on 2016/5/20.
 */
public interface FactorDataService {

    List<FactorEntity> getFactorEntityBetweenDate
            (String code, MyDate start, MyDate end);

    List<FactorEntity> getFactorEntityAllTheTime
            (String code);

}
