package org.wheatgenetics.inventory.org.wheatgenetics.inventory.model;

public class Person extends java.lang.Object
{
    public final java.lang.String firstName, lastName;

    private static java.lang.String adjust(final java.lang.String unadjusted)
    { return null == unadjusted ? "" : unadjusted.trim(); }

    public Person(final java.lang.String firstName, final java.lang.String lastName)
    {
        super();

        this.firstName = org.wheatgenetics.inventory.org.wheatgenetics.inventory.model.Person.adjust(firstName);
        this.lastName  = org.wheatgenetics.inventory.org.wheatgenetics.inventory.model.Person.adjust(lastName );
    }

    public boolean isSet() { return this.firstName.length() > 0 || this.lastName.length() > 0; }
}