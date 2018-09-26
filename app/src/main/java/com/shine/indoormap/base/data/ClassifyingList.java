package com.shine.indoormap.base.data;

import java.util.List;

public class ClassifyingList {

    /**
     * result_code : 0
     * result_desc :
     * result_data : [{"Classifying_ID":"3","Display_Name":"内科"}]
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
         * Classifying_ID : 3
         * Display_Name : 内科
         */

        private String Classifying_ID;
        private String Display_Name;

        public String getClassifying_ID() {
            return Classifying_ID;
        }

        public void setClassifying_ID(String Classifying_ID) {
            this.Classifying_ID = Classifying_ID;
        }

        public String getDisplay_Name() {
            return Display_Name;
        }

        public void setDisplay_Name(String Display_Name) {
            this.Display_Name = Display_Name;
        }
    }
}
