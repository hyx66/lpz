package com.acooly.epei.web;

import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.core.common.service.EntityService;
import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.core.utils.Servlets;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 修订记录：
 * liubin 2015-10-21 20:12 创建
 */
public class LogicalDelEntityController<T extends AbstractEntity, M extends EntityService<T>> extends
																							  AbstractJQueryEntityController<T,M> {

	@Override
	protected Map<String, Object> getSearchParams(HttpServletRequest request) {
		Map<String,Object>  searchParams = Servlets.getParametersStartingWith(request, "search_");
		searchParams.put("EQ_deleted",0);
		return  searchParams;
	}


}
