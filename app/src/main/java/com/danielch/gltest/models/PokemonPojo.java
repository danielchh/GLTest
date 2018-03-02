package com.danielch.gltest.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PokemonPojo implements Parcelable {
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("name")
    @Expose
    private String name;

    public PokemonPojo(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        String[] urlSplit = url.split("/");
        return Integer.parseInt(urlSplit[urlSplit.length - 1]);
    }


    private PokemonPojo(Parcel in) {
        url = in.readString();
        name = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(name);
    }

    @SuppressWarnings("unused")
    public static final Creator<PokemonPojo> CREATOR = new Creator<PokemonPojo>() {
        @Override
        public PokemonPojo createFromParcel(Parcel in) {
            return new PokemonPojo(in);
        }

        @Override
        public PokemonPojo[] newArray(int size) {
            return new PokemonPojo[size];
        }
    };
}