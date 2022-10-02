package com.expert.ExpertAssignment;

import com.expert.ExpertAssignment.model.UrlModel;
import com.expert.ExpertAssignment.service.UrlService;
import com.expert.ExpertAssignment.util.Constant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class ExpertAssignmentApplication {

	public static void main(String[] args) throws IOException {
		final ConfigurableApplicationContext configurableApplicationContext = SpringApplication
				.run(ExpertAssignmentApplication.class, args);
		final UrlService urlService = configurableApplicationContext.getBean(UrlService.class);
		if(args!=null && args.length>0) {
			for (String arg : args) {
				final String[] urlAndFile = arg.split(",");
				String url = null;
				String fileName = null;
				if(urlAndFile.length==2){
					fileName = urlAndFile[1];
				}
				url = urlAndFile[0];
				urlService.fetchDetailsFromURL(new UrlModel(url, fileName));
			}
		}
		else{
			System.out.println(Constant.PLEASE_ENTER_URL);
		}
	}

}
