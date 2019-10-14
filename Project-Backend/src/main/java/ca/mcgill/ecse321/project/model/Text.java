package ca.mcgill.ecse321.project.model;

import javax.persistence.Entity;

@Entity
public class Text extends Review{
   private Boolean isAllowed;

public void setIsAllowed(Boolean value) {
    this.isAllowed = value;
}
public Boolean getIsAllowed() {
    return this.isAllowed;
}
private String description;

public void setDescription(String value) {
    this.description = value;
}
public String getDescription() {
    return this.description;
}
}
