package com.dbsun.base;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.dbsun.entity.system.Page;
import com.dbsun.entity.system.PageData;
import com.dbsun.util.Tools;

@Controller
public class BaseController {

	private static final long serialVersionUID = 6357869213649815390L;
	// 全局session
	@Autowired
	protected HttpSession session;

	// 全局request
	@Autowired
	protected HttpServletRequest request;
	// 全局请求路径项目名
	protected String content;

	// 404 not found 页面跳转
	protected final String NOT_FOUND = "/404";

	private static final Logger log = LoggerFactory
			.getLogger(BaseController.class);

	/**
	 * 通用返回json分页数据
	 * */
	public JSONObject viewReturnPageData(Page page, List<PageData> pageLst) {
		JSONObject json = new JSONObject();// 返回数据必须包含这个格式
		json.put("total", page.getTotalResult());
		json.put("rows", pageLst);
		return json;
	}

	/**
	 * 获取request
	 *
	 * @return HttpServletRequest实例
	 */
	public HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
	}

	/**
	 * 获取session
	 *
	 * @return HttpSession实例
	 */
	public HttpSession getSession() {
		return getRequest().getSession();
	}

	/**
	 * 得到分页列表的信息
	 */
	public Page getPage() {
		String currentResult = request.getParameter("offset");//管理分页编码
		String currentPage = request.getParameter("currentPage");//业务分页编码
		Page page = new Page();
		if(!Tools.isObjEmpty(request.getParameter("showCount"))){//未传入每页显示条数的,默认10条一页
			page.setShowCount(Integer.parseInt(request.getParameter("showCount")));
		}
		if(Tools.isObjEmpty(currentPage)){
			if(Tools.isObjEmpty(currentResult)){
				page.setCurrentResult(0);// 分页开始位置
			}else {
				if(Tools.isObjEmpty(currentResult)){
					page.setCurrentResult(0);// 分页开始位置
				}else {
					page.setCurrentResult(Integer.parseInt(currentResult));// 分页开始位置
				}
			}
		}else{
			//业务功能分页
			int startLimt = (Integer.parseInt(currentPage)-1) * page.getShowCount(); //当前页数
			page.setCurrentResult(startLimt);// 分页开始位置
		}

		return page;
	}

	/**
	 * 得到PageData
	 */
	public PageData getPageData() {
		return new PageData(this.getRequest());
	}


	/**
	 * 将json对象中包含的null和JSONNull属性修改成""
	 * @param jsonObj
	 */
	public JSONObject filterNull(JSONObject jsonObj){
		Iterator<String> it = jsonObj.keys();
		Object obj = null;
		String key = null;
		while (it.hasNext()) {
			key = it.next();
			obj = jsonObj.get(key);
			if(obj instanceof JSONObject){
				filterNull((JSONObject)obj);
			}
			if(obj == null || obj instanceof JSONNull){
				jsonObj.put(key, "");
			}
		}
		return jsonObj;
	}
	/**
	 * 获取请求成功的List
	 * @return
	 */
	public JSONObject getSucJson(Object obj){
		JSONObject json = new JSONObject();
		JSONObject jobj = new JSONObject();
		json.put("msg", "200");
		//如果对象为空，实例化一个空的对象，否则返回的json不会带result
		if(Tools.isObjEmpty(obj))obj = new Object();
		json.put("result", obj);
		jobj = filterNull(json);
		return jobj;
	}

	/**
	 * 添加或者修改返回的json
	 * @param ret
	 * @return
	 */
	public JSONObject getAddOrUpdJson(int ret){
		if(ret >= 1)return getSucJson();
		else if(ret == -1){
			return getAltNoLder();
		}
		else{
			return getFalJson();
		}
	}

	/**
	 * 部长分配没有选择员工，但是部门又没有主管的情况，提示信息
	 * @return
	 */
	public static JSONObject getAltNoLder(){
		JSONObject json  = new JSONObject();
		json.put("msg", "501");
		json.put("result", "注册失败");
		return json;
	}

	/**
	 * 获取操作失败返回的json
	 * @return
	 */
	public JSONObject getFalJson(){
		JSONObject json = new JSONObject();
		json.put("msg", "500");
		json.put("result", "filed");
		return json;
	}

	/**
	 * 获取操作成功返回的json
	 * @return
	 */
	public JSONObject getSucJson(){
		JSONObject json = new JSONObject();
		json.put("msg", "200");
		json.put("result", "success");
		return json;
	}

	
}
