package br.com.fiap.comunicaplus_api_main.service;

import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.comunicaplus_api_main.model.Device;
import br.com.fiap.comunicaplus_api_main.model.Message;
import br.com.fiap.comunicaplus_api_main.repository.MessageRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    @Transactional
    @CacheEvict(value = "messages", allEntries = true) // Evict cache ao salvar para manter consistência
    public Message save(Message message) {
        // Exemplo de validação ou lógica antes de salvar pode ser colocada aqui
        // Se quiser, pode validar relacionamentos com Device aqui também
        return messageRepository.save(message);
    }

    @Cacheable("messages")
    public Page<Message> findAll(Pageable pageable) {
        return messageRepository.findAll(pageable);
    }

    public Optional<Message> findById(Long id) {
        return messageRepository.findById(id);
    }

    @Transactional
    @CacheEvict(value = "messages", allEntries = true) // Limpa cache ao deletar
    public void deleteById(Long id) {
        messageRepository.deleteById(id);
    }
}
