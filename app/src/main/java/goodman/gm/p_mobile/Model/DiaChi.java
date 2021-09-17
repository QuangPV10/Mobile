package goodman.gm.p_mobile.Model;


import java.io.Serializable;

public class DiaChi implements Serializable {
    private String mTenQuanAn;
    private String mDiaChi;
    private double mLatitue;
    private double mLongitue;
    private double mKhoangCach;
    private String mMaQuanAn;

    public DiaChi() {

    }

    public DiaChi(String mTenQuanAn, String mDiaChi, double mLatitue, double mLongitue,  String mMaQuanAn) {
        this.mTenQuanAn = mTenQuanAn;
        this.mDiaChi = mDiaChi;
        this.mLatitue = mLatitue;
        this.mLongitue = mLongitue;
        this.mMaQuanAn = mMaQuanAn;
    }

    public DiaChi(String mTenQuanAn, String mDiaChi, double mLatitue, double mLongitue, double mKhoangCach, String mMaQuanAn) {
        this.mTenQuanAn = mTenQuanAn;
        this.mDiaChi = mDiaChi;
        this.mLatitue = mLatitue;
        this.mLongitue = mLongitue;
        this.mKhoangCach = mKhoangCach;
        this.mMaQuanAn = mMaQuanAn;
    }

    public String getmTenQuanAn() {
        return mTenQuanAn;
    }

    public void setmTenQuanAn(String mTenQuanAn) {
        this.mTenQuanAn = mTenQuanAn;
    }

    public String getmDiaChi() {
        return mDiaChi;
    }

    public void setmDiaChi(String mDiaChi) {
        this.mDiaChi = mDiaChi;
    }

    public double getmLatitue() {
        return mLatitue;
    }

    public void setmLatitue(double mLatitue) {
        this.mLatitue = mLatitue;
    }

    public double getmLongitue() {
        return mLongitue;
    }

    public void setmLongitue(double mLongitue) {
        this.mLongitue = mLongitue;
    }

    public double getmKhoangCach() {
        return mKhoangCach;
    }

    public void setmKhoangCach(double mKhoangCach) {
        this.mKhoangCach = mKhoangCach;
    }

    public String getmMaQuanAn() {
        return mMaQuanAn;
    }

    public void setmMaQuanAn(String mMaQuanAn) {
        this.mMaQuanAn = mMaQuanAn;
    }

}