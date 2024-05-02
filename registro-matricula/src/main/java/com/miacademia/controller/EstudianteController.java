package com.miacademia.controller;

import com.miacademia.dto.CursoDTO;
import com.miacademia.dto.EstudianteDTO;
import com.miacademia.model.Curso;
import com.miacademia.model.Estudiante;
import com.miacademia.service.EstudianteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/estudiantes")
@RequiredArgsConstructor
public class EstudianteController {

    private final EstudianteService estudianteService;

    private final ModelMapper modelMapper;

    @GetMapping
    public Mono<ResponseEntity<Flux<EstudianteDTO>>> listar(){
        Flux<EstudianteDTO> estudiantes = estudianteService.listar().map(this::convertirADTO);

        return Mono.just(ResponseEntity.ok()
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body(estudiantes))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<EstudianteDTO>> guardar(@Valid @RequestBody EstudianteDTO estudianteDTO){
        return estudianteService.guardar(this.convertirADocumento(estudianteDTO))
                .map(this::convertirADTO)
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                ).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<EstudianteDTO>> actualizar(@PathVariable String id, @Valid @RequestBody EstudianteDTO estudianteDTO){
        return Mono.just(estudianteDTO)
                .map(e -> {
                    e.setId(id);
                    return e;
                })
                .flatMap(e -> estudianteService.actualizar(id,convertirADocumento(estudianteDTO)))
                .map(this::convertirADTO)
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<EstudianteDTO>> buscar(@PathVariable String id){
        return estudianteService.buscar(id)
                .map(this::convertirADTO)
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public Mono<ResponseEntity<Boolean>> eliminar(@PathVariable String id){
        return estudianteService.eliminar(id)
                .flatMap(respuesta ->{
                    if(respuesta){
                        return Mono.just(ResponseEntity.noContent().build());
                    }else{
                        return Mono.just(ResponseEntity.notFound().build());
                    }
                });
    }

    private EstudianteDTO convertirADTO(Estudiante model){
        return modelMapper.map(model, EstudianteDTO.class);
    }

    private Estudiante convertirADocumento(EstudianteDTO dto){
        return modelMapper.map(dto, Estudiante.class);
    }
}
