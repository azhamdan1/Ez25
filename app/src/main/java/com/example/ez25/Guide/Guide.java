package com.example.ez25.Guide;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Guide implements Parcelable {
    private String name;
    private String imageUrl;
    private String description;

    public Guide() {
    }
    public Guide(String imageUrl, String name, String description) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;

    }
    protected Guide(Parcel in) {
        name = in.readString();
        description = in.readString();
        imageUrl = in.readString();
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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(imageUrl);
        dest.writeString(description);
    }

    public Guide(String name, String imageUrl) {
            this.name = name;
            this.imageUrl = imageUrl;
        }

        public String getName() {
            return name;
        }

        public String getImageUrl() {
            return imageUrl;
        }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Guide{" +
                "name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}


