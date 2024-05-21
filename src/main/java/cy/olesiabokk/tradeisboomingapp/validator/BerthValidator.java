package cy.olesiabokk.tradeisboomingapp.validator;

import cy.olesiabokk.tradeisboomingapp.entity.Berth;

public class BerthValidator {
    Berth berth;
    private boolean availability;

    public boolean isAvailable(){
        return availability;
    }

    public void setAvailability(boolean availability){
        this.availability = availability;
    }
}
