package com.dwes.api.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.dwes.api.entidades.Categoria;
import com.dwes.api.servicios.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    @Operation(summary = "Obtener todas las categorías paginadas", description = "Devuelve una lista paginada de todas las categorías")
    @ApiResponse(responseCode = "200", description = "Lista de categorías obtenida exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class)))
    public ResponseEntity<Page<Categoria>> getAllCategorias(
            @Parameter(description = "Página solicitada. Página por defecto es 0.", in = ParameterIn.QUERY) @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Número de elementos por página. Valor por defecto es 10.", in = ParameterIn.QUERY) @RequestParam(defaultValue = "10") int size) {
        Page<Categoria> categorias = categoriaService.findAll(PageRequest.of(page, size));
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una categoría por ID", description = "Devuelve una categoría según el ID proporcionado")
    @ApiResponse(responseCode = "200", description = "Categoría encontrada",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class)))
    @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    public ResponseEntity<Categoria> getCategoriaById(
            @Parameter(description = "ID de la categoría a buscar", required = true) @PathVariable Long id) {
        Optional<Categoria> categoriaOptional = categoriaService.findById(id);

        return categoriaOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear una nueva categoría", description = "Crea y devuelve una nueva categoría")
    @ApiResponse(responseCode = "201", description = "Categoría creada con éxito",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class)))
    @ApiResponse(responseCode = "400", description = "Datos proporcionados para la nueva categoría son inválidos")
    public ResponseEntity<Categoria> crearCategoria(@Valid @RequestBody Categoria nuevaCategoria) {
        Categoria categoriaCreada = categoriaService.save(nuevaCategoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaCreada);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una categoría existente", description = "Actualiza y devuelve la categoría actualizada según el ID proporcionado")
    @ApiResponse(responseCode = "200", description = "Categoría actualizada correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class)))
    @ApiResponse(responseCode = "404", description = "Categoría no encontrada para actualizar")
    public ResponseEntity<Categoria> actualizarCategoria(@PathVariable Long id, @Valid @RequestBody Categoria categoria) {
        if (!categoriaService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        categoria.setId(id);
        Categoria categoriaActualizada = categoriaService.save(categoria);
        return ResponseEntity.ok(categoriaActualizada);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una categoría por ID", description = "Elimina una categoría según el ID proporcionado")
    @ApiResponse(responseCode = "204", description = "Categoría eliminada correctamente")
    @ApiResponse(responseCode = "404", description = "Categoría no encontrada para eliminar")
    public ResponseEntity<Void> borrarCategoria(@PathVariable Long id) {
        if (!categoriaService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        categoriaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
