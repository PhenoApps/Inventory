package org.wheatgenetics.inventory;

// Uses android.util.Log.

class InventoryRecord extends java.lang.Object {
    // region Fields
    private int              id;
    private java.lang.String box    = null;
    private java.lang.String envid  = null;
    private java.lang.String person = null;
    private java.lang.String date   = null;
    private int              position;
    private java.lang.String wt = null;
    // endregion


    // region Constructors
    InventoryRecord() { super(); }

    InventoryRecord(final java.lang.String box, final java.lang.String envid, final int position) {
        this();
        this.box      = box     ;
        this.envid    = envid   ;
        this.position = position;
    }

    InventoryRecord(final java.lang.String box, final java.lang.String envid,
    final java.lang.String person, final int position, final java.lang.String wt) {
        this(box, envid, position);
        this.person = person                                         ;
        this.date   = org.wheatgenetics.inventory.Utils.getDateTime();
        this.wt     = wt                                             ;
    }

    InventoryRecord(final java.lang.String id, final java.lang.String box,
    final java.lang.String envid, final java.lang.String person, final java.lang.String date,
    final java.lang.String position, final java.lang.String wt) {
        this();
        this.set(id, box, envid, person, date, position, wt);
    }
    // endregion


    @Override
    public java.lang.String toString() {
        return this.box + "," + this.envid + "," + this.person +
            "," + this.date + "," + this.position + "," + this.wt;
    }


    // region Getters and Setters
    int  getId()             { return this.id; }
    void setId(final int id) { this.id = id  ; }

    java.lang.String getBox     () { return this.box     ; }
    java.lang.String getEnvId   () { return this.envid   ; }
    java.lang.String getPerson  () { return this.person  ; }
    java.lang.String getDate    () { return this.date    ; }
    int              getPosition() { return this.position; }
    java.lang.String getWt      () { return this.wt      ; }
    // endregion


    java.lang.String getPositionAsString() {
        return java.lang.Integer.toString(this.getPosition());
    }

    java.lang.String getCSV() {
        return this.box + ","  + this.envid + "," + this.date +
            "," + this.person + "," + this.wt + "\r\n";
    }

    java.lang.String getSQL() {
        final java.lang.String[] fields = this.toString().split(",");
        for (int i = 0; i < fields.length; i++)
            if (fields[i].length() <= 0)
                fields[i] = "'null'";
            else
                fields[i] = "'" + fields[i] + "'";

        return "(" +
            fields[0] + "," + // box
            fields[1] + "," + // envid
            fields[3] + "," + // date
            fields[2] + "," + // person
            fields[5] + ")";  // wt
    }

    void set(final java.lang.String id, final java.lang.String box, final java.lang.String envid,
    final java.lang.String person, final java.lang.String date, final java.lang.String position,
    final java.lang.String wt) {
        this.setId(java.lang.Integer.parseInt(id));
        this.box      = box                                 ;
        this.envid    = envid                               ;
        this.person   = person                              ;
        this.date     = date                                ;
        this.position = java.lang.Integer.parseInt(position);
        this.wt       = wt                                  ;
    }

    int sendDebugLogMsg(final java.lang.String tag)
    {
        return android.util.Log.d(tag, this.toString());
    }

    int sendErrorLogMsg(final java.lang.String tag)
    {
        return android.util.Log.e(tag,
            this.box + " " + this.getPositionAsString() + " " + this.envid + " " + this.wt);
    }
}