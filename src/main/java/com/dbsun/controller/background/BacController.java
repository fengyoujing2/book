package com.dbsun.controller.background;

import com.dbsun.base.BaseController;
import com.dbsun.entity.system.Page;
import com.dbsun.entity.system.PageData;
import com.dbsun.service.background.BacService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 后台管理
 * @author DongZai
 *
 */
@Controller
@RequestMapping("/bac_")
@SuppressWarnings("unused")
public class BacController extends BaseController {

    @Autowired
    private BacService bacService;

    @RequestMapping("/getPageDb")
    @ResponseBody
    public JSONObject getPageDb(){
        PageData pd = this.getPageData();

        Page page = this.getPage();
        page.setPd(pd);

        return viewReturnPageData(page, bacService.getPageDb(page));
    }

    @RequestMapping("/updateDb")
    @ResponseBody
    public JSONObject updateAtbStudent(){
        PageData pd = this.getPageData();
        return this.getAddOrUpdJson(bacService.updateDb(pd));
    }

    @RequestMapping("/getPageHotDb")
    @ResponseBody
    public JSONObject getPageHotDb(){
        PageData pd = this.getPageData();

        Page page = this.getPage();
        page.setPd(pd);

        return viewReturnPageData(page, bacService.getPageHotDb(page));
    }

    @RequestMapping("/startBrowse")
    @ResponseBody
    public JSONObject startBrowse(){
        PageData pd = this.getPageData();
        return this.getAddOrUpdJson(bacService.startBrowse(pd));
    }

    @RequestMapping("/endBrowse")
    @ResponseBody
    public JSONObject endBrowse(){
        PageData pd = this.getPageData();
        return this.getAddOrUpdJson(bacService.endBrowse(pd));
    }


}
