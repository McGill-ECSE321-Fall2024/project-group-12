package ca.mcgill.ecse321.group12.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.group12.exception.CustomException;
import ca.mcgill.ecse321.group12.model.Game;
import ca.mcgill.ecse321.group12.model.Game.Category;
import ca.mcgill.ecse321.group12.model.Game.Console;
import ca.mcgill.ecse321.group12.model.Game.GameStatus;
import ca.mcgill.ecse321.group12.repository.GameRepository;

@SpringBootTest
public class GameServiceTests {
    @Mock
    private GameRepository gameRepository;
    @InjectMocks
    private GameService gameService;

    @SuppressWarnings("null")
    @Test
    public void testCreateValidGame() {

        // Arrange
        Category aCategory = Category.Action;
        Console aConsole = Console.PC;
        int aInventory = 1;
        float aPrice = 1.0f;
        String aName = "Game Name...";
        String aDescription = "Game Description...";
        GameStatus aStatus = GameStatus.Archived;

        Game game = new Game();
        game.setCategory(aCategory);
        game.setConsole(aConsole);
        game.setInventory(aInventory);
        game.setPrice(aPrice);
        game.setName(aName);
        game.setStatus(aStatus);
        game.setDescription(aDescription);
        when(gameRepository.save(any(Game.class))).thenReturn(game);

        // Act
        Game createdGame = gameService.createGame(aCategory, aConsole, aInventory, aPrice, aName, aDescription, aStatus);

        // Assert
        assertNotNull(createdGame);
        assertEquals(aName, createdGame.getName());
        assertEquals(aCategory, createdGame.getCategory());
        assertEquals(aConsole, createdGame.getConsole());
        assertEquals(aInventory, createdGame.getInventory());
        assertEquals(aPrice, createdGame.getPrice());
        assertEquals(aDescription, createdGame.getDescription());
        assertEquals(aStatus, createdGame.getStatus());
        // should be save(game) instead of save(any(Game.class))
        verify(gameRepository, times(1)).save(any (Game.class));
    }

    @Test
    public void testReadGameByValidId() {
        // Arrange
        int id = 42;
        Category aCategory = Category.Action;
        Console aConsole = Console.PC;
        int aInventory = 1;
        float aPrice = 1.2f;
        String aName = "Game Name...";
        String aDescription = "Game Description...";
        GameStatus aStatus = GameStatus.Archived;

        Game game = new Game(id, aCategory, aConsole, aInventory, aPrice, aName, aDescription, aStatus);

        when(gameRepository.findGameById(id)).thenReturn(game);

        // Act
        Game createdGame = gameService.findGameById(id);

        // Assert
        assertNotNull(createdGame);
        assertEquals(aName, createdGame.getName());
        assertEquals(aCategory, createdGame.getCategory());
        assertEquals(aConsole, createdGame.getConsole());
        assertEquals(aInventory, createdGame.getInventory());
        assertEquals(aPrice, createdGame.getPrice());
        assertEquals(aDescription, createdGame.getDescription());
        assertEquals(aStatus, createdGame.getStatus());
    }

    @Test
    public void testReadGameByInvalidId() {
        // Set up
        int id = 42;

        // Act
        // Assert
        CustomException e = assertThrows(CustomException.class, () -> gameService.findGameById(id));
        assertEquals("There is no game with ID " + id + ".", e.getMessage());
    }

}
