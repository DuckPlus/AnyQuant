package entity;

import javax.persistence.*;
import java.util.Arrays;

/**
 * Created by 67534 on 2016/6/10.
 */
@Entity
@Table(name = "report", schema = "AnyQuant", catalog = "")
public class ReportEntity {
    private int id;
    private int userid;
    private byte[] report;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "userid")
    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    @Basic
    @Column(name = "report")
    public byte[] getReport() {
        return report;
    }

    public void setReport(byte[] report) {
        this.report = report;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReportEntity that = (ReportEntity) o;

        if (id != that.id) return false;
        if (userid != that.userid) return false;
        if (!Arrays.equals(report, that.report)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userid;
        result = 31 * result + Arrays.hashCode(report);
        return result;
    }
}
