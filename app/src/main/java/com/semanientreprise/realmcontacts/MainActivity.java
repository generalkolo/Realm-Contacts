package com.semanientreprise.realmcontacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.addContact, R.id.readContact, R.id.updateContact, R.id.deleteContact})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.addContact:
                startActivity(new Intent(this,addContact.class));
                break;
            case R.id.readContact:
                startActivity(new Intent(this,readContacts.class));
                break;
            case R.id.updateContact:
                startActivity(new Intent(this,updateContact.class));
                break;
            case R.id.deleteContact:
                break;
        }
    }
}
