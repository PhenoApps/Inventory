package org.wheatgenetics.inventory;

public class InventoryRecord {

    //region Fields
    private int id;
    private String mBoxID = null;
    private String mEnvID = null;
    private String mPersonID = null;
    private String mDate = null;
    private int mPosition;
    private String mWt = null;
    //endregion


    //region Constructors
    public InventoryRecord() {
        super();
    }

    public InventoryRecord(final String boxID, final String envID, final int position) {
        this();
        this.mBoxID = boxID;
        this.mEnvID = envID;
        this.mPosition = position;
    }

    public InventoryRecord(final String boxID, final String envID, final String personID,
                           final String date, final int position, final String wt) {
        this(boxID, envID, position);
        this.mPersonID = personID;
        this.mDate = date;
        this.mWt = wt;
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
        return this.mBoxID + "," + this.mEnvID + "," + this.mPersonID + "," +
                this.mDate + "," + this.mPosition + "," + this.mWt;
    }


    //region Getters and Setters
    public int getId() {
        return this.id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getBox() {
        return this.mBoxID;
    }

    public void setBox(final String box) {
        this.mBoxID = box;
    }

    public String getEnvID() {
        return this.mEnvID;
    }

    public void setEnvID(final String envID) {
        this.mEnvID = envID;
    }

    public String getPersonID() {
        return this.mPersonID;
    }

    public void setPersonID(final String personID) {
        this.mPersonID = personID;
    }

    public String getDate() {
        return this.mDate;
    }

    public void setDate(final String date) {
        this.mDate = date;
    }

    public int getPosition() {
        return this.mPosition;
    }

    public void setPosition(final int position) {
        this.mPosition = position;
    }

    public String getWt() {
        return this.mWt;
    }

    public void setWt(final String wt) {
        this.mWt = wt;
    }
    //endregion


    public String getPositionAsString() { return Integer.toString(this.getPosition()); }

    public void set(final String id, final String box, final String envID, final String personID,
                    final String date, final String position, final String wt) {
        this.setId(Integer.parseInt(id));
        this.setBox(box);
        this.setEnvID(envID);
        this.setPersonID(personID);
        this.setDate(date);
        this.setPosition(Integer.parseInt(position));
        this.setWt(wt);
    }
}