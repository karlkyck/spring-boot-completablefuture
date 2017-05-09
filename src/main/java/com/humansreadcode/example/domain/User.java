package com.humansreadcode.example.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Document(collection = User.COLLECTION_NAME)
public class User {

    public static final String COLLECTION_NAME = "users";
    public static final String FIELD_NAME_FIRST_NAME = "firstName";
    public static final String FIELD_NAME_LAST_NAME = "lastName";
    public static final String FIELD_NAME_EMAIL = "email";

    @Id
    private String id;

    @NotNull
    @Size(max = 50)
    @Field(FIELD_NAME_FIRST_NAME)
    private String firstName;

    @NotNull
    @Size(max = 50)
    @Field(FIELD_NAME_LAST_NAME)
    private String lastName;

    @NotNull
    @Size(max = 254)
    @Field(FIELD_NAME_EMAIL)
    private String email;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final User user = (User) o;

        return id != null ? id.equals(user.id) : user.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
