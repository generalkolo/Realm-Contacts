package com.semanientreprise.realmcontacts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.semanientreprise.realmcontacts.RealmClasses.Contacts;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by GeneralKolo on 2018/03/21.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {

    private List<Contacts> arrayList;
    private Context context;

    public ContactsAdapter(List<Contacts> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_contact_layout, null);
        final ContactsViewHolder contactsViewHolder = new ContactsViewHolder(view);

        return contactsViewHolder;
    }

    @Override
    public void onBindViewHolder(ContactsViewHolder holder, int position) {
        holder.firstName.setText(arrayList.get(position).getFirstName());
        holder.lastName.setText(arrayList.get(position).getLastName());
        holder.phoneNumber.setText(arrayList.get(position).getPhoneNumber());
        holder.email.setText(arrayList.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ContactsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.first_name) TextView firstName;
        @BindView(R.id.last_name) TextView lastName;
        @BindView(R.id.phonenumber) TextView phoneNumber;
        @BindView(R.id.email) TextView email;

        public ContactsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
