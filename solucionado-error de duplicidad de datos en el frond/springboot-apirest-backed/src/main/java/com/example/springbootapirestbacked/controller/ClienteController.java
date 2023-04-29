
package com.example.springbootapirestbacked.controller;

        import com.example.springbootapirestbacked.model.Cliente;
        import com.example.springbootapirestbacked.repository.ClienteRepository;
        import com.example.springbootapirestbacked.service.IClienteService;
        import jakarta.validation.Valid;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.dao.DataAccessException;
        import org.springframework.data.domain.Page;
        import org.springframework.data.domain.PageRequest;
        import org.springframework.data.domain.Pageable;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.ResponseEntity;
        import org.springframework.validation.BindingResult;
        import org.springframework.validation.FieldError;
        import org.springframework.validation.ObjectError;
        import org.springframework.web.bind.annotation.*;

        import java.util.*;
        import java.util.stream.Collectors;
        import java.util.stream.Stream;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClienteController {

    @Autowired
    private IClienteService clienteService;

    @GetMapping("/clientes")
    public List<Cliente> index(){
        return clienteService.findAll();
    }
    @GetMapping("/clientes/page/{page}")
    public Page<Cliente> index2(@PathVariable Integer page){
        Pageable pageable=PageRequest.of(page,5);
        return clienteService.findAll(pageable);
    }

  @GetMapping("/clientes/{id}")
   public ResponseEntity<?> show(@PathVariable Long id) {
       Cliente cliente = null;
       Map<String, Object> response = new HashMap<>();
       try {
           cliente = clienteService.findById(id);
       } catch (DataAccessException e) {
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
            response.put("mensaje", "Error al realizar el insert en base de datos");
            response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","El cliente ha sido creado con éxito");
        response.put("cliente",clienteNew);
        return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
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
            clienteService.delete(id);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al eliminar el cliente en la base de datos");
            response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","El cliente ha sido eliminado con éxito");
        return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
    }
}
