package entity;

import javax.persistence.*;

/**
 * Created by 67534 on 2016/5/12.
 */
@Entity
@Table(name = "optionalstock", schema = "AnyQuant", catalog = "")
@IdClass(OptionalstockEntityPK.class)
public class OptionalstockEntity {
    private int userId;
    private String code;

    @Id
    @Column(name = "userID")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "code")
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

        OptionalstockEntity that = (OptionalstockEntity) o;

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
