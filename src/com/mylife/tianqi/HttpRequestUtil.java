package com.mylife.tianqi;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by zhangyong on 14-10-12.
 * http request 相关工具类
 */
public class HttpRequestUtil {

    private static int DEFAULT_CONNECT_TIME_OUT = 30000;//链接超时设置 30s

    private static int DEFAULT_READ_TIME_OUT = 30000;   //读取时间超时时间 30s

    private static String DEFAULT_ENCODING = "UTF-8";

    //请求方法
    private enum RequestMethod {
        GET,
        POST;
    }

    public static String doGet(String url) {
        return doRequest(url, RequestMethod.GET, null, null, DEFAULT_CONNECT_TIME_OUT, DEFAULT_READ_TIME_OUT, DEFAULT_ENCODING, DEFAULT_ENCODING);
    }

    public static String doRequest(String url,
                                   RequestMethod requestMethod,
                                   Map<String, String> parameters,
                                   String[] requestProperties,
                                   int conntectTimeOut,
                                   int readTimeOut, String requestEncoding,
                                   String responseEncoding) {

        HttpURLConnection connection = null;
        String responseContent = null;
        try {
            //提交的参数相关
            StringBuilder params = new StringBuilder();
            if (parameters != null) {
                for (Map.Entry<String, String> element : parameters.entrySet()) {
                    params.append(element.getKey().toString());
                    params.append("=");
                    params.append(URLEncoder.encode(element.getValue().toString(),
                            requestEncoding));
                    params.append("&");
                }

                if (params.length() > 0) {
                    params = params.deleteCharAt(params.length() - 1);
                }
            }

            //解析url中的参数
            int comaIndex = url.indexOf('?');
            String baseUrl = null;
            if (comaIndex == -1) {
                baseUrl = url;
            } else {
                baseUrl = url.substring(0, comaIndex);
                String getParams = url.substring(comaIndex + 1, url
                        .length());
                String[] paramArray = getParams.split("&");
                for (int i = 0; i < paramArray.length; i++) {
                    String string = paramArray[i];
                    int tempIndex = string.indexOf("=");
                    if (tempIndex > 0) {
                        String parameter = string.substring(0, tempIndex);
                        String value = string.substring(tempIndex + 1, string
                                .length());
                        params.append(parameter);
                        params.append("=");
                        params
                                .append(URLEncoder.encode(value,
                                        requestEncoding));
                        params.append("&");
                    }
                }
                params = params.deleteCharAt(params.length() - 1);
            }

            //设置查询链接
            String httpUrl = null;
            if (RequestMethod.GET == requestMethod) {
                httpUrl = baseUrl + "?" + params;
            } else {
                httpUrl = baseUrl;
            }

            connection = (HttpURLConnection) new URL(httpUrl).openConnection();
            connection.setConnectTimeout(conntectTimeOut);
            connection.setReadTimeout(readTimeOut);

            //设置请求属性 比如agent之类
            if (requestProperties != null) {
                for (int i = 0; i < requestProperties.length - 1; i += 2) {
                    connection.addRequestProperty(requestProperties[i], requestProperties[i + 1]);
                }
            }

            //POST 设置dopost方法
            if (requestMethod == RequestMethod.POST) {
                connection.setDoOutput(true);
            }
            connection.connect();

            //若是post则发送数据
            if (RequestMethod.POST == requestMethod) {
                if (params.length() > 0 && params.indexOf("&") > -1) {
                    byte[] b = params.toString().getBytes();
                    connection.getOutputStream().write(b, 0, b.length);
                    connection.getOutputStream().flush();
                    connection.getOutputStream().close();
                }
            }

            InputStream in = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(in,
                    responseEncoding));
            String tempLine = rd.readLine();
            StringBuilder temp = new StringBuilder();
            String crlf = System.getProperty("line.separator");
            while (tempLine != null) {
                temp.append(tempLine);
                temp.append(crlf);
                tempLine = rd.readLine();
            }
            responseContent = temp.toString();
            rd.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return responseContent;
    }

    public static void main(String[] args) {
        String url = "http://cdn.weather.hao.360.cn/sed_api_weather_info.php?code=101010100&v=2&param=weather&app=hao360&_jsonp=__jsonp10__&t=2355130";
        System.out.println(doGet(url));
    }

}
