package com.yankuang.equipment.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 项目名称：yankuang_equipment
 * 类名称：ResponseMeta
 * 类描述：返回前端的json串格式
 * 创建人： liyinpeng
 * 创建时间：2018-8-3 下午12:49:46
 * 修改人： liyinpeng
 * 修改时间：2018-8-3 下午12:49:46
 * 修改备注：
 * @version
 * <p>Copyright: Copyright (c) 2018 Company: KCX</p>
 *
 */
public class ResponseMeta {
    private Map<String, String> meta = new HashMap<String, String>();
    private Object data;

    public ResponseMeta(){

    }

    public ResponseMeta(String code, String message){
        meta.put("code", code);
        meta.put("message", message);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        data = data==null?"":data;
        this.data = data;
    }

    public Map<String, String> getMeta() {
        return meta;
    }

    public ResponseMeta setMeta(String code, String message) {
        meta.put("code", code);
        meta.put("message", message);
        return this;
    }
}
