package ru.gvozdev.emotrack.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.python.core.Py;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.springframework.stereotype.Service;
import ru.gvozdev.emotrack.dto.EventDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.python.core.PySystemState;


@Service
@RequiredArgsConstructor
public class PythonService {

    private final ObjectMapper objectMapper;

//    public Long run(EventDto eventDto){
//
////        PythonInterpreter.initialize(System.getProperties(), System.getProperties(), new String[0]);
////        System.setProperty("python.import.site", "false");
////
////        System.setProperty("python.home", "C:\\Users\\ngvozdev\\.gradle\\caches\\modules-2\\files-2.1\\org.python\\jython\\2.7.3\\7f9276f39cd150629caeb8708c3bbab42b604acc\\jython-2.7.3.jar");
////        PythonInterpreter.initialize(System.getProperties(), System.getProperties(), new String[0]);
//
//
//        PythonInterpreter interpreter = new PythonInterpreter();
//        interpreter.execfile("C:\\Study\\diploma\\EmoTrackRegressionSystem\\pred.py"); // загружаем файл test.py
//
//        // создаем DataFrame в Java
//        List<Map<String, Object>> data = new ArrayList<>();
//        Map<String, Object> row1 = objectMapper.convertValue(eventDto, Map.class);
//        data.add(row1);
////        Map<String, Object> row2 = new HashMap<>();
////        row2.put("id", 2);
////        row2.put("value1", 4);
////        row2.put("value2", 5);
////        data.add(row2);
//
//        // передаем DataFrame в функцию process_data на Python
//        PyObject processFunc = interpreter.get("predict");
//        PyObject[] pyArgs = new PyObject[] {Py.java2py(data)};
//        PyObject result = processFunc.__call__(pyArgs);
//
//        // преобразуем результат обратно в Java объект
//        List<List<String>> processedData = (List<List<String>>) result.__tojava__(List.class);
//        System.out.println(processedData);
//        return Long.valueOf(processedData.get(0).get(0));
//    }

    public Long run(EventDto eventDto) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("C:\\Users\\ngvozdev\\AppData\\Local\\Programs\\Python\\Python39\\python.exe", "C:\\Study\\diploma\\EmoTrackRegressionSystem\\pred.py", objectMapper.writeValueAsString(eventDto));
        Process p = pb.start();
        p.waitFor();
        InputStream stdoutErr = p.getErrorStream();
        BufferedReader readerErr = new BufferedReader(new InputStreamReader(stdoutErr));
        String lineErr = readerErr.readLine();
        System.out.println(lineErr);
        InputStream stdout = p.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));
        String line = reader.readLine();
        List list = objectMapper.readValue(line, List.class);
        Long result = Long.parseLong((String) list.get(0));
        return result;
    }
}
