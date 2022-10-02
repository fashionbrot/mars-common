package com.github.fashionbrot;

/**
 * @author fashionbrot
 */
public class HttpResult {

    public int code;

    public String responseBody;

    public boolean isSuccess(){
        return 200 == this.code;
    }

    public HttpResult(int code, String responseBody) {
        this.code = code;
        this.responseBody = responseBody;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "code=" + code +
                ", responseBody='" + responseBody + '\'' +
                '}';
    }
}
