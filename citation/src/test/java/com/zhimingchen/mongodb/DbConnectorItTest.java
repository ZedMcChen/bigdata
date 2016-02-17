package com.zhimingchen.mongodb;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

public class DbConnectorItTest {
    private DbConnector conn = DbConnector.createDbConnector("localhost", 27017);
    private DBCollection coll = conn.getCollection("testing", "java");
    
    @Test
    public void shouldInsertAndRetrieveDocument() {
        BasicDBObject doc = new BasicDBObject("name", "myname1")
                .append("email", "zc@mail.com")
                .append("age", 42)
                .append("pos", new BasicDBObject("x", 102).append("y", 232));
        coll.save(doc);
        
        BasicDBObject docToFind = new BasicDBObject("name", "myname1");
        DBCursor cursor = coll.find(docToFind);
        assertThat(cursor.count(), is(1));
        cursor.close();
        
        coll.findAndRemove(docToFind);
        cursor = coll.find(docToFind);
        assertThat(cursor.count(), is(0));
        cursor.close();
    }

    @Test
    public void shouldEnsureIndex() {
        BasicDBObject doc = new BasicDBObject("name", "myname2")
                .append("email", "zc@mail.com")
                .append("age", 42)
                .append("pos", new BasicDBObject("x", 102).append("y", 232));
        coll.save(doc);
        coll.save(doc);

        BasicDBObject docToFind = new BasicDBObject("name", "myname2");
        DBCursor cursor = coll.find(docToFind);
        assertThat(cursor.count(), is(1));
        cursor.close();

        coll.findAndRemove(docToFind);
        cursor = coll.find(docToFind);
        assertThat(cursor.count(), is(0));
        cursor.close();
    }

    @Test
    public void shouldAddItemToArray() {
        BasicDBObject doc = new BasicDBObject("name", "myname3")
                .append("email", "zc@mail.com")
                .append("age", 42);
        
        BasicDBObject addHobby = new BasicDBObject("$addToSet", new BasicDBObject("hobbies", "walking"));
        coll.update(doc, addHobby, true, false);
        
        addHobby = new BasicDBObject("$addToSet", new BasicDBObject("hobbies", "reading"));
        coll.update(doc, addHobby, true, false);
        
        addHobby = new BasicDBObject("$addToSet", new BasicDBObject("hobbies", "walking"));
        coll.update(doc, addHobby, true, false);
        
        DBCursor cursor = coll.find(doc);
        assertThat(cursor.count(), is(1));
        cursor.close();
       
        cursor = coll.find(doc, new BasicDBObject("hobbies", 1));
        assertThat(cursor.size(), is(1));
        
        BasicDBList hobbies = (BasicDBList) cursor.next().get("hobbies");
        assertTrue(hobbies.contains("walking"));
        assertTrue(hobbies.contains("reading"));
        cursor.close();
       
        coll.findAndRemove(doc);
        cursor = coll.find(doc);
        assertThat(cursor.count(), is(0));
        cursor.close();
    }

    @Test
    public void shouldUpdateAnExistingField() {
        BasicDBObject doc = new BasicDBObject("name", "myname4")
                .append("email", "zc@mail.com")
                .append("pos", new BasicDBObject("x", 102).append("y", 232));
        coll.save(doc);

        BasicDBObject docToFind = new BasicDBObject("name", "myname4");
        
        coll.update(docToFind, new BasicDBObject("$set", new BasicDBObject("age", 53)), true, false);        
        
        DBCursor cursor = coll.find(docToFind);
        assertThat(cursor.size(), is(1));
        assertThat(cursor.next().get("age").toString(), is("53"));
        cursor.close();

        coll.update(docToFind, new BasicDBObject("$set", new BasicDBObject("age", 64)), true, false);        
        
        cursor = coll.find(docToFind);
        assertThat(cursor.size(), is(1));
        assertThat(cursor.next().get("age").toString(), is("64"));
        cursor.close();

        coll.findAndRemove(docToFind);
        cursor = coll.find(docToFind);
        assertThat(cursor.count(), is(0));
        cursor.close();
    }

}
