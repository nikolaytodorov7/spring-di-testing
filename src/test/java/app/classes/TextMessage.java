package app.classes;

import org.springframework.stereotype.Component;

@Component("textMessage")
public class TextMessage implements Message {
    @Override
    public String format() {
        return "textMessage";
    }
}
