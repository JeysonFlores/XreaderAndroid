package site.xreader.xreaderandroid.models;

import java.util.ArrayList;

import site.xreader.xreaderandroid.services.InternalDbHelper;

public class User {

    private ArrayList<Integer> favorites;
    private String username;

    public User(String username, ArrayList<Integer> favorites) {
        this.favorites = favorites;
        this.username = username;
    }

    public User(String username) {
        this.username = username;
        this.favorites = new ArrayList<>();
    }

    public void updateFavorites(InternalDbHelper internalStorage) {
        this.favorites = internalStorage.getFavoritesFromUser(username);
    }

    public ArrayList<Integer> getFavorites() {
        return favorites;
    }

    public String getUsername() {
        return username;
    }
}
