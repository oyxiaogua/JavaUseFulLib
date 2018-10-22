package com.bean;

import com.google.gson.annotations.SerializedName;

public class BookBean {
	@SerializedName(value = "downloadUrl", alternate = { "downloadurl", "DownloadUrl", "Downloadurl" })
	private String downloadUrl;
	@SerializedName(value = "versionCode", alternate = { "versioncode", "VersionCode", "Versioncode" })
	private String versionCode;
	@SerializedName(value = "versionDes", alternate = { "VersionDes", "versiondes", "Versiondes" })
	private String versionDes;
	@SerializedName(value = "versionName", alternate = { "VersionName", "versionname", "Versionname" })
	private String versionName;

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionDes() {
		return versionDes;
	}

	public void setVersionDes(String versionDes) {
		this.versionDes = versionDes;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String toString() {
		return "BookBean [downloadUrl=" + downloadUrl + ", versionCode=" + versionCode + ", versionDes=" + versionDes
				+ ", versionName=" + versionName + "]";
	}
	

}
