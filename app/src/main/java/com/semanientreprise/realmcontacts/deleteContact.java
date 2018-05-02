package com.semanientreprise.realmcontacts;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.semanientreprise.realmcontacts.RealmClasses.Contacts;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmObject;

public class deleteContact extends AppCompatActivity {

    @BindView(R.id.search_emailAddress)
    EditText searchEmailAddress;
    @BindView(R.id.delete_firstName)
    TextView deleteFirstName;
    @BindView(R.id.delete_lastName)
    TextView deleteLastName;
    @BindView(R.id.delete_email)
    TextView deleteEmail;
    @BindView(R.id.delete_phone_number)
    TextView deletePhoneNumber;
    @BindView(R.id.searchResultContainer)
    RelativeLayout searchResultContainer;

    Realm realm;
    private Contacts contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_contact);
        ButterKnife.bind(this);

        Realm.init(this);
        realm = Realm.getDefaultInstance();
    }

    @OnClick({R.id.search_btn, R.id.delete_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search_btn:
                String search_email = searchEmailAddress.getText().toString();
                if (!search_email.isEmpty())
                    searchForContact(search_email);
                else
                    showToast("Please enter user email address");
                break;
            case R.id.delete_btn:
                showConfirmationDialog();
                break;
        }
    }

    private void showConfirmationDialog() {
        String contactName = contact.getFirstName()+" "+contact.getLastName();

        final AlertDialog.Builder alterDialogBuilder = new AlertDialog.Builder(this);

        alterDialogBuilder.setTitle("CONFIRM ACTION");
        alterDialogBuilder.setMessage("Do you want to delete contact \""+contactName+"\" ?");
        alterDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                realm.beginTransaction();
                RealmObject.deleteFromRealm(contact);
                realm.commitTransaction();

                showToast("Contact Deleted");
                finish();
            }
        });
        alterDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Don't do anything
            }
        });

         AlertDialog alertDialog = alterDialogBuilder.create();
        alertDialog.show();
    }

    private void searchForContact(String search_email) {
        realm.beginTransaction();

        contact = realm.where(Contacts.class).equalTo("email", search_email).findFirst();

        realm.commitTransaction();

        if (contact == null)
            showToast("No contact with such email found");
        else
            showContactDetails(contact.getEmail(), contact.getFirstName(), contact.getLastName(), contact.getPhoneNumber());
    }

    private void showContactDetails(String email, String firstName, String lastName, String phoneNumber) {

        deleteFirstName.setText(firstName);
        deleteLastName.setText(lastName);
        deleteEmail.setText(email);
        deletePhoneNumber.setText(phoneNumber);

        searchResultContainer.setVisibility(View.VISIBLE);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}