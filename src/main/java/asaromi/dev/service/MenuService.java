package asaromi.dev.service;

import asaromi.dev.entity.Menu;
import asaromi.dev.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {
    @Autowired
    private MenuRepository menuRepository;

    public List<Menu> findMenuBySearchAndIsActive(String search, Integer isActive, Integer page, Integer limit) throws RuntimeException {
        try {
            Integer offset = page > 0 ? (page - 1) * limit : 0;
            return this.menuRepository.findAllBySearchAndIsActive(search, isActive, offset, limit);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while fetching the menu");
        }
    }
}
