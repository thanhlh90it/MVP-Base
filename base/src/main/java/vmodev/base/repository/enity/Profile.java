package vmodev.base.repository.enity;

import java.util.List;

/**
 * Created by thanhle on 3/21/17.
 */

public class Profile extends BaseEnity{

    private List<ProfileBean> Table;
    private List<?> Table1;

    public List<ProfileBean> getTable() {
        return Table;
    }

    public void setTable(List<ProfileBean> Table) {
        this.Table = Table;
    }

    public List<?> getTable1() {
        return Table1;
    }

    public void setTable1(List<?> Table1) {
        this.Table1 = Table1;
    }

    public static class ProfileBean {
        /**
         * AD_ID : 1000
         * PO_ID : 1000
         * AD_EMAIL : longdg@gmai.com
         * AD_FIRSTNAME : Long
         * AD_LASTNAME : Do
         * AD_AVATAR :
         * AD_PHONE : 0000000
         * AD_GROUP : 1000
         */

        private int AD_ID;
        private int PO_ID;
        private String AD_EMAIL;
        private String AD_FIRSTNAME;
        private String AD_LASTNAME;
        private String AD_AVATAR;
        private String AD_PHONE;
        private int AD_GROUP;

        public int getAD_ID() {
            return AD_ID;
        }

        public void setAD_ID(int AD_ID) {
            this.AD_ID = AD_ID;
        }

        public int getPO_ID() {
            return PO_ID;
        }

        public void setPO_ID(int PO_ID) {
            this.PO_ID = PO_ID;
        }

        public String getAD_EMAIL() {
            return AD_EMAIL;
        }

        public void setAD_EMAIL(String AD_EMAIL) {
            this.AD_EMAIL = AD_EMAIL;
        }

        public String getAD_FIRSTNAME() {
            return AD_FIRSTNAME;
        }

        public void setAD_FIRSTNAME(String AD_FIRSTNAME) {
            this.AD_FIRSTNAME = AD_FIRSTNAME;
        }

        public String getAD_LASTNAME() {
            return AD_LASTNAME;
        }

        public void setAD_LASTNAME(String AD_LASTNAME) {
            this.AD_LASTNAME = AD_LASTNAME;
        }

        public String getAD_AVATAR() {
            return AD_AVATAR;
        }

        public void setAD_AVATAR(String AD_AVATAR) {
            this.AD_AVATAR = AD_AVATAR;
        }

        public String getAD_PHONE() {
            return AD_PHONE;
        }

        public void setAD_PHONE(String AD_PHONE) {
            this.AD_PHONE = AD_PHONE;
        }

        public int getAD_GROUP() {
            return AD_GROUP;
        }

        public void setAD_GROUP(int AD_GROUP) {
            this.AD_GROUP = AD_GROUP;
        }
    }
}
