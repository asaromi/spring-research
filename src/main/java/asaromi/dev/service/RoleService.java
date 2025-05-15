package asaromi.dev.service;

import asaromi.dev.dto.RoleRequest;
import asaromi.dev.entity.Role;
import asaromi.dev.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role findActiveRoleById(String id) throws ResponseStatusException {
        try {
            Role role = this.roleRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));

            if (role.getDeletedAt() != null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role has been deactivated");
            }

            return role;
        } catch (ResponseStatusException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "An error occurred while fetching the role"
            );
        }
    }

    public List<Role> findAndSearchRoles(Integer page, Integer limit, String search, Integer isActive) throws ResponseStatusException {
        try {
            Integer offset = page > 0 ? (page - 1) * limit : 0;
            if (isActive != null && isActive != 0 && isActive != 1) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid is_active value");
            }

            Integer isSearching = search != null && !search.isEmpty() ? 1 : 0;
            return this.roleRepository.findAllBySearchAndIsActive(limit, offset, isSearching, search, isActive);
        } catch (ResponseStatusException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "An error occurred while fetching the roles"
            );
        }
    }

    @Transactional
    public Role storeRole(RoleRequest request) throws ResponseStatusException {
        try {
            if (request.getName() == null || request.getName().isEmpty() || request.getName().equals(null)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role name cannot be null");
            }

            Role role = new Role();
            role.setName(request.getName());
            role.setDescription(request.getDescription());
            role.setDeletedAt(null);

            return this.roleRepository.save(role);
        } catch (ResponseStatusException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "An error occurred while storing the role"
            );
        }
    }

    @Transactional
    public Role updateRoleAccess(String id, Map<String, List<String>> request) throws ResponseStatusException {
        try {
            if (request.get("accesses") == null || request.get("accesses").isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Accesses cannot be null");
            }

            Role role = this.roleRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));

            if (role.getDeletedAt() != null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role has been deactivated");
            }

            if (request.get("accesses") == null || (request.get("accesses") != null && request.get("accesses").isEmpty())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Accesses cannot be null");
            }

            // Assuming Menu is a class that represents the access
            return null;
        } catch (ResponseStatusException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "An error occurred while updating the role"
            );
        }
    }
}
