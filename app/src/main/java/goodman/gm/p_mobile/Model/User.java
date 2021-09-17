package goodman.gm.p_mobile.Model;

import java.io.Serializable;

public class User implements Serializable {
    private String mFullName;
    private String mUserName;
    private String mPassword;
    private String mEmail;
    private String mPhoneNumber;
    private String mHinhAnh;

    public User() {

    }

    public User(String mFullName, String mUserName, String mPassword, String mEmail, String mPhoneNumber) {
        this.mFullName = mFullName;
        this.mUserName = mUserName;
        this.mPassword = mPassword;
        this.mEmail = mEmail;
        this.mPhoneNumber = mPhoneNumber;
    }

    public User(String mFullName, String mUserName, String mPassword, String mEmail, String mPhoneNumber, String mHinhAnh) {
        this.mFullName = mFullName;
        this.mUserName = mUserName;
        this.mPassword = mPassword;
        this.mEmail = mEmail;
        this.mPhoneNumber = mPhoneNumber;
        this.mHinhAnh = mHinhAnh;
    }

    public String getmHinhAnh() {
        return mHinhAnh;
    }

    public void setmHinhAnh(String mHinhAnh) {
        this.mHinhAnh = mHinhAnh;
    }

    public String getmFullName() {
        return mFullName;
    }

    public void setmFullName(String mFullName) {
        this.mFullName = mFullName;
    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmPhoneNumber() {
        return mPhoneNumber;
    }

    public void setmPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "mFullName='" + mFullName + '\'' +
                ", mUserName='" + mUserName + '\'' +
                ", mPassword='" + mPassword + '\'' +
                ", mEmail='" + mEmail + '\'' +
                ", mPhoneNumber='" + mPhoneNumber + '\'' +
                '}';
    }
}
