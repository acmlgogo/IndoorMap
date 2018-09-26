package com.shine.indoormap.base.data;

import java.util.List;

public class WayData {

    /**
     * result_code : 0
     * result_desc : 获取行走方式成功
     * result_data : {"BackType":"WalkMethod","WalkMethodOneData":{"StartPosition":"内科楼16F","EndPosition":"内科楼10F","WalkData":[{"Type":5,"Name":"电梯口"}]},"WalkMethodTwoData":{"StartPosition":"外科楼10F","EndPosition":"外科楼9F","WalkData":[{"Type":5,"Name":"电梯口"}]},"WayPath":[{"Type":1,"Area_ID":11,"BuilDing_ID":4,"Floor_ID":1229}],"WayData":[[{"Id":398,"State":6,"X":271,"Y":94},{"Id":390,"State":1,"X":313,"Y":125},{"Id":391,"State":1,"X":626,"Y":129}]]}
     */

    private String result_code;
    private String result_desc;
    private ResultDataEntity result_data;

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

    public ResultDataEntity getResult_data() {
        return result_data;
    }

    public void setResult_data(ResultDataEntity result_data) {
        this.result_data = result_data;
    }

    public static class ResultDataEntity {
        /**
         * BackType : WalkMethod
         * WalkMethodOneData : {"StartPosition":"内科楼16F","EndPosition":"内科楼10F","WalkData":[{"Type":5,"Name":"电梯口"}]}
         * WalkMethodTwoData : {"StartPosition":"外科楼10F","EndPosition":"外科楼9F","WalkData":[{"Type":5,"Name":"电梯口"}]}
         * WayPath : [{"Type":1,"Area_ID":11,"BuilDing_ID":4,"Floor_ID":1229}]
         * WayData : [[{"Id":398,"State":6,"X":271,"Y":94},{"Id":390,"State":1,"X":313,"Y":125},{"Id":391,"State":1,"X":626,"Y":129}]]
         */

        private String BackType;
        private WalkMethodOneDataEntity WalkMethodOneData;
        private WalkMethodTwoDataEntity WalkMethodTwoData;
        private List<WayPathEntity> WayPath;
        private List<List<WayDataEntity>> WayData;

        public String getBackType() {
            return BackType;
        }

        public void setBackType(String BackType) {
            this.BackType = BackType;
        }

        public WalkMethodOneDataEntity getWalkMethodOneData() {
            return WalkMethodOneData;
        }

        public void setWalkMethodOneData(WalkMethodOneDataEntity WalkMethodOneData) {
            this.WalkMethodOneData = WalkMethodOneData;
        }

        public WalkMethodTwoDataEntity getWalkMethodTwoData() {
            return WalkMethodTwoData;
        }

        public void setWalkMethodTwoData(WalkMethodTwoDataEntity WalkMethodTwoData) {
            this.WalkMethodTwoData = WalkMethodTwoData;
        }

        public List<WayPathEntity> getWayPath() {
            return WayPath;
        }

        public void setWayPath(List<WayPathEntity> WayPath) {
            this.WayPath = WayPath;
        }

        public List<List<WayDataEntity>> getWayData() {
            return WayData;
        }

        public void setWayData(List<List<WayDataEntity>> WayData) {
            this.WayData = WayData;
        }

        public static class WalkMethodOneDataEntity {
            /**
             * StartPosition : 内科楼16F
             * EndPosition : 内科楼10F
             * WalkData : [{"Type":5,"Name":"电梯口"}]
             */

            private String StartPosition;
            private String EndPosition;
            private List<WalkDataEntity> WalkData;

            public String getStartPosition() {
                return StartPosition;
            }

            public void setStartPosition(String StartPosition) {
                this.StartPosition = StartPosition;
            }

            public String getEndPosition() {
                return EndPosition;
            }

            public void setEndPosition(String EndPosition) {
                this.EndPosition = EndPosition;
            }

            public List<WalkDataEntity> getWalkData() {
                return WalkData;
            }

            public void setWalkData(List<WalkDataEntity> WalkData) {
                this.WalkData = WalkData;
            }

            public static class WalkDataEntity {
                /**
                 * Type : 5
                 * Name : 电梯口
                 */

                private int Type;
                private String Name;

                public int getType() {
                    return Type;
                }

                public void setType(int Type) {
                    this.Type = Type;
                }

                public String getName() {
                    return Name;
                }

                public void setName(String Name) {
                    this.Name = Name;
                }
            }
        }

        public static class WalkMethodTwoDataEntity {
            /**
             * StartPosition : 外科楼10F
             * EndPosition : 外科楼9F
             * WalkData : [{"Type":5,"Name":"电梯口"}]
             */

            private String StartPosition;
            private String EndPosition;
            private List<WalkDataEntityX> WalkData;

            public String getStartPosition() {
                return StartPosition;
            }

            public void setStartPosition(String StartPosition) {
                this.StartPosition = StartPosition;
            }

            public String getEndPosition() {
                return EndPosition;
            }

            public void setEndPosition(String EndPosition) {
                this.EndPosition = EndPosition;
            }

            public List<WalkDataEntityX> getWalkData() {
                return WalkData;
            }

            public void setWalkData(List<WalkDataEntityX> WalkData) {
                this.WalkData = WalkData;
            }

            public static class WalkDataEntityX {
                /**
                 * Type : 5
                 * Name : 电梯口
                 */

                private int Type;
                private String Name;

                public int getType() {
                    return Type;
                }

                public void setType(int Type) {
                    this.Type = Type;
                }

                public String getName() {
                    return Name;
                }

                public void setName(String Name) {
                    this.Name = Name;
                }
            }
        }

        public static class WayPathEntity {
            /**
             * Type : 1
             * Area_ID : 11
             * BuilDing_ID : 4
             * Floor_ID : 1229
             * BuilDing_Name:楼宇名称
             * Floor_Name:楼层名称
             */
            private int Type;
            private int Area_ID;
            private int BuilDing_ID;
            private int Floor_ID;
            private String BuilDing_Name;
            private String Floor_Name;

            public String getBuilDing_Name() {
                return BuilDing_Name;
            }

            public void setBuilDing_Name(String builDing_Name) {
                BuilDing_Name = builDing_Name;
            }

            public String getFloor_Name() {
                return Floor_Name;
            }

            public void setFloor_Name(String floor_Name) {
                Floor_Name = floor_Name;
            }

            public int getType() {
                return Type;
            }

            public void setType(int Type) {
                this.Type = Type;
            }

            public int getArea_ID() {
                return Area_ID;
            }

            public void setArea_ID(int Area_ID) {
                this.Area_ID = Area_ID;
            }

            public int getBuilDing_ID() {
                return BuilDing_ID;
            }

            public void setBuilDing_ID(int BuilDing_ID) {
                this.BuilDing_ID = BuilDing_ID;
            }

            public int getFloor_ID() {
                return Floor_ID;
            }

            public void setFloor_ID(int Floor_ID) {
                this.Floor_ID = Floor_ID;
            }
        }

        public static class WayDataEntity {
            /**
             * Id : 398
             * State : 6
             * X : 271
             * Y : 94
             */

            private int Id;
            private int State;
            private int X;
            private int Y;

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public int getState() {
                return State;
            }

            public void setState(int State) {
                this.State = State;
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
}
