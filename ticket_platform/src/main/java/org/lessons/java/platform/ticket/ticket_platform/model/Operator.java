package org.lessons.java.platform.ticket.ticket_platform.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class Operator {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer operatorId;

    @NotNull (message = "Il nome dell'operatore deve essere esplicitato")
    private String operatorName;

    @NotNull (message = "L'email dell'operatore deve essere esplicitata")
    private String operatorEmail;

    @NotNull (message = "La password dell'operatore deve essere esplicitata")
    private String operatorPassword;

    private boolean notAvailable;

    //@OneToMany(mappedBy = "operator", cascade = {CascadeType.REMOVE})
    //private List<Ticket> tikets;


    public Integer getOperatorId() {
        return this.operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return this.operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorEmail() {
        return this.operatorEmail;
    }

    public void setOperatorEmail(String operatorEmail) {
        this.operatorEmail = operatorEmail;
    }

    public String getOperatorPassword() {
        return this.operatorPassword;
    }

    public void setOperatorPassword(String operatorPassword) {
        this.operatorPassword = operatorPassword;
    }

    public boolean isNotAvailable() {
        return this.notAvailable;
    }

    public boolean getNotAvailable() {
        return this.notAvailable;
    }

    public void setNotAvailable(boolean notAvailable) {
        this.notAvailable = notAvailable;
    }

}
