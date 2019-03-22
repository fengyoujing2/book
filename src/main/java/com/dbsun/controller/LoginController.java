package com.dbsun.controller;

import com.dbsun.base.BaseController;
import com.dbsun.config.AllConfig;
import com.dbsun.entity.system.PageData;
import com.dbsun.service.SysUserService;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController extends BaseController {
    @Autowired
    private SysUserService sysuserService;

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/index")
    public String index(ModelMap model) {
        //验证session是否有效
        HttpSession session = request.getSession();
        PageData user = (PageData) session.getAttribute(AllConfig.SESSION_KEY);
        if (user != null) {
            //先读取session里面是否包含菜单，如果包含则直接使用，不包含则重新获取并设置进入session
            model.addAttribute("name", user.getString("NAME"));//用户名称
            return "index";
        } else {
            //返回到首页
            return "login";
        }

    }

    @RequestMapping("/loginPost")
    public @ResponseBody
    Map<String, Object> loginPost(String account, String password, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        PageData pd = getPageData();//前端传入数据
        //获取浏览器信息  2018/06/07 修改
        Browser browser = UserAgent.parseUserAgentString(request.getHeader("User-Agent")).getBrowser();
        //获取浏览器版本号
        Version version = browser.getVersion(request.getHeader("User-Agent"));
        String versionInfo = null;
        try {
            versionInfo = browser.getName() + "/" + version.getVersion();
        } catch (NullPointerException e) {
        }

        //获取用户信息
        PageData user = sysuserService.getLoginValidation(pd);
        if (user == null) {
            map.put("msg", "500");
            map.put("result", "密码错误");
            return map;
        } else {
                //判定当前用户是否已经登录过，如果登录不让重新登录
                PageData userPd = (PageData) session.getAttribute(AllConfig.SESSION_KEY);
                user.put("version", versionInfo);//浏览器版本号
                if (userPd != null) {//表示登录过则不需要多次登录直接提醒!
                    map.put("msg", "200");
                    map.put("result", "你已经登录过无需再次登录!");
                } else {
                    System.out.println(session.getAttribute(AllConfig.SESSION_KEY));
                    //谦容夸浏览器保证只有1个用户 2018/06/07
                    // 设置session,放入用户信息跟角色权限信息
                    session.setAttribute(AllConfig.SESSION_KEY, user);
                    map.put("msg", "200");
                    map.put("result", "登录成功!");
                }
        }

        return map;
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        // 移除session账户信息
        session.removeAttribute(AllConfig.SESSION_KEY);
        return "login";
    }

    @RequestMapping("/getSessionBac")
    @ResponseBody
    public JSONObject getSessionBac(){
        return this.getSucJson(session.getAttribute(AllConfig.SESSION_KEY));
    }

    @RequestMapping("/getAllUser")
    @ResponseBody
    public JSONObject getAllUser(){
        return this.getSucJson(sysuserService.getAllUser(new PageData()));
    }

    @RequestMapping("/updateUserById")
    @ResponseBody
    public JSONObject updateUserById(){
        PageData pd = this.getPageData();
        return this.getSucJson(sysuserService.updateUserById(pd));
    }

}
