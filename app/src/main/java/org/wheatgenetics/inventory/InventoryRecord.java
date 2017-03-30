package org.wheatgenetics.inventory;

public class InventoryRecord extends java.lang.Object {
    //region Fields
    private int id;
    private String boxID = null;
    private String envID = null;
    private String personID = null;
    private String date = null;
    private int position;
    private String wt = null;
    //endregion


    //region Constructors
    public InventoryRecord() {
        super();
    }

    public InventoryRecord(final String boxID, final String envID, final int position) {
        this();
        this.boxID = boxID;
        this.envID = envID;
        this.position = position;
    }

    public InventoryRecord(final String boxID, final String envID, final String personID,
                           final String date, final int position, final String wt) {
        this(boxID, envID, position);
        this.personID = personID;
        this.date = date;
        this.wt = wt;
    }

    public InventoryRecord(final String id, final String box, final String envID,
                           final String personID, final String date, final String position,
                           final String wt) {
        this();
        this.set(id, box, envID, personID, date, position, wt);
    }
    //endregion


    @Override
    public String toString() {
        return this.boxID + "," + this.envID + "," + this.personID + "," +
                this.date + "," + this.position + "," + this.wt;
    }


    //region Getters and Setters
    public int getId() {
        return this.id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getBox() {
        return this.boxID;
    }

    public void setBox(final String box) {
        this.boxID = box;
    }

    public String getEnvID() {
        return this.envID;
    }

    public void setEnvID(final String envID) {
        this.envID = envID;
    }

    public String getPersonID() {
        return this.personID;
    }

    public void setPersonID(final String personID) {
        this.personID = personID;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(final int position) {
        this.position = position;
    }

    public String getWt() {
        return this.wt;
    }

    public void setWt(final String wt) {
        this.wt = wt;
    }
    //endregion


    public String getPositionAsString() { return java.lang.Integer.toString(this.getPosition()); }

    public void set(final String id, final String box, final String envID, final String personID,
                    final String date, final String position, final String wt) {
        this.setId(java.lang.Integer.parseInt(id));
        this.setBox(box);
        this.setEnvID(envID);
        this.setPersonID(personID);
        this.setDate(date);
        this.setPosition(java.lang.Integer.parseInt(position));
        this.setWt(wt);
    }
}