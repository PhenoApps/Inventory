package org.wheatgenetics.inventory;

// Uses android.content.SharedPreferences.

public class Settings extends java.lang.Object
{
    //region Constants
    final protected java.lang.String FIRST_NAME     = "FirstName"    ;
    final protected java.lang.String LAST_NAME      = "LastName"     ;
    final protected java.lang.String IGNORE_SCALE   = "ignoreScale"  ;
    final protected java.lang.String UPDATE_VERSION = "UpdateVersion";
    //endregion

    protected android.content.SharedPreferences sharedPreferences;


    //region Protected Methods
    protected java.lang.String getString(final java.lang.String key)
    {
        assert key != null;
        assert key.equals(this.FIRST_NAME) || key.equals(this.LAST_NAME);

        assert this.sharedPreferences != null;
        return this.sharedPreferences.getString(key, "");
    }

    protected void setString(final java.lang.String key,
                             final java.lang.String oldValue, java.lang.String newValue)
    {
        assert oldValue != null;
        if (newValue == null) newValue = "";
        if (!oldValue.equals(newValue))
        {
            assert key != null;
            assert key.equals(this.FIRST_NAME) || key.equals(this.LAST_NAME);

            assert this.sharedPreferences != null;
            final android.content.SharedPreferences.Editor editor = this.sharedPreferences.edit();

            assert editor != null;
            editor.putString(key, newValue);
        }
    }

    protected int getUpdateVersion()
    {
        assert this.sharedPreferences != null;
        return this.sharedPreferences.getInt(this.UPDATE_VERSION, -1);
    }
    //endregion


    public Settings(final android.content.SharedPreferences sharedPreferences)
    {
        assert sharedPreferences != null;
        this.sharedPreferences = sharedPreferences;
    }


    //region FirstName
    public java.lang.Boolean firstNameIsSet() { return this.getFirstName().length() > 0; }

    public java.lang.String getFirstName() { return this.getString(this.FIRST_NAME); }
    //endregion


    //region LastName
    public java.lang.String getLastName(){ return this.getString(this.LAST_NAME); }
    //endregion


    //region Name
    public void setName(final java.lang.String firstName, final java.lang.String lastName)
    {
        this.setString(this.FIRST_NAME, this.getFirstName(), firstName);
        this.setString(this.LAST_NAME , this.getLastName() , lastName );


        assert this.sharedPreferences != null;
        final android.content.SharedPreferences.Editor editor = this.sharedPreferences.edit();

        assert editor != null;
        editor.apply();
    }
    //endregion


    //region ignoreScale
    public java.lang.Boolean getIgnoreScale()
    {
        assert this.sharedPreferences != null;
        return this.sharedPreferences.getBoolean(this.IGNORE_SCALE, false);
    }

    public void setIgnoreScaleToTrue()
    {
        if (!this.getIgnoreScale())
        {
            assert this.sharedPreferences != null;
            final android.content.SharedPreferences.Editor editor = this.sharedPreferences.edit();

            assert editor != null;
            editor.putBoolean(this.IGNORE_SCALE, true);
            editor.apply();
        }
    }
    //endregion


    //region UpdateVersion
    public java.lang.Boolean updateVersionIsSet(final int newUpdateVersion)
    {
        return this.getUpdateVersion() >= newUpdateVersion;
    }

    public void setUpdateVersion(final int newUpdateVersion)
    {
        if (this.getUpdateVersion() != newUpdateVersion)
        {
            assert this.sharedPreferences != null;
            final android.content.SharedPreferences.Editor editor = this.sharedPreferences.edit();

            assert editor != null;
            editor.putInt(this.UPDATE_VERSION, newUpdateVersion);
            editor.apply();
        }
    }
    //endregion
}