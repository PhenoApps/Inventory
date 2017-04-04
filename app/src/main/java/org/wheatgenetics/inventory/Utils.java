package org.wheatgenetics.inventory;

class Utils
{
    static java.lang.String getDateTime()
    {
        final java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat(
            "yyyy-MM-dd-hh-mm-ss", java.util.Locale.getDefault());
        return simpleDateFormat.format(java.util.Calendar.getInstance().getTime());
    }

    static java.lang.String getFileName()
    {
        return "inventory_" + org.wheatgenetics.inventory.Utils.getDateTime();
    }
}