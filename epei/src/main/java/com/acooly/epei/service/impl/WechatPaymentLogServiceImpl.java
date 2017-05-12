package com.acooly.epei.service.impl;

import org.springframework.stereotype.Service;
import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.epei.service.WechatPaymentLogService;
import com.acooly.epei.dao.WechatPaymentLogDao;
import com.acooly.epei.domain.WechatPaymentLog;

@Service("WechatPaymentLogService")
public class WechatPaymentLogServiceImpl extends EntityServiceImpl<WechatPaymentLog, WechatPaymentLogDao> implements WechatPaymentLogService {

}
