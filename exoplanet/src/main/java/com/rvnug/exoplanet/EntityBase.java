package com.rvnug.exoplanet;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class EntityBase {
    protected int id;
    public int getId(){
        return this.id;
    }
    public void setId(int id){
        this.id = id;
    }

    @JsonIgnore
    public abstract String getLastInsertedIdFieldName();
    @JsonIgnore
    public abstract String getLastInsertedIdFieldValue();
}
