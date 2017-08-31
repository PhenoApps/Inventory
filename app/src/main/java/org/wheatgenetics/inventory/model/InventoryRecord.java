package org.wheatgenetics.inventory.model;

/**
 * Uses:
 * android.util.Log
 *
 * org.wheatgenetics.javalib.Utils
 */
public class InventoryRecord extends java.lang.Object
{
    // region Fields
    private int              id                                                  ;
    private java.lang.String box = null, envid = null, person = null, date = null;
    private int              position                                            ;
    private java.lang.String wt = null                                           ;
    // endregion

    @java.lang.Override
    public java.lang.String toString()
    {
        return this.getBox() + "," + this.getEnvId() + "," + this.getPerson() +
            "," + this.getDate() + "," + this.getPosition() + "," + this.getWt();
    }

    // region Package Methods
    // region Constructor Package Methods
    InventoryRecord(final java.lang.String box, final java.lang.String envid, final int position)
    { this(); this.box = box; this.envid = envid; this.position = position; }

    InventoryRecord(final java.lang.String box, final java.lang.String envid,
    final java.lang.String person, final int position, final java.lang.String wt)
    {
        this(box, envid, position);
        this.person = person                                       ;
        this.date   = org.wheatgenetics.javalib.Utils.getDateTime();
        this.wt     = wt                                           ;
    }
    // endregion

    void setId(final int id) { this.id = id; }

    java.lang.String getTag()
    { return this.getBox() + "," + this.getEnvId() + "," + this.getPositionAsString(); }

    java.lang.String getCSV()
    {
        return this.getBox() + ","  + this.getEnvId() + "," + this.getDate() +
            "," + this.getPerson() + "," + this.getWt() + "\r\n";
    }

    java.lang.String getSQL()
    {
        final java.lang.String fields[] = this.toString().split(",");
        for (int i = 0; i < fields.length; i++)
            if (fields[i].length() <= 0)
                fields[i] = "null";
            else
                if (!fields[i].equals("null")) fields[i] = "'" + fields[i] + "'";

        return "(" +
            fields[0] + "," +  // box
            fields[1] + "," +  // envid
            fields[3] + "," +  // date
            fields[2] + "," +  // person
            fields[5] + ")" ;  // wt
    }

    int sendErrorLogMsg(final java.lang.String tag)
    {
        return android.util.Log.e(tag, this.getBox() + " " +
            this.getPositionAsString() + " " + this.getEnvId() + " " + this.getWt());
    }
    // endregion

    // region Public Methods
    // region Constructor Public Methods
    public InventoryRecord() { super(); }

    public InventoryRecord(final java.lang.String id, final java.lang.String box,
    final java.lang.String envid, final java.lang.String person, final java.lang.String date,
    final java.lang.String position, final java.lang.String wt)
    { this(); this.set(id, box, envid, person, date, position, wt); }
    // endregion

    // region Getters and Setter Public Methods
    public int getId() { return this.id; }

    public java.lang.String getBox     () { return this.box     ; }
    public java.lang.String getEnvId   () { return this.envid   ; }
    public java.lang.String getPerson  () { return this.person  ; }
    public java.lang.String getDate    () { return this.date    ; }
    public int              getPosition() { return this.position; }
    public java.lang.String getWt      () { return this.wt      ; }
    // endregion

    public java.lang.String getPositionAsString()
    { return java.lang.Integer.toString(this.getPosition()); }

    public void set(final java.lang.String id, final java.lang.String box,
    final java.lang.String envid, final java.lang.String person, final java.lang.String date,
    final java.lang.String position, final java.lang.String wt)
    {
        this.setId(java.lang.Integer.parseInt(id));
        this.box      = box                                 ;
        this.envid    = envid                               ;
        this.person   = person                              ;
        this.date     = date                                ;
        this.position = java.lang.Integer.parseInt(position);
        this.wt       = wt                                  ;
    }

    public int sendDebugLogMsg(final java.lang.String tag)
    { return android.util.Log.d(tag, this.toString()); }
    // endregion
}