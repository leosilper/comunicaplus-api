package br.com.fiap.comunicaplus_api_main.dto;

import java.util.List;

import lombok.Data;

@Data
public class DeviceSummaryDTO {
    private String deviceName;
    private int messageCount;
    private List<String> messageContents;
}
