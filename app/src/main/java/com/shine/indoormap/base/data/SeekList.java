package com.shine.indoormap.base.data;

import java.util.List;

public class SeekList {

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

    /**
     * result_code : 0
     * result_desc :
     * result_data : [{"BuilDing_ID":"1","BuilDing_Name":"A栋","Floor_ID":"1","Floor_Name":"1F","Queue_ID":"1","Queue_Name":"消化内科-张三专家","Coordinate_ID":"1"},{"BuilDing_ID":"1","BuilDing_Name":"A栋","Floor_ID":"1","Floor_Name":"1F","Queue_ID":"1","Queue_Name":"消化内科-历史专家"," Coordinate_ID":"2"}]
     */


    private String result_code;
    private String result_desc;
    private java.util.List<ResultDataEntity> result_data;

    public static class ResultDataEntity {
        /**
         * BuilDing_ID : 1
         * BuilDing_Name : A栋
         * Floor_ID : 1
         * Floor_Name : 1F
         * Queue_ID : 1
         * Queue_Name : 消化内科-张三专家
         * Coordinate_ID : 1
         *  Coordinate_ID : 2
         */

        private String BuilDing_ID;
        private String BuilDing_Name;
        private String Floor_ID;
        private String Floor_Name;
        private String Queue_ID;
        private String Queue_Name;
        private String Coordinate_ID;


        public String getBuilDing_ID() {
            return BuilDing_ID;
        }

        public void setBuilDing_ID(String BuilDing_ID) {
            this.BuilDing_ID = BuilDing_ID;
        }

        public String getBuilDing_Name() {
            return BuilDing_Name;
        }

        public void setBuilDing_Name(String BuilDing_Name) {
            this.BuilDing_Name = BuilDing_Name;
        }

        public String getFloor_ID() {
            return Floor_ID;
        }

        public void setFloor_ID(String Floor_ID) {
            this.Floor_ID = Floor_ID;
        }

        public String getFloor_Name() {
            return Floor_Name;
        }

        public void setFloor_Name(String Floor_Name) {
            this.Floor_Name = Floor_Name;
        }

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

        public String getCoordinate_ID() {
            return Coordinate_ID;
        }

        public void setCoordinate_ID(String Coordinate_ID) {
            this.Coordinate_ID = Coordinate_ID;
        }
    }
}
