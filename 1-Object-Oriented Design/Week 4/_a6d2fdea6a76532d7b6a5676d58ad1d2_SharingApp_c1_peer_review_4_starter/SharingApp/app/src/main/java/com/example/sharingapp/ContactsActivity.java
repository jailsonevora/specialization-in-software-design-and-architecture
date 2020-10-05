package com.example.sharingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Displays a list of all contacts
 */
public class ContactsActivity extends AppCompatActivity {

    private UserList user_list = new UserList();
    private ListView my_contacts;
    private ArrayAdapter<User> adapter;
    private Context context;
    private ItemList item_list = new ItemList();
    private UserList active_borrowers_list = new UserList();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        context = getApplicationContext();
        user_list.loadUsers(context);
        item_list.loadItems(context);

        my_contacts = (ListView) findViewById(R.id.my_contacts);
        adapter = new UserAdapter(ContactsActivity.this, user_list.getUsers());
        my_contacts.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        // When user is long clicked, this starts EditUserActivity
        my_contacts.setOnItemLongClickListener(new android.widget.AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int pos, long id) {

                User user = adapter.getItem(pos);

                ArrayList<User> active_borrowers = item_list.getActiveBorrowers();
                active_borrowers_list.setUsers(active_borrowers);

                // Prevent user from editing an "active" borrower.
                if (active_borrowers_list != null) {
                    if (active_borrowers_list.hasUser(user)) {
                        CharSequence text = "Cannot edit or delete active borrower!";
                        int duration = Toast.LENGTH_SHORT;
                        Toast.makeText(context, text, duration).show();
                        return true;
                    }
                }

                user_list.loadUsers(context); // must load users again here
                int meta_pos = user_list.getIndex(user);

                Intent intent = new Intent(context, EditUserActivity.class);
                intent.putExtra("position", meta_pos);
                startActivity(intent);

                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        context = getApplicationContext();
        user_list.loadUsers(context);

        my_contacts = (ListView) findViewById(R.id.my_contacts);
        adapter = new UserAdapter(ContactsActivity.this, user_list.getUsers());
        my_contacts.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void addUserActivity(View view){
        Intent intent = new Intent(this, AddUserActivity.class);
        startActivity(intent);
    }
}

