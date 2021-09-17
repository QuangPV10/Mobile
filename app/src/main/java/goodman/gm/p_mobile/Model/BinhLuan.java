package goodman.gm.p_mobile.Model;


import java.io.Serializable;
import java.util.List;

public class BinhLuan implements Serializable {
    private String mChamDiem;
    private String mLuotThich;
    private String mNoiDung;
    private String mTieuDe;
    private String manbinhluan;
    private String tenuser;
    private List<String> hinhanhBinhLuanList;


    public BinhLuan() {

    }

    public BinhLuan(String mChamDiem, String mLuotThich, String mNoiDung, String mTieuDe) {
        this.mChamDiem = mChamDiem;
        this.mLuotThich = mLuotThich;
        this.mNoiDung = mNoiDung;
        this.mTieuDe = mTieuDe;
    }

    public String getTenuser() {
        return tenuser;
    }

    public void setTenuser(String tenuser) {
        this.tenuser = tenuser;
    }


    public List<String> getHinhanhBinhLuanList() {
        return hinhanhBinhLuanList;
    }

    public void setHinhanhBinhLuanList(List<String> hinhanhList) {
        this.hinhanhBinhLuanList = hinhanhList;
    }

    public String getManbinhluan() {
        return manbinhluan;
    }

    public void setManbinhluan(String manbinhluan) {
        this.manbinhluan = manbinhluan;
    }


    public String getmChamDiem() {
        return mChamDiem;
    }

    public void setmChamDiem(String mChamDiem) {
        this.mChamDiem = mChamDiem;
    }

    public String getmLuotThich() {
        return mLuotThich;
    }

    public void setmLuotThich(String mLuotThich) {
        this.mLuotThich = mLuotThich;
    }

    public String getmNoiDung() {
        return mNoiDung;
    }

    public void setmNoiDung(String mNoiDung) {
        this.mNoiDung = mNoiDung;
    }

    public String getmTieuDe() {
        return mTieuDe;
    }

    public void setmTieuDe(String mTieuDe) {
        this.mTieuDe = mTieuDe;
    }

}
