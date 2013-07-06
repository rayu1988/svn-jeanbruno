package test.jean.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.stereotype.Component;

import br.gov.go.sectec.portalemprego.web.actions.EmpresaAction;

import com.opensymphony.xwork2.ActionSupport;

/**
 * <p>
 * <b>Title:</b> EmpresaAction.java
 * </p>
 * 
 * <p>
 * <b>Description:</b> Classe responsável por controlar os dados da empresa
 * </p>
 * 
 * <p>
 * <b>Company: </b> Premium Consultoria e Sistemas
 * </p>
 * 
 * @author Silvânio
 * 
 * @version 1.0.0
 */
@ParentPackage(value = "portalemprego")
@Namespace("/testjean")
@Component("testJSONAction")
public class TestJSONAction extends ActionSupport implements SessionAware {
	
	private Map<String, Object> session;

	@Override
	public void setSession(Map<String, Object> arg0) {
		this.session = session;
	}
	
	public Map<String, Object> getSession() {
		return this.session;
	}

	// -- AjaxActions teste com JSON
	@Action(value = "/iniciarTestJSON", results = { @Result(location = "testJSON.page", type = "tiles", name = "success") })
	public String iniciarEmpresa() {
		return "success";
	}
	
    private String name;
    private String greeting;
    private List<String> countryList;
    private Map<String,Long> countryMap;
    
    @Action(value = "/sayHi", results = { @Result(name = "success", type = "json") })
    public String sayHi(){
        greeting="HI " +name;
        countryList=new ArrayList();
        countryList.add("US");
        countryList.add("UK");
        countryList.add("Russia");
        countryMap= new HashMap<String, Long>();
        countryMap.put("US",1L);
        countryMap.put("UK",2L);
        countryMap.put("Russia",3L);
        return ActionSupport.SUCCESS;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getGreeting() {
        return greeting;
    }
    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }
    public List<String> getCountryList() {
        return countryList;
    }
    public void setCountryList(List<String> countryList) {
        this.countryList = countryList;
    }
    public Map<String, Long> getCountryMap() {
        return countryMap;
    }
	
}
