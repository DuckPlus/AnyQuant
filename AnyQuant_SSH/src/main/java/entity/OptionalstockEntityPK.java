package entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by 67534 on 2016/5/12.
 */
public class OptionalstockEntityPK implements Serializable {
    private int userId;
    private String code;

    @Column(name = "userID")
    @Id
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Column(name = "code")
    @Id
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OptionalstockEntityPK that = (OptionalstockEntityPK) o;

        if (userId != that.userId) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        return result;
    }
}
