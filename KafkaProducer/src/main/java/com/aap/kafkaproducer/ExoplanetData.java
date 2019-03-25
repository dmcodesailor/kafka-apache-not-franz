package com.aap.kafkaproducer;

public class ExoplanetData {

    private Integer id;
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    private String name;
    public String getName() { return name; }
    public void setName(String name) {
        this.name = name;
    }

    private String starName;
    public String getStarName() {
        return starName;
    }
    public void setStarName(String starName) {
        this.starName = starName;
    }

    private String entityJson;
    public String getEntityJson() {
        return entityJson;
    }
    public void setEntityJson(String entityJson) {
        this.entityJson = entityJson;
    }

}
