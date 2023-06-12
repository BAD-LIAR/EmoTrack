package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.example.dto.ChoicesDto;
import org.example.dto.ResponseDto;

import java.util.stream.Collectors;

public class OpenAIExample {

    public static final String API_KEY = "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";
    public static final String REQ = "Привет! " +
            "Сгенерируй мне данные со следующими параметрами: " +
            "tittle " +
            "description - описание события " +
            "emotional_condition - эмоциональное состояние после события (от 1 до 10, часто пустое) " +
            "start_date - дата и время начала события " +
            "end_date - дата и время окончания события. " +
            "emotional_condition_for_day - эмоциональное состояние перед сном. " +
            "Записи должны быть скученными (2-3 за день). " +
            "представь в виде sql insert";

    public static void main(String[] args) throws Exception {
        // Ваш API-ключ OpenAI
        String apiKey = API_KEY;

        // Текст запроса, который нужно отправить
        String query = "Какой смысл жизни?";

        // URL-адрес API OpenAI для отправки запроса
        String apiUrl = "https://api.openai.com/v1/engines/text-davinci-003/completions";

        // Создаем объект OkHttpClient для отправки запросов
        OkHttpClient client = new OkHttpClient();

        // Создаем объект запроса
        MediaType mediaType = MediaType.parse("application/json");
        String requestBody = "{\"prompt\": \"" + "Ты можешь сгенерировать датасет?" + "\", \"temperature\": 0.7}";
        RequestBody body = RequestBody.create(mediaType, requestBody);
        Request request = new Request.Builder()
                .url(apiUrl)
                .post(body)
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .build();

        // Отправляем запрос к API OpenAI
        Response response = client.newCall(request).execute();

        // Читаем ответ от API OpenAI
        String responseString = response.body().string();

        ObjectMapper objectMapper = new ObjectMapper();

        ResponseDto responseDto = objectMapper.readValue(responseString, ResponseDto.class);

        responseDto.getChoices().stream()
                .map(ChoicesDto::getText)
                .forEach(System.out::println);


    }
}
