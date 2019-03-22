package com.dbsun.mapper.background;

import com.dbsun.entity.system.Page;
import com.dbsun.entity.system.PageData;

import java.util.List;

public interface BacMapper {

    public List<PageData> getPageDb(Page page);
    public int updateDb(PageData pd);
    public List<PageData> getPageHotDb(Page page);

}
