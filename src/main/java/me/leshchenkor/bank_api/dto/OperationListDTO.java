package me.leshchenkor.bank_api.dto;

import java.util.Date;

public class OperationListDTO {
    private Date startDate;
    private Date finishDate;

    public OperationListDTO() {
    }

    public OperationListDTO(Date startDate, Date finishDate) {
        this.startDate = startDate;
        this.finishDate = finishDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }
}
