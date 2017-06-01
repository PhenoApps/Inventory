package org.wheatgenetics.inventory;

/** Uses:
 * android.content.SharedPreferences
 * android.content.SharedPreferences.Editor
 *
 * org.wheatgenetics.inventory.BuildConfig
 */

class SharedPreferences extends java.lang.Object
{
    // region Private Class Constants
    private static final java.lang.String
        FIRST_NAME   = "FirstName"  , LAST_NAME      = "LastName"     ,
        IGNORE_SCALE = "ignoreScale", UPDATE_VERSION = "UpdateVersion";
    // endregion

    // region Private Field
    private final android.content.SharedPreferences sharedPreferences;
    // endregion

    // region Private Class Method
    private static void assertValidNameKey(final java.lang.String key)
    {
        assert null != key;
        if (org.wheatgenetics.inventory.BuildConfig.DEBUG)
            if (!key.equals(org.wheatgenetics.inventory.SharedPreferences.FIRST_NAME)
            &&  !key.equals(org.wheatgenetics.inventory.SharedPreferences.LAST_NAME ))
                throw new java.lang.AssertionError();
    }
    // endregion

    // region Private Methods
    private java.lang.String getString(final java.lang.String key)
    {
        org.wheatgenetics.inventory.SharedPreferences.assertValidNameKey(key);

        assert null != this.sharedPreferences;
        return this.sharedPreferences.getString(key, "");
    }

    private void setString(final java.lang.String key,
    final java.lang.String oldValue, java.lang.String newValue)
    {
        assert null != oldValue;
        if (null == newValue) newValue = "";
        if (!oldValue.equals(newValue))
        {
            org.wheatgenetics.inventory.SharedPreferences.assertValidNameKey(key);

            assert null != this.sharedPreferences;
            final android.content.SharedPreferences.Editor editor = this.sharedPreferences.edit();

            assert null != editor;
            editor.putString(key, newValue);
            editor.apply();
        }
    }

    private int getUpdateVersion()
    {
        assert null != this.sharedPreferences;
        return this.sharedPreferences.getInt(
            org.wheatgenetics.inventory.SharedPreferences.UPDATE_VERSION, -1);
    }
    // endregion

    // region Package Methods
    // region Constructor Package Method
    SharedPreferences(final android.content.SharedPreferences sharedPreferences)
    {
        super();

        assert null != sharedPreferences;
        this.sharedPreferences = sharedPreferences;
    }
    // endregion

    // region FirstName Package Methods
    java.lang.String getFirstName()
    { return this.getString(org.wheatgenetics.inventory.SharedPreferences.FIRST_NAME); }

    java.lang.Boolean firstNameIsSet() { return this.getFirstName().length() > 0; }
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
        assert null != this.sharedPreferences;
        return this.sharedPreferences.getBoolean(
            org.wheatgenetics.inventory.SharedPreferences.IGNORE_SCALE, false);
    }

    void setIgnoreScaleToTrue()
    {
        if (!this.getIgnoreScale())
        {
            assert null != this.sharedPreferences;
            final android.content.SharedPreferences.Editor editor = this.sharedPreferences.edit();

            assert null != editor;
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
            assert null != this.sharedPreferences;
            final android.content.SharedPreferences.Editor editor = this.sharedPreferences.edit();

            assert null != editor;
            editor.putInt(
                org.wheatgenetics.inventory.SharedPreferences.UPDATE_VERSION, newUpdateVersion);
            editor.apply();
        }
    }
    // endregion
    // endregion
}