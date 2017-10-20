package org.wheatgenetics.inventory.model;

/**
 * Uses:
 * org.wheatgenetics.javalib.Utils
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
public class Person extends java.lang.Object
{
    public final java.lang.String firstName, lastName;

    public Person(final java.lang.String firstName, final java.lang.String lastName)
    {
        super();

        this.firstName = org.wheatgenetics.javalib.Utils.adjust(firstName);
        this.lastName  = org.wheatgenetics.javalib.Utils.adjust(lastName );
    }

    @java.lang.Override
    public java.lang.String toString() { return (this.firstName + " " + this.lastName).trim(); }

    public boolean isSet() { return this.firstName.length() > 0 || this.lastName.length() > 0; }
}