package com.miacademia.controller;

import com.miacademia.dto.CursoDTO;
import com.miacademia.model.Curso;
import com.miacademia.service.CursoService;
import com.miacademia.service.impl.CursoServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/cursos")
@RequiredArgsConstructor
public class CursoController {

    private final CursoService cursoService;

    //add lombok.config to make use of annotations(ex. Qualifier) in constructors
    @Qualifier("cursoMapper")
    private final ModelMapper modelMapper;

    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping
    public Mono<ResponseEntity<Flux<CursoDTO>>> listar() {

        Flux<CursoDTO> cursos = cursoService.listar().map(this::convertirADTO);

        return Mono.just(ResponseEntity.ok()
                                       .contentType(MediaType.APPLICATION_JSON)
                                       .body(cursos))
                   .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping("/{id}")
    public Mono<ResponseEntity<CursoDTO>> buscar(@PathVariable String id) {
          return cursoService.buscar(id)
                  .map(this::convertirADTO)
                  .map(c -> ResponseEntity.ok()
                          .contentType(MediaType.APPLICATION_JSON)
                          .body(c))
                  .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    //@PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public Mono<ResponseEntity<CursoDTO>> guardar(@Valid @RequestBody CursoDTO curso){

        return cursoService.guardar(convertirADocumento(curso))
                .map(this::convertirADTO)
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public Mono<ResponseEntity<CursoDTO>> actualizar(@PathVariable String id,@RequestBody CursoDTO curso){

        return Mono.just(curso)
                .map(c -> {
                    c.setId(id);
                    return c;
                })
                .flatMap(e -> cursoService.actualizar(id, convertirADocumento(curso)))
                .map(this::convertirADTO)
                .map(e -> ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Boolean>> eliminar(@PathVariable String id){
        return cursoService.eliminar(id)
                .flatMap(respuesta -> {
                    if(respuesta){
                        return Mono.just(ResponseEntity.noContent().build());
                    }else{
                        return Mono.just(ResponseEntity.notFound().build());
                    }
                });
    }

    private CursoDTO convertirADTO(Curso model){
        return modelMapper.map(model, CursoDTO.class);
    }

    private Curso convertirADocumento(CursoDTO dto){
        return modelMapper.map(dto, Curso.class);
    }
}
