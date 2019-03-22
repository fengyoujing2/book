package com.dbsun.util;

import net.sf.json.JSONObject;

public class RtnJSONTool {
	
	/**
	 * 部长分配没有选择员工，但是部门又没有主管的情况，提示信息
	 * @return
	 */
	public static JSONObject getAltNoLder(){
		JSONObject json  = new JSONObject();
		json.put("msg", "501");
		json.put("result", "该部门没有主管，请给选择的部门指定主管，或者直接分配到具体的做单员");
		return json;
	}

}
