package com.semanientreprise.realmcontacts;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.semanientreprise.realmcontacts.RealmClasses.Contacts;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class updateContact extends AppCompatActivity {

    @BindView(R.id.search_emailAddress)
    EditText searchEmailAddress;
    @BindView(R.id.search_nameContainer)
    LinearLayout searchNameContainer;
    @BindView(R.id.searchResultContainer)
    RelativeLayout searchResultContainer;
    Realm realm;
    @BindView(R.id.search_first_name)
    TextInputEditText searchFirstName;
    @BindView(R.id.search_last_name)
    TextInputEditText searchLastName;
    @BindView(R.id.search_email)
    TextInputLayout searchEmail;
    @BindView(R.id.search_phone_number)
    TextInputLayout searchPhoneNumber;
    private Contacts contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contact);
        ButterKnife.bind(this);

        Realm.init(this);
        realm = Realm.getDefaultInstance();
    }

    @OnClick({R.id.search_btn, R.id.update_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search_btn:
                String search_email = searchEmailAddress.getText().toString();
                if (!search_email.isEmpty())
                    searchForContact(search_email);
                else
                    showToast("Please enter user email address");
                break;
            case R.id.update_btn:
                String new_email = searchEmail.getEditText().getText().toString();
                String new_firstName = searchFirstName.getText().toString();
                String new_lastName = searchLastName.getText().toString();
                String new_phoneNumber = searchPhoneNumber.getEditText().getText().toString();

                updateContactDetails(new_email, new_firstName, new_lastName, new_phoneNumber);
                break;
        }
    }

    private void searchForContact(String search_email) {
        realm.beginTransaction();

        contact = realm.where(Contacts.class).equalTo("email", search_email).findFirst();

        realm.commitTransaction();

        if (contact == null)
            showToast("No contact with such email found");
        else
            setUpContactForEditing(contact.getEmail(), contact.getFirstName(), contact.getLastName(), contact.getPhoneNumber());

    }

    private void setUpContactForEditing(String email, String firstName, String lastName, String phoneNumber) {

        searchFirstName.setText(firstName);
        searchLastName.setText(lastName);
        searchEmail.getEditText().setText(email);
        searchPhoneNumber.getEditText().setText(phoneNumber);

        searchResultContainer.setVisibility(View.VISIBLE);
    }

    private void updateContactDetails(String new_email, String new_firstName, String new_lastName, String new_phoneNumber) {
        realm.beginTransaction();

        contact.setFirstName(new_firstName);
        contact.setLastName(new_lastName);
        contact.setEmail(new_email);
        contact.setPhoneNumber(new_phoneNumber);

        realm.commitTransaction();

        showToast("One Contact Updated");

        finish();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}