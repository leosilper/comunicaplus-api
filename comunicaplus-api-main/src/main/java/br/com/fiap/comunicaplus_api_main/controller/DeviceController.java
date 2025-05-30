package br.com.fiap.comunicaplus_api_main.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.comunicaplus_api_main.dto.DeviceSummaryDTO;
import br.com.fiap.comunicaplus_api_main.repository.DeviceRepository;
import br.com.fiap.comunicaplus_api_main.repository.MessageRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceRepository deviceRepository;
    private final MessageRepository messageRepository;

    @GetMapping("/summary")
    public List<DeviceSummaryDTO> listSummary() {
        return deviceRepository.findAll().stream().map(device -> {
            DeviceSummaryDTO dto = new DeviceSummaryDTO();
            dto.setDeviceName(device.getDeviceName());  

            // Filtra mensagens onde o device é o remetente (sender)
            var messages = messageRepository.findAll().stream()
                .filter(m -> m.getSender() != null && m.getSender().getId().equals(device.getId()))
                .collect(Collectors.toList());

            dto.setMessageCount(messages.size());
            dto.setMessageContents(messages.stream()
                .map(m -> m.getContent())
                .collect(Collectors.toList()));

            return dto;
        }).collect(Collectors.toList());
    }
}
