package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.example.pojo.Login;
import com.example.user.GithubUser;
import com.example.util.ConstantsUtil;

@RestController
public class MyController {

	private static final Logger LOG = LoggerFactory.getLogger(MyController.class);

	@RequestMapping("/")
	public ModelAndView homePage(Model model, HttpSession session) {
		LOG.info("homePage()");

		return new ModelAndView("login", "login", new Login());
	}

	@GetMapping("/login")
	public ModelAndView loginForm(Model model, HttpSession session) {
		LOG.info("loginForm()");
		return new ModelAndView("login", "login", new Login());
	}

	@RequestMapping(value = "validateUser", method = RequestMethod.POST)
	public ModelAndView validateUser(ModelMap model, @ModelAttribute("login") @Valid Login login,
			BindingResult bindingResult, HttpSession session) {

		String loginUname = System.getenv("LOGIN_UNAME");
		String loginPwd = System.getenv("LOGIN_PWD");
		if ((login.getUserName().equalsIgnoreCase(loginUname) && login.getUserPwd().equals(loginPwd))) {
			session.setAttribute("userName", login.getUserName());
			login.setUserName(login.getUserName());
			return new ModelAndView("userLanding", "login", login);
		}
		if (!(login.getUserName().equalsIgnoreCase(loginUname)))
			bindingResult.rejectValue("userName", "error.user", ConstantsUtil.INVALID_USERNAME);
		if (!(login.getUserPwd().equals(loginPwd)))
			bindingResult.rejectValue("userPwd", "error.user", ConstantsUtil.INVALID_PASSWORD);
		List<FieldError> bindRes = new ArrayList<FieldError>();
		bindRes = bindingResult.getFieldErrors();

		for (FieldError emsg : bindingResult.getFieldErrors()) {
			if (LOG.isInfoEnabled()) {
				LOG.info("bindingResult err is " + emsg.getRejectedValue());
			}
		}

		return new ModelAndView("login", "login", login);
	}
	// LogOut functionality make httpsession values as empty and re-direct to logout

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logoutPage(HttpServletRequest request, HttpServletResponse response, Model model,
			HttpSession session)

	{
		LOG.info("Inside logoutPage()");

		org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		SecurityContextLogoutHandler ctxLogOut = new SecurityContextLogoutHandler();
		if (auth != null) {
			ctxLogOut.logout(request, response, auth);
		}
		model.addAttribute("ctxLogOut", ctxLogOut);

		return new ModelAndView("logout", "ctxLogOut", ctxLogOut);
	}

	@RequestMapping(value = "githubUserInfoPage", method = RequestMethod.POST)
	public ModelAndView githubUserInfoPage(Model model, Login login, BindingResult bindingResult, GithubUser user,
			HttpSession session) {
		GithubUser githubUser = user;
		String githubLink = "/" + login.getGithubusername();
		String githubFullLink = ConstantsUtil.GITHUB_USER_INFO + githubLink;
		List<FieldError> bindRes = new ArrayList<FieldError>();
		if (LOG.isInfoEnabled()) {
			LOG.info("GitHub Full Link is   : " + githubFullLink);
		}
		try {

			RestTemplate restTemplate = new RestTemplate();
			githubUser = restTemplate.getForObject(githubFullLink, GithubUser.class);

			if (!("".equals(login.getGithubusername())) && !("".equals(githubUser.getLogin()))
					&& (login.getGithubusername() != null) && (githubUser.getLogin() != null)
					&& (login.getGithubusername().equals(githubUser.getLogin()))) {
				if (LOG.isInfoEnabled()) {
					LOG.info("User Details:    " + githubUser.toString());
					LOG.info("user entered github user login name  -->" + login.getGithubusername());
					LOG.info(" github api user login name   -->" + githubUser.getLogin());
				}
				login.setGithubusername(login.getGithubusername());
				session.setAttribute("githublogin", githubUser.getLogin());
				session.setAttribute("githubUsrCreatedDt", githubUser.getCreatedAt());
				session.setAttribute("githubUsrlastUpdatedDt", githubUser.getUpdatedAt());
				session.setAttribute("githubpublicrepo", githubUser.getPublicRepos());

				return new ModelAndView("githubUserInfoPage", "githubUserInfoPage", githubUser);
			}

		} catch (HttpClientErrorException e) {

			LOG.error("HttpClientErrorException occurred ");

			bindingResult.rejectValue("githubusername", "error.user", ConstantsUtil.INVALID_GITUSER);
			bindRes = bindingResult.getFieldErrors();
			return new ModelAndView("userLanding", "login", login);
		} catch (Exception e) {

			LOG.error("Error occurred while accessing Git API ");
			return new ModelAndView("userLanding", "login", login);
		}

		return new ModelAndView("githubUserInfoPage", "githubUserInfoPage", githubUser);
	}

}
