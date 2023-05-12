
package com.example.springbootapirestbacked.controller;

        import com.example.springbootapirestbacked.model.Cliente;
        import com.example.springbootapirestbacked.service.IClienteService;
        import org.springframework.core.io.Resource;
        import org.springframework.core.io.UrlResource;
        import jakarta.validation.Valid;
        import org.apache.logging.log4j.LogManager;
        import org.apache.tomcat.util.file.ConfigurationSource;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.core.io.UrlResource;
        import org.springframework.dao.DataAccessException;
        import org.springframework.data.domain.Page;
        import org.springframework.data.domain.PageRequest;
        import org.springframework.data.domain.Pageable;
        import org.springframework.http.HttpHeaders;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.ResponseEntity;
        import org.springframework.validation.BindingResult;
        import org.springframework.web.bind.annotation.*;

        import java.io.File;
        import java.io.IOException;
        import java.net.MalformedURLException;
        import java.nio.file.Files;
        import java.nio.file.Path;
        import java.nio.file.Paths;
        import java.util.*;
        import org.apache.logging.log4j.Logger;
        import org.springframework.web.multipart.MultipartFile;

        import java.util.stream.Collectors;
        import java.util.stream.Stream;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClienteController {
    private static final Logger logger = LogManager.getLogger(ClienteController.class);
    @Autowired
    private IClienteService clienteService;

    @GetMapping("/clientes")
    public List<Cliente> index(){
        return clienteService.findAll();
    }
    @GetMapping("/clientes/page/{page}")
    public Page<Cliente> index2(@PathVariable Integer page){
        Pageable pageable=PageRequest.of(page,4);
        return clienteService.findAll(pageable);
    }

  @GetMapping("/clientes/{id}")
   public ResponseEntity<?> show(@PathVariable Long id) {
       Cliente cliente = null;
       Map<String, Object> response = new HashMap<>();
       try {
           cliente = clienteService.findById(id);
           logger.info("este es el cliente"+cliente);

       } catch (DataAccessException e) {
           logger.error(e);
           response.put("mensaje", "Error al realizar la consulta");
           response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
           return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
       }
       if (cliente == null) {
           response.put("mensaje", "El cliente ID:" .concat(id.toString().concat(" no existe en la base de datos!")));
           return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
       }
       return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
   }


    @PostMapping("/clientes")
    public ResponseEntity<?>  create(@Valid @RequestBody Cliente cliente, BindingResult result){
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()){
            List<String>errors= result.getFieldErrors()
                            .stream()
                                    .map(err -> "El campo'"+err.getField()+"' "+err.getDefaultMessage())
                                            .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        Cliente clienteNew=null;
        try {
           clienteNew= clienteService.save(cliente);
        }catch (DataAccessException e) {
            logger.error(e);
            response.put("mensaje", "Error al realizar el insert en base de datos");
            response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","El cliente ha sido creado con éxito");
        response.put("cliente",clienteNew);
        return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
    }

    @PostMapping("/clientes/upload")
    public  ResponseEntity<?> upload(@RequestParam("archivo")MultipartFile archivo, @RequestParam("id") Long id) {
        Map<String, Object> response = new HashMap<>();
        Cliente cliente = clienteService.findById(id);
        if (!archivo.isEmpty()) {
            String nombreArchivo = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename().replace(" ", "");
            Path rutaArchivo = Paths.get("uploads").resolve(nombreArchivo).toAbsolutePath();
           logger.info(rutaArchivo.toString());
            try {
                Files.copy(archivo.getInputStream(), rutaArchivo);
            } catch (IOException e) {
                response.put("mensaje", "Error al subir la imagen: " + nombreArchivo);
                response.put("error", e.getMessage().concat(":").concat(e.getCause().getMessage()));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            String nombreFotoAnterior = cliente.getFoto();
            if (nombreFotoAnterior != null && nombreFotoAnterior.length() > 0) {
                Path rutaFotoAnterior = Paths.get("uploads").resolve(nombreFotoAnterior).toAbsolutePath();
                File archivoFotoAnterior = rutaFotoAnterior.toFile();
                if (archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()) {
                    archivoFotoAnterior.delete();
                }
            }
            cliente.setFoto(nombreArchivo);
            clienteService.save(cliente);
            response.put("cliente", cliente);
            response.put("mensaje", "Has subido correctamente la imagen: " + nombreArchivo);
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    @GetMapping("uploads/img/{nombreFoto:.+}")
    public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto) {
        Path rutaArchivo = Paths.get("uploads").resolve(nombreFoto).toAbsolutePath();
        logger.info(rutaArchivo.toString());
        Resource recurso;

        try {
            recurso = new UrlResource(rutaArchivo.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        if (!recurso.exists() || !recurso.isReadable()) {
            throw new RuntimeException("Error: No se pudo cargar la imagen " + nombreFoto);
        }
        HttpHeaders cabecera = new HttpHeaders();
        cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"");
        return new ResponseEntity<>(recurso,cabecera, HttpStatus.OK);
    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Cliente cliente) {
        Map<String, Object> response = new HashMap<>();
        Cliente clienteActual = clienteService.findById(id);
        Cliente clienteUpdated = null;
        if (clienteActual == null) {
            response.put("mensaje", "Error: no se pudo editar el cliente con el ID " .concat(id.toString()
                    .concat(" no existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            clienteActual.setNombre(cliente.getNombre());
            clienteActual.setApellido(cliente.getApellido());
            clienteActual.setEmail(cliente.getEmail());
            clienteActual.setCreateAt(cliente.getCreateAt() );
        clienteUpdated= clienteService.save(clienteActual);
        }catch (DataAccessException e){
            logger.error(e);
            response.put("mensaje", "Error al actualizar el clienteen la base de datos");
            response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","El cliente ha sido actualizado con éxito");
        response.put("cliente",clienteUpdated);
        return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
    }

    @DeleteMapping("/clientes/{id}")
    public  ResponseEntity<?> delete(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        Cliente clienteActual = clienteService.findById(id);
        if (clienteActual == null) {
            response.put("mensaje", "Error: no se pudo eliminar el cliente con el ID " .concat(id.toString()
                    .concat(" no existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            String nombreFotoAnterior=clienteActual.getFoto();
            if (nombreFotoAnterior!=null && nombreFotoAnterior.length()>0){
                Path rutaFotoAnterior = Paths.get("uploads").resolve(nombreFotoAnterior).toAbsolutePath();
                File archivoFotoAnterior=rutaFotoAnterior.toFile();
                if (archivoFotoAnterior.exists()&& archivoFotoAnterior.canRead()){
                    archivoFotoAnterior.delete();
                }
            }
            clienteService.delete(id);
        }catch (DataAccessException e){
            logger.error(e);
            response.put("mensaje", "Error al eliminar el cliente en la base de datos");
            response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","El cliente ha sido eliminado con éxito");
        return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
    }
}
