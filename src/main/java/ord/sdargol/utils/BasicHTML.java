package ord.sdargol.utils;

public class BasicHTML {
    public static String generate(String bodyHtml){
        return "<!DOCTYPE html>\n" +
                "<html lang=\"ru\">\n" +
                "  <head>\n" +
                "    <meta charset=\"utf-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\n" +
                "    <title>Bank Card App</title>\n" +
                "  </head>\n" +
                "  <body>\n" +
                bodyHtml + "\n" +
                "  </body>\n" +
                "</html>\n";
    }
}
