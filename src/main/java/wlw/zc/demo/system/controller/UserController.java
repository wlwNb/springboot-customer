package wlw.zc.demo.system.controller;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import server.OrderService;
import wlw.zc.demo.config.UserRealm;
import wlw.zc.demo.netty.NettyServer;
import wlw.zc.demo.socket.NIOLowServer;
import wlw.zc.demo.spring.SpringApplicationContext;
import wlw.zc.demo.system.entity.UploadFile;
import wlw.zc.demo.system.entity.User;

import javax.annotation.Resource;

@RestController
@Slf4j
public class UserController {
	@Autowired
	private UserRealm userRealm;
	@Autowired
	private RedisTemplate redisTemplate;
	@Resource
	private DefaultMQProducer producer;
	@Resource
	private NettyServer discardServer;
	@Resource
	private NIOLowServer nioLowServer;
	@Autowired
	private RedissonClient redissonClient;
	@Reference(version = "1.0.0")
	private OrderService orderService;
	@RequestMapping("/regiester")
	public String regiester(Map<String, Object> model){
		redisTemplate.opsForValue().set("id","1111111111");
		return "success";
	}
	@RequestMapping("/send")
	public void send(Map<String, Object> model) throws UnsupportedEncodingException, InterruptedException, RemotingException, MQClientException, MQBrokerException {
		System.out.println(orderService.send());
		for (int i = 0; i < 10; i++) {
			Message msg = new Message("topic_test",
					"",
					"",
					"{\"data\":[{\"id\":\"94875837\",\"pref\":\"ZHL19779497\",\"mnemonic_code\":\"JWXSP|JWXSP\",\"mixed_query\":\"ZHL5|健胃消食片|JWXSP|健胃消食片|JWXSP\",\"common_name\":\"健胃消食片\",\"product_name\":\"健胃消食片\",\"standard_library_id\":null,\"attribute_specification\":\"1盒*5袋\",\"unit_id\":\"212\",\"dosage_form_id\":\"15\",\"bar_code\":\"\",\"approval_number\":\"\",\"manufacturer\":\"浙江温州制药厂\",\"producing_area\":\"\",\"product_type\":\"0\",\"storage_condition\":\"430\",\"abc_dividing\":\"-1\",\"shelf_position\":\"5201211\",\"containing_hemp_yn\":\"0\",\"prescription_classification\":\"303\",\"prescription_yn\":\"0\",\"business_scope\":\"266\",\"maintenance_type\":\"569\",\"product_function_catagory\":\"0\",\"income_tax_rate\":\"13.0\",\"ouput_tax_rate\":\"13.0\",\"retail_price\":\"100.0\",\"vip_price\":\"80.0\",\"cost_price\":\"0.0\",\"score_product_yn\":\"1\",\"score_rate\":\"1.0\",\"store_max_limit\":\"0.0\",\"store_min_limit\":\"0.0\",\"parent_product_id\":\"0\",\"status\":\"2\",\"scattered_yn\":\"0\",\"node_type\":\"0\",\"collect_type\":\"1\",\"freeze_type\":\"1\",\"create_type\":\"-1\",\"create_user\":\"114364\",\"create_time\":\"2020-11-18 10:27:54\",\"update_user\":\"\",\"update_time\":null,\"yn\":\"1\",\"remark\":\"\",\"used\":\"1\",\"special\":\"0\",\"img_url\":\"\",\"last_purchase\":\"\",\"base_version\":\"14\",\"organSign\":\"ZHL00004379\",\"is_hidden\":\"0\",\"parent_pref\":null,\"pharmacy_pref\":\"ZHL5\",\"scattered_use_yn\":\"0\",\"print_status\":\"0\",\"ext_mess\":\"{\\\"approvalNumberValidity\\\":\\\"\\\",\\\"cosmeticsRegisCard\\\":\\\"\\\",\\\"cosmeticsRegisCardValidity\\\":\\\"\\\",\\\"disinfectProFirmSanitationCard\\\":\\\"\\\",\\\"disinfectProFirmSanitationCardValidity\\\":\\\"\\\",\\\"drugRegisCard\\\":\\\"\\\",\\\"drugRegisCardValidity\\\":\\\"\\\",\\\"drugRegisInstructions\\\":\\\"\\\",\\\"drugRegisInstructionsValidity\\\":\\\"\\\",\\\"drugRegisterCardOnce\\\":\\\"\\\",\\\"drugRegisterCardOnceValidity\\\":\\\"\\\",\\\"drugReplenishInstructions\\\":\\\"\\\",\\\"drugReplenishInstructionsValidity\\\":\\\"\\\",\\\"executeStandard\\\":\\\"\\\",\\\"executeStandardValidity\\\":\\\"\\\",\\\"gMPCertificate\\\":\\\"\\\",\\\"gMPCertificateValidity\\\":\\\"\\\",\\\"gMPRegisDate\\\":\\\"\\\",\\\"healthFoodLicence\\\":\\\"\\\",\\\"healthFoodLicenceValidity\\\":\\\"\\\",\\\"importCosmeticsRegisCard\\\":\\\"\\\",\\\"importCosmeticsRegisCardValidity\\\":\\\"\\\",\\\"importDrugRegisCard\\\":\\\"\\\",\\\"importDrugRegisCardOnce\\\":\\\"\\\",\\\"importDrugRegisCardOnceValidity\\\":\\\"\\\",\\\"importDrugRegisCardValidity\\\":\\\"\\\",\\\"importDrugReplenishInstructions\\\":\\\"\\\",\\\"importDrugReplenishInstructionsValidity\\\":\\\"\\\",\\\"importSpecialCosmeticsSanitationLicence\\\":\\\"\\\",\\\"importSpecialCosmeticsSanitationLicenceValidity\\\":\\\"\\\",\\\"medicalEquipRegisCard\\\":\\\"\\\",\\\"medicalEquipRegisCardOnce\\\":\\\"\\\",\\\"medicalEquipRegisCardOnceValidity\\\":\\\"\\\",\\\"medicalEquipRegisCardValidity\\\":\\\"\\\",\\\"medicineProRegisCard\\\":\\\"\\\",\\\"medicineProRegisCardValidity\\\":\\\"\\\",\\\"productionLicenseNumber\\\":\\\"\\\",\\\"qualityStandard\\\":\\\"\\\",\\\"qualityStandardValidity\\\":\\\"\\\",\\\"secondMedicalEquipOperateCard\\\":\\\"\\\",\\\"secondMedicalEquipOperateCardValidity\\\":\\\"\\\",\\\"specialCosmeticsSanitationLicence\\\":\\\"\\\",\\\"specialCosmeticsSanitationLicenceValidity\\\":\\\"\\\"}\",\"system_type\":\"194973\",\"made_type\":\"0\",\"medical_insurance\":\"0\",\"product_validity\":\"\",\"drug_remind_id\":\"0\",\"usage_and_dosage\":\"\",\"big_unit_id\":null,\"big_unit_bar_code\":null,\"middle_unit_id\":null,\"middle_unit_bar_code\":null,\"big_middle_unit_rate\":null,\"middle_small_unit_rate\":null,\"drug_permission_person\":\"\",\"check_mode\":\"1\",\"special_drug_manage\":\"1\",\"area_code\":\"\",\"manufacturer_code\":\"\",\"standard_code\":\"\",\"dose_channel\":\"\",\"usage\":\"\",\"per_dosage\":\"\",\"dosage_unit\":\"\",\"per_max_prescription\":\"\",\"transform_coefficient\":\"\",\"virtual_standard_library_id\":\"0\",\"medical_insurance_level\":\"0\",\"erp_code\":\"\",\"manage_attr\":\"0\",\"logistics_attr\":\"-1\",\"control_sales_yn\":\"0\",\"replacement_yn\":\"0\",\"management_attr\":\"0\",\"six_levels\":\"\",\"brand_name\":\"\",\"indication\":\"\"}],\"database\":\"xyy_saas_product\",\"es\":1605666473000,\"id\":327402,\"isDdl\":false,\"mysqlType\":{\"id\":\"bigint(20)\",\"pref\":\"varchar(32)\",\"mnemonic_code\":\"varchar(256)\",\"mixed_query\":\"varchar(768)\",\"common_name\":\"varchar(256)\",\"product_name\":\"varchar(256)\",\"standard_library_id\":\"bigint(11)\",\"attribute_specification\":\"varchar(256)\",\"unit_id\":\"int(20)\",\"dosage_form_id\":\"bigint(20)\",\"bar_code\":\"varchar(256)\",\"approval_number\":\"varchar(256)\",\"manufacturer\":\"varchar(256)\",\"producing_area\":\"varchar(256)\",\"product_type\":\"int(11)\",\"storage_condition\":\"int(11)\",\"abc_dividing\":\"int(11)\",\"shelf_position\":\"int(11)\",\"containing_hemp_yn\":\"int(11)\",\"prescription_classification\":\"int(11)\",\"prescription_yn\":\"tinyint(4)\",\"business_scope\":\"int(11)\",\"maintenance_type\":\"int(11)\",\"product_function_catagory\":\"int(11)\",\"income_tax_rate\":\"decimal(10,2)\",\"ouput_tax_rate\":\"decimal(10,2)\",\"retail_price\":\"decimal(10,2)\",\"vip_price\":\"decimal(10,2)\",\"cost_price\":\"decimal(10,2)\",\"score_product_yn\":\"tinyint(4)\",\"score_rate\":\"decimal(10,2)\",\"store_max_limit\":\"decimal(10,2)\",\"store_min_limit\":\"decimal(10,2)\",\"parent_product_id\":\"bigint(20)\",\"status\":\"tinyint(4)\",\"scattered_yn\":\"tinyint(4)\",\"node_type\":\"tinyint(4)\",\"collect_type\":\"tinyint(4)\",\"freeze_type\":\"tinyint(4)\",\"create_type\":\"tinyint(4)\",\"create_user\":\"varchar(32)\",\"create_time\":\"datetime\",\"update_user\":\"varchar(32)\",\"update_time\":\"datetime\",\"yn\":\"tinyint(4)\",\"remark\":\"varchar(2048)\",\"used\":\"tinyint(2)\",\"special\":\"tinyint(2)\",\"img_url\":\"varchar(128)\",\"last_purchase\":\"varchar(128)\",\"base_version\":\"varchar(64)\",\"organSign\":\"varchar(128)\",\"is_hidden\":\"tinyint(4)\",\"parent_pref\":\"varchar(32)\",\"pharmacy_pref\":\"varchar(64)\",\"scattered_use_yn\":\"tinyint(4)\",\"print_status\":\"tinyint(4)\",\"ext_mess\":\"varchar(10000)\",\"system_type\":\"int(11)\",\"made_type\":\"tinyint(4)\",\"medical_insurance\":\"tinyint(4)\",\"product_validity\":\"varchar(64)\",\"drug_remind_id\":\"int(11)\",\"usage_and_dosage\":\"varchar(128)\",\"big_unit_id\":\"int(20)\",\"big_unit_bar_code\":\"varchar(256)\",\"middle_unit_id\":\"int(20)\",\"middle_unit_bar_code\":\"varchar(256)\",\"big_middle_unit_rate\":\"decimal(10,2)\",\"middle_small_unit_rate\":\"decimal(10,2)\",\"drug_permission_person\":\"varchar(64)\",\"check_mode\":\"tinyint(4)\",\"special_drug_manage\":\"tinyint(4)\",\"area_code\":\"varchar(32)\",\"manufacturer_code\":\"varchar(32)\",\"standard_code\":\"varchar(32)\",\"dose_channel\":\"varchar(50)\",\"usage\":\"varchar(50)\",\"per_dosage\":\"varchar(50)\",\"dosage_unit\":\"varchar(50)\",\"per_max_prescription\":\"varchar(50)\",\"transform_coefficient\":\"varchar(50)\",\"virtual_standard_library_id\":\"bigint(11)\",\"medical_insurance_level\":\"int(11)\",\"erp_code\":\"varchar(64)\",\"manage_attr\":\"int(11)\",\"logistics_attr\":\"tinyint(4)\",\"control_sales_yn\":\"tinyint(4)\",\"replacement_yn\":\"tinyint(4)\",\"management_attr\":\"int(11)\",\"six_levels\":\"varchar(128)\",\"brand_name\":\"varchar(128)\",\"indication\":\"varchar(255)\"},\"old\":null,\"pkNames\":[\"id\"],\"sql\":\"\",\"sqlType\":{\"id\":-5,\"pref\":12,\"mnemonic_code\":12,\"mixed_query\":12,\"common_name\":12,\"product_name\":12,\"standard_library_id\":-5,\"attribute_specification\":12,\"unit_id\":4,\"dosage_form_id\":-5,\"bar_code\":12,\"approval_number\":12,\"manufacturer\":12,\"producing_area\":12,\"product_type\":4,\"storage_condition\":4,\"abc_dividing\":4,\"shelf_position\":4,\"containing_hemp_yn\":4,\"prescription_classification\":4,\"prescription_yn\":-6,\"business_scope\":4,\"maintenance_type\":4,\"product_function_catagory\":4,\"income_tax_rate\":3,\"ouput_tax_rate\":3,\"retail_price\":3,\"vip_price\":3,\"cost_price\":3,\"score_product_yn\":-6,\"score_rate\":3,\"store_max_limit\":3,\"store_min_limit\":3,\"parent_product_id\":-5,\"status\":-6,\"scattered_yn\":-6,\"node_type\":-6,\"collect_type\":-6,\"freeze_type\":-6,\"create_type\":-6,\"create_user\":12,\"create_time\":93,\"update_user\":12,\"update_time\":93,\"yn\":-6,\"remark\":12,\"used\":-6,\"special\":-6,\"img_url\":12,\"last_purchase\":12,\"base_version\":12,\"organSign\":12,\"is_hidden\":-6,\"parent_pref\":12,\"pharmacy_pref\":12,\"scattered_use_yn\":-6,\"print_status\":-6,\"ext_mess\":12,\"system_type\":4,\"made_type\":-6,\"medical_insurance\":-6,\"product_validity\":12,\"drug_remind_id\":4,\"usage_and_dosage\":12,\"big_unit_id\":4,\"big_unit_bar_code\":12,\"middle_unit_id\":4,\"middle_unit_bar_code\":12,\"big_middle_unit_rate\":3,\"middle_small_unit_rate\":3,\"drug_permission_person\":12,\"check_mode\":-6,\"special_drug_manage\":-6,\"area_code\":12,\"manufacturer_code\":12,\"standard_code\":12,\"dose_channel\":12,\"usage\":12,\"per_dosage\":12,\"dosage_unit\":12,\"per_max_prescription\":12,\"transform_coefficient\":12,\"virtual_standard_library_id\":-5,\"medical_insurance_level\":4,\"erp_code\":12,\"manage_attr\":4,\"logistics_attr\":-6,\"control_sales_yn\":-6,\"replacement_yn\":-6,\"management_attr\":4,\"six_levels\":12,\"brand_name\":12,\"indication\":12},\"table\":\"saas_product_baseinfo\",\"ts\":1605666474232,\"type\":\"INSERT\"}".getBytes(RemotingHelper.DEFAULT_CHARSET));

			producer.send(msg);
		}
	}

	@GetMapping("/select")
	@RequiresPermissions("query")
	public String select(){
		RLock lock = redissonClient.getLock("1");
		lock.lock();
		//List<SaasMedicalProduct> saasMedicalProducts = mapper.listDataByIds(Arrays.asList(111732909L, 111732937L));
		return null;
	}

	@PostMapping("/importFile")
	public String importFile(UploadFile uploadFile){
		RLock lock = redissonClient.getLock("1");
		lock.lock();
		//List<SaasMedicalProduct> saasMedicalProducts = mapper.listDataByIds(Arrays.asList(111732909L, 111732937L));
		return null;
	}

	@PostMapping("/login")
	public String login(User user){
		if (StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getPassword())) {
			return "请输入用户名和密码！";
		}
		//用户认证信息
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
				user.getUserName(),
				user.getPassword()
		);
		try {
			//进行验证，这里可以捕获异常，然后返回对应信息
			subject.login(usernamePasswordToken);
//            subject.checkRole("admin");
//            subject.checkPermissions("query", "add");
		} catch (UnknownAccountException e) {
			log.error("用户名不存在！", e);
			return "用户名不存在！";
		} catch (AuthenticationException e) {
			log.error("账号或密码错误！", e);
			return "账号或密码错误！";
		} catch (AuthorizationException e) {
			log.error("没有权限！", e);
			return "没有权限";
		}
		return "login success";
	}


	@GetMapping("error")
	public String error(){
		return "登陆失败";
	}
}
