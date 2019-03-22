package com.dbsun.service.background;

import com.dbsun.entity.system.Page;
import com.dbsun.entity.system.PageData;
import com.dbsun.mapper.background.BacMapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class BacService {

    @Autowired
    private BacMapper bacMapper;

    public List<PageData> getPageDb(Page page){
        return bacMapper.getPageDb(page);
    }

    public int updateDb(PageData pd){
        return bacMapper.updateDb(pd);
    }

    public List<PageData> getPageHotDb(Page page){
        return bacMapper.getPageHotDb(page);
    }

    JSONArray jsonArray = new JSONArray();

    public int startBrowse(PageData pd){

        int ret = -1;

        try {
            pd.put("startTime", System.currentTimeMillis());
            jsonArray.add(pd);

            ret = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    public int endBrowse(PageData pd){
        int ret = -1;
        try {

            Iterator<Object> iterator = jsonArray.iterator();
            while (iterator.hasNext()){
                JSONObject retPd = (JSONObject)iterator.next();

                if(retPd.getString("id").equals(pd.getString("id"))){
                    pd.put("time_length", System.currentTimeMillis() - (Long) retPd.get("startTime"));
                    this.updateDb(pd);
                    jsonArray.remove(retPd);

                    break;
                }
            }

            ret = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;

    }


}
