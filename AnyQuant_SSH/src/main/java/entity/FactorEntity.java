package entity;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by 67534 on 2016/5/22.
 */
@Entity
@Table(name = "factor", schema = "AnyQuant", catalog = "")
@IdClass(FactorEntityPK.class)
public class FactorEntity {
    private String code;
    private Date date;

    /**
     *  5日平均换手率（Turnover Rate）。
     *  属于成交量型因子
     */
    private double vol5;
    private double vol10;
    private double vol60;
    private double vol120;
    /**
     * 5日移动均线（Moving average）。
     * 取最近N天的前复权价格的均值。
     * 属于均线型因子。
     */
    private double ma5;
    private double ma10;
    private double ma60;
    private double ma120;
    /**
     * 市净率（Price-to-book ratio）。
     * 计算方法：市净率=总市值/归属于母公司所有者权益合计。
     * 属于估值与市值类因子。
     */
    private double pb;

    /**
     * 市盈率（Price-earnings ratio）。
     * 使用TTM算法。市盈率=总市值/归属于母公司所有者的净利润（TTM）。
     * 属于估值与市值类因子。
     */
    private double pe;
    /**
     * 市现率（Price-to-cash-flow ratio）。
     * 计算方法：总市值/经营活动产生的现金流量净额（TTM）。
     * 属于估值与市值类因子。
     */
    private double pcf;
    /**
     * 市销率（Price-to-sales ratio）。
     * 计算方法：市销率=总市值/营业总收入（TTM）。
     * 属于估值与市值类因子。
     */
    private double ps;
    /**
     * 心理线指标（Psychological line index）
     * 是一定时期内投资者趋向买方或卖方的心理事实转的数值度量，
     * 用于判断股价的未来趋势。
     * 属于能量型因子。
     */
    private double psy;
    /**
     * 分析师推荐评级（Recommended rating score by analyst）
     * 属于分析师预期类因子。
     */
    private double rec;
    /**
     * 分析师推荐评级变化（Changes of recommended rating score by analyst）
     * 相比于60 个交易日前。
     * 属于分析师预期类因子。
     */
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
    @Column(name = "vol10")
    public double getVol10() {
        return vol10;
    }

    public void setVol10(double vol10) {
        this.vol10 = vol10;
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

        FactorEntity entity = (FactorEntity) o;

        if (Double.compare(entity.vol5, vol5) != 0) return false;
        if (Double.compare(entity.vol10, vol10) != 0) return false;
        if (Double.compare(entity.vol60, vol60) != 0) return false;
        if (Double.compare(entity.vol120, vol120) != 0) return false;
        if (Double.compare(entity.ma5, ma5) != 0) return false;
        if (Double.compare(entity.ma10, ma10) != 0) return false;
        if (Double.compare(entity.ma60, ma60) != 0) return false;
        if (Double.compare(entity.ma120, ma120) != 0) return false;
        if (Double.compare(entity.pb, pb) != 0) return false;
        if (Double.compare(entity.pe, pe) != 0) return false;
        if (Double.compare(entity.pcf, pcf) != 0) return false;
        if (Double.compare(entity.ps, ps) != 0) return false;
        if (Double.compare(entity.psy, psy) != 0) return false;
        if (Double.compare(entity.rec, rec) != 0) return false;
        if (Double.compare(entity.darec, darec) != 0) return false;
        if (code != null ? !code.equals(entity.code) : entity.code != null) return false;
        if (date != null ? !date.equals(entity.date) : entity.date != null) return false;

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
        temp = Double.doubleToLongBits(vol10);
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
