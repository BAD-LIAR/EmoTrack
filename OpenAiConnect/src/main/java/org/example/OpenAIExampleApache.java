package org.example;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.example.dto.ChoicesDto;
import org.example.dto.ResponseDto;
import org.json.JSONObject;

public class OpenAIExampleApache {

    public static final String API_KEY = "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";

    public static void main(String[] args) throws IOException {
        // Установите ключ API
        String apiKey = API_KEY;

        // Установите движок OpenAI, который вы хотите использовать
        String engine = "text-davinci-003";

        // Установите параметры запроса для конечной точки "completions"
        String prompt = "Hello,\n" +
                "\n" +
                "Sure, I can generate data for you with the following parameters:\n" +
                "\n" +
                " userId = 2" +
                "Title\n" +
                "Description - description of the event\n" +
                "Emotional condition - emotional state after the event (from 1 to 10, often empty)\n" +
                "Start date - date and time of the event start\n" +
                "End date - date and time of the event end\n" +
                "Emotional condition for the day - emotional state before sleep\n" +
                "The records will be sparse (4-5 per day)." +
                " Generate as sql insert" +
                " 25 rows";

        int maxTokens = 2048;
        double temperature = 0.8;

        // Создайте объект HttpClient
        HttpClient httpClient = HttpClientBuilder.create().build();

        // Создайте объект HttpPost и установите URL-адрес
        HttpPost httpPost = new HttpPost("https://api.openai.com/v1/engines/" + engine + "/completions");

        // Установите заголовки запроса, включая ключ API
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Authorization", "Bearer " + apiKey);

        // Создайте объект JSON и установите параметры запроса
        JSONObject json = new JSONObject();
        json.put("prompt", prompt);
        json.put("max_tokens", maxTokens);
        json.put("temperature", temperature);

        // Установите тело запроса
        StringEntity entity = new StringEntity(json.toString());
        httpPost.setEntity(entity);
        File file = new File("C:\\Study\\diploma\\inserts.sql");
        PrintWriter printWriter = new PrintWriter(file);
        for (int i = 0; i < 7000; i+=25) {
            System.out.println("Start " + i);
            // Отправьте запрос и получите ответ
            HttpResponse response = httpClient.execute(httpPost);

            // Извлеките тело ответа
            String responseBody = EntityUtils.toString(response.getEntity());

            // Распечатайте ответ
            ObjectMapper objectMapper = new ObjectMapper();

            ResponseDto responseDto = objectMapper.readValue(responseBody, ResponseDto.class);

            String ins = responseDto.getChoices().stream()
                    .map(ChoicesDto::getText)
                    .collect(Collectors.joining());

            printWriter.println(ins);
            System.out.println("End " + (i + 25));
        }
        printWriter.close();
    }
}
