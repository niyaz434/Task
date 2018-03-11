package com.example.mohamedniyaz.volley;


import android.os.Parcel;
import android.os.Parcelable;

public class Data implements Parcelable {

    int personId;
    String name;
    String skill;
    String image;

    public Data() {
    }

    public Data(int id, String name, String skill) {
        this.personId = id;
        this.name = name;
        this.skill = skill;
    }
    private Data(Parcel in) {
        personId = in.readInt();
        name = in.readString();
        skill = in.readString();
        image = in.readString();

    }

    public Data(int id, String name, String skill, String image) {
        this.personId = id;
        this.name = name;
        this.skill = skill;
        this.image = image;
    }

    public Data(int id) {
        this.personId =id;
    }


    public int getId() {
        return personId;
    }

    public void setId(int id) {
        this.personId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {


        dest.writeInt(personId);
        dest.writeString(name);
        dest.writeString(skill);
        dest.writeString(image);

    }

    public static final Parcelable.Creator<Data> CREATOR = new Parcelable.Creator<Data>() {
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        public Data[] newArray(int size) {
            return new Data[size];

        }
    };
}
