package com.acooly.epei.web;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.core.utils.Encodes;
import com.acooly.core.utils.Strings;
import com.acooly.epei.dao.HospitalDao;
import com.acooly.epei.domain.Company;
import com.acooly.epei.domain.Customer;
import com.acooly.epei.domain.CustomerFamily;
import com.acooly.epei.domain.Hospital;
import com.acooly.epei.domain.RegOriginEnum;
import com.acooly.epei.domain.Vip;
import com.acooly.epei.service.CompanyService;
import com.acooly.epei.service.CustomerFamilyService;
import com.acooly.epei.service.CustomerNOService;
import com.acooly.epei.service.CustomerService;
import com.acooly.epei.service.OrderBaseService;
import com.acooly.epei.service.VipService;
import com.acooly.epei.util.CodeUtils;
import com.acooly.module.security.utils.Digests;
import com.acooly.module.sms.ShortMessageSendService;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@Controller
@RequestMapping(value = "/manage/epei/customer")
public class CustomerManagerController extends AbstractJQueryEntityController<Customer, CustomerService> {

	public static final String MOBILE_EL = "^1[0-9]{10}$";

	public static final int SALT_SIZE = 8;

	public static final int HASH_INTERATIONS = 1024;

	@Autowired
	private OrderBaseService orderBaseService;

	@Autowired
	private HospitalDao hospitalDao;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustomerNOService customerNOService;

	@Autowired
	private ShortMessageSendService shortMessageSendService;

	@Autowired
	private VipService vipService;

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private CustomerFamilyService customerFamilyService;

	private static final String RESET_PASSWORD = "sms.template.resetpassword";

	private Logger logger = LoggerFactory.getLogger(CustomerManagerController.class);

	@RequestMapping({ "theCustomer" })
	public String show(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			model.addAllAttributes(referenceData(request));
			AbstractEntity entity = loadEntity(request);
			if (entity == null) {
				throw new RuntimeException("LoadEntity failure.");
			}
			model.addAttribute(getEntityName(), entity);
			model.addAttribute("customerOrders",
					orderBaseService.getTheCustomerOrders(customerService.findCustomerById(entity.getId()).getCusNo()));
		} catch (Exception e) {
			logger.warn(getExceptionMessage("show", e), e);
			handleException("查看", e, request);
		}
		return getShowView();
	}

	@RequestMapping("cancelCustomer")
	@ResponseBody
	public JsonResult cancelCustomer(Long id) {
		JsonResult result = new JsonResult();
		try {
			Customer customer = customerService.get(id);
			if (customer != null) {
				customer.setDeleted(1);
				customerService.update(customer);
				result.setMessage("会员锁定成功");
			} else {
				result.setSuccess(false);
				result.setMessage("要锁定的会员不存在,试试刷新页面.");
			}
		} catch (Exception e) {
			logger.error("锁定会员发生异常,", e);
			result.setSuccess(false);
			result.setMessage("系统出错啦,请稍后再试.");
		}
		return result;
	}

	@RequestMapping("recoverCustomer")
	@ResponseBody
	public JsonResult recoverCustomer(Long id) {
		JsonResult result = new JsonResult();
		try {
			Customer customer = customerService.get(id);
			if (customer != null) {
				customer.setDeleted(0);
				customerService.update(customer);
				result.setMessage("会员解锁成功");
			} else {
				result.setSuccess(false);
				result.setMessage("要解锁的会员不存在,试试刷新页面.");
			}
		} catch (Exception e) {
			logger.error("解锁会员发生异常,", e);
			result.setSuccess(false);
			result.setMessage("系统出错啦,请稍后再试.");
		}
		return result;
	}

	@RequestMapping("resetPassword")
	@ResponseBody
	public JsonResult resetPassword(Long id) {
		JsonResult result = new JsonResult();
		try {
			Customer customer = customerService.get(id);
			if (customer != null) {
				String newPassword = CodeUtils.getRandCode(8);
				customerService.changePassword(customer, newPassword);
				Map<String, Object> params = new HashMap<>();
				params.put("userName", customer.getUserName());
				params.put("password", newPassword);
				shortMessageSendService.sendByTemplateAsync(customer.getMobile(), RESET_PASSWORD, params);
				result.setMessage("密码重置成功");
			} else {
				result.setSuccess(false);
				result.setMessage("需要重置密码的会员不存在,试试刷新页面.");
			}
		} catch (Exception e) {
			logger.error("重置密码发生异常,", e);
			result.setSuccess(false);
			result.setMessage("系统出错啦,请稍后再试.");

		}
		return result;
	}

	@Override
	protected Customer onSave(HttpServletRequest request, HttpServletResponse response, Model model, Customer entity,
			boolean isCreate) {
		customerService.onSave(request, response, model, entity, isCreate);
		return entity;
	}

	protected void onShow(HttpServletRequest request, HttpServletResponse response, Model model, Customer entity)
			throws Exception {
		convertEnum(entity);
	}

	private void convertEnum(Customer entity) {
		entity.setRegOrigin(RegOriginEnum.getMsgByCode(entity.getRegOrigin()));
	}

	/**
	 * 异步查询所有VIP级别
	 */
	@RequestMapping("queryVipAll")
	@ResponseBody
	public List<Vip> queryVipAll() {
		return vipService.getAll();
	}

	/**
	 * 异步查询所有VIP购买商的信息
	 */
	@RequestMapping("queryCompanyAll")
	@ResponseBody
	public List<Company> queryCompanyAll() {
		return companyService.getAll();
	}

	/****************************************** Excel导入用到的一些方法 *********************************************/
	protected List<Customer> doImport(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, UploadResult> uploadResults = doUpload(request);
		List<String[]> lines = doImportLoad(uploadResults, getCharsetName(request));
		beforeUnmarshal(lines);
		List<Customer> entities = unmarshal(lines);
		afterUnmarshal(entities);
		customerService.vipIncludeSaves(entities);
		customerFamilyService.createCustomerFamily(entities);
		return entities;
	}

	private String getCharsetName(HttpServletRequest request) {
		String charset = "UTF-8";
		String requestCharset = StringUtils.trimToEmpty(request.getParameter("charset"));
		if (StringUtils.isNotBlank(requestCharset)) {
			charset = requestCharset;
		}
		return charset;
	}

	@Override
	protected List<Customer> unmarshal(List<String[]> lines) {
		List<Customer> entities = new LinkedList<Customer>();
		for (int i = 1; i < lines.size(); i++) {
			Customer c = doUnmarshalEntity(lines.get(i), i);
			if (c != null)
				entities.add(c);
		}
		return entities;
	}

	protected Customer doUnmarshalEntity(String[] line, int lineCount) {
		// 验证用户名是否已经存在，手机号码格式是否正确。
		if (customerService.findCustomerByUsername(line[1]) != null || !Pattern.matches(MOBILE_EL, line[1])) {
			throw new BusinessException("导入第" + lineCount + "条信息有误：用户名已存在或输入有误");
		}
		Customer customer = new Customer();
		customer.setName(line[0]);
		customer.setUserName(line[1]);
		// 设置密码
		if (StringUtils.isNotBlank(line[2])) {
			customer.setPassword(line[2]);
		} else {
			throw new BusinessException("导入第" + lineCount + "条信息有误：密码不能为空");
		}
		// 验证手机号码格式是否正确
		if (StringUtils.isNotBlank(line[3])) {
			if (Pattern.matches(MOBILE_EL, line[3])) {
				customer.setMobile(line[3]);
			} else {
				throw new BusinessException("导入第" + lineCount + "条信息有误：手机号码格式有误");
			}
		}
		if(StringUtils.isNotBlank(line[4].trim())){
			customer.setIdCard(line[4].trim());
		}else{
			throw new BusinessException("导入第" + lineCount + "条信息有误：身份证号不能为空");
		}
		if (StringUtils.isNotBlank(line[5])) {
			if (customerService.findCustomerByUsername(line[5]) != null) {
				customer.setReferenceMobile(line[5]);
			} else {
				throw new BusinessException("导入第" + lineCount + "条信息有误：所填推荐人还不是会员");
			}
		}
		// 设置性别
		if (line[6].equals("女")) {
			customer.setSex("0");
		} else {
			customer.setSex("1");
		}
		customer.setBirthday(line[7]);
		customer.setNativePlace(line[8]);
		customer.setEmail(line[9]);
		customer.setProfession(line[10]);
		customer.setAddress(line[11]);
		customer.setFamily(line[12]);
		// 设置会员是否已婚
		if (line[13].equals("已婚")) {
			customer.setMaritalStatus("1");
		} else {
			customer.setMaritalStatus("0");
		}
		customer.setDegreeOfEducation(line[14]);
		customer.setEmergencyContactPerson(line[15]);
		customer.setEmergencyContactRelationship(line[16]);
		customer.setEmergencyContactNumber(line[17]);
		customer.setMedicalHistory(line[18]);
		customer.setGeneticHistory(line[19]);
		customer.setHabits(line[20]);
		customer.setDiet(line[21]);
		customer.setPhysical(line[22]);
		customer.setSource(line[23]);
		customer.setReceiveInfo(line[24]);
		customer.setPhoneNumber(line[25]);
		// 设置会员的类型
		if (line[26].equals("医院管理员")) {
			customer.setCustomerType("1");
		} else {
			customer.setCustomerType("0");
		}
		// 根据医院名称设置会员所属医院ID
		if (StringUtils.isNotBlank(line[27])) {
			Hospital h = hospitalDao.findByName(line[27]);
			if (h != null && customer.getCustomerType().equals("1")) {
				customer.setCustomerHosId(h.getId());
			} else {
				throw new BusinessException("第" + lineCount + "条信息有误：所填医院不存在或会员不是医院管理员");
			}
		} else {
			customer.setCustomerHosId(-1L);
		}
		// 设置医院管理员类型
		if (StringUtils.isNotBlank(line[28])) {
			if (customer.getCustomerType().equals("1")) {
				if (line[28].equals("陪诊")) {
					customer.setAdminType(0);
				} else if (line[28].equals("陪护")) {
					customer.setAdminType(1);
				}
			} else {
				throw new BusinessException("第" + lineCount + "条信息有误：该会员不是医院管理员不能选择管理员类型");
			}
		}
		String vipName = line[29].trim();
		if (StringUtils.isNotBlank(vipName)) {
			Vip vip = vipService.findByName(vipName);
			if (vip != null) {
				customer.setVipId(vip.getId());			
			}else{
				throw new BusinessException("没有找到相应的VIP项目");
			}
		}
		String companyName = line[30].trim();
		if(StringUtils.isNotBlank(companyName)){
			Company company = companyService.findByName(companyName);
			if(company != null){
				customer.setVipCompanyId(company.getId());
			}else{
				throw new BusinessException("没有找到相应的企业信息");
			}
		}
		customer.setRegOrigin("PC");
		customerNOService.genarateCusNo(customer);
		entryptPassword(customer);
		customer.setDeleted(0);
		return customer;
	}

	private void entryptPassword(Customer customer) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		customer.setSalt(Encodes.encodeHex(salt));
		customer.setPassword(entryptPassword(customer.getPassword(), Encodes.encodeHex(salt)));
	}

	private String entryptPassword(String plainPassword, String salt) {
		return Encodes.encodeHex(Digests.sha1(plainPassword.getBytes(), Encodes.decodeHex(salt), HASH_INTERATIONS));
	}

	/******************************************* Excel导出用到的一些方法 ********************************************/
	@Override
	public boolean doExportExcelBody(List<Customer> list, HttpServletRequest request, HttpServletResponse response) {
		WritableWorkbook workbook = null;
		OutputStream out = null;
		try {
			WritableFont font1 = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false,
					UnderlineStyle.NO_UNDERLINE, Colour.RED);
			WritableCellFormat cellFormat = new WritableCellFormat(font1);
			out = response.getOutputStream();
			String[] headerNames = { "姓名", "用户名", "入会时间", "手机号码", "身份证", "推荐人", "性别", "生日", "籍贯", "邮箱", "职业", "地址",
					"家庭成员", "婚姻状况", "学历", "紧急联系人", "与紧急联系人关系", "紧急联系人电话", "病史", "遗传史", "兴趣爱好", "膳食结构", "体检情况", "会员来源",
					"愿意通过哪种方式接收消息", "预留电话", "会员类别", "管理员所属医院", "医院管理员类型" };
			workbook = Workbook.createWorkbook(out);
			WritableSheet sheet = workbook.createSheet("Sheet1", 0);
			int row = 0;

			if (headerNames != null) {
				for (int i = 0; i < headerNames.length; i++) {
					sheet.addCell(new Label(i, row, headerNames[i], cellFormat));
				}
				row++;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (Customer entity : list) {
				List<String> entityData = new ArrayList<String>();
				entityData.add(entity.getName());
				entityData.add(entity.getUserName());
				// 入会时间
				entityData.add(sdf.format(entity.getCreateTime()));
				entityData.add(entity.getMobile());
				entityData.add(entity.getIdCard());
				entityData.add(entity.getReferenceMobile());
				// 性别 1：男 2：女
				if (entity.getSex() != null) {
					if (entity.getSex().equals("1")) {
						entityData.add("男");
					} else {
						entityData.add("女");
					}
				} else {
					entityData.add(null);
				}
				entityData.add(entity.getBirthday());
				entityData.add(entity.getNativePlace());
				entityData.add(entity.getEmail());
				entityData.add(entity.getProfession());
				entityData.add(entity.getAddress());
				entityData.add(entity.getFamily());
				// 婚姻状况 1：已婚 2：未婚
				if (StringUtils.isNotBlank(entity.getMaritalStatus())) {
					if (entity.getMaritalStatus().equals("1")) {
						entityData.add("已婚");
					} else {
						entityData.add("未婚");
					}
				} else {
					entityData.add(null);
				}
				entityData.add(entity.getDegreeOfEducation());
				entityData.add(entity.getEmergencyContactPerson());
				entityData.add(entity.getEmergencyContactRelationship());
				entityData.add(entity.getEmergencyContactNumber());
				entityData.add(entity.getMedicalHistory());
				entityData.add(entity.getGeneticHistory());
				entityData.add(entity.getHabits());
				entityData.add(entity.getDiet());
				entityData.add(entity.getPhysical());
				entityData.add(entity.getSource());
				entityData.add(entity.getReceiveInfo());
				entityData.add(entity.getPhoneNumber());
				// 会员类型 1：医院管理员 2：普通会员
				if (entity.getCustomerType() != null) {
					if (entity.getCustomerType().equals("1")) {
						entityData.add("医院管理员");
					} else {
						entityData.add("普通会员");
					}
				} else {
					entityData.add(null);
				}
				// 管理员所属医院
				if (entity.getCustomerHosId() != null) {
					if (hospitalDao.findOne(entity.getCustomerHosId()) != null) {
						entityData.add(hospitalDao.findOne(entity.getCustomerHosId()).getName());
					} else {
						entityData.add(null);
					}
				} else {
					entityData.add(null);
				}
				// 医院管理员类型 1：陪护 2：陪诊
				if (entity.getAdminType() != null) {
					if (entity.getAdminType().equals("1")) {
						entityData.add("陪护");
					} else {
						entityData.add("陪诊");
					}
				} else {
					entityData.add(null);
				}
				for (int i = 0; i < entityData.size(); i++) {
					sheet.addCell(new Label(i, row, Strings.trimToEmpty((String) entityData.get(i))));
				}
				row++;
			}
			workbook.write();
			out.flush();
		} catch (Exception e) {
		} finally {
			try {
				workbook.close();
			} catch (Exception localException2) {
			}
			IOUtils.closeQuietly(out);
		}
		return false;
	}

	/*************************************** 会员导入用到的excel模板下载 ***********************************************/
	@RequestMapping("downloadCustomerExcelModel")
	public String downloadCustomerExcelModel(HttpServletRequest request, HttpServletResponse response) {
		try {
			// XLS文件的一些属性
			doExportExcelHeader(request, response);
			// XLS文件内容
			WritableWorkbook workbook = null;
			OutputStream out = null;
			try {
				// 设置第一行栏目名称的字体
				WritableFont font1 = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false,
						UnderlineStyle.NO_UNDERLINE, Colour.RED);
				WritableCellFormat cellFormat = new WritableCellFormat(font1);
				out = response.getOutputStream();
				String[] headerNames = { "姓名", "用户名（必填，手机号）", "密码（必填，8位以上）", "电话（手机号，必填）", "身份证号（必填）", "推荐人（手机号）", "性别（男/女）",
						"生日（格式：19890101）", "籍贯", "邮箱", "职业", "地址", "家庭成员", "婚姻状况（已婚/未婚）", "文化程度", "紧急联系人", "与紧急联系人关系",
						"紧急联系人电话（手机号）", "个人病史", "遗传史", "生活习惯", "膳食结构", "体检情况", "会员来源", "愿意通过哪种方式接收消息", "预留电话（手机号或座机号）",
						"会员类型（普通会员/医院管理员）", "所属医院", "管理员类型（陪诊/陪护）", "VIP名称", "VIP购买商" };
				workbook = Workbook.createWorkbook(out);
				WritableSheet sheet = workbook.createSheet("Sheet1", 0);
				int row = 0;

				if (headerNames != null) {
					for (int i = 0; i < headerNames.length; i++) {
						sheet.addCell(new Label(i, row, headerNames[i], cellFormat));
					}
				}
				workbook.write();
				out.flush();
			} catch (Exception e) {
			} finally {
				try {
					workbook.close();
				} catch (Exception localException2) {
				}
				IOUtils.closeQuietly(out);
			}
			return null;
		} catch (Exception e) {
			logger.warn(getExceptionMessage("exportExcel", e), e);
			handleException("导出Excel", e, request);
		}
		return getSuccessView();
	}

}
