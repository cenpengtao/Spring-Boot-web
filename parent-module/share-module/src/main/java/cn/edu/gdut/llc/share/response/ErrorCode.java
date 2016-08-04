/**
 * 
 */
package cn.edu.gdut.llc.share.response;

/**
 * 错误代码
 * @author cpt72
 *
 */
public enum ErrorCode {
	
	/**
	 * 验证码错误
	 */
	ValidateCodeError("0x01"),
	/**
	 * 用户名或密码错误
	 */
	UsernameOrPasswordError("0x02"), 
	/**
	 * 无权访问
	 */
	Deny("0x03");
	
	private String code;
	private ErrorCode(String code)
	{
		this.code=code;
	}
	public String getCode() {
		return code;
	}
}
