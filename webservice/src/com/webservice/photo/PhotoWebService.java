package com.webservice.photo;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.springframework.stereotype.Service;

import com.webservice.photo.service.IPhotoService;
import com.webservice.utils.Config;
import com.webservice.utils.SpringContextUtil;

@Service("photoWebService")
@WebService(serviceName = "PhotoWebService", targetNamespace = "http://photo.webservice.com/")
public class PhotoWebService {

	@Resource
	private Config config;

	@Resource
	private WebServiceContext wsContext;

	@WebMethod
	public String findPhotoByZPH(@WebParam(name = "key") String key,
			@WebParam(name = "serviceName") String serviceName,
			@WebParam(name = "way") String way) {
		IPhotoService service = SpringContextUtil.getBean(serviceName,
				IPhotoService.class);
		if (service != null) {
			if (way.toLowerCase().equals(config.getGmsfhm_key().toLowerCase())) {
				return service.findPhotoByGMSFHM(key);
			} else if (way.toLowerCase().equals(
					config.getZph_key().toLowerCase())) {
				return service.findPhotoByZPH(key);
			}
		}
		String clientInfo = getClientInfo();
		System.out.println(clientInfo);
		return "";
	}

	private String getClientInfo() {
		MessageContext mc = wsContext.getMessageContext();
		HttpServletRequest request = (HttpServletRequest) (mc
				.get(MessageContext.SERVLET_REQUEST));
		String remortAddress = request.getRemoteAddr();
		return (remortAddress);
	}

}
