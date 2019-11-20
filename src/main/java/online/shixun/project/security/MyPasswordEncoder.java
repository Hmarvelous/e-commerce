package online.shixun.project.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import online.shixun.project.core.Constant;
import online.shixun.project.util.MD5Utils;

/**
 * 自定义密码加密类
 * @author am
 *
 */
public class MyPasswordEncoder implements PasswordEncoder {

	/**
	 * 密码加密
	 */
	@Override
	public String encode(CharSequence rawPassword) {
		// 进行MD5加密
		return MD5Utils.stringToMD5(rawPassword + Constant.PASSWORD_SALT);
	}

	/**
	 * 密码对比(加密之后的)
	 */
	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		// 使用MD5进行加密对比
		return encodedPassword.equals(MD5Utils.stringToMD5(rawPassword + Constant.PASSWORD_SALT));
	}

}
