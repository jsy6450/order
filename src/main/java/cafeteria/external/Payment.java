package cafeteria.external;

public class Payment {

    private Long id;
    private Long orderId;
    private String phoneNumber;
    private Integer amt;
    private String status = "PaymentApproved";

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getOrderId() {
        return orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public String getPhoneNumber() {
    	return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
    	this.phoneNumber = phoneNumber;
    }
    public Integer getAmt() {
        return amt;
    }
    public void setAmt(Integer amt) {
        this.amt = amt;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

}
