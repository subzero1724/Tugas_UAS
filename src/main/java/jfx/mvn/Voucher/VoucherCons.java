package jfx.mvn.Voucher;

import java.sql.Date;

public class VoucherCons {
    private int id;
    private String VoucherID;
    private String VoucherCode;
    private Date date;
    private int QTY_Disc;

    public VoucherCons(int id, String VoucherID,
            String VoucherCode, Date date, int QTY_Disc) {
        this.id = id;
        this.VoucherID = VoucherID;
        this.VoucherCode = VoucherCode;
        this.date = date;
        this.QTY_Disc = QTY_Disc;
    }

    public int getID() {
        return id;
    }

    public String getVoucherID() {
        return VoucherID;
    }

    public String getVoucherCode() {
        return VoucherCode;
    }

    public Date getDate() {
        return date;
    }

    public int getQTY_Disc() {
        return QTY_Disc;
    }
}
