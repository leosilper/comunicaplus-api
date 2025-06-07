package br.com.fiap.comunicaplus_api_main.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.comunicaplus_api_main.dto.DeviceSummaryDTO;
import br.com.fiap.comunicaplus_api_main.model.Device;
import br.com.fiap.comunicaplus_api_main.repository.DeviceRepository;
import br.com.fiap.comunicaplus_api_main.repository.MessageRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceRepository deviceRepository;
    private final MessageRepository messageRepository;

    // Endpoint para cadastrar novo dispositivo
    @PostMapping
    public ResponseEntity<Device> create(@RequestBody @Valid Device device) {
        Device saved = deviceRepository.save(device);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // Endpoint para resumo dos dispositivos
    @GetMapping("/summary")
    public List<DeviceSummaryDTO> listSummary() {
        return deviceRepository.findAll().stream().map(device -> {
            DeviceSummaryDTO dto = new DeviceSummaryDTO();
            dto.setDeviceName(device.getDeviceName());
            dto.setBluetoothAddress(device.getBluetoothAddress());
            dto.setWifiDirectAddress(device.getWifiDirectAddress());
            dto.setStatus(device.getStatus() != null ? device.getStatus().name() : null);

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
