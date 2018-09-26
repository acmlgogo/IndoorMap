package com.shine.indoormap.base.data;

import java.util.List;

public class Facilities {

    /**
     * result_code : 0
     * result_desc : 获取基础设施数据成功
     * result_data : {"Type":"Floor","FloorFacilitieData":[{"Floor_ID":1218,"FloorName":"15F","MarkData":[{"MarkId":649,"State":15,"X":552,"Y":63},{"MarkId":650,"State":15,"X":543,"Y":146}]},{"Floor_ID":1229,"FloorName":"16F","MarkData":[{"MarkId":395,"State":15,"X":724,"Y":309},{"MarkId":399,"State":15,"X":664,"Y":84},{"MarkId":400,"State":15,"X":897,"Y":216},{"MarkId":401,"State":15,"X":594,"Y":175}]}],"AreaFacilitieData":[{"MarkId":122,"State":15,"X":501,"Y":432},{"MarkId":123,"State":15,"X":783,"Y":199}]}
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
         * Type : Floor
         * FloorFacilitieData : [{"Floor_ID":1218,"FloorName":"15F","MarkData":[{"MarkId":649,"State":15,"X":552,"Y":63},{"MarkId":650,"State":15,"X":543,"Y":146}]},{"Floor_ID":1229,"FloorName":"16F","MarkData":[{"MarkId":395,"State":15,"X":724,"Y":309},{"MarkId":399,"State":15,"X":664,"Y":84},{"MarkId":400,"State":15,"X":897,"Y":216},{"MarkId":401,"State":15,"X":594,"Y":175}]}]
         * AreaFacilitieData : [{"MarkId":122,"State":15,"X":501,"Y":432},{"MarkId":123,"State":15,"X":783,"Y":199}]
         */

        private String Type;
        private List<FloorFacilitieDataEntity> FloorFacilitieData;
        private List<AreaFacilitieDataEntity> AreaFacilitieData;

        public String getType() {
            return Type;
        }

        public void setType(String Type) {
            this.Type = Type;
        }

        public List<FloorFacilitieDataEntity> getFloorFacilitieData() {
            return FloorFacilitieData;
        }

        public void setFloorFacilitieData(List<FloorFacilitieDataEntity> FloorFacilitieData) {
            this.FloorFacilitieData = FloorFacilitieData;
        }

        public List<AreaFacilitieDataEntity> getAreaFacilitieData() {
            return AreaFacilitieData;
        }

        public void setAreaFacilitieData(List<AreaFacilitieDataEntity> AreaFacilitieData) {
            this.AreaFacilitieData = AreaFacilitieData;
        }

        public static class FloorFacilitieDataEntity {
            /**
             * Floor_ID : 1218
             * FloorName : 15F
             * MarkData : [{"MarkId":649,"State":15,"X":552,"Y":63},{"MarkId":650,"State":15,"X":543,"Y":146}]
             */

            private int Floor_ID;
            private String FloorName;
            private List<MarkDataEntity> MarkData;

            public int getFloor_ID() {
                return Floor_ID;
            }

            public void setFloor_ID(int Floor_ID) {
                this.Floor_ID = Floor_ID;
            }

            public String getFloorName() {
                return FloorName;
            }

            public void setFloorName(String FloorName) {
                this.FloorName = FloorName;
            }

            public List<MarkDataEntity> getMarkData() {
                return MarkData;
            }

            public void setMarkData(List<MarkDataEntity> MarkData) {
                this.MarkData = MarkData;
            }

            public static class MarkDataEntity {
                /**
                 * MarkId : 649
                 * State : 15
                 * X : 552
                 * Y : 63
                 */

                private int MarkId;
                private int State;
                private int X;
                private int Y;

                public int getMarkId() {
                    return MarkId;
                }

                public void setMarkId(int MarkId) {
                    this.MarkId = MarkId;
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

        public static class AreaFacilitieDataEntity {
            /**
             * MarkId : 122
             * State : 15
             * X : 501
             * Y : 432
             */

            private int MarkId;
            private int State;
            private int X;
            private int Y;

            public int getMarkId() {
                return MarkId;
            }

            public void setMarkId(int MarkId) {
                this.MarkId = MarkId;
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
