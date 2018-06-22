package tk.burdukowsky.beauty_api.company;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

public class RatingWrapper {

    @DecimalMin("1.0")
    @DecimalMax("5.0")
    private Float value;

    public RatingWrapper() {
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }
}
