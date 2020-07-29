package managers;

import com.joker.dao.history.HistorySqlDao;
import com.joker.model.TableHistory;
import com.joker.services.history.HistoryServiceBean;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HistoryServiceTest {

    @InjectMocks
    private HistoryServiceBean historyService;

    @Mock
    private HistorySqlDao historyDao;

    @Captor
    private ArgumentCaptor<TableHistory> historyCaptor;


    private static final TableHistory history1 = new TableHistory();

    private static final TableHistory history2 = new TableHistory();

    @BeforeClass
    public static void setUp() {
        history1.setTableId(20);
        history1.setId1(1);
        history1.setId2(2);
        history1.setId3(3);
        history1.setId4(4);

        history1.setScore1(10.1);
        history1.setScore2(3.5);
        history1.setScore3(8.7);
        history1.setScore4(11.0);

        history2.setTableId(22);
        history2.setId1(3);
        history2.setId2(4);
        history2.setId3(1);
        history2.setId4(23);

        history2.setScore1(4.5);
        history2.setScore2(2.4);
        history2.setScore3(7.7);
        history2.setScore4(1.2);
    }


    @Test
    public void testAddHistory() {
        historyService.addHistory(history1);

        verify(historyDao, times(1)).addHistory(historyCaptor.capture());

        assertEquals(history1, historyCaptor.getValue());
    }

    @Test
    public void testAddHistoryReturnsTrue() {
        when(historyDao.addHistory(any(TableHistory.class))).thenReturn(true);

        assertTrue(historyService.addHistory(history1));
    }

    @Test
    public void testGetUserHistoryReturnsNull() {
        when(historyDao.getUserHistory(anyLong())).thenReturn(null);

        assertNull(historyService.getUserHistory(anyLong()));
    }

    @Test
    public void testGetUserHistory() {
        List<TableHistory> expected = new ArrayList<>();
        expected.add(history1);
        expected.add(history2);

        when(historyDao.getUserHistory(anyLong())).thenReturn(expected);

        assertArrayEquals(new List[]{expected}, new List[]{historyService.getUserHistory(anyLong())});
    }
}
