//ClassModel.java
package com.example.myapplication;
import android.os.Parcel;
import android.os.Parcelable;
public class ClassModel implements Parcelable {
    private String className;
    private String classTimeAndDay;
    private String classInstructor;

    public ClassModel(String className, String classTimeAndDay, String classInstructor) {
        this.className = className;
        this.classTimeAndDay = classTimeAndDay;
        this.classInstructor = classInstructor;
    }

    protected ClassModel(Parcel in) {
        className = in.readString();
        classTimeAndDay = in.readString();
        classInstructor = in.readString();
    }
    public static final Creator<ClassModel> CREATOR = new Creator<ClassModel>() {
        @Override
        public ClassModel createFromParcel(Parcel in) {
            return new ClassModel(in);
        }

        @Override
        public ClassModel[] newArray(int size) {
            return new ClassModel[size];
        }
    };

    public String getClassName() {
        return className;
    }

    public String getClassTimeAndDay() {
        return classTimeAndDay;
    }

    public String getClassInstructor() {
        return classInstructor;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setClassTimeAndDay(String classTimeAndDay) {
        this.classTimeAndDay = classTimeAndDay;
    }
    public void setClassInstructor(String classInstructor) {
        this.classInstructor = classInstructor;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(className);
        dest.writeString(classTimeAndDay);
        dest.writeString(classInstructor);
    }
}
