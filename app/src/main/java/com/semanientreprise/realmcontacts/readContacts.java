package com.semanientreprise.realmcontacts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.semanientreprise.realmcontacts.RealmClasses.Contacts;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

public class readContacts extends AppCompatActivity {

    Realm realm;
    @BindView(R.id.contacts_recView)
    RecyclerView contactsRecView;
    private RecyclerView.LayoutManager contactsLayoutManager;
    private RecyclerView.Adapter contactsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_contacts);
        ButterKnife.bind(this);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        realm.beginTransaction();

        RealmResults<Contacts> results = realm.where(Contacts.class).findAll();

        realm.commitTransaction();

        contactsLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        contactsRecView.setLayoutManager(contactsLayoutManager);

        contactsRecView.setHasFixedSize(true);

        contactsAdapter = new ContactsAdapter(results,this);

        contactsRecView.setAdapter(contactsAdapter);
    }
}
