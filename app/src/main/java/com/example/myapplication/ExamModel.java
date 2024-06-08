// ExamModel.java
package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

public class ExamModel implements Parcelable {
    private String examName;
    private String associatedClass;
    private Calendar examDateTime;
    public ExamModel(String examName, String associatedClass, Calendar examDateTime) {
        this.examName = examName;
        this.associatedClass = associatedClass;
        this.examDateTime = examDateTime;
    }

    protected ExamModel(Parcel in) {
        examName = in.readString();
        associatedClass = in.readString();
        examDateTime = (Calendar) in.readSerializable();
    }

    public static final Creator<ExamModel> CREATOR = new Creator<ExamModel>() {
        @Override
        public ExamModel createFromParcel(Parcel in) {
            return new ExamModel(in);
        }

        @Override
        public ExamModel[] newArray(int size) {
            return new ExamModel[size];
        }
    };

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getAssociatedClass() {
        return associatedClass;
    }

    public void setAssociatedClass(String associatedClass) {
        this.associatedClass = associatedClass;
    }

    public Calendar getExamDateTime() {
        return examDateTime;
    }

    public void setExamDateTime(Calendar examDateTime) {
        this.examDateTime = examDateTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(examName);
        dest.writeString(associatedClass);
        dest.writeSerializable(examDateTime);
    }
}
