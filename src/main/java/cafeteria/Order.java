package cafeteria;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cafeteria.external.Payment;
import cafeteria.external.PaymentService;

@Entity
@Table(name="ORDER_MANAGEMENT")
public class Order {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String phoneNumber;
    private String productName;
    private Integer qty;
    private Integer amt;
    private String status = "Ordered";
    private Date createTime = new Date();
    
    @PostPersist
    public void onPostPersist(){
        final Ordered ordered = new Ordered();
        BeanUtils.copyProperties(this, ordered);
        ordered.publishAfterCommit();

        final Payment payment = new Payment();
        payment.setOrderId(this.id);
        payment.setPhoneNumber(this.phoneNumber);
        payment.setAmt(this.amt);

        OrderApplication.applicationContext.getBean(PaymentService.class).pay(payment);

    }

    @PreRemove
    public void onPreRemove() {
        //switch (this.status) {
        //case "OrderCanceled":
            final OrderCanceled orderCanceled = new OrderCanceled();
            BeanUtils.copyProperties(this, orderCanceled);
            orderCanceled.publishAfterCommit();
          
            final cafeteria.external.Cancellation cancellation = new cafeteria.external.Cancellation();
            // mappings goes here
            cancellation.setOrderId(this.getId());
            cancellation.setStatus("Delivery Cancelled");
            OrderApplication.applicationContext.getBean(cafeteria.external.CancellationService.class)
                    .save(cancellation);

        //}
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(final String productName) {
        this.productName = productName;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(final Integer qty) {
        this.qty = qty;
    }

    public Integer getAmt() {
        return amt;
    }

    public void setAmt(final Integer amt) {
        this.amt = amt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(final Date createTime) {
        this.createTime = createTime;
    } 



}
