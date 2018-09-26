package com.shine.indoormap.base.data.initalize;

import com.shine.indoormap.base.data.initalize.BuilDingEntity;

import java.util.List;

public class Initalize {


    private String result_code;
    private String result_desc;

    public List<resultDataEntity> getResult_data() {
        return result_data;
    }

    public void setResult_data(List<resultDataEntity> result_data) {
        this.result_data = result_data;
    }

    private java.util.List<resultDataEntity> result_data;

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

    public static class resultDataEntity {

        public List<FacilitiesDataEntity> getFacilitiesData() {
            return FacilitiesData;
        }

        public void setFacilitiesData(List<FacilitiesDataEntity> facilitiesData) {
            FacilitiesData = facilitiesData;
        }

        /**
         * Area : {"Area_ID":"11","Area_Name":"南院横屏"}
         * BuilDing : [{"BuilDing_ID":"1","Floor":[{"Floor_ID":"1178","Floor_Imager_URL":"/Upload/Floor/201805301746507223.png","Floor_Name":"1"},{"Floor_ID":"1180","Floor_Imager_URL":"/Upload/Floor/201805301746453350.png","Floor_Name":"2"},{"Floor_ID":"1181","Floor_Imager_URL":"","Floor_Name":"3F"},{"Floor_ID":"1182","Floor_Imager_URL":"","Floor_Name":"4F"},{"Floor_ID":"1183","Floor_Imager_URL":"/Upload/Floor/201805310946527860.png","Floor_Name":"5F"}],"Max_Floor":"5","Min_Floor":"1","Name":"门诊楼","X":"122","Y":"279"}]
         * Current : {"BuilDing_ID":"1","BuilDing_Name":"门诊楼","Floor_Coordinate_ID":"314","Floor_ID":"1183","Floor_Name":"5F"}
         * Items : {"Items_ID":"3","Items_Name":"南院"}
         * Terminal : {"Floor_Coordinate_ID":"314","IP":"125.112.9.85","MAC":"E0-28-17-05-22-45","Resolution":"1"}
         */

        private AreaEntity Area;
        private CurrentEntity Current;
        private ItemsEntity Items;
        private TerminalEntity Terminal;
        private List<BuilDingEntity> BuilDing;
        private List<FacilitiesDataEntity> FacilitiesData;

        public AreaEntity getArea() {
            return Area;
        }

        public void setArea(AreaEntity Area) {
            this.Area = Area;
        }

        public CurrentEntity getCurrent() {
            return Current;
        }

        public void setCurrent(CurrentEntity Current) {
            this.Current = Current;
        }

        public ItemsEntity getItems() {
            return Items;
        }

        public void setItems(ItemsEntity Items) {
            this.Items = Items;
        }

        public TerminalEntity getTerminal() {
            return Terminal;
        }

        public void setTerminal(TerminalEntity Terminal) {
            this.Terminal = Terminal;
        }

        public List<BuilDingEntity> getBuilDing() {
            return BuilDing;
        }

        public void setBuilDing(List<BuilDingEntity> BuilDing) {
            this.BuilDing = BuilDing;
        }

        public static class AreaEntity {
            /**
             * Area_ID : 11
             * Area_Name : 南院横屏
             */

            private String Area_ID;
            private String Area_Name;
            private String Area_Image_URL;
            private String X;
            private String Y;

            public String getX() {
                return X;
            }

            public void setX(String x) {
                X = x;
            }

            public String getY() {
                return Y;
            }

            public void setY(String y) {
                Y = y;
            }

            public String getArea_Image_URL() {
                return Area_Image_URL;
            }

            public void setArea_Image_URL(String area_Image_URL) {
                Area_Image_URL = area_Image_URL;
            }


            public String getArea_ID() {
                return Area_ID;
            }

            public void setArea_ID(String Area_ID) {
                this.Area_ID = Area_ID;
            }

            public String getArea_Name() {
                return Area_Name;
            }

            public void setArea_Name(String Area_Name) {
                this.Area_Name = Area_Name;
            }
        }

        public static class CurrentEntity {
            /**
             * BuilDing_ID : 1
             * BuilDing_Name : 门诊楼
             * Floor_Coordinate_ID : 314
             * Floor_ID : 1183
             * Floor_Name : 5F
             */

            private String BuilDing_ID;
            private String BuilDing_Name;
            private String Floor_Coordinate_ID;
            private String Floor_ID;
            private String Floor_Name;

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

            public String getFloor_Coordinate_ID() {
                return Floor_Coordinate_ID;
            }

            public void setFloor_Coordinate_ID(String Floor_Coordinate_ID) {
                this.Floor_Coordinate_ID = Floor_Coordinate_ID;
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
        }

        public static class ItemsEntity {
            /**
             * Items_ID : 3
             * Items_Name : 南院
             */

            private String Items_ID;
            private String Items_Name;

            public String getItems_ID() {
                return Items_ID;
            }

            public void setItems_ID(String Items_ID) {
                this.Items_ID = Items_ID;
            }

            public String getItems_Name() {
                return Items_Name;
            }

            public void setItems_Name(String Items_Name) {
                this.Items_Name = Items_Name;
            }
        }

        public static class TerminalEntity {
            /**
             * Floor_Coordinate_ID : 314
             * IP : 125.112.9.85
             * MAC : E0-28-17-05-22-45
             * Resolution : 1
             */

            private String Floor_Coordinate_ID;
            private String IP;
            private String MAC;
            private String Resolution;

            public int getX() {
                return X;
            }

            public void setX(int x) {
                X = x;
            }

            public int getY() {
                return Y;
            }

            public void setY(int y) {
                Y = y;
            }

            private int X;
            private int Y;

            public String getFloor_Coordinate_ID() {
                return Floor_Coordinate_ID;
            }

            public void setFloor_Coordinate_ID(String Floor_Coordinate_ID) {
                this.Floor_Coordinate_ID = Floor_Coordinate_ID;
            }

            public String getIP() {
                return IP;
            }

            public void setIP(String IP) {
                this.IP = IP;
            }

            public String getMAC() {
                return MAC;
            }

            public void setMAC(String MAC) {
                this.MAC = MAC;
            }

            public String getResolution() {
                return Resolution;
            }

            public void setResolution(String Resolution) {
                this.Resolution = Resolution;
            }
        }

        public static class BuilDingEntity {
            /**
             * BuilDing_ID : 1
             * Floor : [{"Floor_ID":"1178","Floor_Imager_URL":"/Upload/Floor/201805301746507223.png","Floor_Name":"1"},{"Floor_ID":"1180","Floor_Imager_URL":"/Upload/Floor/201805301746453350.png","Floor_Name":"2"},{"Floor_ID":"1181","Floor_Imager_URL":"","Floor_Name":"3F"},{"Floor_ID":"1182","Floor_Imager_URL":"","Floor_Name":"4F"},{"Floor_ID":"1183","Floor_Imager_URL":"/Upload/Floor/201805310946527860.png","Floor_Name":"5F"}]
             * Max_Floor : 5
             * Min_Floor : 1
             * Name : 门诊楼
             * X : 122
             * Y : 279
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
                 * Floor_ID : 1178
                 * Floor_Imager_URL : /Upload/Floor/201805301746507223.png
                 * Floor_Name : 1
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

        public static class FacilitiesDataEntity {
            private String Name;

            public String getName() {
                return Name;
            }

            public void setName(String name) {
                Name = name;
            }

            public String getLogogram() {
                return Logogram;
            }

            public void setLogogram(String logogram) {
                Logogram = logogram;
            }

            public String getImage_Url() {
                return Image_Url;
            }

            public void setImage_Url(String image_Url) {
                Image_Url = image_Url;
            }

            public String getValue() {
                return Value;
            }

            public void setValue(String value) {
                Value = value;
            }

            private String Logogram;
            private String Image_Url;
            private String Value;
        }
    }
}
