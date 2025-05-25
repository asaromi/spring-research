package asaromi.dev.controller;

import asaromi.dev.entity.Menu;
import asaromi.dev.service.MenuService;
import asaromi.dev.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @GetMapping(path = "/menus")
    public ResponseEntity<ApiResponse> getSearchableMenu(
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "is_active", required = false, defaultValue = "2") Integer isActive,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "limit", defaultValue = "10") Integer limit
    ) {
        try {
            List<Menu> menuList = this.menuService.findMenuBySearchAndIsActive(search, isActive == 2 ? null : isActive, page, limit);
            return ResponseEntity.ok(ApiResponse.<List<Menu>>builder().data(menuList).build());
        } catch (Exception e) {
            return ResponseEntity
                    .status(500)
                    .body(ApiResponse.<String>builder().data(e.getMessage()).build());
        }
    }
}
