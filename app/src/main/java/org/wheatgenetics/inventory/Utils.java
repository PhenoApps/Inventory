package org.wheatgenetics.inventory;

class Utils
{
    static java.lang.String[] makeStringArray(final java.lang.String value)
    {
        return new java.lang.String[]{value};
    }

    static java.lang.String[] makeStringArray(final int value)
    {
        return org.wheatgenetics.inventory.Utils.makeStringArray(java.lang.String.valueOf(value));
    }

    static java.lang.String getDateTime()
    {
        final java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat(
            "yyyy-MM-dd-hh-mm-ss", java.util.Locale.getDefault());
        return simpleDateFormat.format(java.util.Calendar.getInstance().getTime());
    }

    static java.lang.String getExportFileName()
    {
        return "inventory_" + org.wheatgenetics.inventory.Utils.getDateTime();
    }
}