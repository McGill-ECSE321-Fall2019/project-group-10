package ca.mcgill.ecse321.project.model;

import javax.persistence.Entity;

@Entity
public enum RatingValue{
   private int one;

public void setOne(int value) {
    this.one = value;
}
public int getOne() {
    return this.one;
}
private int two;

public void setTwo(int value) {
    this.two = value;
}
public int getTwo() {
    return this.two;
}
private int three;

public void setThree(int value) {
    this.three = value;
}
public int getThree() {
    return this.three;
}
private int four;

public void setFour(int value) {
    this.four = value;
}
public int getFour() {
    return this.four;
}
private int five;

public void setFive(int value) {
    this.five = value;
}
public int getFive() {
    return this.five;
}
}
