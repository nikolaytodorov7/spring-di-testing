package app.classes;

import org.springframework.stereotype.Component;

@Component("videoMessage")
public class VideoMessage implements Message {
    @Override
    public String format() {
        return "videoMessage";
    }
}
