//AssignmentModel.java
package com.example.myapplication;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class AssignmentModel implements Parcelable {
    private String assignmentName;
    private String associatedClass;
    private Date dueDate;


    public AssignmentModel(String assignmentName, String associatedClass, Date dueDate) {
        this.assignmentName = assignmentName;
        this.associatedClass = associatedClass;
        this.dueDate = dueDate;
    }

    protected AssignmentModel(Parcel in) {
        assignmentName = in.readString();
        associatedClass = in.readString();
        String dueDateString = in.readString();
        if (dueDateString != null) {
            dueDate = new Date(Long.parseLong(dueDateString));
        }
    }

    public static final Creator<AssignmentModel> CREATOR = new Creator<AssignmentModel>() {
        @Override
        public AssignmentModel createFromParcel(Parcel in) {
            return new AssignmentModel(in);
        }

        @Override
        public AssignmentModel[] newArray(int size) {
            return new AssignmentModel[size];
        }
    };

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public String getAssociatedClass() {
        return associatedClass;
    }

    public void setAssociatedClass(String associatedClass) {
        this.associatedClass = associatedClass;
    }
    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(assignmentName);
        dest.writeString(associatedClass);
        dest.writeString(dueDate != null ? String.valueOf(dueDate.getTime()) : null);
    }
}
