package org.wheatgenetics.inventory;

/** Uses:
 * android.content.SharedPreferences
 * android.content.SharedPreferences.Editor
 */

class SharedPreferences extends java.lang.Object
{
    // region Private Class Constants
    private static final java.lang.String
        FIRST_NAME   = "FirstName"  , LAST_NAME      = "LastName"     ,
        IGNORE_SCALE = "ignoreScale", UPDATE_VERSION = "UpdateVersion";
    // endregion


    // region Private Field
    private android.content.SharedPreferences sharedPreferences;
    // endregion


    // region Private Methods
    private java.lang.String getString(final java.lang.String key)
    {
        assert key != null;
        assert key.equals(org.wheatgenetics.inventory.SharedPreferences.FIRST_NAME)
            || key.equals(org.wheatgenetics.inventory.SharedPreferences.LAST_NAME );

        assert this.sharedPreferences != null;
        return this.sharedPreferences.getString(key, "");
    }

    private void setString(final java.lang.String key,
    final java.lang.String oldValue, java.lang.String newValue)
    {
        assert oldValue != null;
        if (newValue == null) newValue = "";
        if (!oldValue.equals(newValue))
        {
            assert key != null;
            assert key.equals(org.wheatgenetics.inventory.SharedPreferences.FIRST_NAME)
                || key.equals(org.wheatgenetics.inventory.SharedPreferences.LAST_NAME );

            assert this.sharedPreferences != null;
            final android.content.SharedPreferences.Editor editor = this.sharedPreferences.edit();

            assert editor != null;
            editor.putString(key, newValue);
            editor.apply();
        }
    }

    private int getUpdateVersion()
    {
        assert this.sharedPreferences != null;
        return this.sharedPreferences.getInt(
            org.wheatgenetics.inventory.SharedPreferences.UPDATE_VERSION, -1);
    }
    // endregion


    // region Package Methods
    // region Constructor Package Method
    SharedPreferences(final android.content.SharedPreferences sharedPreferences)
    {
        super();
        assert sharedPreferences != null;
        this.sharedPreferences = sharedPreferences;
    }
    // endregion


    // region FirstName Package Methods
    java.lang.Boolean firstNameIsSet() { return this.getFirstName().length() > 0; }

    java.lang.String getFirstName()
    { return this.getString(org.wheatgenetics.inventory.SharedPreferences.FIRST_NAME); }
    // endregion


    // region LastName Package Method
    java.lang.String getLastName()
    { return this.getString(org.wheatgenetics.inventory.SharedPreferences.LAST_NAME); }
    // endregion


    // region Name Package Methods
    void setName(final java.lang.String firstName, final java.lang.String lastName)
    {
        this.setString(org.wheatgenetics.inventory.SharedPreferences.FIRST_NAME,
            /* oldValue => */ this.getFirstName(), /* newValue => */ firstName);
        this.setString(org.wheatgenetics.inventory.SharedPreferences.LAST_NAME,
            /* oldValue => */ this.getLastName(), /* newValue => */ lastName);
    }

    java.lang.String getName() { return this.getFirstName() + " " + this.getLastName(); }

    /**
     * A "safe" name is a full name (first name and last name) where the first name and last name
     * are separated with an underscore ("_") instead of a space (" ").
     */
    java.lang.String getSafeName() { return this.getFirstName() + "_" + this.getLastName(); }
    // endregion


    // region IgnoreScale Package Methods
    java.lang.Boolean getIgnoreScale()
    {
        assert this.sharedPreferences != null;
        return this.sharedPreferences.getBoolean(
            org.wheatgenetics.inventory.SharedPreferences.IGNORE_SCALE, false);
    }

    void setIgnoreScaleToTrue()
    {
        if (!this.getIgnoreScale())
        {
            assert this.sharedPreferences != null;
            final android.content.SharedPreferences.Editor editor = this.sharedPreferences.edit();

            assert editor != null;
            editor.putBoolean(org.wheatgenetics.inventory.SharedPreferences.IGNORE_SCALE, true);
            editor.apply();
        }
    }
    // endregion


    // region UpdateVersion Package Methods
    java.lang.Boolean updateVersionIsSet(final int newUpdateVersion)
    { return this.getUpdateVersion() >= newUpdateVersion; }

    void setUpdateVersion(final int newUpdateVersion)
    {
        if (this.getUpdateVersion() != newUpdateVersion)
        {
            assert this.sharedPreferences != null;
            final android.content.SharedPreferences.Editor editor = this.sharedPreferences.edit();

            assert editor != null;
            editor.putInt(
                org.wheatgenetics.inventory.SharedPreferences.UPDATE_VERSION, newUpdateVersion);
            editor.apply();
        }
    }
    // endregion
    // endregion
}