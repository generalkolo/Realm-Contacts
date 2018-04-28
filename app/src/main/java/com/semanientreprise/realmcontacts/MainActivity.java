package com.semanientreprise.realmcontacts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.semanientreprise.realmcontacts.RealmClasses.Contacts;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.first_name)
    EditText firstName;
    @BindView(R.id.last_name)
    EditText lastName;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.phone_number)
    EditText phoneNumber;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Realm.init(this);
        realm = Realm.getDefaultInstance();
    }

    @OnClick(R.id.save_btn)
    public void onViewClicked() {
        String first_name = firstName.getText().toString();
        String last_name = lastName.getText().toString();
        String Email = email.getText().toString();
        String phonenumber = phoneNumber.getText().toString();

        if(validRegistration(first_name,last_name,Email,phonenumber))
            SaveContacts(first_name,last_name,Email,phonenumber);
    }

    private void SaveContacts(String first_name, String last_name, String email, String phonenumber) {
        realm.beginTransaction();

        Contacts contacts = realm.createObject(Contacts.class);

        contacts.setEmail(email);
        contacts.setFirstName(first_name);
        contacts.setLastName(last_name);
        contacts.setPhoneNumber(phonenumber);

        realm.commitTransaction();

        setEmptyEditText();

        realm.beginTransaction();

        RealmResults<Contacts> contactResult = realm.where(Contacts.class).equalTo("email",email).findAll();
        showToast(contactResult.get(0).getEmail());

        realm.commitTransaction();
    }

    private void setEmptyEditText() {
        firstName.setText("");
        lastName.setText("");
        email.setText("");
        phoneNumber.setText("");
    }

    private boolean validRegistration(String firstName, String lastName, String email, String phoneNumber) {
        boolean complete_registration = false;


        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty())
            showToast("No Field can be empty!!");
        else
            complete_registration = true;

        return complete_registration;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
