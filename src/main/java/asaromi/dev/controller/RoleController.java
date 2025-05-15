package asaromi.dev.controller;

import asaromi.dev.dto.RoleRequest;
import asaromi.dev.entity.Role;
import asaromi.dev.dto.ApiResponse;
import asaromi.dev.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RoleController {
    // Define your endpoints here
    @Autowired
    private RoleService roleService;

    @GetMapping(path = "/roles")
    public ResponseEntity<ApiResponse> getRoles(
            @RequestParam(name = "search", required = false, defaultValue = "") String search,
            @RequestParam(name = "is_active", required = false, defaultValue = "") String isActive,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "limit", defaultValue = "10") Integer limit
    ) {
        try {
            List<Role> roles = roleService.findAndSearchRoles(page, limit, search, isActive.isEmpty() ? null : Integer.parseInt(isActive));
            return ResponseEntity.ok(ApiResponse.<List<Role>>builder().data(roles).build());
        } catch (ResponseStatusException e) {
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body(ApiResponse.<String>builder().data(e.getReason()).build());
        }
    }

    @GetMapping(path = "/roles/{roleId}")
    public ResponseEntity<ApiResponse> getRoleById(
            @PathVariable("roleId") String roleId
    ) {
        try {
            Role role = roleService.findActiveRoleById(roleId);
            return ResponseEntity.ok(ApiResponse.<Role>builder().data(role).build());
        } catch (ResponseStatusException e) {
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body(ApiResponse.<String>builder().data(e.getReason()).build());
        }
    }


    @PostMapping(path = "/roles")
    public ResponseEntity<ApiResponse> createRole(
            @RequestBody RoleRequest request
    ) {
        try {
            Role role = roleService.storeRole(request);
            return ResponseEntity.status(201).body(ApiResponse.<Role>builder().data(role).build());
        } catch (ResponseStatusException e) {
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body(ApiResponse.<String>builder().data(e.getReason()).build());
        }
    }

    @PutMapping(path = "/roles/{roleId}/access")
    public ResponseEntity<ApiResponse> assignRoleAccess(
            @PathVariable("roleId") String roleId,
            @RequestBody Map<String, List<String>> body
            ) {
        try {
            Role role = roleService.updateRoleAccess(roleId, body);
            return ResponseEntity.status(201).body(ApiResponse.<Role>builder().data(role).build());
        } catch (ResponseStatusException e) {
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body(ApiResponse.<String>builder().data(e.getReason()).build());
        }
    }
}
