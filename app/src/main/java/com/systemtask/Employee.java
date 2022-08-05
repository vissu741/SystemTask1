package com.systemtask;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Employee extends RealmObject {


    public static final String PROPERTY_NAME = "name";
    public static final String PROPERTY_AGE = "age";
    public static final String PROPERTY_CITY = "city";

    @PrimaryKey
    @Required
    public String name;
    public int age;
    public String city;

}
