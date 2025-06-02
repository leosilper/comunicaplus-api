package br.com.fiap.comunicaplus_api_main.config;

import br.com.fiap.comunicaplus_api_main.model.*;
import br.com.fiap.comunicaplus_api_main.repository.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder {

    private final DeviceRepository deviceRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @PostConstruct
    public void seedDatabase() {

        // Usuários
        if (userRepository.count() == 0) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            List<User> users = List.of(
                    User.builder()
                            .name("Alice")
                            .email("alice@email.com")
                            .password(encoder.encode("123456"))
                            .deviceId("device-alice-001")
                            .role(Role.USER)
                            .build(),
                    User.builder()
                            .name("Bob")
                            .email("bob@email.com")
                            .password(encoder.encode("123456"))
                            .deviceId("device-bob-001")
                            .role(Role.USER)
                            .build(),
                    User.builder()
                            .name("Carol")
                            .email("carol@email.com")
                            .password(encoder.encode("123456"))
                            .deviceId("device-carol-001")
                            .role(Role.ADMIN)
                            .build()
            );

            userRepository.saveAll(users);
        }

        // Dispositivos
        if (deviceRepository.count() == 0) {
            List<Device> devices = List.of(
                    Device.builder()
                            .deviceName("Celular Alice")
                            .bluetoothAddress("00:11:22:33:44:AA")
                            .wifiDirectAddress("192.168.49.1")
                            .status(DeviceStatus.ONLINE)
                            .totalMessagesSent(0)
                            .totalMessagesReceived(0)
                            .build(),
                    Device.builder()
                            .deviceName("Celular Bob")
                            .bluetoothAddress("00:11:22:33:44:BB")
                            .wifiDirectAddress("192.168.49.2")
                            .status(DeviceStatus.ONLINE)
                            .totalMessagesSent(0)
                            .totalMessagesReceived(0)
                            .build(),
                    Device.builder()
                            .deviceName("Celular Carol")
                            .bluetoothAddress("00:11:22:33:44:CC")
                            .wifiDirectAddress("192.168.49.3")
                            .status(DeviceStatus.OFFLINE)
                            .totalMessagesSent(0)
                            .totalMessagesReceived(0)
                            .build()
            );

            deviceRepository.saveAll(devices);
        }

        // Mensagens
        if (messageRepository.count() == 0) {
            List<Device> devices = deviceRepository.findAll();
            Random random = new Random();

            // Criar uma mensagem de cada tipo
            for (MessageType type : MessageType.values()) {
                Device sender = devices.get(random.nextInt(devices.size()));
                Device recipient;
                do {
                    recipient = devices.get(random.nextInt(devices.size()));
                } while (recipient.equals(sender));

                Message message = Message.builder()
                        .sender(sender)
                        .recipient(recipient)
                        .content("Mensagem do tipo: " + type.name())
                        .timestamp(LocalDateTime.now().minusMinutes(random.nextInt(120)))
                        .delivered(random.nextBoolean())
                        .forwarded(random.nextBoolean())
                        .messageType(type)
                        .build();

                messageRepository.save(message);

                sender.setTotalMessagesSent(sender.getTotalMessagesSent() + 1);
                recipient.setTotalMessagesReceived(recipient.getTotalMessagesReceived() + 1);
                deviceRepository.save(sender);
                deviceRepository.save(recipient);
            }

            // Mensagens adicionais para variedade
            String[] contents = {
                    "Sinal fraco nesta região.",
                    "Preciso de socorro no bairro Novo Horizonte.",
                    "Grupos reunidos no colégio central.",
                    "Enviando kit de primeiros socorros.",
                    "Sem energia, aguardando instruções."
            };

            for (String content : contents) {
                Device sender = devices.get(random.nextInt(devices.size()));
                Device recipient;
                do {
                    recipient = devices.get(random.nextInt(devices.size()));
                } while (recipient.equals(sender));

                Message message = Message.builder()
                        .sender(sender)
                        .recipient(recipient)
                        .content(content)
                        .timestamp(LocalDateTime.now().minusMinutes(random.nextInt(120)))
                        .delivered(random.nextBoolean())
                        .forwarded(random.nextBoolean())
                        .messageType(MessageType.values()[random.nextInt(MessageType.values().length)])
                        .build();

                messageRepository.save(message);

                sender.setTotalMessagesSent(sender.getTotalMessagesSent() + 1);
                recipient.setTotalMessagesReceived(recipient.getTotalMessagesReceived() + 1);
                deviceRepository.save(sender);
                deviceRepository.save(recipient);
            }
        }
    }
}