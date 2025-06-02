package br.com.fiap.comunicaplus_api_main.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O dispositivo de origem é obrigatório")
    @ManyToOne
    private Device sender;

    @NotNull(message = "O dispositivo de destino é obrigatório")
    @ManyToOne
    private Device recipient;

    @NotBlank(message = "O conteúdo da mensagem é obrigatório")
    @Column(length = 1000)
    private String content;

    @NotNull(message = "A data/hora do envio é obrigatória")
    private LocalDateTime timestamp;

    private boolean delivered;

    private boolean forwarded;

    @NotNull(message = "O tipo da mensagem é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "message_type")
    private MessageType messageType;
}
