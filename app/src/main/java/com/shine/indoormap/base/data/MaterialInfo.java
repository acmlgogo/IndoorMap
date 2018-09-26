package com.shine.indoormap.base.data;

import java.util.List;

public class MaterialInfo {

    /**
     * requestType : 0
     * sources : [{"id":"1","type":"0","time":"60","path":"http://localhost:46003/Upload/180706092044063.png","name":"180706092044063.png"},{"id":"2","type":"0","time":"60","path":"http://localhost:46003/Upload/180706135604420.png","name":"180706135604420.png"}]
     */

    private String requestType;
    private List<SourcesEntity> sources;

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public List<SourcesEntity> getSources() {
        return sources;
    }

    public void setSources(List<SourcesEntity> sources) {
        this.sources = sources;
    }

    public static class SourcesEntity {
        /**
         * id : 1
         * type : 0
         * time : 60
         * path : http://localhost:46003/Upload/180706092044063.png
         * name : 180706092044063.png
         */

        private String id;
        private String type;
        private String time;
        private String path;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
