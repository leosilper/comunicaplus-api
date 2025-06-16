package br.com.fiap.comunicaplus_api_main.mapper;

import org.springframework.stereotype.Component;

import br.com.fiap.comunicaplus_api_main.dto.DeviceDTO;
import br.com.fiap.comunicaplus_api_main.model.Device;

@Component
public class DeviceMapper {

    public DeviceDTO toDTO(Device device) {
        if (device == null) return null;

        DeviceDTO dto = new DeviceDTO();
        dto.setId(device.getId());
        dto.setDeviceName(device.getDeviceName());
        dto.setBluetoothAddress(device.getBluetoothAddress());
        dto.setWifiDirectAddress(device.getWifiDirectAddress());
        dto.setStatus(device.getStatus() != null ? device.getStatus().name() : null);
        dto.setTotalMessagesSent(device.getTotalMessagesSent());
        dto.setTotalMessagesReceived(device.getTotalMessagesReceived());
        return dto;
    }

    public Device toEntity(DeviceDTO dto) {
        if (dto == null) return null;

        Device device = new Device();
        device.setId(dto.getId());
        device.setDeviceName(dto.getDeviceName());
        device.setBluetoothAddress(dto.getBluetoothAddress());
        device.setWifiDirectAddress(dto.getWifiDirectAddress());
        // O status deve ser convertido manualmente se for enum:
        // device.setStatus(DeviceStatus.valueOf(dto.getStatus()));
        device.setTotalMessagesSent(dto.getTotalMessagesSent());
        device.setTotalMessagesReceived(dto.getTotalMessagesReceived());
        return device;
    }
}
