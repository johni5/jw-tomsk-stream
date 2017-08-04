package com.del.jws.server.ctlr;

import com.del.jws.red5.AppUtils;
import com.del.jws.server.db.Meeting;
import com.del.jws.server.db.User;
import com.del.jws.server.db.UserRole;
import com.del.jws.server.security.AuthUtils;
import com.del.jws.server.security.ClientType;
import com.del.jws.server.service.UserService;
import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dodolinel
 * date: 20.03.2017
 */
@Controller
@RequestMapping("/")
@SessionAttributes({"roles"})
public class BaseController {

    static final Logger logger = Red5LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;

    @Autowired
    private ApplicationContext appContext;

    @Autowired
    AuthenticationTrustResolver authenticationTrustResolver;

    @RequestMapping(value = {"/list"}, method = RequestMethod.GET)
    public String listUsers(ModelMap model) {
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "userslist";
    }

    @RequestMapping(value = {"/admin"}, method = RequestMethod.GET)
    public String admin(ModelMap model) {
        return "admin";
    }

    @RequestMapping(value = {"/publish"}, method = RequestMethod.GET)
    public String publish(ModelMap model) {
        User user = getUser();
        List<Meeting> activeMeetings = userService.listActiveMeetings(user.getId());
        Meeting currentMeeting;
        if (activeMeetings != null && !activeMeetings.isEmpty()) {
            currentMeeting = activeMeetings.iterator().next();
        } else {
            currentMeeting = userService.beginMeeting(user.getId());
        }
        model.addAttribute("meeting", currentMeeting);
        prepareFlashClient(model, ClientType.RECORD, currentMeeting);
        return "publish";
    }

    @RequestMapping(value = {"/meetings"}, method = RequestMethod.GET)
    public String meetings(ModelMap model) {
        List<Meeting> meeting = userService.listActiveMeetings(null);
        model.addAttribute("meetings", meeting);
        return "meetings";
    }

    @RequestMapping(value = {"/subscribe-{id}"}, method = RequestMethod.GET)
    public String subscribe(@PathVariable Long id, ModelMap model) {
        logger.debug("Subscribe to: " + id);
        Meeting meeting = userService.findMeetingById(id);
        prepareFlashClient(model, ClientType.VIEW, meeting);
        model.addAttribute("redirectCloseUrl", "end-subscribe-" + id);
        return "subscribe";
    }

    @RequestMapping(value = {"/end-subscribe-{id}"}, method = RequestMethod.GET)
    public String endSubscribe(@PathVariable Long id, ModelMap model) {
        logger.debug("Subscribe to: " + id);
        Meeting meeting = userService.findMeetingById(id);
        if (meeting.getEndDate() != null) {
            model.addAttribute("success", "Трансляция завершена");
        } else {
            model.addAttribute("warn", "Трансляция прервана");
        }
        return meetings(model);
    }

    @RequestMapping(value = {"/end-meeting-{id}"}, method = RequestMethod.GET)
    public String endMeeting(@PathVariable Long id, ModelMap model) {
        logger.debug("End meeting: " + id);
        userService.endMeeting(id);
        model.addAttribute("success", "Трансляция завершена");
        return "info";
    }

    private void prepareFlashClient(ModelMap model, ClientType clientType, Meeting meeting) {
        try {
            model.addAttribute("host", "rtmp://" + request.getServerName() + "/jws-stream");
            model.addAttribute("nsName", AppUtils.codeMeetingId(meeting.getId()));
            model.addAttribute("clientType", clientType.name());
            User user = getUser();
            model.addAttribute("guid", AuthUtils.getGuid(
                    user.getLogin(),
                    user.getPassword()
            ));
            model.addAttribute("login", user.getLogin());
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(), e);
            model.addAttribute("error", e.getMessage());
        }
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(ModelMap model) {
//        ApplicationContext appContext = SecurityWebApplicationContextUtils.
//                findRequiredWebApplicationContext(request.getSession().getServletContext());
//        logger.info("Detect app context: " + appContext.getApplicationName());
//        logger.info("\t\t >> " + appContext.getId());
//        logger.info("\t\t >> " + appContext.getDisplayName());
//        ApplicationContext parent = appContext.getParent();
//        if (parent != null) {
//            logger.info("\t\t parent:  " + parent.getApplicationName());
//            logger.info("\t\t\t\t >>  " + parent.getId());
//            logger.info("\t\t\t\t >>  " + parent.getDisplayName());
//        }
//
//        String[] names = appContext.getBeanDefinitionNames();
//        logger.info(" ****************************** " );
//        for (String name : names) {
//            logger.info(name + " >>  " + appContext.getBean(name));
//        }
//        logger.info(" ****************************** " );
//
//        Map<String, SecurityExpressionHandler> handlers = appContext
//                .getBeansOfType(SecurityExpressionHandler.class);
//        for (SecurityExpressionHandler h : handlers.values()) {
//            logger.info("Detect: " + h);
//            if (FilterInvocation.class.equals(GenericTypeResolver.resolveTypeArgument(
//                    h.getClass(), SecurityExpressionHandler.class))) {
//                logger.info("Found: " + h);
//            }
//        }
        if (!isCurrentAuthenticationAnonymous()) {
            model.addAttribute("user", getUser());
            return "welcome";
        } else {
            return "login";
        }
    }

    @RequestMapping(value = {"/newuser"}, method = RequestMethod.GET)
    public String newUser(ModelMap model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("edit", false);
        return "registration";
    }

    @RequestMapping(value = {"/newuser"}, method = RequestMethod.POST)
    public String saveUser(@Valid User user,
                           BindingResult result,
                           ModelMap model) {
        if (result.hasErrors()) {
            return "registration";
        }
        if (!userService.isUserLoginUnique(user.getId(), user.getLogin())) {
            FieldError uniqueLoginError = new FieldError("user", "login", "Пользователь с логином '" + user.getLogin() + "' уже существует!");
            result.addError(uniqueLoginError);
            return "registration";
        }
        user.setLastIp(request.getServerName());
        userService.saveUser(user);
        model.addAttribute("success", "Пользователь " + user.getFirstName() + " " + user.getLastName() + " успешно создан");
        return listUsers(model);
    }

    @RequestMapping(value = {"/edit-user-{login}"}, method = RequestMethod.GET)
    public String editUser(@PathVariable String login, ModelMap model) {
        User user = userService.findByLogin(login);
        model.addAttribute("user", user);
        model.addAttribute("edit", true);
        return "registration";
    }

    @RequestMapping(value = {"/edit-user-{login}"}, method = RequestMethod.POST)
    public String updateUser(@Valid User user,
                             BindingResult result,
                             ModelMap model,
                             @PathVariable String login) {

        if (result.hasErrors()) {
            return "registration";
        }
        userService.updateUser(user);
        model.addAttribute("success", "Пользователь " + user.getFirstName() + " " + user.getLastName() + " успешно обновлен");
        return listUsers(model);
    }

    @RequestMapping(value = {"/delete-user-{login}"}, method = RequestMethod.GET)
    public String deleteUser(@PathVariable String login) {
        userService.deleteUserByLogin(login);
        return "redirect:/list";
    }

    @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
        return "access-denied";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        if (isCurrentAuthenticationAnonymous()) {
            return "login";
        } else {
            try {
                User u = getUser();
                u.setLastIp(request.getServerName());
                logger.debug("Save last IP: " + request.getRemoteAddr() + " for: " + u.getLogin());
                userService.updateUser(u);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            return "redirect:/welcome";
        }
    }

    @RequestMapping(value = "/not_found", method = RequestMethod.GET)
    public String handleResourceNotFoundException(ModelMap model) {
        return "page-not-found";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            persistentTokenBasedRememberMeServices.logout(request, response, auth);
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return "redirect:/login?logout";
    }

    @ModelAttribute("roles")
    public List<UserRole> initializeRoles() {
        return new ArrayList<UserRole>(Arrays.asList(UserRole.values()));
    }

    public User getUser() {
        String userName;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userService.findByLogin(userName);
    }

    private boolean isCurrentAuthenticationAnonymous() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authenticationTrustResolver.isAnonymous(authentication);
    }


}
