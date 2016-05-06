package entity;

import javax.persistence.*;
import java.sql.Date;

/**
 * @author Qiang
 * @date 16/5/6
 */
@Entity
@Table(name = "stockdata", schema = "AnyQuant", catalog = "")
@IdClass(StockdataEntityPK.class)
public class StockdataEntity {
    private String code;
    private Date date;
    private double high;
    private double low;
    private double open;
    private double close;
    private double preClose;
    private long turnoverVol;
    private double turnoverValue;
    private double turnoverRate;
    private double pb;
    private double pe;
    private double accAdjFactor;
    private double cirMarketValue;
    private double totalMarketValue;
    private double amplitude;
    private double changeRate;

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
    @Column(name = "turnoverVol")
    public long getTurnoverVol() {
        return turnoverVol;
    }

    public void setTurnoverVol(long turnoverVol) {
        this.turnoverVol = turnoverVol;
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
    @Column(name = "turnoverRate")
    public double getTurnoverRate() {
        return turnoverRate;
    }

    public void setTurnoverRate(double turnoverRate) {
        this.turnoverRate = turnoverRate;
    }

    @Basic
    @Column(name = "pb")
    public double getPb() {
        return pb;
    }

    public void setPb(double pb) {
        this.pb = pb;
    }

    @Basic
    @Column(name = "pe")
    public double getPe() {
        return pe;
    }

    public void setPe(double pe) {
        this.pe = pe;
    }

    @Basic
    @Column(name = "accAdjFactor")
    public double getAccAdjFactor() {
        return accAdjFactor;
    }

    public void setAccAdjFactor(double accAdjFactor) {
        this.accAdjFactor = accAdjFactor;
    }

    @Basic
    @Column(name = "cirMarketValue")
    public double getCirMarketValue() {
        return cirMarketValue;
    }

    public void setCirMarketValue(double cirMarketValue) {
        this.cirMarketValue = cirMarketValue;
    }

    @Basic
    @Column(name = "totalMarketValue")
    public double getTotalMarketValue() {
        return totalMarketValue;
    }

    public void setTotalMarketValue(double totalMarketValue) {
        this.totalMarketValue = totalMarketValue;
    }

    @Basic
    @Column(name = "amplitude")
    public double getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(double amplitude) {
        this.amplitude = amplitude;
    }

    @Basic
    @Column(name = "changeRate")
    public double getChangeRate() {
        return changeRate;
    }

    public void setChangeRate(double changeRate) {
        this.changeRate = changeRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StockdataEntity that = (StockdataEntity) o;

        if (Double.compare(that.high, high) != 0) return false;
        if (Double.compare(that.low, low) != 0) return false;
        if (Double.compare(that.open, open) != 0) return false;
        if (Double.compare(that.close, close) != 0) return false;
        if (Double.compare(that.preClose, preClose) != 0) return false;
        if (turnoverVol != that.turnoverVol) return false;
        if (Double.compare(that.turnoverValue, turnoverValue) != 0) return false;
        if (Double.compare(that.turnoverRate, turnoverRate) != 0) return false;
        if (Double.compare(that.pb, pb) != 0) return false;
        if (Double.compare(that.pe, pe) != 0) return false;
        if (Double.compare(that.accAdjFactor, accAdjFactor) != 0) return false;
        if (Double.compare(that.cirMarketValue, cirMarketValue) != 0) return false;
        if (Double.compare(that.totalMarketValue, totalMarketValue) != 0) return false;
        if (Double.compare(that.amplitude, amplitude) != 0) return false;
        if (Double.compare(that.changeRate, changeRate) != 0) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = code != null ? code.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
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
        result = 31 * result + (int) (turnoverVol ^ (turnoverVol >>> 32));
        temp = Double.doubleToLongBits(turnoverValue);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(turnoverRate);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(pb);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(pe);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(accAdjFactor);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(cirMarketValue);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(totalMarketValue);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(amplitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(changeRate);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
