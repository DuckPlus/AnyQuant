package entity;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by 67534 on 2016/5/10.
 */
@Entity
@Table(name = "benchmarkdata", schema = "AnyQuant", catalog = "")
@IdClass(BenchmarkdataEntityPK.class)
public class BenchmarkdataEntity {
    private String code;
    private Date date;
    private String name;
    private double high;
    private double low;
    private double open;
    private double close;
    private double preClose;
    private double turnoverValue;
    private double changeValue;
    private double changePct;
    private long turnoverVol;

    @Id
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Id
    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
    @Column(name = "high")
    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    @Basic
    @Column(name = "low")
    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    @Basic
    @Column(name = "open")
    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    @Basic
    @Column(name = "close")
    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    @Basic
    @Column(name = "preClose")
    public double getPreClose() {
        return preClose;
    }

    public void setPreClose(double preClose) {
        this.preClose = preClose;
    }

    @Basic
    @Column(name = "turnoverValue")
    public double getTurnoverValue() {
        return turnoverValue;
    }

    public void setTurnoverValue(double turnoverValue) {
        this.turnoverValue = turnoverValue;
    }

    @Basic
    @Column(name = "changeValue")
    public double getChangeValue() {
        return changeValue;
    }

    public void setChangeValue(double changeValue) {
        this.changeValue = changeValue;
    }

    @Basic
    @Column(name = "changePct")
    public double getChangePct() {
        return changePct;
    }

    public void setChangePct(double changePct) {
        this.changePct = changePct;
    }

    @Basic
    @Column(name = "turnoverVol")
    public long getTurnoverVol() {
        return turnoverVol;
    }

    public void setTurnoverVol(long turnoverVol) {
        this.turnoverVol = turnoverVol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BenchmarkdataEntity that = (BenchmarkdataEntity) o;

        if (Double.compare(that.high, high) != 0) return false;
        if (Double.compare(that.low, low) != 0) return false;
        if (Double.compare(that.open, open) != 0) return false;
        if (Double.compare(that.close, close) != 0) return false;
        if (Double.compare(that.preClose, preClose) != 0) return false;
        if (Double.compare(that.turnoverValue, turnoverValue) != 0) return false;
        if (Double.compare(that.changeValue, changeValue) != 0) return false;
        if (Double.compare(that.changePct, changePct) != 0) return false;
        if (turnoverVol != that.turnoverVol) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = code != null ? code.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        temp = Double.doubleToLongBits(high);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(low);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(open);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(close);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(preClose);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(turnoverValue);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(changeValue);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(changePct);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) (turnoverVol ^ (turnoverVol >>> 32));
        return result;
    }
}
