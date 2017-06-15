package org.wheatgenetics.inventory;

/**
 * Uses:
 * android.app.AlertDialog
 * android.app.AlertDialog.Builder
 * android.content.Context
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 * android.support.annotation.NonNull
 * android.view.LayoutInflater
 * android.view.View
 * android.widget.EditText
 * android.widget.LinearLayout
 *
 * org.wheatgenetics.inventory.model.Person
 * org.wheatgenetics.inventory.R
 */

class SetPersonAlertDialog extends java.lang.Object
{
    interface PersonSetter
    {
        void setPerson(@android.support.annotation.NonNull
        final org.wheatgenetics.inventory.model.Person person);
    }

    // region Fields
    private final android.content.Context                                       context     ;
    private final org.wheatgenetics.inventory.SetPersonAlertDialog.PersonSetter personSetter;

    private android.app.AlertDialog alertDialog       = null                         ;
    private android.widget.EditText firstNameEditText = null, lastNameEditText = null;
    // endregion

    SetPersonAlertDialog(@android.support.annotation.NonNull final android.content.Context context,
    @android.support.annotation.NonNull
    final org.wheatgenetics.inventory.SetPersonAlertDialog.PersonSetter personSetter)
    {
        super();

        this.context      = context     ;
        this.personSetter = personSetter;
    }

    // region Private Methods
    private org.wheatgenetics.inventory.model.Person makePerson()
    {
        assert null != this.firstNameEditText;
        assert null != this.lastNameEditText ;
        return new org.wheatgenetics.inventory.model.Person(
            this.firstNameEditText.getText().toString(),
            this.lastNameEditText.getText ().toString());
    }

    private void sendPerson() { this.personSetter.setPerson(this.makePerson()); }
    // endregion

    void show(final org.wheatgenetics.inventory.model.Person person)
    {
        if (null == this.alertDialog)
        {
            {
                final android.app.AlertDialog.Builder builder =
                    new android.app.AlertDialog.Builder(this.context);
                {
                    final android.view.View setPersonView =
                        android.view.LayoutInflater.from(this.context).inflate(
                            org.wheatgenetics.inventory.R.layout.set_person_alert_dialog,
                            new android.widget.LinearLayout(this.context)               ,
                            false                                                       );

                    assert null != setPersonView;
                    this.firstNameEditText = (android.widget.EditText) setPersonView.findViewById(
                        org.wheatgenetics.inventory.R.id.setPersonFirstNameEditText);
                    this.lastNameEditText = (android.widget.EditText) setPersonView.findViewById(
                        org.wheatgenetics.inventory.R.id.setPersonLastNameEditText);

                    builder.setCancelable(false)
                        .setTitle(org.wheatgenetics.inventory.R.string.setPersonAlertDialogTitle)
                        .setView (setPersonView                                                 )
                        .setPositiveButton(org.wheatgenetics.inventory.R.string.positiveButtonText,
                            new android.content.DialogInterface.OnClickListener()
                            {
                                @java.lang.Override
                                public void onClick(final android.content.DialogInterface dialog,
                                final int which)
                                {
                                    org.wheatgenetics.inventory.
                                        SetPersonAlertDialog.this.sendPerson();
                                }
                            });
                }
                this.alertDialog = builder.create();
            }
            assert null != this.alertDialog;
        }

        assert null != this.firstNameEditText;
        assert null != this.lastNameEditText ;
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
        this.alertDialog.show();
    }

    void show() { this.show(null); }
}