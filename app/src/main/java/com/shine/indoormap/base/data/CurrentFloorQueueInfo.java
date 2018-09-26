package com.shine.indoormap.base.data;

import java.util.List;

public class CurrentFloorQueueInfo {

    /**
     * result_code : 0
     * result_data : [{"Coordinate_ID":"24","Queue_ID":"5","Queue_Name":"儿科二","X":553,"Y":224},{"Coordinate_ID":"51","Queue_ID":"7","Queue_Name":"儿科四","X":445,"Y":219}]
     * result_desc :
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
         * Coordinate_ID : 24
         * Queue_ID : 5
         * Queue_Name : 儿科二
         * X : 553
         * Y : 224
         */

        private String Coordinate_ID;
        private String Queue_ID;
        private String Queue_Name;
        private int X;
        private int Y;

        public String getCoordinate_ID() {
            return Coordinate_ID;
        }

        public void setCoordinate_ID(String Coordinate_ID) {
            this.Coordinate_ID = Coordinate_ID;
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

        public int getX() {
            return X;
        }

        public void setX(int X) {
            this.X = X;
        }

        public int getY() {
            return Y;
        }

        public void setY(int Y) {
            this.Y = Y;
        }
    }
}
