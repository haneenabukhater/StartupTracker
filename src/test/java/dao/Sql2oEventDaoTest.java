package dao;
import model.Event;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import sun.reflect.annotation.ExceptionProxy;


import static org.junit.Assert.*;


public class Sql2oEventDaoTest {
    private Sql2oEventDao eventDao;
    //private Sql2oAttendees
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString,"","");
        eventDao = new Sql2oEventDao(sql2o);
        //attendeeDao = new Sql2oEventDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void eventAddNewEventInstanceWithID_True() throws Exception{
        Event event =setNewEvent();
        eventDao.add(event);
        assertEquals(1, event.getId());
    }
    @Test
    public void getAllEvents() throws Exception{
        Event event = setNewEvent();
        Event event2 = setNewEvent2();
        eventDao.add(event);
        eventDao.add(event2);
        assertEquals(2, eventDao.getAllEvents().size());
    }
    @Test
    public void eventFindById_True() throws Exception{
        Event event = setNewEvent();
        Event event2 = setNewEvent2();
        eventDao.add(event);
        eventDao.add(event2);
        Event foundEvent = eventDao.findById(event.getId());
        assertEquals(event, foundEvent);
    }

    @Test
    public void eventFindByName_True () throws Exception{
        Event event = setNewEvent();
        Event event2 = setNewEvent2();
        eventDao.add(event);
        eventDao.add(event2);
        int id = eventDao.findByName(event.getName());
        assertEquals(1, id);
    }
    @Test
    public void eventUpdateAnEventById_True() throws Exception{
        Event event = setNewEvent();
        Event event2 = setNewEvent2();
        eventDao.add(event);
        eventDao.add(event2);
        int id = event.getId();
        eventDao.update("Edited Event", "This is a description", id);
        assertEquals("Edited Event", eventDao.findById(id).getName());
    }


    //Helper Method
    public Event setNewEvent(){
        return new Event("Event One", "Learn how to code!");
    }
    public Event setNewEvent2(){
        return new Event("Event Two", "Learn how to land the job!");
    }
}