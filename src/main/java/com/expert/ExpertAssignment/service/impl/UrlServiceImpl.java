package com.expert.ExpertAssignment.service.impl;

import com.expert.ExpertAssignment.exception.InvalidRequestException;
import com.expert.ExpertAssignment.model.UrlModel;
import com.expert.ExpertAssignment.model.UrlResponseModel;
import com.expert.ExpertAssignment.service.UrlService;
import com.expert.ExpertAssignment.util.Constant;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class UrlServiceImpl implements UrlService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    CloseableHttpClient httpClient = null;

    CloseableHttpResponse response = null;

    @Override
    public UrlResponseModel fetchDetailsFromURL(UrlModel urlModel) throws IOException {

        String responseBody = null;
        CloseableHttpResponse response = null;
        if (urlModel.getUrl().isEmpty() || urlModel.getUrl() == null) {
            throw new InvalidRequestException(Constant.URL_NOT_PRESENT);
        }
        try{
            response = sendPostRequest(urlModel.getUrl());
            printAllHeaders(response);
            responseBody = EntityUtils.toString(response.getEntity());
            printIfResponseIsHtml(responseBody);
            saveResponseInFile(urlModel, responseBody);
        }
        catch (Exception e) {
            throw new InvalidRequestException(e.getLocalizedMessage());
        }
        finally {
            if(response!=null) {
                response.close();
            }

            if(httpClient!=null){
                httpClient.close();
            }
        }
        return new UrlResponseModel(Constant.SUCCESS, Constant.RESP_MSG, responseBody);
    }

    private void saveResponseInFile(UrlModel urlModel, String responseBody) throws IOException {

        if(urlModel.getFileName().isEmpty() || urlModel.getFileName()==null){
            System.out.println(Constant.FILE_LOCATION_EMPTY);
            System.out.println(Constant.NO_FILE_CREATED + urlModel.getUrl());
        }
        else{
            final File file = new File(urlModel.getFileName());
            final FileWriter fileWriter = new FileWriter(file.getAbsolutePath());
            fileWriter.write(responseBody);
            fileWriter.close();
            System.out.println(Constant.RESPONSE_WRITE_SUCCESS + file.getAbsolutePath());
        }
    }

    private void printAllHeaders(CloseableHttpResponse response) {

        final List<HashMap<String, Object>> headers = objectMapper.convertValue(response.getAllHeaders(), List.class);
        headers.stream().forEach(header ->{
            System.out.println(header.get(Constant.NAME).toString()+"        "+header.get(Constant.VALUE).toString());
        });
    }

    private void printIfResponseIsHtml(String responseBody) {

        final Pattern htmlPattern = Pattern.compile(Constant.HTML_REGEX, Pattern.DOTALL);
        final boolean isHTML = htmlPattern.matcher(responseBody).matches();
        if (isHTML) {
            final Document doc = Jsoup.parse(responseBody);
            final String title = doc.title();
            System.out.println(Constant.TITLE_IS + title);
        }
    }

    private CloseableHttpResponse sendPostRequest(String url) throws IOException {

        try {
            final HttpGet httpGet = new HttpGet(url);
            httpClient = HttpClients.createDefault();
            response = httpClient.execute(httpGet);
        }
        catch (Exception e){
            System.out.println(Constant.URL_EXECUTION_ERROR);
            throw new InvalidRequestException(Constant.URL_EXECUTION_ERROR+ "       "+e.getLocalizedMessage());
        }
        return response;
    }
}
