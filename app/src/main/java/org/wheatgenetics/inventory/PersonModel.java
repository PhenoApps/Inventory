package org.wheatgenetics.inventory;

class PersonModel extends java.lang.Object
{
    final java.lang.String firstName, lastName;

    private static java.lang.String adjust(final java.lang.String unadjusted)
    { return null == unadjusted ? "" : unadjusted.trim(); }

    PersonModel(final java.lang.String firstName, final java.lang.String lastName)
    {
        super();

        this.firstName = org.wheatgenetics.inventory.PersonModel.adjust(firstName);
        this.lastName  = org.wheatgenetics.inventory.PersonModel.adjust(lastName );
    }
}