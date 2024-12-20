package com.stress.data.controller;

import com.stress.data.service.KafkaProducerBufferedService;
import com.stress.data.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/")
public class MessageController {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private KafkaProducerBufferedService kafkaProducerBufferedService;

    @PostMapping("/send/{total}")
    public String sendMessage(@PathVariable int total) {
        String start = getCurrentTime();

        kafkaProducerService.processNumber(total);

        String end = getCurrentTime();

        return logTime(total, start, end);
    }

    @PostMapping("/sendBuffered/{total}")
    public String sendBufferedMessage(@PathVariable int total) {
        String start = getCurrentTime();

        kafkaProducerBufferedService.processBufferedNumber(total);

        String end = getCurrentTime();

        return logTime(total, start, end);
    }

    @PostMapping("/sendRandom/{total}")
    public String sendRandom(@PathVariable int total) {
        String start = getCurrentTime();

        kafkaProducerService.processRandomizedNumber(total);

        String end = getCurrentTime();

        return logTime(total, start, end);
    }

    private String logTime(Integer totalMessages, String start, String end) {
        return "totalMessages: " + totalMessages + "\nstart time: " + start + "\nend time: " + end + "\n";
    }

    private String getCurrentTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}

