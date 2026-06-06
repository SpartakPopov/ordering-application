package com.example.ordering_app.configuration.db;

import com.example.ordering_app.persistence.entity.CategoryEntity;
import com.example.ordering_app.persistence.entity.MenuItemEntity;
import com.example.ordering_app.persistence.impl.CategoryRepositoryJPA;
import com.example.ordering_app.persistence.impl.MenuItemRepositoryJPA;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class DatabaseDataInitializer implements CommandLineRunner {

    private final CategoryRepositoryJPA categoryRepositoryJPA;
    private final MenuItemRepositoryJPA menuItemRepositoryJPA;

    public DatabaseDataInitializer(CategoryRepositoryJPA categoryRepositoryJPA,
                                   MenuItemRepositoryJPA menuItemRepositoryJPA) {
        this.categoryRepositoryJPA = categoryRepositoryJPA;
        this.menuItemRepositoryJPA = menuItemRepositoryJPA;
    }

    @Override
    public void run(String... args) {
        if (categoryRepositoryJPA.count() > 0) return;

        // ── Categories ──────────────────────────────────────────────
        CategoryEntity entrees  = category("Entrées");
        CategoryEntity plats    = category("Plats Principaux");
        CategoryEntity desserts = category("Desserts");
        CategoryEntity boissons = category("Boissons");

        categoryRepositoryJPA.saveAll(List.of(entrees, plats, desserts, boissons));

        // ── Entrées ─────────────────────────────────────────────────
        MenuItemEntity soupeOignon = item(
                "Soupe à l'Oignon",
                8.50,
                entrees,
                "https://www.themealdb.com/images/media/meals/xvrrux1511783685.jpg"
        );
        MenuItemEntity escargots = item(
                "Escargots de Bourgogne",
                13.50,
                entrees,
                "https://www.themealdb.com/images/media/meals/rvtvuw1511190488.jpg"
        );
        MenuItemEntity foieGras = item(
                "Foie Gras Maison",
                17.00,
                entrees,
                "https://www.themealdb.com/images/media/meals/wurrux1468416624.jpg"
        );
        MenuItemEntity saladeNicoise = item(
                "Salade Niçoise",
                10.50,
                entrees,
                "https://www.themealdb.com/images/media/meals/1549542994.jpg"
        );

        // ── Plats Principaux ─────────────────────────────────────────
        MenuItemEntity boeufBourguignon = item(
                "Boeuf Bourguignon",
                23.50,
                plats,
                "https://www.themealdb.com/images/media/meals/sytuqu1511553755.jpg"
        );
        MenuItemEntity confitCanard = item(
                "Confit de Canard",
                25.00,
                plats,
                "https://www.themealdb.com/images/media/meals/wvpvsu1511786158.jpg"
        );
        MenuItemEntity soleMeuniere = item(
                "Sole Meunière",
                27.00,
                plats,
                "https://www.themealdb.com/images/media/meals/1548772327.jpg"
        );
        MenuItemEntity steakFrites = item(
                "Steak Frites",
                21.00,
                plats,
                "https://www.themealdb.com/images/media/meals/vussxq1511882648.jpg"
        );
        MenuItemEntity quicheLorraine = item(
                "Quiche Lorraine",
                14.50,
                plats,
                "https://www.themealdb.com/images/media/meals/yvpuuy1511797244.jpg"
        );
        MenuItemEntity croqueMonsieu = item(
                "Croque Monsieur",
                13.00,
                plats,
                "https://www.themealdb.com/images/media/meals/eqnf3p1779649407.jpg"
        );

        // ── Desserts ─────────────────────────────────────────────────
        MenuItemEntity cremeBrulee = item(
                "Crème Brûlée",
                8.00,
                desserts,
                "https://www.themealdb.com/images/media/meals/uryqru1511798039.jpg"
        );
        MenuItemEntity tarteTatin = item(
                "Tarte Tatin",
                8.50,
                desserts,
                "https://www.themealdb.com/images/media/meals/ryspuw1511786688.jpg"
        );
        MenuItemEntity mousseChocolat = item(
                "Mousse au Chocolat",
                7.50,
                desserts,
                "https://www.themealdb.com/images/media/meals/uttuxy1511382180.jpg"
        );
        MenuItemEntity millefeuille = item(
                "Mille-feuille",
                9.00,
                desserts,
                "https://www.themealdb.com/images/media/meals/tqtywx1468317395.jpg"
        );
        MenuItemEntity profiteroles = item(
                "Profiteroles",
                8.50,
                desserts,
                "https://www.themealdb.com/images/media/meals/xrysxr1483568462.jpg"
        );

        // ── Boissons ─────────────────────────────────────────────────
        MenuItemEntity eauMineral = item(
                "Eau Minérale",
                3.00,
                boissons,
                "https://www.themealdb.com/images/media/meals/1529446137.jpg"
        );
        MenuItemEntity jusOrange = item(
                "Jus d'Orange",
                4.50,
                boissons,
                "https://www.themealdb.com/images/media/meals/jcr46d1614763831.jpg"
        );
        MenuItemEntity cafe = item(
                "Café",
                3.50,
                boissons,
                "https://www.themealdb.com/images/media/meals/twspvx1511784937.jpg"
        );
        MenuItemEntity vinRouge = item(
                "Vin Rouge",
                7.50,
                boissons,
                "https://www.themealdb.com/images/media/meals/zadvgb1699012544.jpg"
        );
        MenuItemEntity champagne = item(
                "Champagne",
                13.00,
                boissons,
                "https://www.themealdb.com/images/media/meals/rxvxrr1511797671.jpg"
        );

        menuItemRepositoryJPA.saveAll(List.of(
                soupeOignon, escargots, foieGras, saladeNicoise,
                boeufBourguignon, confitCanard, soleMeuniere, steakFrites, quicheLorraine, croqueMonsieu,
                cremeBrulee, tarteTatin, mousseChocolat, millefeuille, profiteroles,
                eauMineral, jusOrange, cafe, vinRouge, champagne
        ));
    }

    private CategoryEntity category(String name) {
        CategoryEntity c = new CategoryEntity();
        c.setName(name);
        return c;
    }

    private MenuItemEntity item(String name, double price, CategoryEntity category, String imageUrl) {
        MenuItemEntity e = new MenuItemEntity();
        e.setName(name);
        e.setPrice(price);
        e.setCategory(category);
        e.setImageUrl(imageUrl);
        return e;
    }
}
