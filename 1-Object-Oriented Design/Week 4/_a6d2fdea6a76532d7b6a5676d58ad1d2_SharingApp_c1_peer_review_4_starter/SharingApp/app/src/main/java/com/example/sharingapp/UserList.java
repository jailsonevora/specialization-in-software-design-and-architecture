package com.example.sharingapp;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

//import static android.provider.Telephony.Mms.Part.FILENAME;

/**
 * Created by jevora on 11/19/2017.
 */

public class UserList {

    private static ArrayList<User> users;
    private String FILENAME = "user_file.sav";

    public UserList() {
        users = new ArrayList<User>();
    }

    public void loadUsers(Context context) {
        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<User>>() {}.getType();
            users = gson.fromJson(isr, listType); // temporary
            fis.close();
        } catch (FileNotFoundException e) {
            users = new ArrayList<User>();
        } catch (IOException e) {
            users = new ArrayList<User>();
        }
    }

    public boolean isUsernameAvailable(String username_str) {
        for (User i: users)
            if (i.getUsername().equals(username_str)) return false;
        return true;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void saveUsers(Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(users, osw);
            osw.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public boolean hasUser(User user) {
        for (User i : users) {
            if (user.getId().equals(i.getId())) {
                return true;
            }
        }
        return false;
    }

    public int getIndex(User user) {
        int pos = 0;
        for (User i : users) {
            if (user.getId().equals(i.getId())) {
                return pos;
            }
            pos = pos+1;
        }
        return -1;
    }

    public User getUser(int pos) {
        return users.get(pos);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public int getSize() {
        return users.size();
    }

    public ArrayList<String> getAllUsernames() {
        ArrayList<String> username = new ArrayList<String>();
        for (User i : users)
            username.add(i.getUsername());
        return username;
    }

    public User getUserByUsername(String borrower_str) {
        for (User i: users)
            if (i.getUsername().equals(borrower_str)) return i;
        return null;
    }
}
