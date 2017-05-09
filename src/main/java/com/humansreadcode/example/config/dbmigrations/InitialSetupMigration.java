package com.humansreadcode.example.config.dbmigrations;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.humansreadcode.example.domain.User;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import org.bson.types.ObjectId;

@ChangeLog(order = "001")
public class InitialSetupMigration {

    private static final String FIELD_NAME_ID = "_id";

    @ChangeSet(order = "01",
               author = "developer",
               id = "01-addUsers")
    public void addUsers(final DB db) {
        final DBCollection userCollection = db.getCollection(User.COLLECTION_NAME);

        userCollection.insert(BasicDBObjectBuilder
                                      .start()
                                      .add(FIELD_NAME_ID, new ObjectId("590f86d92449343841cc2c3f"))
                                      .add(User.FIELD_NAME_FIRST_NAME, "User")
                                      .add(User.FIELD_NAME_LAST_NAME, "One")
                                      .add(User.FIELD_NAME_EMAIL, "user1@example.com")
                                      .get());

        userCollection.insert(BasicDBObjectBuilder
                                      .start()
                                      .add(FIELD_NAME_ID, new ObjectId("590f86d92449343841cc2c40"))
                                      .add(User.FIELD_NAME_FIRST_NAME, "User")
                                      .add(User.FIELD_NAME_LAST_NAME, "Two")
                                      .add(User.FIELD_NAME_EMAIL, "user2@example.com")
                                      .get());
    }
}
