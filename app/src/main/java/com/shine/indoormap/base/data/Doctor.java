package com.shine.indoormap.base.data;

import java.util.List;

public class Doctor {

    /**
     * result_code : 0
     * result_desc :
     * result_data : [{"Queue_ID":"1","Queue_Name":"呼吸内科","Doctor_Name":"张三","Title":"主任医师","Login":"A001","Visit_Time":"周一上午,周二上午,周三全天","Speciality":"特长","Introduction":"简介","Image_URL":"http://ip:8085/Doctor/A001.jpg","Whether_Visit":"0","Whether_Connect":"0","Coordinate_ID ":"1"},{"Queue_ID":"1","Queue_Name":"呼吸内科","Doctor_Name":"李四","Title":"主任医师","Login":"A001","Visit_Time":"周一上午,周二上午,周三全天","Speciality":"特长","Introduction":"简介","Image_URL":"http://ip:8085/Doctor/A001.jpg","Whether_Visit":"1","Whether_Connect":"1","Coordinate_ID ":"2"}]
     */

    private String result_code;
    private String result_desc;
    private List<ResultDataEntity> result_data;

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getResult_desc() {
        return result_desc;
    }

    public void setResult_desc(String result_desc) {
        this.result_desc = result_desc;
    }

    public List<ResultDataEntity> getResult_data() {
        return result_data;
    }

    public void setResult_data(List<ResultDataEntity> result_data) {
        this.result_data = result_data;
    }

    public static class ResultDataEntity {
        /**
         * Queue_ID : 1
         * Queue_Name : 呼吸内科
         * Doctor_Name : 张三
         * Title : 主任医师
         * Login : A001
         * Visit_Time : 周一上午,周二上午,周三全天
         * Speciality : 特长
         * Introduction : 简介
         * Image_URL : http://ip:8085/Doctor/A001.jpg
         * Whether_Visit : 0
         * Whether_Connect : 0
         * Coordinate_ID  : 1
         */

        private String Queue_ID;
        private String Queue_Name;
        private String Doctor_Name;
        private String Title;
        private String Login;
        private String Visit_Time;
        private String Speciality;
        private String Introduction;
        private String Image_URL;
        private String Whether_Visit;
        private String Whether_Connect;
        private String Coordinate_ID;

        public String getQueue_ID() {
            return Queue_ID;
        }

        public void setQueue_ID(String Queue_ID) {
            this.Queue_ID = Queue_ID;
        }

        public String getQueue_Name() {
            return Queue_Name;
        }

        public void setQueue_Name(String Queue_Name) {
            this.Queue_Name = Queue_Name;
        }

        public String getDoctor_Name() {
            return Doctor_Name;
        }

        public void setDoctor_Name(String Doctor_Name) {
            this.Doctor_Name = Doctor_Name;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getLogin() {
            return Login;
        }

        public void setLogin(String Login) {
            this.Login = Login;
        }

        public String getVisit_Time() {
            return Visit_Time;
        }

        public void setVisit_Time(String Visit_Time) {
            this.Visit_Time = Visit_Time;
        }

        public String getSpeciality() {
            return Speciality;
        }

        public void setSpeciality(String Speciality) {
            this.Speciality = Speciality;
        }

        public String getIntroduction() {
            return Introduction;
        }

        public void setIntroduction(String Introduction) {
            this.Introduction = Introduction;
        }

        public String getImage_URL() {
            return Image_URL;
        }

        public void setImage_URL(String Image_URL) {
            this.Image_URL = Image_URL;
        }

        public String getWhether_Visit() {
            return Whether_Visit;
        }

        public void setWhether_Visit(String Whether_Visit) {
            this.Whether_Visit = Whether_Visit;
        }

        public String getWhether_Connect() {
            return Whether_Connect;
        }

        public void setWhether_Connect(String Whether_Connect) {
            this.Whether_Connect = Whether_Connect;
        }

        public String getCoordinate_ID() {
            return Coordinate_ID;
        }

        public void setCoordinate_ID(String Coordinate_ID) {
            this.Coordinate_ID = Coordinate_ID;
        }
    }
}
