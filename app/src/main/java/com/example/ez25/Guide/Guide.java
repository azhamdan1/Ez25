package com.example.ez25.Guide;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Guide implements Parcelable {
    private String photo;
    private String tittle;

    public Guide() {
    }

    public Guide(String photo, String tittle, String description, String price, double rating, String type, String productID) {
        this.photo = photo;
        this.tittle = tittle;

    }

    public static final Creator<Guide> CREATOR = new Creator<Guide>() {
        @Override
        public Guide createFromParcel(Parcel in) {
            return new Guide(in);
        }

        @Override
        public Guide[] newArray(int size) {
            return new Guide[size];
        }
    };

    @Override
    public String toString() {
        return "Guide{" +
                "photo='" + photo + '\'' +
                ", tittle='" + tittle + '\'' +
                '}';
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    protected Guide(@NonNull Parcel in) {
        photo = in.readString();
        tittle = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(photo);
        dest.writeString(tittle);
    }
}
