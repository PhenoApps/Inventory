package org.wheatgenetics.inventory;

public class InventoryRecord extends java.lang.Object {
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
    public InventoryRecord() { super(); }

    public InventoryRecord(final String box, final String envid, final int position) {
        this();
        this.box      = box     ;
        this.envid    = envid   ;
        this.position = position;
    }

    public InventoryRecord(final String box, final String envid,
    final String person, final String date, final int position, final String wt) {
        this(box, envid, position);
        this.person = person;
        this.date   = date  ;
        this.wt     = wt    ;
    }

    public InventoryRecord(final String id, final String box, final String envid,
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
    public int  getId()             { return this.id; }
    public void setId(final int id) { this.id = id  ; }

    public String getBox()                 { return this.box; }
    public void   setBox(final String box) { this.box = box ; }

    public String getEnvId()                   { return this.envid ; }
    public void   setEnvId(final String envid) { this.envid = envid; }

    public String getPerson()                    { return this.person  ; }
    public void   setPerson(final String person) { this.person = person; }

    public String getDate()                  { return this.date; }
    public void   setDate(final String date) { this.date = date; }

    public int  getPosition()                   { return this.position    ; }
    public void setPosition(final int position) { this.position = position; }

    public String getWt()                { return this.wt; }
    public void   setWt(final String wt) { this.wt = wt  ; }
    // endregion


    public String getPositionAsString() { return java.lang.Integer.toString(this.getPosition()); }

    public String getCSV() {
        return this.box + ","  + this.envid + "," + this.date +
            "," + this.person + "," + this.wt + "\r\n";
    }

    public void set(final String id, final String box, final String envid,
    final String person, final String date, final String position, final String wt) {
        this.setId      (java.lang.Integer.parseInt(id)      );
        this.setBox     (box                                 );
        this.setEnvId   (envid                               );
        this.setPerson  (person                              );
        this.setDate    (date                                );
        this.setPosition(java.lang.Integer.parseInt(position));
        this.setWt      (wt                                  );
    }
}