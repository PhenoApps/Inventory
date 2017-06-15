package org.wheatgenetics.sharedpreferences;

/** Uses:
 * android.content.SharedPreferences
 * android.support.annotation.NonNull
 * android.util.Log
 *
 * org.wheatgenetics.inventory.BuildConfig
 * org.wheatgenetics.inventory.PersonModel
 * org.wheatgenetics.sharedpreferences.UpdateVersionSharedPreferences
 */

public class SharedPreferences
extends org.wheatgenetics.sharedpreferences.UpdateVersionSharedPreferences
{
    private static final java.lang.String
        FIRST_NAME = "FirstName", LAST_NAME = "LastName", IGNORE_SCALE = "ignoreScale";

    @java.lang.Override
    void validateStringKey(@android.support.annotation.NonNull final java.lang.String key)
    {
        if (org.wheatgenetics.inventory.BuildConfig.DEBUG
        &&  !key.equals(org.wheatgenetics.sharedpreferences.SharedPreferences.FIRST_NAME)
        &&  !key.equals(org.wheatgenetics.sharedpreferences.SharedPreferences.LAST_NAME ))
            throw new java.lang.AssertionError();
    }

    // region Private Methods
    private static int sendDebugLogMsg(
    @android.support.annotation.NonNull final java.lang.String tag,
    @android.support.annotation.NonNull       java.lang.String msg)
    {
        if (msg.equals("")) msg = "empty";
        return android.util.Log.d("SharedPreferences." + tag, msg);
    }

    // region First Name Private Methods
    private java.lang.String getFirstName()
    {
        final java.lang.String firstName = this.getString(
            org.wheatgenetics.sharedpreferences.SharedPreferences.FIRST_NAME,
            /* validateKey => */ true                                       );
        org.wheatgenetics.sharedpreferences.SharedPreferences.sendDebugLogMsg(
            "getFirstName()", firstName);
        return firstName;
    }

    private void setFirstName(final java.lang.String firstName)
    {
        this.setString(org.wheatgenetics.sharedpreferences.SharedPreferences.FIRST_NAME,
            /* oldValue    => */ this.getFirstName(),
            /* newValue    => */ firstName          ,
            /* validateKey => */ true               );
        org.wheatgenetics.sharedpreferences.SharedPreferences.sendDebugLogMsg(
            "setFirstName()", this.getFirstName());
    }
    // endregion

    // region Last Name Private Methods
    private java.lang.String getLastName()
    {
        final java.lang.String lastName = this.getString(
            org.wheatgenetics.sharedpreferences.SharedPreferences.LAST_NAME,
            /* validateKey => */ true                                      );
        org.wheatgenetics.sharedpreferences.SharedPreferences.sendDebugLogMsg(
            "getLastName()", lastName);
        return lastName;
    }

    private void setLastName(final java.lang.String lastName)
    {
        this.setString(org.wheatgenetics.sharedpreferences.SharedPreferences.LAST_NAME,
            /* oldValue    => */ this.getLastName(),
            /* newValue    => */ lastName          ,
            /* validateKey => */ true              );
        org.wheatgenetics.sharedpreferences.SharedPreferences.sendDebugLogMsg(
            "setLastName()", this.getLastName());
    }
    // endregion
    // endregion

    // region Public Methods
    // region Constructor Public Method
    public SharedPreferences(final android.content.SharedPreferences sharedPreferences)
    { super(sharedPreferences); }
    // endregion

    // region Person Public Methods
    public boolean personIsSet()
    { return this.getFirstName().length() > 0 || this.getLastName().length() > 0; }

    public void setPerson(final org.wheatgenetics.inventory.PersonModel personModel)
    {
        java.lang.String firstName, lastName;
        if (null == personModel)
        {
            firstName = null;
            lastName  = null;
        }
        else
        {
            firstName = personModel.firstName;
            lastName  = personModel.lastName ;
        }
        this.setFirstName(firstName);
        this.setLastName (lastName );
    }
    // endregion

    // region FirstName Public Methods
//    public java.lang.String getFirstName()
//    {
//        return this.getString(org.wheatgenetics.sharedpreferences.SharedPreferences.FIRST_NAME,
//            /* validateKey => */ true);
//    }

//    public boolean firstNameIsSet() { return this.getFirstName().length() > 0; }
    // endregion

    // region LastName Public Method
//    public java.lang.String getLastName()
//    {
//        return this.getString(org.wheatgenetics.sharedpreferences.SharedPreferences.LAST_NAME,
//            /* validateKey => */ true);
//    }
    // endregion

    // region Name Public Methods
//    public void setName(final java.lang.String firstName, final java.lang.String lastName)
//    {
//        this.setString(org.wheatgenetics.sharedpreferences.SharedPreferences.FIRST_NAME,
//            /* oldValue    => */ this.getFirstName(),
//            /* newValue    => */ firstName          ,
//            /* validateKey => */ true               );
//        this.setString(org.wheatgenetics.sharedpreferences.SharedPreferences.LAST_NAME,
//            /* oldValue    => */ this.getLastName(),
//            /* newValue    => */ lastName          ,
//            /* validateKey => */ true              );
//    }

//    public java.lang.String getName() { return this.getFirstName() + " " + this.getLastName(); }

    /**
     * A "safe" name is a full name (first name and last name) where the first name and last name
     * are separated with an underscore ("_") instead of a space (" ").
     */
//    public java.lang.String getSafeName() { return this.getFirstName() + "_" + this.getLastName(); }
    // endregion

    // region IgnoreScale Public Methods
    /* public boolean getIgnoreScale()
    {
        return this.getBoolean(org.wheatgenetics.sharedpreferences.SharedPreferences.IGNORE_SCALE);
    }

    public void setIgnoreScaleToTrue()
    {
        this.setBooleanToTrue(org.wheatgenetics.sharedpreferences.SharedPreferences.IGNORE_SCALE);
    }*/
    // endregion
    // endregion
}