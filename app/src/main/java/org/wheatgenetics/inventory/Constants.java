package org.wheatgenetics.inventory;

// Uses android.os.Environment.

class Constants
{
    static java.io.File MAIN_PATH =
        new java.io.File(android.os.Environment.getExternalStorageDirectory(), "Inventory");
}