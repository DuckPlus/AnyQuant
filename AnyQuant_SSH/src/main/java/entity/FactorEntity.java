package entity;

import javax.persistence.*;
import java.sql.Date;

/**
 * @author Qiang
 * @date 16/5/20
 */
@Entity
@Table(name = "factor", schema = "AnyQuant", catalog = "")
@IdClass(FactorEntityPK.class)
public class FactorEntity {
    private String code;
    private Date date;
    private double vol5;
    private double vol20;
    private double vol60;
    private double vol120;
    private double ma5;
    private double ma10;
    private double ma60;
    private double ma120;
    private double pb;
    private double pe;
    private double pcf;
    private double ps;
    private double psy;
    private double rec;
    private double darec;

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
    @Column(name = "vol5")
    public double getVol5() {
        return vol5;
    }

    public void setVol5(double vol5) {
        this.vol5 = vol5;
    }

    @Basic
    @Column(name = "vol20")
    public double getVol20() {
        return vol20;
    }

    public void setVol20(double vol20) {
        this.vol20 = vol20;
    }

    @Basic
    @Column(name = "vol60")
    public double getVol60() {
        return vol60;
    }

    public void setVol60(double vol60) {
        this.vol60 = vol60;
    }

    @Basic
    @Column(name = "vol120")
    public double getVol120() {
        return vol120;
    }

    public void setVol120(double vol120) {
        this.vol120 = vol120;
    }

    @Basic
    @Column(name = "ma5")
    public double getMa5() {
        return ma5;
    }

    public void setMa5(double ma5) {
        this.ma5 = ma5;
    }

    @Basic
    @Column(name = "ma10")
    public double getMa10() {
        return ma10;
    }

    public void setMa10(double ma10) {
        this.ma10 = ma10;
    }

    @Basic
    @Column(name = "ma60")
    public double getMa60() {
        return ma60;
    }

    public void setMa60(double ma60) {
        this.ma60 = ma60;
    }

    @Basic
    @Column(name = "ma120")
    public double getMa120() {
        return ma120;
    }

    public void setMa120(double ma120) {
        this.ma120 = ma120;
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
    @Column(name = "pcf")
    public double getPcf() {
        return pcf;
    }

    public void setPcf(double pcf) {
        this.pcf = pcf;
    }

    @Basic
    @Column(name = "ps")
    public double getPs() {
        return ps;
    }

    public void setPs(double ps) {
        this.ps = ps;
    }

    @Basic
    @Column(name = "psy")
    public double getPsy() {
        return psy;
    }

    public void setPsy(double psy) {
        this.psy = psy;
    }

    @Basic
    @Column(name = "rec")
    public double getRec() {
        return rec;
    }

    public void setRec(double rec) {
        this.rec = rec;
    }

    @Basic
    @Column(name = "darec")
    public double getDarec() {
        return darec;
    }

    public void setDarec(double darec) {
        this.darec = darec;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FactorEntity that = (FactorEntity) o;

        if (Double.compare(that.vol5, vol5) != 0) return false;
        if (Double.compare(that.vol20, vol20) != 0) return false;
        if (Double.compare(that.vol60, vol60) != 0) return false;
        if (Double.compare(that.vol120, vol120) != 0) return false;
        if (Double.compare(that.ma5, ma5) != 0) return false;
        if (Double.compare(that.ma10, ma10) != 0) return false;
        if (Double.compare(that.ma60, ma60) != 0) return false;
        if (Double.compare(that.ma120, ma120) != 0) return false;
        if (Double.compare(that.pb, pb) != 0) return false;
        if (Double.compare(that.pe, pe) != 0) return false;
        if (Double.compare(that.pcf, pcf) != 0) return false;
        if (Double.compare(that.ps, ps) != 0) return false;
        if (Double.compare(that.psy, psy) != 0) return false;
        if (Double.compare(that.rec, rec) != 0) return false;
        if (Double.compare(that.darec, darec) != 0) return false;
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
        temp = Double.doubleToLongBits(vol5);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(vol20);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(vol60);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(vol120);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(ma5);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(ma10);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(ma60);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(ma120);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(pb);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(pe);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(pcf);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(ps);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(psy);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(rec);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(darec);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
