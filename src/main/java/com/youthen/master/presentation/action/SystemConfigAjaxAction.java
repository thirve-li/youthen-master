package com.youthen.master.presentation.action;

import java.util.Properties;
import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import com.youthen.framework.common.ConfigFileHolder;
import com.youthen.framework.common.context.SessionContext;
import com.youthen.framework.presentation.action.AbstractAjaxAction;
import com.youthen.master.service.LoginUserService;
import com.youthen.master.service.dto.LoginUserDto;

@Namespace("/mst-config")
@Controller
public class SystemConfigAjaxAction extends AbstractAjaxAction {

    private static final long serialVersionUID = 5777011856331394619L;

    @Resource
    LoginUserService LoginUserService;

    @Override
    protected Object doExecute(final Object aArgs) throws Exception {
        final String filePath = "webmessage.properties";
        ConfigFileHolder.setFilePath(filePath);
        String result = ConfigFileHolder.getString("config.smtTest.fail");
        final JavaMailSenderImpl sender = new JavaMailSenderImpl();
        final MimeMessage mm = sender.createMimeMessage();
        try {
            final MimeMessageHelper mmh = new MimeMessageHelper(mm, true, "utf-8");
            sender.setHost(ConfigFileHolder.getString("mail.smtpserver.ip"));
            sender.setUsername(ConfigFileHolder.getString("mail.username"));
            sender.setPassword(ConfigFileHolder.getString("mail.password"));
            sender.setPort(Integer.parseInt(ConfigFileHolder.getString("mail.smtpserver.port")));
            final Properties prop = new Properties();
            prop.setProperty("mail.smtp.host", ConfigFileHolder.getString("mail.smtpserver.ip"));
            prop.setProperty("mail.smtp.auth", "true");
            sender.setJavaMailProperties(prop);
            final String mailFrom = ConfigFileHolder.getString("mail.from");
            final HttpServletRequest request = ServletActionContext.getRequest();
            final LoginUserDto userDto = this.LoginUserService.getById(SessionContext.getUser().getUserId());

            String mailTo = request.getParameter("mailTo");// 当前登录用户的邮箱:这个参数不空,就是用户管理中的邮件测试;如果为空,就是系统管理的邮件测试
            if (StringUtils.isEmpty(mailTo) && userDto != null) {
                mailTo = userDto.getEmail();
            }

            mmh.setTo(mailTo);
            mmh.setFrom(mailFrom);
            mmh.setSubject("Sisqp mail test");
            mmh.setText("Sisqp mail test successfully!");
            sender.send(mm);
            result = ConfigFileHolder.getString("config.smtTest.success");
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
