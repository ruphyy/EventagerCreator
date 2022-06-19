package net.ruphyy.eventagercreator.Activities;

public class Events {
    private String aTopic;
    private String aDescription;
    private double aPrice;
    private int aAge;
    private String aLocation;
    private char aKind;

    public Events(String pTopic, String pDescription, double pPrice, int pAge, String pLocation, char pKind) {

        aTopic = pTopic;
        aDescription = pDescription;
        aPrice = pPrice;
        aAge = pAge;
        aLocation = pLocation;
        aKind = pKind;

    }

    public void testMethod() {  }

    public void setTopic(String pTopic) { aTopic = pTopic; }

    public void setDescription(String aDescription) { this.aDescription = aDescription; }

    public void setPrice(int aPrice) { this.aPrice = aPrice; }

    public void setAge(int aAge) { this.aAge = aAge; }

    public void setLocation(String aLocation) { this.aLocation = aLocation; }

    public void setKind(char aKind) { this.aKind = aKind; }


    public String getTopic() {
        return aTopic;
    }

    public String getDescription() {
        return aDescription;
    }

    public double getPrice() {
        return aPrice;
    }

    public int getAge() {
        return aAge;
    }

    public String getLocation() {
        return aLocation;
    }

    public char getKind() {
        return aKind;
    }
}
