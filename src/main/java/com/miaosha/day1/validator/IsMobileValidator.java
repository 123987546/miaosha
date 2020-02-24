package com.miaosha.day1.validator;
import  javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.miaosha.day1.utils.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;



public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

	private boolean required = false;

	/**
	 * 获取注解，看注解是不是必须的
	 * @param constraintAnnotation
	 */
	public void initialize(IsMobile constraintAnnotation) {
		required = constraintAnnotation.required();
	}

	/**
	 * 检验被注解的值知否合法（为空？。。。）
	 * @param value
	 * @param context
	 * @return
	 */
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(required) {
			return ValidatorUtil.isMobile(value);
		}else {
			if(StringUtils.isEmpty(value)) {
				return true;
			}else {
				return ValidatorUtil.isMobile(value);
			}
		}
	}

}
