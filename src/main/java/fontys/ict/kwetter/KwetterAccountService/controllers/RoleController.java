package fontys.ict.kwetter.KwetterAccountService.controllers;

import fontys.ict.kwetter.KwetterAccountService.models.Role;
import fontys.ict.kwetter.KwetterAccountService.repositories.RoleRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

public class RoleController {
    private final RoleRepository roleRepository;

    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @RequestMapping(value = "/{roleId}", method = RequestMethod.GET)
    public @ResponseBody
    Optional<Role> getRole(@PathVariable("roleId") Long roleId) {
        return roleRepository.findById(roleId);
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    List<Role> getAll() {
        return roleRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public @ResponseBody
    Role createRole(@RequestBody Role role){
        return roleRepository.save(role);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public @ResponseBody Role updateRole(@RequestBody Role role){
        return roleRepository.save(role);
    }

    @RequestMapping(method = RequestMethod.DELETE, consumes = "application/json")
    public @ResponseBody void deleteRole(@RequestBody Role role){
        roleRepository.delete(role);
    }
}
