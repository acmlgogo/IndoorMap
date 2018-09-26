package com.shine.indoormap.base.data.initalize;

import java.util.List;

class BuilDingEntity {

    /**
     * BuilDing_ID : 2
     * Floor : [{"Floor_ID":"1","Floor_Imager_URL":"http://IP:8085/Floor/2.png","Floor_Name":"1F"},{"Floor_ID":"2","Floor_Imager_URL":"http://IP:8085/Floor/2.png","Floor_Name":"1F"},{"Floor_ID":"3","Floor_Imager_URL":"http://IP:8085/Floor/3.png","Floor_Name":"3F"},{"Floor_ID":"4","Floor_Imager_URL":"http://IP:8085/Floor/4.png","Floor_Name":"4F"}]
     * Max_Floor : 10
     * Min_Floor : -5
     * Name : B栋
     * X : 300
     * Y : 200
     */
    private String BuilDing_ID;
    private String Max_Floor;
    private String Min_Floor;
    private String Name;
    private String X;
    private String Y;
    private List<FloorEntity> Floor;

    public String getBuilDing_ID() {
        return BuilDing_ID;
    }

    public void setBuilDing_ID(String BuilDing_ID) {
        this.BuilDing_ID = BuilDing_ID;
    }

    public String getMax_Floor() {
        return Max_Floor;
    }

    public void setMax_Floor(String Max_Floor) {
        this.Max_Floor = Max_Floor;
    }

    public String getMin_Floor() {
        return Min_Floor;
    }

    public void setMin_Floor(String Min_Floor) {
        this.Min_Floor = Min_Floor;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getX() {
        return X;
    }

    public void setX(String X) {
        this.X = X;
    }

    public String getY() {
        return Y;
    }

    public void setY(String Y) {
        this.Y = Y;
    }

    public List<FloorEntity> getFloor() {
        return Floor;
    }

    public void setFloor(List<FloorEntity> Floor) {
        this.Floor = Floor;
    }

    public static class FloorEntity {
        /**
         * Floor_ID : 1
         * Floor_Imager_URL : http://IP:8085/Floor/2.png
         * Floor_Name : 1F
         */

        private String Floor_ID;
        private String Floor_Imager_URL;
        private String Floor_Name;

        public String getFloor_ID() {
            return Floor_ID;
        }

        public void setFloor_ID(String Floor_ID) {
            this.Floor_ID = Floor_ID;
        }

        public String getFloor_Imager_URL() {
            return Floor_Imager_URL;
        }

        public void setFloor_Imager_URL(String Floor_Imager_URL) {
            this.Floor_Imager_URL = Floor_Imager_URL;
        }

        public String getFloor_Name() {
            return Floor_Name;
        }

        public void setFloor_Name(String Floor_Name) {
            this.Floor_Name = Floor_Name;
        }
    }
}
