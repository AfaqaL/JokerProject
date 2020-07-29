package managers;

import com.joker.dao.table.InMemoryTableDao;
import com.joker.game.Card;
import com.joker.game.Table;
import com.joker.helper.CardHelper;
import com.joker.model.Room;
import com.joker.model.User;
import com.joker.model.dto.CardDTO;
import com.joker.model.dto.TableResponse;
import com.joker.model.enums.CardColor;
import com.joker.model.enums.CardValue;
import com.joker.services.game.GameServiceBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceTest {

    @InjectMocks
    private GameServiceBean gameService;

    @Mock
    private InMemoryTableDao tableDao;

    @Mock
    private Table table;

    @Captor
    private ArgumentCaptor<Room> roomCaptor;

    @Captor
    private ArgumentCaptor<Long> tableIdCaptor;

    @Captor
    private ArgumentCaptor<Integer> numCaptor;

    @Captor
    private ArgumentCaptor<Card> cardCaptor;

    @Test
    public void testCreateTable() {
        Room room = new Room();
        room.setId(1);
        room.setPassword("password");
        room.setBayonet(200);
        room.setPlayers(Arrays.asList(
                new User(1, "user1", "mail1", "pass1", 10),
                new User(2, "user2", "mail2", "pass2", 20),
                new User(3, "user3", "mail3", "pass3", 30),
                new User(4, "user4", "mail4", "pass4", 40)));

        gameService.createTable(room);

        verify(tableDao).createTable(roomCaptor.capture());

        Room actual = roomCaptor.getValue();
        assertEquals(room.getId(), actual.getId());
        assertEquals(room.getPassword(), actual.getPassword());
        assertEquals(room.getBayonet(), actual.getBayonet());
        assertEquals(4, actual.getPlayers().size());
        assertArrayEquals(new List[]{room.getPlayers()}, new List[]{actual.getPlayers()});
    }

    @Test
    public void testGetTableThrowsException() {
        when(tableDao.getTable(1)).thenReturn(null);

        assertThrows(NullPointerException.class, () -> gameService.getTable(1, 1));
    }

    @Test
    public void testGetTable() {
        TableResponse expected = new TableResponse();

        when(tableDao.getTable(1)).thenReturn(table);
        when(table.getTable(2)).thenReturn(expected);

        assertSame(expected, gameService.getTable(1, 2));
    }

    @Test
    public void testGetVersion() {
        int expected = 4;

        when(tableDao.getTable(anyLong())).thenReturn(table);
        when(table.getVersion()).thenReturn(expected);

        assertEquals(expected, gameService.getVersion(anyLong()));
    }

    @Test
    public void testDeclareNumber() {
        when(tableDao.getTable(anyLong())).thenReturn(table);

        long tableId = 3;
        int num = 4;
        gameService.declareNumber(tableId, num);

        verify(tableDao).getTable(tableIdCaptor.capture());
        verify(table).declareNumber(numCaptor.capture());

        assertEquals(tableId, tableIdCaptor.getValue().longValue());
        assertEquals(num, numCaptor.getValue().intValue());
    }

    @Test
    public void testPutCard() {
        when(tableDao.getTable(anyLong())).thenReturn(table);

        long tableId = 3;
        CardDTO dto = new CardDTO();
        dto.setColor(CardColor.CLUBS);
        dto.setValue(CardValue.SEVEN);

        Card card = CardHelper.fromDTO(dto);

        gameService.putCard(tableId, dto);

        verify(tableDao).getTable(tableIdCaptor.capture());
        verify(table).putCard(cardCaptor.capture());

        assertEquals(tableId, tableIdCaptor.getValue().longValue());
        assertEquals(card.color, cardCaptor.getValue().color);
        assertEquals(card.value, cardCaptor.getValue().value);
    }

    @Test
    public void testSetSuperiorCard() {
        when(tableDao.getTable(anyLong())).thenReturn(table);

        long tableId = 3;
        CardDTO dto = new CardDTO();
        dto.setColor(CardColor.CLUBS);
        dto.setValue(CardValue.SEVEN);

        Card card = CardHelper.fromDTO(dto);

        gameService.setSuperiorCard(tableId, dto);

        verify(tableDao).getTable(tableIdCaptor.capture());
        verify(table).setSuperiorCard(cardCaptor.capture());

        assertEquals(tableId, tableIdCaptor.getValue().longValue());
        assertEquals(card.color, cardCaptor.getValue().color);
        assertEquals(card.value, cardCaptor.getValue().value);
    }
}
