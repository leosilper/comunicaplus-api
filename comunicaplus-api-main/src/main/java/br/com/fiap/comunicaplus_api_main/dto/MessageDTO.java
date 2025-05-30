package br.com.fiap.comunicaplus_api_main.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MessageDTO {
    private Long id;
    private String senderDeviceName;
    private String recipientDeviceName;
    private String content;
    private LocalDateTime timestamp;
    private boolean delivered;
    private boolean forwarded;
}
