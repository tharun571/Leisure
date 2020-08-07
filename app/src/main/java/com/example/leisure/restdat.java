package com.example.leisure;

class restdat {
String Location;

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    @Override
    public String toString() {
        return "restdat{" +
                "Location='" + Location + '\'' +
                '}';
    }
}
