package entity;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by 67534 on 2016/5/18.
 */
@Entity
@Table(name = "stock", schema = "AnyQuant", catalog = "")
public class StockEntity {
    private String code;
    private String name;
    private String region;
    private String board;
    private double totalShares;
    private String officeAddr;
    private String primeOperating;
    private Date listDate;

    @Id
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "region")
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Basic
    @Column(name = "board")
    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    @Basic
    @Column(name = "totalShares")
    public double getTotalShares() {
        return totalShares;
    }

    public void setTotalShares(double totalShares) {
        this.totalShares = totalShares;
    }

    @Basic
    @Column(name = "officeAddr")
    public String getOfficeAddr() {
        return officeAddr;
    }

    public void setOfficeAddr(String officeAddr) {
        this.officeAddr = officeAddr;
    }

    @Basic
    @Column(name = "primeOperating")
    public String getPrimeOperating() {
        return primeOperating;
    }

    public void setPrimeOperating(String primeOperating) {
        this.primeOperating = primeOperating;
    }

    @Basic
    @Column(name = "listDate")
    public Date getListDate() {
        return listDate;
    }

    public void setListDate(Date listDate) {
        this.listDate = listDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StockEntity entity = (StockEntity) o;

        if (Double.compare(entity.totalShares, totalShares) != 0) return false;
        if (code != null ? !code.equals(entity.code) : entity.code != null) return false;
        if (name != null ? !name.equals(entity.name) : entity.name != null) return false;
        if (region != null ? !region.equals(entity.region) : entity.region != null) return false;
        if (board != null ? !board.equals(entity.board) : entity.board != null) return false;
        if (officeAddr != null ? !officeAddr.equals(entity.officeAddr) : entity.officeAddr != null) return false;
        if (primeOperating != null ? !primeOperating.equals(entity.primeOperating) : entity.primeOperating != null)
            return false;
        if (listDate != null ? !listDate.equals(entity.listDate) : entity.listDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = code != null ? code.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (region != null ? region.hashCode() : 0);
        result = 31 * result + (board != null ? board.hashCode() : 0);
        temp = Double.doubleToLongBits(totalShares);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (officeAddr != null ? officeAddr.hashCode() : 0);
        result = 31 * result + (primeOperating != null ? primeOperating.hashCode() : 0);
        result = 31 * result + (listDate != null ? listDate.hashCode() : 0);
        return result;
    }
}
