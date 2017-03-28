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
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getBox() {
        return mBoxID;
    }

    public void setBox(final String title) {
        this.mBoxID = title;
    }

    public String getEnvID() {
        return mEnvID;
    }

    public void setEnvID(final String author) {
        this.mEnvID = author;
    }

    public String getPersonID() {
        return mPersonID;
    }

    public void setPersonID(final String author) {
        this.mPersonID = author;
    }


    public void setDate(final String author) {
        this.mDate = author;
    }

    public String getDate() {
        return mDate;
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(final int author) {
        this.mPosition = author;
    }

    public String getWt() {
        return mWt;
    }

    public void setWt(final String author) {
        this.mWt = author;
    }

    @Override
    public String toString() {
        return mBoxID + "," + mEnvID + "," + mPersonID + "," + mDate + "," + mPosition + "," + mWt;
    }
    //endregion
}