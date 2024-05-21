package cy.olesiabokk.tradeisboomingapp.validator;

import cy.olesiabokk.tradeisboomingapp.entity.Stock;

public class StockValidator {
    private Stock stock;
    private boolean availableSpace;
    private boolean needGoods;
    private boolean needLoad;

    public boolean hasAvailableSpace(){
        return availableSpace;
    }

    public void setAvailableSpace(boolean availableSpace){
        this.availableSpace = availableSpace;
    }

    public boolean isNeedGoods(){
        return needGoods;
    }

    public void setNeedGoods(boolean needGoods){
        this.needGoods = needGoods;
    }

    public boolean isNeedLoad(){
        return needLoad;
    }

    public void setNeedLoad(boolean needLoad){
        this.needLoad = needLoad;
    }
}
