package org.wheatgenetics.inventory;

/**
 * Uses:
 * android.app.Activity
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 * android.support.annotation.NonNull
 * android.view.LayoutInflater
 * android.view.View
 * android.widget.EditText
 * android.widget.LinearLayout
 *
 * org.wheatgenetics.androidlibrary.AlertDialog
 *
 * org.wheatgenetics.inventory.model.Person
 *
 * org.wheatgenetics.inventory.R
 */
class SetPersonAlertDialog extends org.wheatgenetics.androidlibrary.AlertDialog
{
    @java.lang.SuppressWarnings("UnnecessaryInterfaceModifier")
    interface PersonStorer
    {
        public abstract void storePerson(@android.support.annotation.NonNull
            final org.wheatgenetics.inventory.model.Person person);
    }

    // region Fields
    private final org.wheatgenetics.inventory.SetPersonAlertDialog.PersonStorer personStorer;
    private       android.widget.EditText                firstNameEditText, lastNameEditText;
    // endregion

    // region Private Methods
    private org.wheatgenetics.inventory.model.Person makePerson()
    {
        assert null != this.firstNameEditText; assert null != this.lastNameEditText;
        return new org.wheatgenetics.inventory.model.Person(
            this.firstNameEditText.getText().toString(),
            this.lastNameEditText.getText ().toString());
    }

    private void storePerson()
    { assert null != this.personStorer; this.personStorer.storePerson(this.makePerson()); }
    // endregion

    SetPersonAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.inventory.SetPersonAlertDialog.PersonStorer personStorer)
    { super(activity); this.personStorer = personStorer; }

    // region Overridden Methods
    @java.lang.Override
    public void configure()
    {
        this.setTitle(org.wheatgenetics.inventory.R.string.setPersonAlertDialogTitle)
            .setCancelableToFalse();
        {
            final android.view.View setPersonView =
                android.view.LayoutInflater.from(this.activity()).inflate(
                    org.wheatgenetics.inventory.R.layout.set_person_alert_dialog,
                    new android.widget.LinearLayout(this.activity()), false);

            assert null != setPersonView;
            this.firstNameEditText = (android.widget.EditText) setPersonView.findViewById(
                org.wheatgenetics.inventory.R.id.setPersonFirstNameEditText);
            this.lastNameEditText = (android.widget.EditText) setPersonView.findViewById(
                org.wheatgenetics.inventory.R.id.setPersonLastNameEditText);

            this.setView(setPersonView);
        }
        this.setOKPositiveButton(new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                { org.wheatgenetics.inventory.SetPersonAlertDialog.this.storePerson(); }
            });
    }

    @java.lang.Override
    public void show() { this.show(null); }
    // endregion

    void show(final org.wheatgenetics.inventory.model.Person person)
    {
        assert null != this.firstNameEditText; assert null != this.lastNameEditText;
        {
            java.lang.String firstName, lastName;
            if (null == person)
            {
                firstName = null;
                lastName  = null;
            }
            else
            {
                firstName = person.firstName;
                lastName  = person.lastName ;
            }
            this.firstNameEditText.setText(firstName);
            this.lastNameEditText.setText (lastName );
        }

        super.show();
    }
}