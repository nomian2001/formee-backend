package dtapcs.springframework.Formee.helper;

public class SendEmailRunnable implements Runnable {
    private String title;
    private String content;
    private String recipients;

    public SendEmailRunnable(String title, String content, String recipients) {
        this.title = title;
        this.content = content;
        this.recipients = recipients;
    }

    @Override
    public void run() {
        SendEmailSSL.SendEmail(title, content, recipients);
    }
}
