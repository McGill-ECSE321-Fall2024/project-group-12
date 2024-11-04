package ca.mcgill.ecse321.group12.service;

import org.hibernate.dialect.function.array.ArrayToStringFunction;
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
        float aPrice = 1.2f;
        String aName = "Game Name...";
        String aDescription = "Game Description...";
        GameStatus aStatus = GameStatus.Archived;

        Game game = new Game(0, aCategory, aConsole, aInventory, aPrice, aName, aDescription, aStatus);
        when(gameRepository.save(any(Game.class))).thenReturn(game);

        // Act
        Game createdGame = gameService.createGame(aCategory, aConsole, aInventory, aPrice, aName, aDescription, aStatus);

        // Assert
        assertNotNull(createdGame);
        assertEquals(aName, createdGame.getName());
        assertEquals(aCategory.toString(), createdGame.getCategory().toString());
        assertEquals(aConsole.toString(), createdGame.getConsole().toString());
        assertEquals(aInventory, createdGame.getInventory());
        assertEquals(aPrice, createdGame.getPrice());
        assertEquals(aDescription, createdGame.getDescription());
        assertEquals(aStatus.toString(), createdGame.getStatus().toString());
        verify(gameRepository, times(1)).save(game);
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
        assertEquals(aConsole.toString(), createdGame.getConsole().toString());
        assertEquals(aInventory, createdGame.getInventory());
        assertEquals(aPrice, createdGame.getPrice());
        assertEquals(aDescription, createdGame.getDescription());
        assertEquals(aStatus.toString(), createdGame.getStatus().toString());
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
