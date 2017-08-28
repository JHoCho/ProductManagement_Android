package com.example.jaeho.productmanagement.Model.DAOS.NodeJS;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by jaeho on 2017. 7. 24..
 */

public class SendHttpWithMsg {
    private String sendedMsg;

    public SendHttpWithMsg() {
    }

    ;

    public SendHttpWithMsg(String url, String port, String subject, String contents) {

        sendedMsg = sendHttpWithMsg(url, port, subject, contents);
    }

    public String getSendedMsg() {
        return sendedMsg;
    }

    public String sendHttpWithMsg(String url, String port, String subject, String contents) {
        //기본적인 설정
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        HttpParams params = client.getParams();
        HttpConnectionParams.setConnectionTimeout(params, Integer.parseInt(port));
        HttpConnectionParams.setSoTimeout(params, Integer.parseInt(port));
        post.setHeader("Content-type", "application/json; charset=utf-8");
        // JSON OBject를 생성하고 데이터를 입력합니다.
        //여기서 제이슨 데이터가 만들어집니다.
        JSONObject jObj = new JSONObject();

        try {
            jObj.put("title", subject);
            jObj.put("description", contents);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        try {
            // JSON을 String 형변환하여 httpEntity에 넣어줍니다.
            StringEntity se;
            se = new StringEntity(jObj.toString());
            HttpEntity he = se;
            post.setEntity(he);
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        try {
            //httpPost 를 서버로 보내고 응답을 받습니다.
            HttpResponse response = client.execute(post);

            // 받아온 응답으로부터 내용을 받아옵니다.
            // 단순한 string으로 읽어와 그내용을 리턴해줍니다. 서버측에선 OK로 줄것으로 예상
            BufferedReader bufReader =
                    new BufferedReader(new InputStreamReader(
                            response.getEntity().getContent(),
                            "utf-8"
                    ));
            String line = null;
            String result = "";
            while ((line = bufReader.readLine()) != null) {
                result += line;
            }
            return result;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return "Error" + e.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error" + e.toString();
        }
    }
}