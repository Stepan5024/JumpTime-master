package com.example.p.jumptime.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.p.jumptime.R;

import javax.mail.Session;


public class MessageApp extends Fragment implements OnClickListener {

    Session session = null;
    ProgressDialog pdialog = null;
    Context context = null;
    EditText reciep, sub, msg;
    String rec, subject, textMessage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send, container, false);
        context = getContext();

        Button login = (Button) view.findViewById(R.id.btn_submit);
        reciep = (EditText) view.findViewById(R.id.et_to);
        sub = (EditText) view.findViewById(R.id.et_sub);
        msg = (EditText) view.findViewById(R.id.et_text);

        login.setOnClickListener(this);
        return view;

    }
    private void sendEmail(){
        String user_email = reciep.getText().toString();

        String email = "bokarevstepan1@gmail.com";
        String subject = sub.getText().toString();
        String message = msg.getText().toString() + " /n " + user_email;

        SendMail sm = new SendMail(getContext(), email, subject, message);
        sm.execute();


    }
    @Override
    public void onClick(View v) {

        sendEmail();

       /* rec = reciep.getText().toString();
        subject = sub.getText().toString();
        textMessage = msg.getText().toString();

        Properties props = new Properties();
        //конфиг для gmail
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("bokarevstepan1@gmail.com", "stepan11");
            }
        });

        pdialog = ProgressDialog.show(context, "", "Sending Mail...", true);

        RetreiveFeedTask task = new RetreiveFeedTask();
        task.execute();*/
    }

    /*class RetreiveFeedTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("test@yandex.ru"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(rec));
                message.setSubject(subject);
                message.setContent(textMessage, "text/html; charset=utf-8");
                Transport.send(message);
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            pdialog.dismiss();
            reciep.setText("");
            msg.setText("");
            sub.setText("");
            Toast.makeText(getContext(), "Message sent", Toast.LENGTH_LONG).show();
        }
    }
*/
}