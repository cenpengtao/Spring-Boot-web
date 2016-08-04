/**
 * 
 */
package cn.edu.gdut.llc.share.response;

import java.io.Serializable;

/**
 * @author cpt72
 *
 */
public class ResponseMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	private boolean status;
	private String errorCode;
	private String message;
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(ErrorCode errorCode) {
		if(errorCode!=null)
			this.errorCode = errorCode.getCode();
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getJsonStr()
	{
		return "{\"status\": \""+status+"\",\"errorCode\": \""+errorCode+"\",\"message\": \""+message+"\"}";
	}
}
