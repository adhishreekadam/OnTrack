//ToDoModel.java
package com.example.myapplication;
import android.os.Parcel;
import android.os.Parcelable;

public class ToDoModel implements Parcelable {
    private String task;
    private boolean isChecked;

    public ToDoModel(String task) {
        this.task = task;
        this.isChecked = false;
    }

    protected ToDoModel(Parcel in) {
        task = in.readString();
        isChecked = in.readByte() != 0;
    }

    public static final Creator<ToDoModel> CREATOR = new Creator<ToDoModel>() {
        @Override
        public ToDoModel createFromParcel(Parcel in) {
            return new ToDoModel(in);
        }

        @Override
        public ToDoModel[] newArray(int size) {
            return new ToDoModel[size];
        }
    };

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        this.isChecked = checked;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(task);
        dest.writeByte((byte) (isChecked ? 1 : 0));
    }
}
