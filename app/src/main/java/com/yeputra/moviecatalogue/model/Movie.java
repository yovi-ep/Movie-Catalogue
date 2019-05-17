package com.yeputra.moviecatalogue.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private int icon;
    private String title;
    private String description;
    private String date;

    public Movie(int icon, String title, String description, String date) {
        this.icon = icon;
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public int getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    private Movie(Parcel in) {
        icon = in.readInt();
        title = in.readString();
        description = in.readString();
        date = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(icon);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(date);
    }
}
