package org.wheatgenetics.inventory;

class InventoryRecord extends java.lang.Object {
    // region Fields
    private int    id;
    private String box    = null;
    private String envid  = null;
    private String person = null;
    private String date   = null;
    private int    position;
    private String wt = null;
    // endregion


    // region Constructors
    InventoryRecord() { super(); }

    InventoryRecord(final String box, final String envid, final int position) {
        this();
        this.box      = box     ;
        this.envid    = envid   ;
        this.position = position;
    }

    InventoryRecord(final String box, final String envid,
    final String person, final String date, final int position, final String wt) {
        this(box, envid, position);
        this.person = person;
        this.date   = date  ;
        this.wt     = wt    ;
    }

    InventoryRecord(final String id, final String box, final String envid,
    final String person, final String date, final String position, final String wt) {
        this();
        this.set(id, box, envid, person, date, position, wt);
    }
    // endregion


    @Override
    public String toString() {
        return this.box + "," + this.envid + "," + this.person +
            "," + this.date + "," + this.position + "," + this.wt;
    }


    // region Getters and Setters
    int  getId()             { return this.id; }
    void setId(final int id) { this.id = id  ; }

    String getBox     () { return this.box     ; }
    String getEnvId   () { return this.envid   ; }
    String getPerson  () { return this.person  ; }
    String getDate    () { return this.date    ; }
    int    getPosition() { return this.position; }
    String getWt      () { return this.wt      ; }
    // endregion


    String getPositionAsString() { return java.lang.Integer.toString(this.getPosition()); }

    String getCSV() {
        return this.box + ","  + this.envid + "," + this.date +
            "," + this.person + "," + this.wt + "\r\n";
    }

    void set(final String id, final String box, final String envid,
    final String person, final String date, final String position, final String wt) {
        this.setId(java.lang.Integer.parseInt(id));
        this.box      = box                                 ;
        this.envid    = envid                               ;
        this.person   = person                              ;
        this.date     = date                                ;
        this.position = java.lang.Integer.parseInt(position);
        this.wt       = wt                                  ;
    }
}