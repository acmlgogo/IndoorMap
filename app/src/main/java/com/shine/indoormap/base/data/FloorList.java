package com.shine.indoormap.base.data;

import java.util.List;

public class FloorList {

    /**
     * result_code : 0
     * result_data : [{"Floor_ID":6,"Floor_Name":"1","Queue_Data":[{"Coordinate_ID":"220","Queue_ID":29,"Queue_Name":"妇科"},{"Coordinate_ID":"221","Queue_ID":49,"Queue_Name":"儿科"},{"Coordinate_ID":"230","Queue_ID":31,"Queue_Name":"呼吸内科"}]},{"Floor_ID":4,"Floor_Name":"-1","Queue_Data":[]},{"Floor_ID":3,"Floor_Name":"-2","Queue_Data":[]},{"Floor_ID":2,"Floor_Name":"-3","Queue_Data":[]}]
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
        public int getBuild_ID() {
            return Build_ID;
        }

        public void setBuild_ID(int build_ID) {
            Build_ID = build_ID;
        }

        public String getBuildName() {
            return BuildName;
        }

        public void setBuildName(String buildName) {
            BuildName = buildName;
        }

        /**
         * Floor_ID : 6
         * Floor_Name : 1
         * Queue_Data : [{"Coordinate_ID":"220","Queue_ID":29,"Queue_Name":"妇科"},{"Coordinate_ID":"221","Queue_ID":49,"Queue_Name":"儿科"},{"Coordinate_ID":"230","Queue_ID":31,"Queue_Name":"呼吸内科"}]
         */
        private int Build_ID;
        private String BuildName;
        private int Floor_ID;
        private String Floor_Name;

        private List<QueueDataEntity> Queue_Data;

        public int getFloor_ID() {
            return Floor_ID;
        }

        public void setFloor_ID(int Floor_ID) {
            this.Floor_ID = Floor_ID;
        }

        public String getFloor_Name() {
            return Floor_Name;
        }

        public void setFloor_Name(String Floor_Name) {
            this.Floor_Name = Floor_Name;
        }

        public List<QueueDataEntity> getQueue_Data() {
            return Queue_Data;
        }

        public void setQueue_Data(List<QueueDataEntity> Queue_Data) {
            this.Queue_Data = Queue_Data;
        }

        public static class QueueDataEntity {
            /**
             * Coordinate_ID : 220
             * Queue_ID : 29
             * Queue_Name : 妇科
             */

            private String Coordinate_ID;
            private int Queue_ID;
            private String Queue_Name;
            private int Floor_ID;

            public int getFloor_ID() {
                return Floor_ID;
            }

            public void setFloor_ID(int floor_ID) {
                Floor_ID = floor_ID;
            }

            public String getFloor_Name() {
                return Floor_Name;
            }

            public void setFloor_Name(String floor_Name) {
                Floor_Name = floor_Name;
            }

            private String Floor_Name;


            public String getCoordinate_ID() {
                return Coordinate_ID;
            }

            public void setCoordinate_ID(String Coordinate_ID) {
                this.Coordinate_ID = Coordinate_ID;
            }

            public int getQueue_ID() {
                return Queue_ID;
            }

            public void setQueue_ID(int Queue_ID) {
                this.Queue_ID = Queue_ID;
            }

            public String getQueue_Name() {
                return Queue_Name;
            }

            public void setQueue_Name(String Queue_Name) {
                this.Queue_Name = Queue_Name;
            }
        }
    }
}
