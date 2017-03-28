package org.wheatgenetics.inventory;

public class InventoryRecord {

    //region Fields
    private int id;
    private String mBoxID;
    private String mEnvID;
    private String mPersonID;
    private String mDate;
    private int mPosition;
    private String mWt;
    //endregion

    //region Constructors
    public InventoryRecord() {
    }

    public InventoryRecord(final String mBoxID, final String mEnvID, final String mPersonID,
                           final String mDate, final int mPosition, final String mWt) {
        super();
        this.mBoxID = mBoxID;
        this.mEnvID = mEnvID;
        this.mPersonID = mPersonID;
        this.mDate = mDate;
        this.mPosition = mPosition;
        this.mWt = mWt;
    }
    //endregion

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

    @Override
    public String toString() {
        return this.mBoxID + "," + this.mEnvID + "," + this.mPersonID + "," +
            this.mDate + "," + this.mPosition + "," + this.mWt;
    }
}