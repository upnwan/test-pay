package nosmoke;

public class PointPaid extends AbstractEvent {

    private Long id;
    private Long point;
    private Long payId;

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
    public Long getCheckInId() {
        return payId;
    }

    public void setCheckInId(Long payId) {
        this.payId = payId;
    }
}