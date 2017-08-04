package chat2;

import org.red5.logging.Red5LoggerFactory;
import org.red5.server.net.rtmpt.RTMPTServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by DodolinEL
 * date: 22.05.2017
 */
public class ChatRTMPTServlet extends RTMPTServlet {

    private static final org.slf4j.Logger logger = Red5LoggerFactory.getLogger(ChatRTMPTServlet.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("********* SERVICE ChatRTMPTServlet ***********");
        super.service(req, resp);
    }
}
