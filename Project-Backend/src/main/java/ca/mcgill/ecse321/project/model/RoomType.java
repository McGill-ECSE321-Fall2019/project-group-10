package ca.mcgill.ecse321.project.model;

import javax.persistence.Entity;

@Entity
public enum RoomType{
   private String small;

public void setSmall(String value) {
    this.small = value;
}
public String getSmall() {
    return this.small;
}
private String large;

public void setLarge(String value) {
    this.large = value;
}
public String getLarge() {
    return this.large;
}
}
