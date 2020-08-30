package pojo;

import java.util.List;

public class PojoCourses {

	//Now because these three fields are again Nested Jsons, so will create three more classes for each of these
	private List<PojoWebAutomation> webAutomation; //Because this is an Array, let's wrap class name in List<>
	private List<PojoApi> api;
	private List<PojoMobile> mobile;
	
	public List<PojoWebAutomation> getWebAutomation() {
		return webAutomation;
	}
	public void setWebAutomation(List<PojoWebAutomation> webAutomation) {
		this.webAutomation = webAutomation;
	}
	public List<PojoApi> getApi() {
		return api;
	}
	public void setApi(List<PojoApi> api) {
		this.api = api;
	}
	public List<PojoMobile> getMobile() {
		return mobile;
	}
	public void setMobile(List<PojoMobile> mobile) {
		this.mobile = mobile;
	}
}
