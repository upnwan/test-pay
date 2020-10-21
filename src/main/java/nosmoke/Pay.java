package nosmoke;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Pay_table")
public class Pay {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long point;
    private Long payId;

    public Long getPayId() {
        return payId;
    }

    public void setPayId(Long payId) {
        this.payId = payId;
    }

    @PostPersist
    public void onPostPersist(){
        Paid paid = new Paid();
        BeanUtils.copyProperties(this, paid);
        paid.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        nosmoke.external.Deduct deduct = new nosmoke.external.Deduct();
        // mappings goes here
        deduct.setPoint(this.getPoint());
        deduct.setPayId(this.getId());
        PayApplication.applicationContext.getBean(nosmoke.external.DeductService.class)
            .pay(deduct);


    }

    @PostUpdate
    public void onPostUpdate(){
        System.out.println("Update Event raised....................");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getPoint() {
        return point;
    }

    public void setPoint(Long point) {
        this.point = point;
    }




}
