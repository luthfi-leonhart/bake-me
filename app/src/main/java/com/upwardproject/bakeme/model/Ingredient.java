package com.upwardproject.bakeme.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Dark on 02/09/2017.
 */

public class Ingredient implements Parcelable {
    private String name;
    private int quantity;
    private String measure;

    public Ingredient(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public Ingredient setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getMeasure() {
        return measure;
    }

    public Ingredient setMeasure(String measure) {
        this.measure = measure;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.quantity);
        dest.writeString(this.measure);
    }

    protected Ingredient(Parcel in) {
        this.name = in.readString();
        this.quantity = in.readInt();
        this.measure = in.readString();
    }

    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}
