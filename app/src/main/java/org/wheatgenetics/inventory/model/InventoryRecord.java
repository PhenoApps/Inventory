package org.wheatgenetics.inventory.model;

/**
 * Uses:
 * android.support.annotation.NonNull
 * android.util.Log
 *
 * org.wheatgenetics.javalib.Utils
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class InventoryRecord extends java.lang.Object
{
    // region Fields
    private final java.lang.String box, envid, person, date;
    private final int              position                ;
    private final java.lang.String wt                      ;
    // endregion

    private static java.lang.String trim(java.lang.String string)
    {
        final java.lang.String nullString = "null";
        if (null == string)
            return nullString;
        else
        {
            string = string.trim();
            return string.length() > 0 ? string : nullString;
        }
    }

    // region Constructors
    private InventoryRecord(final java.lang.String box, final java.lang.String envid,
    final java.lang.String person, final java.lang.String date, final int position,
    final java.lang.String wt)
    {
        super();

        this.box  = box ; this.envid    = envid   ; this.person = person;
        this.date = date; this.position = position; this.wt     = wt    ;
    }

    public InventoryRecord(final java.lang.String box, final java.lang.String envid,
    final java.lang.String person, final java.lang.String date, final java.lang.String position,
    final java.lang.String wt)
    { this(box, envid, person, date, java.lang.Integer.parseInt(position), wt); }

    public InventoryRecord(final java.lang.String box, final java.lang.String envid,
    final java.lang.String person, final int position, final java.lang.String wt)
    {
        this(box, envid, person, org.wheatgenetics.javalib.Utils.getDateTime(), position,
            org.wheatgenetics.inventory.model.InventoryRecord.trim(wt));
    }
    // endregion

    @android.support.annotation.NonNull @java.lang.Override public java.lang.String toString()
    {
        return this.getBox() + "," + this.getEnvId() + "," + this.getPerson() +
            "," + this.getDate() + "," + this.getPosition() + "," + this.getWt();
    }

    // region Package Methods
    java.lang.String getCSV()
    {
        return this.getBox() + ","  + this.getEnvId() + "," + this.getDate() +
            "," + this.getPerson() + "," + this.getWt() + "\r\n";
    }

    java.lang.String getSQL()
    {
        final java.lang.String fields[] = this.toString().split(",");
        for (int i = 0; i < fields.length; i++)
        {
            final java.lang.String field = fields[i];
            if (field.length() <= 0)
                fields[i] = "null";
            else
                if (!field.equals("null")) fields[i] = "'" + field + "'";
        }

        return "(" +
            fields[0] + "," +  // box
            fields[1] + "," +  // envid
            fields[3] + "," +  // date
            fields[2] + "," +  // person
            fields[5] + ")" ;  // wt
    }
    // endregion

    // region Public Methods
    // region Getter Public Methods
    public java.lang.String getBox     () { return this.box     ; }
    public java.lang.String getEnvId   () { return this.envid   ; }
    public java.lang.String getPerson  () { return this.person  ; }
    public java.lang.String getDate    () { return this.date    ; }
    public int              getPosition() { return this.position; }
    public java.lang.String getWt      () { return this.wt      ; }
    // endregion

    public java.lang.String getPositionAsString()
    { return java.lang.Integer.toString(this.getPosition()); }

    public void sendDebugLogMsg(final java.lang.String tag)
    { android.util.Log.d(tag, this.toString()); }
    // endregion
}