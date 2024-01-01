package jfx.mvn;

import java.sql.Date;

public class CustomerData {
    private Integer id;
    private String customerID;
    private Double total;
    private Date date;
    private String emUsername;

    public CustomerData(Integer id, String customerID, Double total, Date date, String emUsername) {
        this.id = id;
        this.customerID = customerID;
        this.total = total;
        this.date = date;
        this.emUsername = emUsername;
    }

    public Integer getId() {
        return id;
    }

    public String getCustomerID() {
        return customerID;
    }

    public Double getTotal() {
        return total;
    }

    public Date getDate() {
        return date;
    }

    public String getEmUsername() {
        return emUsername;
    }
}
