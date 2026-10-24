package com.example.ProjetoBiblioteca.exception;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404: registro não existe
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroResponse> handleNaoEncontrado(RecursoNaoEncontradoException ex) {
        return montar(HttpStatus.NOT_FOUND, ex.getMessage(), null);
    }

    // 400: regra de negócio violada (limite de empréstimos, livro já emprestado...)
    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ErroResponse> handleRegraNegocio(RegraNegocioException ex) {
        return montar(HttpStatus.BAD_REQUEST, ex.getMessage(), null);
    }

    // 400: falha no @Valid dos DTOs (@NotBlank, @Email...)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponse> handleValidacao(MethodArgumentNotValidException ex) {
        Map<String, String> campos = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(erro -> campos.put(erro.getField(), erro.getDefaultMessage()));

        return montar(HttpStatus.BAD_REQUEST, "Dados inválidos", campos);
    }

    // 409: violação de unique ou de chave estrangeira no banco
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErroResponse> handleIntegridade(DataIntegrityViolationException ex) {
        return montar(HttpStatus.CONFLICT,
                "Operação não permitida: o registro viola uma restrição do banco", null);
    }

    // 500: qualquer erro inesperado (não expõe detalhes internos)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponse> handleGenerico(Exception ex) {
        return montar(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor", null);
    }

    private ResponseEntity<ErroResponse> montar(HttpStatus status, String mensagem,
                                                Map<String, String> campos) {
        ErroResponse corpo = new ErroResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                mensagem,
                campos);
        return ResponseEntity.status(status).body(corpo);
    }
}
}
