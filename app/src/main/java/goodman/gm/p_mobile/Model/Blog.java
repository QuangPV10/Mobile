package goodman.gm.p_mobile.Model;

import java.io.Serializable;

public class Blog implements Serializable {
    private String mTieuDe;
    private String mHinhAnh;
    private String mNoiDung;
    private String mTenQuan;
    private String mNgayCapNhat;
    private Double mPoint;
    private String mMaBlog;

    public Blog() {

    }

    public Blog(String mTieuDe, String mHinhAnh, String mNoiDung, String mTenQuan, String mNgayCapNhat, Double mPoint) {
        this.mTieuDe = mTieuDe;
        this.mHinhAnh = mHinhAnh;
        this.mNoiDung = mNoiDung;
        this.mTenQuan = mTenQuan;
        this.mNgayCapNhat = mNgayCapNhat;
        this.mPoint = mPoint;
    }

    public String getmTieuDe() {
        return mTieuDe;
    }

    public void setmTieuDe(String mTieuDe) {
        this.mTieuDe = mTieuDe;
    }

    public String getmHinhAnh() {
        return mHinhAnh;
    }

    public void setmHinhAnh(String mHinhAnh) {
        this.mHinhAnh = mHinhAnh;
    }

    public String getmNoiDung() {
        return mNoiDung;
    }

    public void setmNoiDung(String mNoiDung) {
        this.mNoiDung = mNoiDung;
    }

    public String getmTenQuan() {
        return mTenQuan;
    }

    public void setmTenQuan(String mTenQuan) {
        this.mTenQuan = mTenQuan;
    }

    public String getmNgayCapNhat() {
        return mNgayCapNhat;
    }

    public void setmNgayCapNhat(String mNgayCapNhat) {
        this.mNgayCapNhat = mNgayCapNhat;
    }

    public Double getmPoint() {
        return mPoint;
    }

    public void setmPoint(Double mPoint) {
        this.mPoint = mPoint;
    }

    public String getmMaBlog() {
        return mMaBlog;
    }

    public void setmMaBlog(String mMaBlog) {
        this.mMaBlog = mMaBlog;
    }


}
