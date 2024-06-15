package uni.project.online.shop.model.paypal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class Token {
	@SerializedName("access_token")
	@JsonProperty("access_token")
	private String accessToken;
	@SerializedName("token_type")
	@JsonProperty("token_type")
	private String tokenType;
	@SerializedName("expires_in")
	@JsonProperty("expires_in")
	private Integer expiresIn;
	@SerializedName("refresh_token")
	@JsonProperty("refresh_token")
	private String refreshToken;
	@SerializedName("refresh_expires_in")
	@JsonProperty("refresh_expires_in")
	private Integer refreshExpiresIn;
	
	public Integer getRefreshExpiresIn() {
		return refreshExpiresIn;
	}
	public void setRefreshExpiresIn(Integer refreshExpiresIn) {
		this.refreshExpiresIn = refreshExpiresIn;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getTokenType() {
		return tokenType;
	}
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	public Integer getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	@Override
	public String toString() {
		return "Psd2Token [accessToken=" + accessToken + ", tokenType=" + tokenType + ", expiresIn=" + expiresIn
				+ ", refreshToken=" + refreshToken + "]";
	} 

}
