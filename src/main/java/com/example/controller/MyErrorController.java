package com.example.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController implements ErrorController {

	private static final Logger LOG = LoggerFactory.getLogger(MyErrorController.class);

	@RequestMapping("/error")
	public String handleError(HttpServletRequest request) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		if (status != null) {
			LOG.error("Error Page Request Handle!!! ");
			LOG.error("Error Status Code -->" + status);
		}
		return "error";
	}

	@Override
	public String getErrorPath() {
		return null;
	}
}